package net.nuclearteam.createnuclear.foundation.data.recipe;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.nuclearteam.createnuclear.CNBlocks;
import net.nuclearteam.createnuclear.CNItems;
import net.nuclearteam.createnuclear.CreateNuclear;

@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
public class CNEnrichedRecipeGen extends EnrichedRecipeGen {

    GeneratedRecipe
        ENRICHING_CAMPFIRES = convert(Items.CAMPFIRE, CNBlocks.ENRICHING_CAMPFIRE),
        ENRICHED_YELLOWCAKE = convert(() -> Ingredient.of(CNItems.YELLOWCAKE), () -> CNItems.ENRICHED_YELLOWCAKE)
    ;


    public CNEnrichedRecipeGen(PackOutput output) {
        super(output, CreateNuclear.MOD_ID);
    }

    @Override
    public String getName() {
        return "CreateNuclear's Processing Recipes: " + getRecipeType().getId().getPath();
    }
}
