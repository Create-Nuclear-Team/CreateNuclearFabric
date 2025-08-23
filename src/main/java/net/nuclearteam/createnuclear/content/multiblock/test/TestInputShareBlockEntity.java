package net.nuclearteam.createnuclear.content.multiblock.test;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class TestInputShareBlockEntity extends SmartBlockEntity implements SidedStorageBlockEntity {
    public SharedReactorInventory sharedInventory;
    public BlockPos pos;
    public UUID uuid;

    public TestInputShareBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        if (sharedInventory == null) {
            sharedInventory = SharedInventoryManager.getOrCreate(pos, 9);
        }
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
    }

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        sharedInventory.read(tag);
    }

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        sharedInventory.write(tag);
    }

    @Override
    public @Nullable Storage<ItemVariant> getItemStorage(Direction side) {
        return sharedInventory.getInventoryHandler();
    }
}
