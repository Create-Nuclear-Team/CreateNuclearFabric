package net.nuclearteam.createnuclear.content.multiblock.test;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SharedReactorInventory {
    private final List<ItemStack> inventory;
    private final ItemStackHandler itemHandler;
    private final ItemStackHandler itemHandlerEmpty;


    public SharedReactorInventory(int size) {
        itemHandler = new ItemStackHandler(size);
        inventory = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            inventory.add(ItemStack.EMPTY);
        }

        itemHandlerEmpty = new ItemStackHandler(size);
        for (int i = 0; i < size; i++) {
            itemHandlerEmpty.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public List<ItemStack> getInventory() {
        return inventory;
    }

    public ItemStackHandler getInventoryHandler() {
        return itemHandler;
    }

    public ItemStackHandler getInventoryHandlerEmpty() {
        return itemHandlerEmpty;
    }

    public void clearInventory() {
        inventory.clear();
    }

    public void read(CompoundTag tag) {
        ListTag list = tag.getList("Inventory", 10);
        for (int i = 0; i < list.size(); i++) {
            inventory.add(i, ItemStack.of(list.getCompound(i)));
        }
        itemHandler.deserializeNBT(tag.getCompound("Items"));

    }

    public void write(CompoundTag tag) {
        ListTag list = new ListTag();
        for (ItemStack itemStack : inventory) {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.put("Inventory", itemStack.save(new CompoundTag()));
            list.add(compoundTag);
        }

        tag.put("Inventory", list);
        tag.put("Items", itemHandler.serializeNBT());

    }
}
