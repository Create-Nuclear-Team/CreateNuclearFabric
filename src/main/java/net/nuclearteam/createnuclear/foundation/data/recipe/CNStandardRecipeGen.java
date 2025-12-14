package net.nuclearteam.createnuclear.foundation.data.recipe;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.simibubi.create.api.data.recipe.BaseRecipeProvider;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.createmod.catnip.platform.CatnipServices;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.nuclearteam.createnuclear.CNBlocks;
import net.nuclearteam.createnuclear.CNItems;
import net.nuclearteam.createnuclear.CNTags;
import net.nuclearteam.createnuclear.CreateNuclear;
import net.nuclearteam.createnuclear.content.equipment.cloth.ClothItem;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CNStandardRecipeGen extends BaseRecipeProvider {

    private final String CRAFTING = enterFolder("crafting");
    GeneratedRecipe
        WHITE_CLOTH_FROM_STRING = create(ClothItem.Cloths.WHITE_CLOTH::getItem).unlockedBy(() -> Items.STRING)
            .viaShaped(b -> b
               .define('#', Tags.Items.STRING)
                .pattern("###")
                .pattern("###")
                .showNotification(true)
            ),

        WHITE_CLOTH_FROM_WOOL = create(ClothItem.Cloths.WHITE_CLOTH::getItem).returns(6).unlockedBy(() -> Items.WHITE_WOOL).withSuffix("_wool")
            .viaShaped(b -> b
                .define('#', Blocks.WHITE_WOOL)
                .pattern("###")
                .pattern("###")
                .showNotification(true)
            )
    ;

    private final String CRAFTING_MATERIALS = enterFolder("crafting/materials");

    GeneratedRecipe
        LEAD_COMPACTING = metalCompacting(ImmutableList.of(CNItems.LEAD_NUGGET, CNItems.LEAD_INGOT, CNBlocks.LEAD_BLOCK),
            ImmutableList.of(() -> CNTags.forgeItemTag("nuggets/lead"), () -> CNTags.forgeItemTag("ingots/lead"), () -> CNTags.forgeItemTag("storage_blocks/lead"))),

        STEEL_COMPACTING = metalCompacting(ImmutableList.of(CNItems.STEEL_NUGGET, CNItems.STEEL_INGOT, CNBlocks.STEEL_BLOCK),
            ImmutableList.of(() -> CNTags.forgeItemTag("nuggets/steel"), () -> CNTags.forgeItemTag("ingots/steel"), () -> CNTags.forgeItemTag("storage_blocks/steel")))
    ;


    private final String CRAFTING_REACTOR = enterFolder("crafting/reactor");

    String currentFolder = "";

    String enterFolder(String folder) {
        currentFolder = folder;
        return currentFolder;
    }

    GeneratedRecipeBuilder create(Supplier<ItemLike> result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }

    GeneratedRecipeBuilder create(ResourceLocation result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }

    GeneratedRecipeBuilder create(ItemProviderEntry<? extends  ItemLike> result) {
        return create(result::get);
    }

    GeneratedRecipe metalCompacting(List<ItemProviderEntry<? extends ItemLike>> variants,
                                    List<Supplier<TagKey<Item>>> ingredients) {
        GeneratedRecipe result = null;
        for (int i = 0; i + 1 < variants.size(); i++) {
            ItemProviderEntry<? extends ItemLike> currentEntry = variants.get(i);
            ItemProviderEntry<? extends ItemLike> nextEntry = variants.get(i + 1);
            Supplier<TagKey<Item>> currentIngredient = ingredients.get(i);
            Supplier<TagKey<Item>> nextIngredient = ingredients.get(i + 1);

            result = create(nextEntry).withSuffix("_from_compacting")
                    .unlockedBy(currentEntry::get)
                    .viaShaped(b -> b.pattern("###")
                            .pattern("###")
                            .pattern("###")
                            .define('#', currentIngredient.get()));
        }
        return result;
    }

    class GeneratedRecipeBuilder {

        private final String path;
        private String suffix;
        private Supplier<? extends ItemLike> result;
        private ResourceLocation compatDatagenOutput;
        List<ConditionJsonProvider> recipeConditions;
        private RecipeCategory category;

        private Supplier<ItemPredicate> unlockedBy;
        private int amount;

        private GeneratedRecipeBuilder(String path) {
            this.path = path;
            this.recipeConditions = new ArrayList<>();
            this.suffix = "";
            this.amount = 1;
            this.category = RecipeCategory.MISC;
        }

        public GeneratedRecipeBuilder(String path, Supplier<? extends ItemLike> result) {
            this(path);
            this.result = result;
        }

        public GeneratedRecipeBuilder(String path, ResourceLocation result) {
            this(path);
            this.compatDatagenOutput = result;
        }

        GeneratedRecipeBuilder returns(int amount) {
            this.amount = amount;
            return this;
        }

        GeneratedRecipeBuilder unlockedBy(Supplier<? extends ItemLike> item) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(item.get())
                    .build();
            return this;
        }

        GeneratedRecipeBuilder unlockedByTag(Supplier<TagKey<Item>> tag) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                    .of(tag.get())
                    .build();
            return this;
        }

        GeneratedRecipeBuilder whenModLoaded(String modid) {
            return withCondition(DefaultResourceConditions.allModsLoaded(modid));
        }

        GeneratedRecipeBuilder whenModMissing(String modid) {
            return withCondition(DefaultResourceConditions.not(DefaultResourceConditions.allModsLoaded(modid)));
        }

        GeneratedRecipeBuilder withCondition(ConditionJsonProvider condition) {
            recipeConditions.add(condition);
            return this;
        }

        GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        GeneratedRecipeBuilder withCategory(RecipeCategory category) {
            this.category = category;
            return this;
        }

        // FIXME 5.1 refactor - recipe categories as markers instead of sections?
        GeneratedRecipe viaShaped(UnaryOperator<ShapedRecipeBuilder> builder) {
            return register(consumer -> {
                ShapedRecipeBuilder b = builder.apply(ShapedRecipeBuilder.shaped(category, result.get(), amount));
                if (unlockedBy != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                b.save(consumer, createSimpleLocation(path));
            });
        }

        GeneratedRecipe viaShapeless(UnaryOperator<ShapelessRecipeBuilder> builder) {
            return register(consumer -> {
                ShapelessRecipeBuilder b = builder.apply(ShapelessRecipeBuilder.shapeless(category, result.get(), amount));
                if (unlockedBy != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                b.save(consumer, createSimpleLocation(path));
            });
        }

        GeneratedRecipe viaNetheriteSmithing(Supplier<? extends Item> base, Supplier<Ingredient> upgradeMaterial) {
            this.withCategory(RecipeCategory.COMBAT);
            return register(consumer -> {
                SmithingTransformRecipeBuilder b =
                        SmithingTransformRecipeBuilder.smithing(Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                                Ingredient.of(base.get()), upgradeMaterial.get(), category, result.get()
                                        .asItem());
                b.unlocks("has_item", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(base.get())
                        .build()));
                b.save(consumer, createSimpleLocation(path));
            });
        }

        private ResourceLocation createSimpleLocation(String recipeType) {
            return CreateNuclear.asResource(recipeType + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation createLocation(String recipeType) {
            return CreateNuclear.asResource(recipeType + "/" + path + "/" + getRegistryName().getPath() + suffix);
        }

        private ResourceLocation getRegistryName() {
            return compatDatagenOutput == null ? CatnipServices.REGISTRIES.getKeyOrThrow(result.get()
                    .asItem()) : compatDatagenOutput;
        }

        GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCooking(Supplier<? extends ItemLike> item) {
            return unlockedBy(item).viaCookingIngredient(() -> Ingredient.of(item.get()));
        }

        GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCookingTag(Supplier<TagKey<Item>> tag) {
            return unlockedByTag(tag).viaCookingIngredient(() -> Ingredient.of(tag.get()));
        }

        GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder viaCookingIngredient(Supplier<Ingredient> ingredient) {
            return new GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder(ingredient);
        }

        class GeneratedCookingRecipeBuilder {

            private final Supplier<Ingredient> ingredient;
            private float exp;
            private int cookingTime;

            private final RecipeSerializer<? extends AbstractCookingRecipe> FURNACE = RecipeSerializer.SMELTING_RECIPE,
                    SMOKER = RecipeSerializer.SMOKING_RECIPE, BLAST = RecipeSerializer.BLASTING_RECIPE,
                    CAMPFIRE = RecipeSerializer.CAMPFIRE_COOKING_RECIPE;

            GeneratedCookingRecipeBuilder(Supplier<Ingredient> ingredient) {
                this.ingredient = ingredient;
                cookingTime = 200;
                exp = 0;
            }

            GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder forDuration(int duration) {
                cookingTime = duration;
                return this;
            }

            GeneratedRecipeBuilder.GeneratedCookingRecipeBuilder rewardXP(float xp) {
                exp = xp;
                return this;
            }

            GeneratedRecipe inFurnace() {
                return inFurnace(b -> b);
            }

            GeneratedRecipe inFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                return create(FURNACE, builder, 1);
            }

            GeneratedRecipe inSmoker() {
                return inSmoker(b -> b);
            }

            GeneratedRecipe inSmoker(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                create(FURNACE, builder, 1);
                create(CAMPFIRE, builder, 3);
                return create(SMOKER, builder, .5f);
            }

            GeneratedRecipe inBlastFurnace() {
                return inBlastFurnace(b -> b);
            }

            GeneratedRecipe inBlastFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
                create(FURNACE, builder, 1);
                return create(BLAST, builder, .5f);
            }

            private GeneratedRecipe create(RecipeSerializer<? extends AbstractCookingRecipe> serializer,
                                           UnaryOperator<SimpleCookingRecipeBuilder> builder, float cookingTimeModifier) {
                return register(consumer -> {
                    boolean isOtherMod = compatDatagenOutput != null;

                    SimpleCookingRecipeBuilder b = builder.apply(SimpleCookingRecipeBuilder.generic(ingredient.get(),
                            RecipeCategory.MISC, isOtherMod ? Items.DIRT : result.get(), exp,
                            (int) (cookingTime * cookingTimeModifier), serializer));

                    if (unlockedBy != null)
                        b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));

                    b.save(result -> {
                        consumer.accept(
                                isOtherMod ? new ModdedCookingRecipeResult(result, compatDatagenOutput, recipeConditions)
                                        : result);
                    }, createSimpleLocation(CatnipServices.REGISTRIES.getKeyOrThrow(serializer)
                            .getPath()));
                });
            }
        }
    }

    @Override
    public String getName() {
        return "CreateNuclear's Standard Recipes";
    }

    public CNStandardRecipeGen(FabricDataOutput output) {
        super(output, CreateNuclear.MOD_ID);
    }

    private record ModdedCookingRecipeResult(FinishedRecipe wrapped, ResourceLocation outputOverride,
                                             List<ConditionJsonProvider> conditions) implements FinishedRecipe {
        @Override
        public ResourceLocation getId() {
            return wrapped.getId();
        }

        @Override
        public RecipeSerializer<?> getType() {
            return wrapped.getType();
        }

        @Override
        public JsonObject serializeAdvancement() {
            return wrapped.serializeAdvancement();
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return wrapped.getAdvancementId();
        }

        @Override
        public void serializeRecipeData(JsonObject object) {
            wrapped.serializeRecipeData(object);
            object.addProperty("result", outputOverride.toString());

            ConditionJsonProvider.write(object, conditions.toArray(new ConditionJsonProvider[0]));
        }

    }
}
