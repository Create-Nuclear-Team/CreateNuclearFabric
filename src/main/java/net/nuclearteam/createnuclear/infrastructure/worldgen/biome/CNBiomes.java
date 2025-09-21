package net.nuclearteam.createnuclear.infrastructure.worldgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.nuclearteam.createnuclear.CreateNuclear;
import org.jetbrains.annotations.NotNull;

public class CNBiomes {
    public static final class Irradiated {
        public static final ResourceKey<Biome> PLAIN = key("irradiated_plain");
    }

    public static final ResourceKey<Biome> SPACE = ResourceKey.create(Registries.BIOME, new ResourceLocation("space"));

    public static Biome createSpaceBiome(HolderGetter<PlacedFeature> holderGetter, HolderGetter<ConfiguredWorldCarver<?>> holder2) {
        Biome.BiomeBuilder builder = new Biome.BiomeBuilder();
        MobSpawnSettings.Builder spawn = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder genSettings = new BiomeGenerationSettings.Builder(holderGetter, holder2);
        BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder();
        effects.fogColor(0).waterColor(4159204).waterFogColor(329011).skyColor(0);
        return builder
                .downfall(0)
                .temperature(1)
                .specialEffects(effects.build())
                .mobSpawnSettings(spawn.build())
                .hasPrecipitation(false)
                .generationSettings(genSettings.build())
                .temperatureAdjustment(Biome.TemperatureModifier.NONE).build();
    }

    public static void bootstrapRegistries(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> featureLookup = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carverLookup = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(SPACE, createSpaceBiome(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER)));
        context.register(Irradiated.PLAIN, IrradiatedBiomes.createPlain(featureLookup, carverLookup));
    }

    public static @NotNull ResourceKey<Biome> key(String id) {
        return ResourceKey.create(Registries.BIOME, CreateNuclear.asResource(id));
    }
}
