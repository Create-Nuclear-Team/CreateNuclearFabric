package net.nuclearteam.createnuclear.infrastructure.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.nuclearteam.createnuclear.infrastructure.config.CNConfigs;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ConfigPlacementFilter extends PlacementFilter {
    public static final ConfigPlacementFilter INSTANCE = new ConfigPlacementFilter();
    public static final Codec<ConfigPlacementFilter> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos pos) {
        return !CNConfigs.common().worldGen.disable.get();
    }

    @Override
    public PlacementModifierType<?> type() {
        return CNPlacementModifiers.CONFIG_FILTER.get();
    }
}
