package net.nuclearteam.createnuclear.content.multiblockRefactor.casing;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.nuclearteam.createnuclear.CNBlocks;
import net.nuclearteam.createnuclear.CreateNuclear;
import net.nuclearteam.createnuclear.content.multiblock.input.ReactorInput;
import net.nuclearteam.createnuclear.content.multiblockRefactor.FindController;
import net.nuclearteam.createnuclear.content.multiblockRefactor.TypeBlocks;
import org.jetbrains.annotations.Nullable;

public class ReactorCasingBlock extends Block implements IWrenchable, IBE<ReactorCasingBlockEntity> {
    private final TypeBlocks typeBlocks;
    protected FindController findController = new FindController();

    public ReactorCasingBlock(Properties properties, TypeBlocks typeBlocks) {
        super(properties);
        this.typeBlocks = typeBlocks;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        CreateNuclear.LOGGER.info("ReactorBlock.onPlace");
        this.findController.findController(pos, level, level.players(), true);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        this.findController.findController(pos, level, level.players(), false);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        this.findController.findController(pos, level, level.players(), false);
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();

//        if (player.getItemInHand(InteractionHand.OFF_HAND).is(Blocks.HOPPER.asItem())) {
//            level.setBlock(pos, CNBlocks.REACTOR_INPUT.getDefaultState().setValue(ReactorInput.FACING, context.getClickedFace()), 2);
//            player.sendSystemMessage(Component.translatable("reactor.update.casing.input"));
//        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public Class<ReactorCasingBlockEntity> getBlockEntityClass() {
        return typeBlocks.getClassBlockEntityType();
    }

    @Override
    public BlockEntityType<? extends ReactorCasingBlockEntity> getBlockEntityType() {
        return typeBlocks.getBlockEntityType();
    }
}
