package net.nuclearteam.createnuclear.foundation.events;

import io.github.fabricators_of_create.porting_lib.event.client.FogEvents;
import io.github.fabricators_of_create.porting_lib.event.client.FogEvents.ColorData;
import io.github.fabricators_of_create.porting_lib.event.client.FogEvents.FogData;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import com.mojang.blaze3d.shaders.FogShape;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.nuclearteam.createnuclear.CNFluids;
import org.jetbrains.annotations.Nullable;

/**
 * Tints and thickens the fog while the camera is submerged in one of the mod's fluids,
 * mirroring the fog color/distance of the NeoForge TintedFluidType definitions.
 */
public class CNFluidFogHandler {

    private enum FluidFog {
        URANIUM(CNFluids.URANIUM, 0x38FF08, 1f / 32f),
        THORIUM(CNFluids.THORIUM, 0x38F9FF, 1f / 32f),
        NITROGEN(CNFluids.LIQUID_NITROGEN, 0x23ECD5, 1f / 16f),
        ;

        private final FluidEntry<?> fluid;
        private final int color;
        private final float distanceModifier;

        FluidFog(FluidEntry<?> fluid, int color, float distanceModifier) {
            this.fluid = fluid;
            this.color = color;
            this.distanceModifier = distanceModifier;
        }
    }

    public static void register() {
        FogEvents.SET_COLOR.register(CNFluidFogHandler::onFogColor);
        FogEvents.RENDER_FOG.register(CNFluidFogHandler::onRenderFog);
    }

    private static void onFogColor(ColorData event, float partialTicks) {
        FluidFog fog = getSubmergedFluid(event.getCamera());
        if (fog == null) return;

        event.setRed((fog.color >> 16 & 0xFF) / 255F);
        event.setGreen((fog.color >> 8 & 0xFF) / 255F);
        event.setBlue((fog.color & 0xFF) / 255F);
    }

    private static boolean onRenderFog(FogMode mode, FogType type, Camera camera, float partialTick, float renderDistance,
                                       float nearDistance, float farDistance, FogShape shape, FogData fogData) {
        FluidFog fog = getSubmergedFluid(camera);
        if (fog == null) return false;

        fogData.scaleFarPlaneDistance(fog.distanceModifier);
        return true;
    }

    @Nullable
    private static FluidFog getSubmergedFluid(Camera camera) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return null;

        BlockPos blockPos = camera.getBlockPosition();
        FluidState fluidState = level.getFluidState(blockPos);
        if (camera.getPosition().y >= blockPos.getY() + fluidState.getHeight(level, blockPos)) return null;

        Fluid fluid = fluidState.getType();
        for (FluidFog fog : FluidFog.values()) {
            if (fog.fluid.get().isSame(fluid)) return fog;
        }
        return null;
    }
}
