package net.nuclearteam.createnuclear.infrastructure.worldgen.biome.surfacerule;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.nuclearteam.createnuclear.CNBlocks;
import net.nuclearteam.createnuclear.infrastructure.worldgen.biome.CNBiomes;
import org.jetbrains.annotations.NotNull;

public class IrradiatedSurfaceRules {
    private static final SurfaceRules.RuleSource BEDROCK = block(Blocks.BEDROCK);
    public static final SurfaceRules.RuleSource HARD_ROCK = block(CNBlocks.ENRICHED_SOUL_SOIL.get());

    public static final SurfaceRules.RuleSource IRRADIATED = createDefaultRule();

    public static @NotNull SurfaceRules.RuleSource createDefaultRule() {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        SurfaceRules.isBiome(CNBiomes.Irradiated.PLAIN),
                        HARD_ROCK
                ),
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), BEDROCK)
        );
    }

    private static @NotNull SurfaceRules.RuleSource block(@NotNull Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
