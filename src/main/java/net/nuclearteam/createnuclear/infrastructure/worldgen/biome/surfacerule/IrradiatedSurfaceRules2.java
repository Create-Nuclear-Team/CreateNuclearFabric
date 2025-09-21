package net.nuclearteam.createnuclear.infrastructure.worldgen.biome.surfacerule;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.minecraft.world.level.levelgen.SurfaceRules.ConditionSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.nuclearteam.createnuclear.CNBlocks;
import net.nuclearteam.createnuclear.CreateNuclear;
import net.nuclearteam.createnuclear.infrastructure.worldgen.biome.CNBiomes;
import net.nuclearteam.createnuclear.infrastructure.worldgen.biome.CNNoiseData;
import org.jetbrains.annotations.NotNull;

public class IrradiatedSurfaceRules2 {
    private static final ConditionSource IS_MARE = biome(CNBiomes.Irradiated.PLAIN);
    private static final ConditionSource IS_HIGHLANDS = biome();

    private static final RuleSource BEDROCK = block(Blocks.BEDROCK);
    private static final RuleSource LUNASLATE = block(CNBlocks.ENRICHED_SOUL_SOIL.get());
    private static final RuleSource MOON_DIRT = block(CNBlocks.STEEL_BLOCK.get());
    private static final RuleSource MOON_ROCK = block(CNBlocks.LEAD_BLOCK.get());
    private static final RuleSource MOON_TURF = block(CNBlocks.LEAD_ORE.get());
    private static final RuleSource MOON_BASALT = block(CNBlocks.RAW_LEAD_BLOCK.get());
    private static final RuleSource DEBUG_STATE = block(CNBlocks.REACTOR_CASING.get());

    private static final RuleSource SECONDARY_MATERIAL = SurfaceRules.sequence(
            SurfaceRules.ifTrue(IS_MARE, MOON_BASALT),
            SurfaceRules.ifTrue(IS_HIGHLANDS, MOON_DIRT)
    );
    private static final RuleSource SURFACE_MATERIAL = SurfaceRules.sequence(
            SurfaceRules.ifTrue(IS_MARE, MOON_BASALT),
            SurfaceRules.ifTrue(IS_HIGHLANDS, MOON_TURF)
    );
    private static final RuleSource SURFACE_GENERATION = SurfaceRules.sequence(
            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SURFACE_MATERIAL),
            SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SECONDARY_MATERIAL)
    );

    public static final RuleSource DEFAULT_RULE = createDefaultRule();

    public static @NotNull RuleSource createDefaultRule() {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(CNBiomes.Irradiated.PLAIN),
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(
                                        SurfaceRules.noiseCondition(CNNoiseData.EROSION, 0.035, 0.0465),
                                        MOON_TURF
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.noiseCondition(CNNoiseData.EROSION, 0.039, 0.0545),
                                        MOON_ROCK
                                ),
                                SurfaceRules.ifTrue(
                                        SurfaceRules.noiseCondition(CNNoiseData.EROSION, 0.0545, 0.069),
                                        MOON_BASALT
                                )
                        )
                ),

                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK),
                SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), SURFACE_GENERATION),
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("lunaslate", VerticalAnchor.absolute(-4), VerticalAnchor.absolute(4)), LUNASLATE)
        );
    }

    private static @NotNull RuleSource block(@NotNull Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    public static SurfaceRules.@NotNull ConditionSource biome(@NotNull TagKey<Biome> biome) {
        return new BiomeTagRule(biome);
    }

    @SafeVarargs
    public static SurfaceRules.@NotNull ConditionSource biome(@NotNull ResourceKey<Biome> @NotNull ... keys) {
        return SurfaceRules.isBiome(keys);
    }

    public static void register() {
        Registry.register(BuiltInRegistries.MATERIAL_RULE, CreateNuclear.asResource("irradiated_surface"), Codec.unit(DEFAULT_RULE));
    }
}
