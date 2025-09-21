package net.nuclearteam.createnuclear.infrastructure.worldgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.nuclearteam.createnuclear.CNEntityType;

public class IrradiatedBiomes {
    public static Biome createPlain(HolderGetter<PlacedFeature> featureLookup, HolderGetter<ConfiguredWorldCarver<?>> carverLookup) {
        return IrradiatedBiomes.irradiated(featureLookup, carverLookup, new BiomeGenerationSettings.Builder(featureLookup, carverLookup));
    }

    public static void monsters(MobSpawnSettings.Builder builder, int zombieWeight, int zombieVillagerWeight, int skeletonWeight) {
//        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CNEntityType.IRRADIATED_CAT.get(), 100, 2, 4));
//        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CNEntityType.IRRADIATED_CHICKEN.get(), 100, 1, 4));
//        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CNEntityType.IRRADIATED_WOLF.get(), 100, 2, 4));
    }

    public static void addDefaultIrradiatedOres(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_STRUCTURES, MiscOverworldPlacements.BLUE_ICE);
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE);
    }

    public static void addDefaultSoftDisks(BiomeGenerationSettings.Builder builder) {
        // builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, GCOrePlacedFeatures.BASALT_DISK_MOON);
        builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.VOID_START_PLATFORM);
    }
    public static Biome irradiated(HolderGetter<PlacedFeature> featureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter, BiomeGenerationSettings.Builder generation) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeSpecialEffects.Builder effectBuilder = new BiomeSpecialEffects.Builder();
        effectBuilder.waterColor(4159204)
                .waterFogColor(329011)
                .fogColor(329011)
                .skyColor(0)
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                ;

        IrradiatedBiomes.addDefaultIrradiatedOres(generation);
        IrradiatedBiomes.addDefaultSoftDisks(generation);
        IrradiatedBiomes.monsters(spawnBuilder, 95,5,100);
//        generation.addCarver(GenerationStep.Carving.AIR, )

        return new Biome.BiomeBuilder()
                .mobSpawnSettings(spawnBuilder.build())
                .hasPrecipitation(false)
                .temperature(2f)
                .downfall(0f)
                .specialEffects(effectBuilder.build())
                .generationSettings(generation.build())
                .build();
    }
}
