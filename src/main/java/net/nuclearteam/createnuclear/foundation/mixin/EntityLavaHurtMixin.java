package net.nuclearteam.createnuclear.foundation.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.nuclearteam.createnuclear.CNTags.CNFluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityLavaHurtMixin {

    @Shadow
    public abstract Level level();

    @Shadow
    public abstract AABB getBoundingBox();

    // Uranium is tagged as lava to get lava physics, but it must not set entities on fire:
    // skip the lava burn unless the entity is also touching a real (non-uranium) lava.
    @Inject(method = "lavaHurt", at = @At("HEAD"), cancellable = true)
    private void CN$skipLavaHurtInUranium(CallbackInfo ci) {
        AABB box = getBoundingBox().deflate(0.001);
        Level level = level();
        boolean inUranium = false;
        for (BlockPos pos : BlockPos.betweenClosed(
                (int) Math.floor(box.minX), (int) Math.floor(box.minY), (int) Math.floor(box.minZ),
                (int) Math.floor(box.maxX), (int) Math.floor(box.maxY), (int) Math.floor(box.maxZ))) {
            FluidState fluid = level.getFluidState(pos);
            if (!fluid.is(FluidTags.LAVA))
                continue;
            if (!fluid.is(CNFluidTags.URANIUM.tag))
                return;
            inUranium = true;
        }
        if (inUranium)
            ci.cancel();
    }
}
