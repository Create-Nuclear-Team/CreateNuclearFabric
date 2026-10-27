package net.nuclearteam.createnuclear.foundation.data.recipe;

import net.createmod.catnip.lang.Lang;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.nuclearteam.createnuclear.CNTags;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * The idea is to have the common ("c:") tags generated on the fly, without any typo/writing
 * issues each time a new one is added: a material only declares which {@link ResourceType}
 * shapes it actually has, and the matching {@code TagKey}s are derived and cached from that.
 * <p>
 * Tag names follow the Fabric 1.20.1 conventions ({@code c:<material>_ingots},
 * {@code c:raw_<material>_ores}, ...), the same pattern Fabric API's convention tags and
 * Create's {@code CommonMetal} use. A common tag already provided by Create, such as brass,
 * should instead go through Create's own
 * {@code com.simibubi.create.foundation.data.recipe.CommonMetal} class.
 */
public enum CNMaterialTags {
    URANIUM(ResourceType.ORE, ResourceType.STORAGE_BLOCK, ResourceType.RAW_STORAGE_BLOCK,
            ResourceType.RAW_MATERIAL, ResourceType.DUST, ResourceType.BUCKET, ResourceType.FLUID),
    LEAD(ResourceType.ORE, ResourceType.STORAGE_BLOCK, ResourceType.RAW_STORAGE_BLOCK,
            ResourceType.RAW_MATERIAL, ResourceType.INGOT, ResourceType.NUGGET),
    THORIUM(ResourceType.ORE, ResourceType.STORAGE_BLOCK, ResourceType.RAW_STORAGE_BLOCK,
            ResourceType.RAW_MATERIAL, ResourceType.INGOT, ResourceType.NUGGET, ResourceType.DUST, ResourceType.BUCKET, ResourceType.FLUID),
    STEEL(ResourceType.STORAGE_BLOCK, ResourceType.INGOT, ResourceType.NUGGET),
    NITRATE(ResourceType.ORE),
    COAL(ResourceType.DUST),
    NITROGEN(ResourceType.BUCKET, ResourceType.FLUID),
    ;

    private final String name;
    private final Set<ResourceType> available;
    private final Map<ResourceType, Object> cache = new EnumMap<>(ResourceType.class);

    CNMaterialTags(ResourceType... types) {
        this.name = Lang.asId(name());
        this.available = types.length == 0 ? EnumSet.noneOf(ResourceType.class) : EnumSet.copyOf(Arrays.asList(types));
    }

    public String getName() {
        return name;
    }

    private void require(ResourceType type) {
        if (!available.contains(type))
            throw new IllegalStateException(this + " has no " + type + " tag");
    }

    private ItemLikeTag itemLike(ResourceType type) {
        require(type);
        return (ItemLikeTag) cache.computeIfAbsent(type, t -> new ItemLikeTag(t.path(getName())));
    }

    @SuppressWarnings("unchecked")
    private TagKey<Item> itemOnly(ResourceType type) {
        require(type);
        return (TagKey<Item>) cache.computeIfAbsent(type, t -> CNTags.forgeItemTag(t.path(getName())));
    }

    /** {@code c:<material>_ores} */
    public ItemLikeTag ores() { return itemLike(ResourceType.ORE); }

    /** {@code c:<material>_blocks} */
    public ItemLikeTag storageBlocks() { return itemLike(ResourceType.STORAGE_BLOCK); }

    /** {@code c:raw_<material>_blocks} */
    public ItemLikeTag rawStorageBlocks() { return itemLike(ResourceType.RAW_STORAGE_BLOCK); }

    /** {@code c:raw_<material>_ores} */
    public TagKey<Item> rawMaterials() { return itemOnly(ResourceType.RAW_MATERIAL); }

    /** {@code c:<material>_ingots} */
    public TagKey<Item> ingots() { return itemOnly(ResourceType.INGOT); }

    /** {@code c:<material>_nuggets} */
    public TagKey<Item> nuggets() { return itemOnly(ResourceType.NUGGET); }

    /** {@code c:<material>_dusts} */
    public TagKey<Item> dusts() { return itemOnly(ResourceType.DUST); }

    /** {@code c:<material>_buckets} */
    public TagKey<Item> buckets() { return itemOnly(ResourceType.BUCKET); }

    /** {@code c:<material>} */
    @SuppressWarnings("unchecked")
    public TagKey<Fluid> fluid() {
        require(ResourceType.FLUID);
        return (TagKey<Fluid>) cache.computeIfAbsent(ResourceType.FLUID, t -> CNTags.forgeFluidTag(t.path(getName())));
    }

    @Override
    public String toString() { return name; }

    /**
     * Provides an English name for every common tag declared here, using the
     * {@code tag.<registry>.<namespace>.<path>} keys recipe viewers (EMI) look up.
     */
    public static void provideLang(BiConsumer<String, String> consumer) {
        for (CNMaterialTags material : values()) {
            for (ResourceType type : material.available) {
                String path = type.path(material.getName());
                String registry = type == ResourceType.FLUID ? "fluid" : "item";
                consumer.accept(String.join(".", "tag", registry, CNTags.NameSpace.FABRIC.id, path.replace('/', '.')),
                    Lang.asId(path).replace('_', ' ').transform(CNMaterialTags::titleCase));
            }
        }
    }

    private static String titleCase(String text) {
        StringBuilder builder = new StringBuilder(text.length());
        for (String word : text.split(" ")) {
            if (word.isEmpty()) continue;
            if (!builder.isEmpty()) builder.append(' ');
            builder.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return builder.toString();
    }

    private enum ResourceType {
        ORE("", "_ores"),
        STORAGE_BLOCK("", "_blocks"),
        RAW_STORAGE_BLOCK("raw_", "_blocks"),
        RAW_MATERIAL("raw_", "_ores"),
        INGOT("", "_ingots"),
        NUGGET("", "_nuggets"),
        DUST("", "_dusts"),
        BUCKET("", "_buckets"),
        FLUID("", ""),
        ;

        private final String prefix;
        private final String suffix;

        ResourceType(String prefix, String suffix) {
            this.prefix = prefix;
            this.suffix = suffix;
        }

        String path(String materialName) {
            return prefix + materialName + suffix;
        }
    }

    public record ItemLikeTag(TagKey<Item> items, TagKey<Block> blocks) {
        private ItemLikeTag(String path) {
            this(CNTags.forgeItemTag(path), CNTags.forgeBlockTag(path));
        }
    }
}
