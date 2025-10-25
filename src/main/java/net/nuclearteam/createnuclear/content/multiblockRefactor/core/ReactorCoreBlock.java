package net.nuclearteam.createnuclear.content.multiblockRefactor.core;

import net.nuclearteam.createnuclear.content.multiblockRefactor.TypeBlocks;
import net.nuclearteam.createnuclear.content.multiblockRefactor.casing.ReactorCasingBlock;

public class ReactorCoreBlock extends ReactorCasingBlock {
    public ReactorCoreBlock(Properties properties, TypeBlocks typeBlocks) {
        super(properties, typeBlocks);
    }

    public static ReactorCoreBlock create(Properties properties) {
        return new ReactorCoreBlock(properties, TypeBlocks.CORE);
    }
}
