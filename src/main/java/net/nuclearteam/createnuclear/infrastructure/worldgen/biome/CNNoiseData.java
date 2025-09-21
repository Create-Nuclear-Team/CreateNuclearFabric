package net.nuclearteam.createnuclear.infrastructure.worldgen.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise.NoiseParameters;
import net.nuclearteam.createnuclear.CreateNuclear;

public class CNNoiseData {
    public static final ResourceKey<NoiseParameters> EROSION = createKey("irradiated/erosion");
    public static final ResourceKey<NoiseParameters> BASALT_MARE = createKey("irradiated/basalt_mare");
    public static final ResourceKey<NoiseParameters> BASALT_MARE_HEIGHT = createKey("irradiated/basalt_mare_height");

    private static ResourceKey<NoiseParameters> createKey(String id) {
        return ResourceKey.create(Registries.NOISE, CreateNuclear.asResource(id));
    }

    public static void bootstrapRegistries(BootstapContext<NoiseParameters> context) {
//        register(context, EROSION, -11, 1, 1, 0, 1, 1);
//        register(context, BASALT_MARE, 5, 0, 0.1, 0.2, 0.1, 0, 0, 0, 0);
//        register(context, BASALT_MARE_HEIGHT, -12, 0.3);
    }

    private static void register(
            BootstapContext<NoiseParameters> context,
            ResourceKey<NoiseParameters> key,
            int firstOctave,
            double amplitude,
            double... otherAmplitudes
    ) {
        context.register(key, new NoiseParameters(firstOctave, amplitude, otherAmplitudes));
    }
}
