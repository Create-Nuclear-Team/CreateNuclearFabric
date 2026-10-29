package net.nuclearteam.createnuclear.foundation.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.FluidState;
import net.nuclearteam.createnuclear.CNTags.CNFluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Uranium is tagged as lava for its physics, but striders must not treat it as lava
@Mixin(Strider.class)
public abstract class StriderUraniumMixin {

    @Inject(method = "canStandOnFluid", at = @At("HEAD"), cancellable = true)
    private void CN$cannotStandOnUranium(FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        if (fluidState.is(CNFluidTags.URANIUM.tag))
            cir.setReturnValue(false);
    }

    // Don't float back up to the surface of uranium
    @Inject(method = "floatStrider", at = @At("HEAD"), cancellable = true)
    private void CN$noFloatInUranium(CallbackInfo ci) {
        Strider self = (Strider) (Object) this;
        if (self.level().getFluidState(self.blockPosition()).is(CNFluidTags.URANIUM.tag))
            ci.cancel();
    }

    // Don't wander towards uranium
    @Inject(method = "getWalkTargetValue", at = @At("HEAD"), cancellable = true)
    private void CN$avoidUranium(BlockPos pos, LevelReader level, CallbackInfoReturnable<Float> cir) {
        if (level.getFluidState(pos).is(CNFluidTags.URANIUM.tag))
            cir.setReturnValue(0.0F);
    }
}
