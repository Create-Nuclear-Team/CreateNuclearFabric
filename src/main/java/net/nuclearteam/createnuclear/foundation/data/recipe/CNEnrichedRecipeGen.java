package net.nuclearteam.createnuclear.foundation.data.recipe;

import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.nuclearteam.createnuclear.CNBlocks;
import net.nuclearteam.createnuclear.CNItems;
import net.nuclearteam.createnuclear.CNRecipeTypes;
import net.nuclearteam.createnuclear.CreateNuclear;

import java.util.function.Supplier;

@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
public class CNEnrichedRecipeGen extends ProcessingRecipeGen {

    GeneratedRecipe
        ENRICHING_CAMPFIRES = convert(Items.CAMPFIRE, CNBlocks.ENRICHING_CAMPFIRE),
        ENRICHED_YELLOWCAKE = convert(() -> Ingredient.of(CNItems.YELLOWCAKE), () -> CNItems.ENRICHED_YELLOWCAKE)
    ;

    public GeneratedRecipe convert(ItemLike input, ItemLike result) {
        return convert(() -> Ingredient.of(input), () -> result);
    }

    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> result) {
        return create(
            CreateNuclear.asResource(
                RegisteredObjects.getKeyOrThrow(
                    result.get().asItem()
                )
                .getPath()
            ),
            p -> p.withItemIngredients(input.get()).output(result.get()));
    }


    public CNEnrichedRecipeGen(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    protected CNRecipeTypes getRecipeType() {
        return CNRecipeTypes.ENRICHED;
    }

    @Override
    public String getName() {
        return "CreateNuclear's Processing Recipes: " + getRecipeType().getId().getPath();
    }
}
