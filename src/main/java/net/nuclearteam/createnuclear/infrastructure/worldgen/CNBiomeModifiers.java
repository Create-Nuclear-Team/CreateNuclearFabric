package net.nuclearteam.createnuclear.infrastructure.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.function.Predicate;

public class CNBiomeModifiers {
    public static void bootstrap() {
        Predicate<BiomeSelectionContext> isOverworld = BiomeSelectors.foundInOverworld();

        addOre(isOverworld, CNPlacedFeatures.URANIUM_ORE);
        addOre(isOverworld, CNPlacedFeatures.LEAD_ORE);
        addOre(isOverworld, CNPlacedFeatures.STRIATED_ORES_OVERWORLD);
    }

    private static void addOre(Predicate<BiomeSelectionContext> test, ResourceKey<PlacedFeature> feature) {
        BiomeModifications.addFeature(test, Decoration.UNDERGROUND_ORES, feature);
    }
}
