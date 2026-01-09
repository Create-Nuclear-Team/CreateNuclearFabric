package net.nuclearteam.createnuclear.foundation.data.recipe;

import com.simibubi.create.api.data.recipe.DatagenMod;
import com.simibubi.create.api.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.nuclearteam.createnuclear.CNRecipeTypes;

import java.util.function.Supplier;

public abstract class EnrichedRecipeGen extends ProcessingRecipeGen {

    protected GeneratedRecipe moddedCompacting(DatagenMod mod, String input, String output) {
        return create("compat/" + mod.getId() + "/" + output, b -> b.require(mod, input)
                .output(mod, output)
                .whenModLoaded(mod.getId()));
    }

    protected GeneratedRecipe moddedPaths(DatagenMod mod, String... blocks) {
        for(String block : blocks) {
            moddedCompacting(mod, block, block + "_path");
        }
        return null;
    }

    public GeneratedRecipe convert(ItemLike input, ItemLike result) {
        return convert(() -> Ingredient.of(input), () -> result);
    }

    public GeneratedRecipe convert(Supplier<Ingredient> input, Supplier<ItemLike> result) {
        return create(asResource(CatnipServices.REGISTRIES.getKeyOrThrow(result.get()
                                .asItem())
                        .getPath()),
                p -> p.withItemIngredients(input.get())
                        .output(result.get()));
    }

    protected GeneratedRecipe moddedConversion(DatagenMod mod, String input, String output) {
        return create("compat/" + mod.getId() + "/" + output, p -> p.require(mod, input)
                .output(mod, output)
                .whenModLoaded(mod.getId()));
    }

    public EnrichedRecipeGen(PackOutput generator, String defaultNamespace) {
        super(generator, defaultNamespace);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return CNRecipeTypes.ENRICHED;
    }
}
