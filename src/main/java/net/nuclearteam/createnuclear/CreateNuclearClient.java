package net.nuclearteam.createnuclear;


import net.createmod.ponder.foundation.PonderIndex;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.resources.ResourceLocation;
import net.nuclearteam.createnuclear.content.biome.BiomeIrradiationExtractorItem;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.CNModelLayers;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.cat.IrradiatedCatModel;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.chicken.IrradiatedChickenModel;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.cow.IrradiatedCowModel;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.wolf.IrradiatedWolfModel;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.zombie.IrradiatedZombieModel;
import net.nuclearteam.createnuclear.content.equipment.armor.AntiRadiationArmorModel;
import net.nuclearteam.createnuclear.content.equipment.armor.AntiRadiationArmorRenderer;
import net.nuclearteam.createnuclear.content.particles.IrradiatedParticles;
import net.nuclearteam.createnuclear.content.particles.IrradiatedParticlesData;
import net.nuclearteam.createnuclear.content.particles.NuclearMushroomCloudParticle;
import net.nuclearteam.createnuclear.content.particles.SmallNuclearExplosionParticle;
import net.nuclearteam.createnuclear.foundation.events.CNClientEvent;
import net.nuclearteam.createnuclear.foundation.events.ClientEvents;
import net.nuclearteam.createnuclear.foundation.events.RodsTooltipHandler;
import net.nuclearteam.createnuclear.foundation.ponder.CreateNuclearPonderPlugin;
import net.nuclearteam.createnuclear.foundation.utility.ClothTagHelper;
import net.nuclearteam.createnuclear.content.multiblock.input.item.ReactorRodInputClient;

import static net.nuclearteam.createnuclear.CNPackets.getChannel;

@Environment(EnvType.CLIENT)
public class CreateNuclearClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
       ReactorRodInputClient.register();
       registerItemProperties();
       registerParticles();
       registerModelLayers();

        PonderIndex.addPlugin(new CreateNuclearPonderPlugin());

       getChannel().initClientListener();
       CNClientEvent.register();
       ClientEvents.register();
       RodsTooltipHandler.register();

        ArmorRenderer.register(
                new AntiRadiationArmorRenderer(),
                CNItems.ANTI_RADIATION_HELMETS.get(),
                CNItems.ANTI_RADIATION_CHESTPLATES.get(),
                CNItems.ANTI_RADIATION_LEGGINGS.get(),
                CNItems.ANTI_RADIATION_BOOTS.get()
        );
    }

    private static void registerItemProperties() {
        ResourceLocation clothColor = CreateNuclear.asResource("cloth_color");
        ClampedItemPropertyFunction colorProvider = (stack, world, entity, seed) -> {
            DyeColor dye = DyeColor.byName(ClothTagHelper.getClothColor(stack, "default"), null);
            return dye == null ? 0f : (dye.getId() + 1) / 16f;
        };
        ItemProperties.register(CNItems.ANTI_RADIATION_HELMETS.get(), clothColor, colorProvider);
        ItemProperties.register(CNItems.ANTI_RADIATION_CHESTPLATES.get(), clothColor, colorProvider);
        ItemProperties.register(CNItems.ANTI_RADIATION_LEGGINGS.get(), clothColor, colorProvider);
        ItemProperties.register(CNItems.ANTI_RADIATION_BOOTS.get(), clothColor, colorProvider);

        ClampedItemPropertyFunction chargeProvider = (stack, world, entity, seed) -> {
            int maxCharge = BiomeIrradiationExtractorItem.getMaxCharge();
            return maxCharge <= 0 ? 0f : (float) BiomeIrradiationExtractorItem.getChargeTag(stack, 0) / maxCharge;
        };
        ItemProperties.register(CNItems.IRRADIATION_BIOME_EXTRACTOR.get(),
                CreateNuclear.asResource(BiomeIrradiationExtractorItem.TAG), chargeProvider);
    }

    private static void registerModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(CNModelLayers.IRRADIATED_CHICKEN, IrradiatedChickenModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CNModelLayers.IRRADIATED_WOLF, IrradiatedWolfModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CNModelLayers.IRRADIATED_CAT, IrradiatedCatModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CNModelLayers.IRRADIATED_COW, IrradiatedCowModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CNModelLayers.IRRADIATED_ZOMBIE, IrradiatedZombieModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(CNModelLayers.ANTI_IRRADIATION_ARMOR, AntiRadiationArmorModel::createBodyLayer);
    }

    @SuppressWarnings("unchecked")
    private static void registerParticles() {
        ParticleFactoryRegistry particles = ParticleFactoryRegistry.getInstance();
        particles.register(CNParticleRegistry.NUCLEAR_MUSHROOM_CLOUD.get(), new NuclearMushroomCloudParticle.Factory());
        particles.register(CNParticleRegistry.NUCLEAR_MUSHROOM_CLOUD_SMOKE.get(), SmallNuclearExplosionParticle.NukeFactory::new);
        particles.register(CNParticleRegistry.NUCLEAR_MUSHROOM_CLOUD_EXPLOSION.get(), SmallNuclearExplosionParticle.NukeFactory::new);
        particles.register((ParticleType<IrradiatedParticlesData>) CNParticleTypes.IRRADIATED_PARTICLES.get(), IrradiatedParticles.Provider::new);
    }
}
