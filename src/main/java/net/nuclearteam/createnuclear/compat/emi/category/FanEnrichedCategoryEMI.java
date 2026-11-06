package net.nuclearteam.createnuclear.compat.emi.category;

import com.simibubi.create.compat.emi.CreateEmiAnimations;
import com.simibubi.create.compat.emi.recipes.fan.FanEmiRecipe;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import net.createmod.catnip.gui.element.GuiGameElement;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.nuclearteam.createnuclear.CNBlocks;
import net.nuclearteam.createnuclear.compat.emi.CNEmi;
import net.nuclearteam.createnuclear.content.kinetics.fan.processing.EnrichedRecipe;

public class FanEnrichedCategoryEMI extends FanEmiRecipe.MultiOutput<EnrichedRecipe>{
    public FanEnrichedCategoryEMI(EnrichedRecipe recipe) {
        super(CNEmi.FAN_ENRICHING, recipe);
    }

    @Override
    protected void renderAttachedBlock(GuiGraphics graphics) {
        GuiGameElement.of(CNBlocks.ENRICHING_FIRE.getDefaultState())
                .scale(SCALE)
                .atLocal(0, 0, 2)
                .lighting(CreateEmiAnimations.DEFAULT_LIGHTING)
                .render(graphics);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return super.getCategory();
    }

    public Component getTitle() {
        return Component.translatable("createnuclear.enriched.fan.recipe");
    }
}
