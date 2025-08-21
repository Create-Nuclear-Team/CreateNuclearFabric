package net.nuclearteam.createnuclear.foundation.data.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.crafting.Ingredient;
import net.nuclearteam.createnuclear.CNItems;
import net.nuclearteam.createnuclear.CNTags;
import net.nuclearteam.createnuclear.CreateNuclear;

import java.util.function.UnaryOperator;

@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public class CNPressingRecipeGen extends ProcessingRecipeGen {

    GeneratedRecipe
        GRAPHENE = create("graphene", b -> b
            .require(Ingredient.of(CNTags.forgeItemTag("coal_dusts")))
            .output(CNItems.GRAPHENE)
        )
    ;

    <T extends ProcessingRecipe<?>> GeneratedRecipe create(String name, UnaryOperator<ProcessingRecipeBuilder<T>> transform) {
        return create(CreateNuclear.asResource(name), transform);
    }


    public CNPressingRecipeGen(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.PRESSING;
    }


    @Override
    public String getName() {
        return "CreateNuclear's Processing Recipes: " + getRecipeType().getId().getPath();
    }
}
