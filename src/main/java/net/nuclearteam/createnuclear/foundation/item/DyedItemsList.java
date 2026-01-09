package net.nuclearteam.createnuclear.foundation.item;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class DyedItemsList<T extends Item> implements Iterable<ItemEntry<T>> {

    private static final int COLOR_AMOUNT = DyeColor.values().length;
    private final ItemEntry<?>[] values = new ItemEntry<?>[COLOR_AMOUNT];

    public DyedItemsList(Function<DyeColor, ItemEntry<? extends T>> filter) {
        for (DyeColor color : DyeColor.values()) {
            values[color.ordinal()] = filter.apply(color);
        }
    }

    @SuppressWarnings("unchecked")
    public ItemEntry<T> get(DyeColor color) {
        return (ItemEntry<T>) values[color.ordinal()];
    }

    public boolean contains(Item item) {
        for (ItemEntry<?> entry : values) {
            if (entry.is(item)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public ItemEntry<T>[] toArray() {
        return (ItemEntry<T>[]) Arrays.copyOf(values, values.length);
    }

    @Override
    public Iterator<ItemEntry<T>> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < values.length;
            }

            @SuppressWarnings("unchecked")
            @Override
            public ItemEntry<T> next() {
                if (!hasNext()) throw new NoSuchElementException();
                return (ItemEntry<T>) values[index++];
            }
        };
    }
}
