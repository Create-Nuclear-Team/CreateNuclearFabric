package net.nuclearteam.createnuclear.foundation.ponder;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.nuclearteam.createnuclear.CNBlocks;
import net.nuclearteam.createnuclear.CNItems;

public class CNPonderIndex {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        // Reactor
        HELPER.forComponents(CNBlocks.REACTOR_CONTROLLER)
                .addStoryBoard("reactor/setup", CNPonderReactor::init)
                .addStoryBoard("reactor/setup", CNPonderReactor::enable);


        HELPER.forComponents(CNItems.REACTOR_BLUEPRINT)
                .addStoryBoard("reactor/setup", CNPonderReactor::enable);

        ;

    }
}
