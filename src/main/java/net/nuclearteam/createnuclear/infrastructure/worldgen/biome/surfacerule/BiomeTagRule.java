package net.nuclearteam.createnuclear.infrastructure.worldgen.biome.surfacerule;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.jetbrains.annotations.NotNull;

public record BiomeTagRule(@NotNull TagKey<Biome> tag) implements SurfaceRules.ConditionSource {
    private static final MapCodec<BiomeTagRule> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(TagKey.codec(Registries.BIOME).fieldOf("tag").forGetter(rule -> rule.tag)).apply(instance, BiomeTagRule::new));

    @Override
    public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
        return KeyDispatchDataCodec.of(BiomeTagRule.CODEC);
    }

    @Override
    public SurfaceRules.Condition apply(SurfaceRules.Context context) {
        return null;
    }

    private class Predicate extends SurfaceRules.LazyYCondition {
        protected Predicate(SurfaceRules.Context context) {
            super(context);
        }

        @Override
        protected boolean compute() {
            return this.context.biome.get().is(BiomeTagRule.this.tag);
        }
    }
}