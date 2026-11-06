package net.nuclearteam.createnuclear.foundation.mixin;

import net.minecraft.world.entity.item.ItemEntity;
import net.nuclearteam.createnuclear.CNTags.CNFluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityUraniumMixin {

    @Shadow
    protected abstract void setUnderwaterMovement();

    // Uranium is tagged as lava: make items float on it like they do on water
    @Inject(method = "setUnderLavaMovement", at = @At("HEAD"), cancellable = true)
    private void CN$floatOnUranium(CallbackInfo ci) {
        ItemEntity self = (ItemEntity) (Object) this;
        if (self.getFluidHeight(CNFluidTags.URANIUM.tag) > 0) {
            setUnderwaterMovement();
            ci.cancel();
        }
    }
}
