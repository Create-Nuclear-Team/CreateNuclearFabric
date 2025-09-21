package net.nuclearteam.createnuclear.foundation.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.AABB;
import net.nuclearteam.createnuclear.CNEffects;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.cat.IrradiatedCat;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.chicken.IrradiatedChicken;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.wolf.IrradiatedWolf;
import net.nuclearteam.createnuclear.infrastructure.worldgen.biome.CNBiomes;

public class CommonEvent {

    public static void register() {
        ServerTickEvents.START_WORLD_TICK.register(CommonEvent::onWorldTick);
    }

    private static void onWorldTick(Level world) {
        if (!world.isClientSide()) {
            world.players().forEach(player -> {
                if (player.tickCount % 20 == 0) return;
                Holder<Biome> biome = world.getBiome(player.getOnPos());
                if (biome.is(CNBiomes.Irradiated.PLAIN)){
                    //player.addEffect(new MobEffectInstance(CNEffects.RADIATION.get(),600,2));
                }

            });
        }
    }
}
