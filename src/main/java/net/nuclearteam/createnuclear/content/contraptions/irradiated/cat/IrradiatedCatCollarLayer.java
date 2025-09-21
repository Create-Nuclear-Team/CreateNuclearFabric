package net.nuclearteam.createnuclear.content.contraptions.irradiated.cat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@SuppressWarnings("unused")
public class IrradiatedCatCollarLayer extends RenderLayer<IrradiatedCat, IrradiatedCatModel<IrradiatedCat>> {
    private static final ResourceLocation CAT_COLLAR_LOCATION = new ResourceLocation("textures/entity/cat/cat_collar.png");
    private final IrradiatedCatModel<IrradiatedCat> irradiatedCatModel;

    public IrradiatedCatCollarLayer(RenderLayerParent<IrradiatedCat, IrradiatedCatModel<IrradiatedCat>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.irradiatedCatModel = new IrradiatedCatModel<>(modelSet.bakeLayer(ModelLayers.CAT_COLLAR));
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, IrradiatedCat livingEntity, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        if (livingEntity.isTame()) {
            float[] fs = livingEntity.getCollarColor().getTextureDiffuseColors();
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.irradiatedCatModel, CAT_COLLAR_LOCATION, matrixStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, partialTick, ageInTicks, netHeadYaw, headPitch, fs[0], fs[1], fs[2]);
        }
    }
}
