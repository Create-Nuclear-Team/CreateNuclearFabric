package net.nuclearteam.createnuclear.foundation.block;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.nuclearteam.createnuclear.content.multiblockRefactor.TypeBlocks;
import net.nuclearteam.createnuclear.content.multiblockRefactor.casing.ReactorCasingBlock;

@MethodsReturnNonnullByDefault
@SuppressWarnings("deprecation")
public abstract class HorizontalDirectionalReactorBlock extends ReactorCasingBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public HorizontalDirectionalReactorBlock(Properties properties, TypeBlocks typeBlocks) {
        super(properties,  typeBlocks);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
