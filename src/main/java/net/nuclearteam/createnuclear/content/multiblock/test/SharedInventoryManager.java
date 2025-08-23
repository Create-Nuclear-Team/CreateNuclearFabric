package net.nuclearteam.createnuclear.content.multiblock.test;

import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class SharedInventoryManager {
    private static final Map<BlockPos, SharedReactorInventory> inventoryMap = new HashMap<>();

    public static SharedReactorInventory getOrCreate(BlockPos pos, int size) {
        return inventoryMap.computeIfAbsent(pos, p -> new SharedReactorInventory(size));
    }

    public static SharedReactorInventory get(BlockPos pos) {
        return inventoryMap.get(pos);
    }

    public static void remove(BlockPos pos) {
        inventoryMap.remove(pos);
    }
}
