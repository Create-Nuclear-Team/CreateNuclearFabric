package net.nuclearteam.createnuclear.content.multiblockRefactor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.nuclearteam.createnuclear.CNBlocks;
import net.nuclearteam.createnuclear.content.multiblock.controller.ReactorControllerBlock;
import net.nuclearteam.createnuclear.content.multiblock.controller.ReactorControllerBlockEntity;

import java.util.List;

public class FindController {
    private Block controller = CNBlocks.REACTOR_CONTROLLER.get();;
    public ReactorControllerBlock findController(BlockPos pos, Level level, List<? extends Player> players, boolean first) {
        BlockPos newBlock;
        Vec3i vpos = new Vec3i(pos.getX(), pos.getY(), pos.getZ());

        for (int y = vpos.getY() -3; y != vpos.getY()+4; y+=1) {
            for (int x = vpos.getX()-5; x != vpos.getX()+5; x+=1) {
                for (int z = vpos.getZ()-5; z != vpos.getZ()+5; z++) {
                    newBlock = new BlockPos(x, y, z);
                    if (level.getBlockState(newBlock).is(this.controller)) {
                        ReactorControllerBlock controllerBlock = (ReactorControllerBlock) level.getBlockState(newBlock).getBlock();
                        controllerBlock.verify(level.getBlockState(newBlock), newBlock, level, players, first);
                        ReactorControllerBlockEntity entity = (ReactorControllerBlockEntity) controllerBlock.getBlockEntity(level, newBlock);
                        if (entity.created) {
                            return controllerBlock;
                        }
                    }
                }
            }
        }
        return null;
    }
    public ReactorControllerBlock findController(BlockPos pos, Level level, List<? extends Player> players) {
        BlockPos newBlock;
        Vec3i vpos = new Vec3i(pos.getX(), pos.getY(), pos.getZ());
        newBlock = new BlockPos(vpos.getX(), vpos.getY() +3, vpos.getZ());
        if (level.getBlockState(newBlock).is(this.controller)) {
            ReactorControllerBlock controllerBlock = (ReactorControllerBlock) level.getBlockState(newBlock).getBlock();
            controllerBlock.verify(level.getBlockState(newBlock), newBlock, level, players, false);
            ReactorControllerBlockEntity entity = controllerBlock.getBlockEntity(level, newBlock);
            if (entity.created) {
                return controllerBlock;
            }
        }
        return null;
    }
}
