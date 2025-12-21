package net.nuclearteam.createnuclear;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.nuclearteam.createnuclear.content.enriching.campfire.EnrichingCampfireBlockEntity;
import net.nuclearteam.createnuclear.content.multiblock.casing.ReactorCasingBlockEntity;
import net.nuclearteam.createnuclear.content.multiblock.controller.ReactorControllerBlockEntity;
import net.nuclearteam.createnuclear.content.multiblock.core.ReactorCoreBlockEntity;
import net.nuclearteam.createnuclear.content.multiblock.input.ReactorInputEntity;
import net.nuclearteam.createnuclear.content.multiblock.output.ReactorOutputEntity;
import net.nuclearteam.createnuclear.content.multiblock.output.ReactorOutputRenderer;


public class CNBlockEntityTypes {
    private static final CreateRegistrate REGISTRATE = CreateNuclear.registrate();

    public static final BlockEntityEntry<ReactorControllerBlockEntity> REACTOR_CONTROLLER =
            REGISTRATE.blockEntity("reactor_controller", ReactorControllerBlockEntity::new)
            .validBlocks(CNBlocks.REACTOR_CONTROLLER)
            .register();

    public static final BlockEntityEntry<ReactorInputEntity> REACTOR_INPUT =
            REGISTRATE.blockEntity("reactor_input", ReactorInputEntity::new)
                    .validBlocks(CNBlocks.REACTOR_INPUT)
                    .register();

    public static final BlockEntityEntry<ReactorOutputEntity> REACTOR_OUTPUT =
            REGISTRATE.blockEntity("reactor_output", ReactorOutputEntity::new)
                    .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF), false)
                    .validBlocks(CNBlocks.REACTOR_OUTPUT)
                    .renderer(() -> ReactorOutputRenderer::new)
                    .register();

    public static final BlockEntityEntry<EnrichingCampfireBlockEntity> ENRICHING_CAMPFIRE_BLOCK =
            REGISTRATE.blockEntity("enriching_campfire_block", EnrichingCampfireBlockEntity::new)
                    .validBlocks(CNBlocks.ENRICHING_CAMPFIRE)
                    .register();

    public static final BlockEntityEntry<ReactorCasingBlockEntity> REACTOR_CASING =
            REGISTRATE.blockEntity("reactor_casing", ReactorCasingBlockEntity::new)
                    .validBlocks(CNBlocks.REACTOR_CASING)
                    .register();

    public static final BlockEntityEntry<ReactorCoreBlockEntity> REACTOR_CORE =
            REGISTRATE.blockEntity("reactor_core", ReactorCoreBlockEntity::new)
                    .validBlocks(CNBlocks.REACTOR_CORE)
                    .register();


    public static void register() {
    }
}
