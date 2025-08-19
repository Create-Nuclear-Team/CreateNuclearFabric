package net.nuclearteam.createnuclear.infrastructure.data;

import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.nuclearteam.createnuclear.CNTag;
import net.nuclearteam.createnuclear.CreateNuclear;

@SuppressWarnings({"unused", "deprecated"})
public class CreateNuclearRegistrateTags {
    public static void addGenerators() {
        CreateNuclear.REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, CreateNuclearRegistrateTags::genEntityTags);
        CreateNuclear.REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, CreateNuclearRegistrateTags::genItemTags);
    }

    private static void genEntityTags(RegistrateTagsProvider<EntityType<?>> provIn) {
        TagGen.CreateTagsProvider<EntityType<?>> prov = new TagGen.CreateTagsProvider<>(provIn, EntityType::builtInRegistryHolder);

        for (CNTag.EntityTypeTags tag : CNTag.EntityTypeTags.values()) {
            if (tag.alwaysDatagen) {
                prov.getOrCreateRawBuilder(tag.tag);
            }
        }
    }

    private static void genItemTags(RegistrateTagsProvider<Item> provIn) {
        TagGen.CreateTagsProvider<Item> prov = new TagGen.CreateTagsProvider<>(provIn, Item::builtInRegistryHolder);

        for (CNTag.ItemTags tag : CNTag.ItemTags.values()) {
            if (tag.alwaysDatagen) {
                prov.getOrCreateRawBuilder(tag.tag);
            }
        }
    }


}
