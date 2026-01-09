package net.nuclearteam.createnuclear;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.nuclearteam.createnuclear.content.equipment.armor.AntiRadiationArmorItem;
import net.nuclearteam.createnuclear.content.equipment.armor.AntiRadiationArmorItem.Boot;
import net.nuclearteam.createnuclear.content.equipment.armor.AntiRadiationArmorItem.Chestplate;
import net.nuclearteam.createnuclear.content.equipment.armor.AntiRadiationArmorItem.Helmet;
import net.nuclearteam.createnuclear.content.equipment.armor.AntiRadiationArmorItem.Leggings;

import com.tterrag.registrate.util.entry.EntityEntry;
import com.tterrag.registrate.util.entry.ItemEntry;

import io.github.fabricators_of_create.porting_lib.util.LazySpawnEggItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.nuclearteam.createnuclear.content.equipment.cloth.ClothItem;
import net.nuclearteam.createnuclear.content.multiblock.bluePrintItem.ReactorBluePrint;
import net.nuclearteam.createnuclear.foundation.item.DyedItemsList;
import net.nuclearteam.createnuclear.foundation.utility.TextUtils;
import net.nuclearteam.createnuclear.CNTags.CNItemTags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.nuclearteam.createnuclear.content.equipment.armor.AntiRadiationArmorItem.Chestplate.getChestplateTag;
import static net.nuclearteam.createnuclear.content.equipment.armor.AntiRadiationArmorItem.Helmet.DyeItemHelmetList.getHelmetTag;
import static net.nuclearteam.createnuclear.content.equipment.armor.AntiRadiationArmorItem.Leggings.getLeggingsTag;

@SuppressWarnings("unused")
public class CNItems {
    private static final CreateRegistrate REGISTRATE = CreateNuclear.registrate();

    public static final ItemEntry<Item>
        URANIUM_POWDER = REGISTRATE
            .item("uranium_powder", Item::new)
            .register(),

        YELLOWCAKE = REGISTRATE
            .item("yellowcake", Item::new)
            .properties(p -> p.food(new FoodProperties.Builder()
                .nutrition(20)
                .saturationMod(0.3F)
                .alwaysEat()
                .effect(new MobEffectInstance(CNEffects.RADIATION.get(),600,2), 1.0F)
                .effect(new MobEffectInstance(MobEffects.HUNGER, 600, 1), 1.0F)
                .build())
            )
            .register(),

        ENRICHED_YELLOWCAKE = REGISTRATE
            .item("enriched_yellowcake", Item::new)
            .register(),

        COAL_DUST = REGISTRATE
            .item("coal_dust", Item::new)
            .tag(CNTags.forgeItemTag("dusts"), CNTags.forgeItemTag("dusts/coal"))
            .register(),

        GRAPHENE = REGISTRATE
            .item("graphene", Item::new)
            .register(),

