package net.nuclearteam.createnuclear.content.multiblockRefactor.core;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.nuclearteam.createnuclear.CNBlocks;
import net.nuclearteam.createnuclear.content.multiblock.controller.ReactorControllerBlockEntity;
import net.nuclearteam.createnuclear.content.multiblockRefactor.casing.ReactorCasingBlockEntity;

public class ReactorCoreBlockEntity extends ReactorCasingBlockEntity {
    public ReactorCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide()) return;
        ExplosionController explosionController = new ExplosionController();

        BlockPos controllerPos = getBlockPosForReactor();
        if (level.getBlockEntity(controllerPos) instanceof ReactorControllerBlockEntity reactorCoreBlock) {
            explosionController.calculate(reactorCoreBlock, getBlockPos());
        }
    }

    private BlockPos getBlockPosForReactor() {
        BlockPos pos = this.getBlockPos();
        BlockPos posInput = new BlockPos(pos.getX(), pos.getY(), pos.getZ());

        int[][][] directions = {
                {{0, 2, 2}, {0, 1, 2}, {0, 0, 2}, {0, -1, 2}, {0, -2, 2}}, // NORTH
                {{0, 2, -2}, {0, 1, -2}, {0, 0, -2}, {0, -1, -2}, {0, -2, -2}}, // SOUTH

                {{2, 2, 0}, {2, 1, 0}, {2, 0, 0}, {2, -1, 0}, {2, -2, 0}}, // EAST
                {{-2, 2, 0}, {-2, 1, 0}, {-2, 0, 0}, {-2, -1, 0}, {-2, -2, 0}} // WEST
        };

        for (int[][] direction : directions) {
            for (int[] dir :  direction) {
                BlockPos newPos = pos.offset(dir[0], dir[1], dir[2]);
                if (level.getBlockState(newPos).is(CNBlocks.REACTOR_CONTROLLER.get())) {
                    posInput = newPos;
                    break;
                }
            }
        }

        return posInput;
    }
}
