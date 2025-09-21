package net.nuclearteam.createnuclear.foundation.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.world.level.LevelAccessor;
import net.nuclearteam.createnuclear.CreateNuclear;

import java.util.concurrent.Executor;

public class CommonEvents {
    public static void onLoadWorld(Executor executor, LevelAccessor world) {
        CreateNuclear.TEST_PROPA.onLoadWorld(world);
    }

    public static void onUnloadWorld(Executor executor, LevelAccessor world) {
        CreateNuclear.TEST_PROPA.onUnloadWorld(world);
    }

    public static void register() {
        ServerWorldEvents.LOAD.register(CommonEvents::onLoadWorld);
        ServerWorldEvents.UNLOAD.register(CommonEvents::onUnloadWorld);
    }
}