        STEEL_INGOT = REGISTRATE
            .item("steel_ingot", Item::new)
            .tag(CNTags.forgeItemTag("ingots"), CNTags.forgeItemTag("ingots/steel"))
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 9)
                .unlockedBy("has_storage_blocks_steel", RegistrateRecipeProvider.has(CNTags.forgeItemTag("storage_blocks/steel")))
                .requires(CNTags.forgeItemTag("storage_blocks/steel"))
                .save(p, CreateNuclear.asResource("crafting/" + c.getName() + "_from_decompacting"))
            )
            .register(),

        RAW_URANIUM = REGISTRATE
            .item("raw_uranium", Item::new)
            .tag(CNTags.forgeItemTag("raw_ores"), CNTags.forgeItemTag("raw_materials"), CNTags.forgeItemTag("raw_materials/uranium"))
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 9)
                .unlockedBy("has_storage_blocks_raw_uranium", RegistrateRecipeProvider.has(CNTags.forgeItemTag("storage_blocks/raw_uranium")))
                .requires(CNTags.forgeItemTag("storage_blocks/raw_uranium"))
                .save(p, CreateNuclear.asResource("crafting/" + c.getName() + "_from_decompacting"))
            )
            .register(),

        RAW_LEAD = REGISTRATE
            .item("raw_lead", Item::new)
            .tag(CNTags.forgeItemTag("raw_ores"), CNTags.forgeItemTag("raw_materials"), CNTags.forgeItemTag("raw_materials/lead"))
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 9)
                .unlockedBy("has_storage_blocks_raw_lead", RegistrateRecipeProvider.has(CNTags.forgeItemTag("storage_blocks/raw_lead")))
                .requires(CNTags.forgeItemTag("storage_blocks/raw_lead"))
                .save(p, CreateNuclear.asResource("crafting/" + c.getName() + "_from_decompacting"))
            )
            .register(),

        LEAD_INGOT = REGISTRATE
            .item("lead_ingot", Item::new)
            .tag(CNTags.forgeItemTag("ingots"), CNTags.forgeItemTag("ingots/lead"))
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(),9)
                .unlockedBy("has_storage_blocks_lead", RegistrateRecipeProvider.has(CNTags.forgeItemTag("storage_blocks/lead")))
                .requires(CNTags.forgeItemTag("storage_blocks/lead"))
                .save(p, CreateNuclear.asResource("crafting/" + c.getName() + "_from_decompacting"))
            )
            .register(),

        LEAD_NUGGET = REGISTRATE
            .item("lead_nugget", Item::new)
            .tag(CNTags.forgeItemTag("nuggets"), CNTags.forgeItemTag("nuggets/lead"))
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 9)
                .unlockedBy("has_storage_blocks_lead_nugget", RegistrateRecipeProvider.has(CNTags.forgeItemTag("ingots/lead")))
                .requires(CNTags.forgeItemTag("ingots/lead"))
                .save(p, CreateNuclear.asResource("crafting/" + c.getName() + "_from_decompacting"))
            )
            .register(),

        STEEL_NUGGET = REGISTRATE
            .item("steel_nugget", Item::new)
            .tag(CNTags.forgeItemTag("nuggets"), CNTags.forgeItemTag("nuggets/steel"))
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get(), 9)
                    .unlockedBy("has_storage_blocks_steel_nugget", RegistrateRecipeProvider.has(CNTags.forgeItemTag("ingots/steel")))
                    .requires(CNTags.forgeItemTag("ingots/steel"))
                    .save(p, CreateNuclear.asResource("crafting/" + c.getName() + "_from_decompacting"))
            )
            .register(),

        URANIUM_ROD = REGISTRATE
            .item("uranium_rod", Item::new)
            .tag(CNTags.forgeItemTag("rods"), CNItemTags.FUEL.tag)
            .register(),

        GRAPHITE_ROD = REGISTRATE
            .item("graphite_rod", Item::new)
            .tag(CNTags.forgeItemTag("rods"), CNItemTags.COOLER.tag)
            .register()
    ;
  
    public static final DyedItemsList<Helmet> ANTI_RADIATION_HELMETS = new DyedItemsList<>(color -> {
       String colorName = color.getSerializedName();

        TagKey<Item> tag = !colorName.equals("white")
                ? CNItemTags.ANTI_RADIATION_HELMET_FULL_DYE.tag
                : CNItemTags.ANTI_RADIATION_ARMOR.tag;
        return REGISTRATE.item(colorName + "_anti_radiation_helmet", p -> new Helmet(p, color))
               .tag(
                   CNTags.forgeItemTag("helmets"),
                   CNTags.forgeItemTag("armors"),
                   CNTags.forgeItemTag("armors/helmets"),
                   getHelmetTag(colorName),
                   CNItemTags.ALL_ANTI_RADIATION_ARMORS.tag,
                   CNItemTags.ANTI_RADIATION_HELMET_FULL_DYE.tag
               )
               .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, c.get())
                    .unlockedBy("has_cloth", RegistrateRecipeProvider.has(CNItemTags.CLOTH.tag))
                    .define('X', CNTags.forgeItemTag("ingots/lead"))
                    .define('Y', ClothItem.Cloths.getByColor(color).get())
                    .define('Z', CNBlocks.REINFORCED_GLASS.asItem())
                    .pattern("YXY")
                    .pattern("XZX")
                    .showNotification(true)
                    .save(p, CreateNuclear.asResource("crafting/items/armors/" + c.getName())))
               .lang(TextUtils.titleCaseConversion(color.getName()) +" Anti Radiation Helmet")
               .model((c, p) -> p.generated(c, CreateNuclear.asResource("item/armors/helmets/" + colorName + "_anti_radiation_helmet")))
               .register();

    });

    public static final DyedItemsList<Chestplate> ANTI_RADIATION_CHESTPLATES = new DyedItemsList<>(color -> {
        String colorName = color.getSerializedName();

        TagKey<Item> tag = !colorName.equals("white")
                ? CNItemTags.ANTI_RADIATION_CHESTPLATE_DYE.tag
                : CNItemTags.ANTI_RADIATION_ARMOR.tag;
        return REGISTRATE.item(colorName + "_anti_radiation_chestplate",  p -> new Chestplate(p, color))
                .tag(
                    CNTags.forgeItemTag("chestplates"),
                    CNTags.forgeItemTag("armors"),
                    CNTags.forgeItemTag("armors/chestplates"),
                    getChestplateTag(colorName),
                    CNItemTags.ALL_ANTI_RADIATION_ARMORS.tag,
                    CNItemTags.ANTI_RADIATION_CHESTPLATE_FULL_DYE.tag
                )
                .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, c.get())
                        .unlockedBy("has_cloth", RegistrateRecipeProvider.has(CNItemTags.CLOTH.tag))
                        .define('X', CNTags.forgeItemTag("ingots/lead"))
                        .define('Y', ClothItem.Cloths.getByColor(color).get())
                        .define('Z', CNItems.GRAPHITE_ROD)
                        .pattern("Y Y")
                        .pattern("XXX")
                        .pattern("ZXZ")
                        .showNotification(true)
                        .save(p, CreateNuclear.asResource("crafting/items/armors/" + c.getName())))
                .lang(TextUtils.titleCaseConversion(color.getName()) +" Anti Radiation Chestplate")
                .model((c, p) -> p.generated(c, CreateNuclear.asResource("item/armors/chestplates/" + colorName + "_anti_radiation_chestplate")))
                .register();

    });

    public static final DyedItemsList<Leggings> ANTI_RADIATION_LEGGINGS = new DyedItemsList<>(color -> {

        String colorName = color.getSerializedName();
        TagKey<Item> tag = !colorName.equals("white")
                ? CNItemTags.ANTI_RADIATION_LEGGINGS_DYE.tag
                : CNItemTags.ANTI_RADIATION_ARMOR.tag;
        return REGISTRATE.item(colorName + "_anti_radiation_leggings",  p -> new Leggings(p, color))
                .tag(
                    CNTags.forgeItemTag("leggings"),
                    CNTags.forgeItemTag("armors"),
                    CNTags.forgeItemTag("armors/leggings"),
                    getLeggingsTag(colorName),
                    CNItemTags.ALL_ANTI_RADIATION_ARMORS.tag,
                    CNItemTags.ANTI_RADIATION_LEGGINGS_FULL_DYE.tag
                )
                .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, c.get())
                        .unlockedBy("has_cloth", RegistrateRecipeProvider.has(CNItemTags.CLOTH.tag))
                        .define('X', CNTags.forgeItemTag("ingots/lead"))
                        .define('Y', ClothItem.Cloths.getByColor(color).get())
                        .pattern("YXY")
                        .pattern("X X")
                        .pattern("Y Y")
                        .showNotification(true)
                        .save(p, CreateNuclear.asResource("crafting/items/armors/" + c.getName())))
                .lang(TextUtils.titleCaseConversion(color.getName()) +" Anti Radiation Leggings")
                .model((c, p) -> p.generated(c, CreateNuclear.asResource("item/armors/leggings/" + colorName + "_anti_radiation_leggings")))
                .register();

    });

    public static final ItemEntry<? extends AntiRadiationArmorItem.Boot>
            ANTI_RADIATION_BOOTS = REGISTRATE.item("anti_radiation_boots", Boot::new)
            .tag(
                CNTags.forgeItemTag("boots"),
                CNTags.forgeItemTag("armors"),
                CNTags.forgeItemTag("armors/boots"),
                CNItemTags.ANTI_RADIATION_BOOTS_DYE.tag,
                CNItemTags.ANTI_RADIATION_ARMOR.tag,
                CNItemTags.ALL_ANTI_RADIATION_ARMORS.tag
            )
            .lang("Anti Radiation Boots")
            .recipe((c, p) -> ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, c.get())
                    .unlockedBy("has_cloth", RegistrateRecipeProvider.has(CNItemTags.CLOTH.tag))
                    .define('X', CNTags.forgeItemTag("ingots/lead"))
                    .define('Y', ClothItem.Cloths.WHITE_CLOTH.getItem())
                    .pattern("Y Y")
                    .pattern("X X")
                    .showNotification(true)
                    .save(p, CreateNuclear.asResource("crafting/items/armors/" + c.getName())))
            .model((c, p) -> p.generated(c, CreateNuclear.asResource("item/armors/anti_radiation_boots")))
            .register();


    public static final DyedItemsList<ClothItem> CLOTHS = new DyedItemsList<>(color -> {
        String colorName = color.getSerializedName();
        List<Item> ingredients = new ArrayList<>(Arrays.asList(Items.WHITE_DYE, Items.ORANGE_DYE, Items.MAGENTA_DYE, Items.LIGHT_BLUE_DYE, Items.YELLOW_DYE, Items.LIME_DYE, Items.PINK_DYE, Items.GRAY_DYE, Items.LIGHT_GRAY_DYE, Items.CYAN_DYE, Items.PURPLE_DYE, Items.BLUE_DYE, Items.BROWN_DYE, Items.GREEN_DYE, Items.RED_DYE, Items.BLACK_DYE));
        return REGISTRATE.item(colorName+ "_cloth", p -> new ClothItem(p, color))
                .tag(CNItemTags.CLOTH.tag)
                .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get())
                        .unlockedBy("has_white_cloth", RegistrateRecipeProvider.has(ClothItem.Cloths.WHITE_CLOTH.getItem()))
                        .requires(CNItemTags.CLOTH.tag)
                        .requires(ingredients.get(color.ordinal()))
                        .save(p, CreateNuclear.asResource("shapeless/cloth/" + c.getName()))
                )
                .lang(TextUtils.titleCaseConversion(color.getName()) + " Cloth")
                .model((c, p) -> p.generated(c, CreateNuclear.asResource("item/cloth/" + colorName + "_cloth")))
                .register();
    });

    public static final ItemEntry<ReactorBluePrint> REACTOR_BLUEPRINT = REGISTRATE
            .item("reactor_blueprint_item", ReactorBluePrint::new)
            .lang("Reactor Blueprint")
            .recipe((c, p) -> {
                ShapedRecipeBuilder.shaped(RecipeCategory.MISC, c.get())
                        .unlockedBy("has_reactor_controller", RegistrateRecipeProvider.has(CNBlocks.REACTOR_CONTROLLER.get()))
                        .define('S', CNTags.forgeItemTag("ingots/steel"))
                        .define('D', AllBlocks.DISPLAY_BOARD)
                        .define('P', AllItems.PRECISION_MECHANISM)
                        .define('E', AllItems.EMPTY_SCHEMATIC)
                        .pattern("SDS")
                        .pattern("SPS")
                        .pattern("SES")
                        .save(p, CreateNuclear.asResource("crafting/" + c.getName()));
                ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, c.get())
                        .unlockedBy("has_reactor_blueprint", RegistrateRecipeProvider.has(CNBlocks.REACTOR_CONTROLLER.get()))
                        .requires(CNItems.REACTOR_BLUEPRINT)
                        .save(p, CreateNuclear.asResource("shapeless/" + c.getName() + "_clear"));
            })
            .model((c, p) -> p.generated(c, CreateNuclear.asResource("item/reactor_blueprint")))
            .properties(p -> p.stacksTo(1))
            .register();

    public static final ItemEntry<LazySpawnEggItem> SPAWN_WOLF = registerSpawnEgg("wolf_irradiated_spawn_egg", CNEntityType.IRRADIATED_WOLF, 0x42452B,0x4C422B, "Irradiated Wolf Spawn Egg");
    public static final ItemEntry<LazySpawnEggItem> SPAWN_CAT = registerSpawnEgg("cat_irradiated_spawn_egg", CNEntityType.IRRADIATED_CAT, 0x382C19,0x742728, "Irradiated Cat Spawn Egg");
    public static final ItemEntry<LazySpawnEggItem> SPAWN_CHICKEN = registerSpawnEgg("chicken_irradiated_spawn_egg", CNEntityType.IRRADIATED_CHICKEN, 0x6B9455,0x95393C, "Irradiated Chicken Spawn Egg");

    private static ItemEntry<LazySpawnEggItem> registerSpawnEgg(String name, EntityEntry<? extends Mob> mobEntityType, int backgroundColor, int highlightColor, String nameItems) {
        return REGISTRATE
                .item(name, p -> new LazySpawnEggItem(mobEntityType, backgroundColor,highlightColor, p))
                .lang(nameItems)
                .model((c, p) -> p.withExistingParent(c.getName(), new ResourceLocation("item/template_spawn_egg")))
                .register();
    }

    public static void registerCNItems() {
        CreateNuclear.LOGGER.info("Registering mod items for {}", CreateNuclear.MOD_ID);
    }

}
