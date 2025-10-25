package net.nuclearteam.createnuclear.content.multiblockRefactor;

import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.nuclearteam.createnuclear.CNBlockEntityTypes;
import net.nuclearteam.createnuclear.content.multiblockRefactor.casing.ReactorCasingBlockEntity;


public enum TypeBlocks implements StringRepresentable {
    CASING,
    CONTROLLER,
    COOLER,
    CORE,
    FRAME,
    INPUT,
    OUTPUT,
    REINFORCED
    ;

    @MethodsReturnNonnullByDefault
    @Override
    public String getSerializedName() {
        return Lang.asId(name());
    }

    public BlockEntityType<? extends ReactorCasingBlockEntity> getBlockEntityType() {
        return switch (this) {
            case CASING -> CNBlockEntityTypes.REACTOR_CASING.get();
            case CONTROLLER -> CNBlockEntityTypes.REACTOR_CONTROLLER.get();
            case COOLER, FRAME, REINFORCED -> null;
            case CORE -> CNBlockEntityTypes.REACTOR_CORE.get();
            case INPUT -> CNBlockEntityTypes.REACTOR_INPUT.get();
            case OUTPUT -> CNBlockEntityTypes.REACTOR_OUTPUT.get();
        };
    }

    public Class<ReactorCasingBlockEntity> getClassBlockEntityType() {
        return switch (this) {
            case CASING -> ReactorCasingBlockEntity.class;
//            case CONTROLLER -> CNBlockEntityTypes.REACTOR_CONTROLLER.get();
            case COOLER, FRAME, REINFORCED -> null;
            case CORE -> ReactorCasingBlockEntity.class;
//            case INPUT -> CNBlockEntityTypes.REACTOR_INPUT.get();
//            case OUTPUT -> CNBlockEntityTypes.REACTOR_OUTPUT.get();
        };
    }
}
