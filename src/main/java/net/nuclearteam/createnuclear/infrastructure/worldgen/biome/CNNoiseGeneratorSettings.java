package net.nuclearteam.createnuclear.infrastructure.worldgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.nuclearteam.createnuclear.CNBlocks;
import net.nuclearteam.createnuclear.CreateNuclear;
import net.nuclearteam.createnuclear.infrastructure.worldgen.biome.surfacerule.IrradiatedSurfaceRules;
import net.nuclearteam.createnuclear.infrastructure.worldgen.biome.surfacerule.IrradiatedSurfaceRules2;
import org.jetbrains.annotations.NotNull;

public class CNNoiseGeneratorSettings {
    public static final ResourceKey<NoiseGeneratorSettings> IRRADIATED = key("irradiated_noise");

    public static void bootstrapRegistries(BootstapContext<NoiseGeneratorSettings> context) {
        HolderGetter<DensityFunction> densityLookup = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<NormalNoise.NoiseParameters> noiseLookup = context.lookup(Registries.NOISE);

        context.register(IRRADIATED, new NoiseGeneratorSettings(
                NoiseSettings.create(-32, 256, 1, 2),
                CNBlocks.STEEL_BLOCK.get().defaultBlockState(),
                Blocks.AIR.defaultBlockState(),
                CNNoiseGeneratorSettings.irradiated(densityLookup, noiseLookup),
                IrradiatedSurfaceRules2.DEFAULT_RULE,
                new OverworldBiomeBuilder().spawnTarget(),
                32,
                false,
                false,
                false,
                false
        ));
    }

    public static NoiseRouter irradiated(HolderGetter<DensityFunction> densityLookup, HolderGetter<NormalNoise.NoiseParameters> noiseLookup) {
        DensityFunction shiftX = CNDensityFunctions.getFunction(densityLookup, NoiseRouterData.SHIFT_X);
        DensityFunction shiftZ = CNDensityFunctions.getFunction(densityLookup, NoiseRouterData.SHIFT_Z);
        DensityFunction y = CNDensityFunctions.getFunction(densityLookup, NoiseRouterData.Y);
        return new NoiseRouter(
                DensityFunctions.constant(1), // barrierNoise
                DensityFunctions.zero(), // fluidLevelFloodednessNoise
                DensityFunctions.zero(), // fluidLevelSpreadNoise
                DensityFunctions.zero(), // lavaNoise
                DensityFunctions.shiftedNoise2d(
                        shiftX, shiftZ, 0.25, noiseLookup.getOrThrow(Noises.TEMPERATURE)
                ), // temperature
                DensityFunctions.shiftedNoise2d(
                        shiftX, shiftZ, 0, noiseLookup.getOrThrow(Noises.VEGETATION)
                ), // vegetation
                CNDensityFunctions.getFunction(densityLookup, NoiseRouterData.CONTINENTS), // continents
                CNDensityFunctions.getFunction(densityLookup, CNDensityFunctions.Irradiated.EROSION), // erosion
                CNDensityFunctions.getFunction(densityLookup, NoiseRouterData.DEPTH), // depth
                CNDensityFunctions.getFunction(densityLookup, NoiseRouterData.RIDGES), // ridges
                DensityFunctions.add(
                        DensityFunctions.constant(0.1171875),
                        DensityFunctions.mul(
                                DensityFunctions.yClampedGradient(
                                        -30, -40, 0, 1
                                ),
                                DensityFunctions.add(
                                        DensityFunctions.constant(-0.1171875),
                                        DensityFunctions.add(
                                                DensityFunctions.constant(-0.078125),
                                                DensityFunctions.mul(
                                                        DensityFunctions.yClampedGradient(
                                                                240, 256, 1, 0
                                                        ),
                                                        DensityFunctions.add(
                                                                DensityFunctions.constant(0.078125),
                                                                DensityFunctions.add(
                                                                        DensityFunctions.constant(-0.703125),
                                                                        DensityFunctions.mul(
                                                                                DensityFunctions.constant(4),
                                                                                DensityFunctions.mul(
                                                                                        CNDensityFunctions.getFunction(densityLookup, NoiseRouterData.DEPTH),
                                                                                        DensityFunctions.cache2d(CNDensityFunctions.getFunction(densityLookup, NoiseRouterData.FACTOR))
                                                                                ).quarterNegative()
                                                                        )
                                                                ).clamp(-30, 64)
                                                        )
                                                )
                                        )
                                )
                        )
                ), // initialDensityWithoutJaggedness
                DensityFunctions.blendDensity(CNDensityFunctions.getFunction(densityLookup, CNDensityFunctions.Irradiated.FINAL_DENSITY)), // finalDensity
                DensityFunctions.zero(), // veinToggle
                DensityFunctions.zero(), // veinRidged
                DensityFunctions.zero()  // veinGap
        );
    }

    private static @NotNull ResourceKey<NoiseGeneratorSettings> key(String id) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, CreateNuclear.asResource(id));
    }
}
