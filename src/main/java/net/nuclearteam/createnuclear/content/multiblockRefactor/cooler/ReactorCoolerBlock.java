package net.nuclearteam.createnuclear.content.multiblockRefactor.cooler;

import net.nuclearteam.createnuclear.content.multiblockRefactor.TypeBlocks;
import net.nuclearteam.createnuclear.content.multiblockRefactor.casing.ReactorCasingBlock;

public class ReactorCoolerBlock extends ReactorCasingBlock {
    public ReactorCoolerBlock(Properties properties, TypeBlocks typeBlocks) {
        super(properties, typeBlocks);
    }

    public static ReactorCoolerBlock create(Properties properties) {
        return new ReactorCoolerBlock(properties, TypeBlocks.COOLER);
    }
}
