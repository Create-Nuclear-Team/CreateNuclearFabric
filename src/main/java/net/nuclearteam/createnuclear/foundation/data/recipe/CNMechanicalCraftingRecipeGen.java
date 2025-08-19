package net.nuclearteam.createnuclear.foundation.data.recipe;

import com.google.common.base.Supplier;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.MechanicalCraftingRecipeGen;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.nuclearteam.createnuclear.*;

import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
public class CNMechanicalCraftingRecipeGen extends CreateRecipeProvider {

    GeneratedRecipe
        GRAPHITE_ROD = create(CNItems.GRAPHITE_ROD::get)
            .recipe(b -> b
                .key('S', Ingredient.of(CNTag.forgeItemTag("ingots/steel")))
                .key('G', Ingredient.of(CNItems.GRAPHENE))
                .patternLine("SGS")
                .patternLine("SGS")
                .patternLine("SGS")
                .patternLine("SGS")
            ),

        URANIUM_ROD = create(CNItems.URANIUM_ROD::get)
            .recipe(b -> b
                .key('U', Ingredient.of(CNItems.ENRICHED_YELLOWCAKE))
                    .patternLine("    U")
                    .patternLine("   U ")
                    .patternLine("  U  ")
                    .patternLine(" U   ")
                    .patternLine("U    ")
            ),
        REACTOR_FRAME = create(CNBlocks.REACTOR_FRAME::get)
            .recipe(b -> b
                .key('C', Ingredient.of(CNBlocks.REACTOR_CASING))
                .key('G', Ingredient.of(CNBlocks.REINFORCED_GLASS))
                .key('B', Ingredient.of(CNFluids.URANIUM.get().getBucket()))
                .key('S', Ingredient.of(CNTag.forgeItemTag("ingots/steel")))
                .patternLine("CCCCC")
                .patternLine("CSGSC")
                .patternLine("CGBGC")
                .patternLine("CSGSC")
                .patternLine("CCCCC")
            ),

        REACTOR_CONTROLLER = create(CNBlocks.REACTOR_CONTROLLER::get)
            .recipe(b -> b
                .key('C', Ingredient.of(CNBlocks.REACTOR_CASING))
                .key('V', Ingredient.of(AllBlocks.ITEM_VAULT))
                .key('O', Ingredient.of(AllBlocks.SMART_OBSERVER))
                .key('T', Ingredient.of(AllItems.ELECTRON_TUBE))
                .key('N', Ingredient.of(Items.NETHERITE_INGOT))
                .key('X', Ingredient.of(Items.NETHER_STAR))
                .patternLine("CCCCC")
                .patternLine("CNONC")
                .patternLine("CTXTC")
                .patternLine("CNVNC")
                .patternLine("CCCCC")
            ),

        REACTOR_COOLER = create(CNBlocks.REACTOR_COOLER::get)
            .recipe(b -> b
                .key('C', Ingredient.of(CNBlocks.REACTOR_CASING))
                .key('I', Ingredient.of(Blocks.BLUE_ICE))
                .key('G', Ingredient.of(CNBlocks.REINFORCED_GLASS))
                .key('S', Ingredient.of(CNTag.forgeItemTag("ingots/steel")))
                .patternLine("CCCCC")
                .patternLine("CSGSC")
                .patternLine("CIGIC")
                .patternLine("CSGSC")
                .patternLine("CCCCC")
            ),

        REACTOR_CORE = create(CNBlocks.REACTOR_CORE::get)
            .recipe(b -> b
                .key('C', Ingredient.of(CNBlocks.REACTOR_CASING))
                .key('P', Ingredient.of(AllItems.PRECISION_MECHANISM))
                .key('B', Ingredient.of(CNFluids.URANIUM.get().getBucket()))
                .key('S', Ingredient.of(CNTag.forgeItemTag("ingots/steel")))
                .patternLine("CCCCC")
                .patternLine("CPSPC")
                .patternLine("CSBSC")
                .patternLine("CPSPC")
                .patternLine("CCCCC")
            );

    GeneratedRecipeBuilder create(Supplier<ItemLike> result) {
        return new GeneratedRecipeBuilder(result);
    }

    class GeneratedRecipeBuilder {

        private String suffix;
        private final Supplier<ItemLike> result;
        private int amount;

        public GeneratedRecipeBuilder(Supplier<ItemLike> result) {
            this.suffix = "";
            this.result = result;
            this.amount = 1;
        }

        GeneratedRecipeBuilder returns(int amount) {
            this.amount = amount;
            return this;
        }

        GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        GeneratedRecipe recipe(UnaryOperator<MechanicalCraftingRecipeBuilder> builder) {
            return register(consumer -> {
                MechanicalCraftingRecipeBuilder b =
                        builder.apply(MechanicalCraftingRecipeBuilder.shapedRecipe(result.get(), amount));
                ResourceLocation location = CreateNuclear.asResource("mechanical_crafting/" + RegisteredObjects.getKeyOrThrow(result.get()
                                .asItem())
                        .getPath() + suffix);
                b.build(consumer, location);
            });
        }
    }

    public CNMechanicalCraftingRecipeGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "CreateNuclair's Mechanical Crafting Recipes";
    }
}
