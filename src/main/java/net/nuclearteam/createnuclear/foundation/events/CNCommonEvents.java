package net.nuclearteam.createnuclear.foundation.events;

import net.nuclearteam.createnuclear.infrastructure.command.CNCommands;

public class CNCommonEvents {
    public static void register() {
        // Living-entity fluid effects are injected alongside the persistent radiation state.
        CNCommands.register();
    }
}
