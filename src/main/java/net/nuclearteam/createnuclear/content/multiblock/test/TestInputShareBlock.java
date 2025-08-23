package net.nuclearteam.createnuclear.content.multiblock.test;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.nuclearteam.createnuclear.CNBlockEntityTypes;

import java.util.UUID;

public class TestInputShareBlock extends Block implements IBE<TestInputShareBlockEntity> {
    public TestInputShareBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Class<TestInputShareBlockEntity> getBlockEntityClass() {
        return TestInputShareBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends TestInputShareBlockEntity> getBlockEntityType() {
        return CNBlockEntityTypes.TEST_INPUT_SHARE_BLOCk_ENTITY.get();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        UUID uuid = UUID.randomUUID();
        if (stack.is(Items.PAPER) && !stack.getOrCreateTag().contains("nbt")) {
            CompoundTag tag = new CompoundTag();
            tag.putString("uuid", uuid.toString());
            tag.putInt("x", pos.getX());
            tag.putInt("y", pos.getY());
            tag.putInt("z", pos.getZ());
            stack.getOrCreateTag().put("nbt", tag);
           return InteractionResult.SUCCESS;
        } else if (stack.is(Items.PAPER) && stack.getOrCreateTag().contains("nbt")) {
            CompoundTag tag = stack.getOrCreateTag().getCompound("nbt");
            BlockPos posTag = new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
            withBlockEntityDo(level, pos, p -> {
                p.pos = posTag;
                p.uuid =  UUID.fromString(tag.getString("uuid"));
                p.sharedInventory = SharedInventoryManager.getOrCreate(posTag, 9);
            });
        }
        return InteractionResult.PASS;
    }
}
