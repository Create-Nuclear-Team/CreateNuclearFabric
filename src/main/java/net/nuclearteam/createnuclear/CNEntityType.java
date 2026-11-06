package net.nuclearteam.createnuclear;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.EntityEntry;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.storage.loot.LootTable;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.cat.IrradiatedCat;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.cat.IrradiatedCatRenderer;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.chicken.IrradiatedChicken;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.chicken.IrradiatedChickenRenderer;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.cow.IrradiatedCow;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.cow.IrradiatedCowRenderer;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.wolf.IrradiatedWolf;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.wolf.IrradiatedWolfRenderer;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.zombie.IrradiatedZombie;
import net.nuclearteam.createnuclear.content.contraptions.irradiated.zombie.IrradiatedZombieRenderer;
import net.nuclearteam.createnuclear.content.explosion.NuclearExplosionEntity;

public class CNEntityType {
    private static final CreateRegistrate REGISTRATE = CreateNuclear.registrate();


    public static final EntityEntry<IrradiatedCat> IRRADIATED_CAT = REGISTRATE
        .entity("irradiated_cat", IrradiatedCat::new, MobCategory.CREATURE)
        .loot((tb, e) -> tb.add(e, LootTable.lootTable()))
        .tag(CNTags.CNEntityTypeTags.IRRADIATED_IMMUNE.tag)
        .properties(b -> b.dimensions(EntityDimensions.scalable(0.6f, 0.7f)))
        .renderer(() -> IrradiatedCatRenderer::new)
        .lang("Irradiated Cat")
        .attributes(IrradiatedCat::createAttributes)
        .register();

    public static final EntityEntry<IrradiatedChicken> IRRADIATED_CHICKEN = REGISTRATE
        .entity("irradiated_chicken", IrradiatedChicken::new, MobCategory.CREATURE)
        .loot((tb, e) -> tb.add(e, LootTable.lootTable()))
        .tag(CNTags.CNEntityTypeTags.IRRADIATED_IMMUNE.tag)
        .properties(b -> b.dimensions(EntityDimensions.scalable(0.4f, 0.7f)))
        .renderer(() -> IrradiatedChickenRenderer::new)
        .lang("Irradiated Chicken")
        .attributes(IrradiatedChicken::createAttributes)
        .register();

    public static final EntityEntry<IrradiatedWolf> IRRADIATED_WOLF = REGISTRATE
        .entity("irradiated_wolf", IrradiatedWolf::new, MobCategory.CREATURE)
        .loot((tb, e) -> tb.add(e, LootTable.lootTable()))
        .tag(CNTags.CNEntityTypeTags.IRRADIATED_IMMUNE.tag)
        .properties(b -> b.dimensions(EntityDimensions.scalable(0.6f, 0.85f)))
        .renderer(() -> IrradiatedWolfRenderer::new)
        .lang("Irradiated Wolf")
        .attributes(IrradiatedWolf::createAttributes)
        .register();

    public static final EntityEntry<IrradiatedCow> IRRADIATED_COW = REGISTRATE
        .entity("irradiated_cow", IrradiatedCow::new, MobCategory.CREATURE)
        .loot((tb, e) -> tb.add(e, LootTable.lootTable()))
        .tag(CNTags.CNEntityTypeTags.IRRADIATED_IMMUNE.tag)
        .properties(b -> b.dimensions(EntityDimensions.scalable(0.6f, 0.85f)))
        .renderer(() -> IrradiatedCowRenderer::new)
        .lang("Irradiated Cow")
        .attributes(IrradiatedCow::createAttributes)
        .register();

    public static final EntityEntry<IrradiatedZombie> IRRADIATED_ZOMBIE = REGISTRATE
        .entity("irradiated_zombie", IrradiatedZombie::new, MobCategory.MONSTER)
        .loot((tb, e) -> tb.add(e, LootTable.lootTable()))
        .tag(CNTags.CNEntityTypeTags.IRRADIATED_IMMUNE.tag)
        .properties(b -> b.dimensions(EntityDimensions.scalable(0.6f, 1.95f)))
        .renderer(() -> IrradiatedZombieRenderer::new)
        .lang("Irradiated Zombie")
        .attributes(IrradiatedZombie::createAttributes)
        .register();

    public static final EntityEntry<NuclearExplosionEntity> NUCLEAR_EXPLOSION = REGISTRATE
        .entity("nuclear_explosion", NuclearExplosionEntity::new, MobCategory.MISC)
        .properties(b -> b
            .dimensions(EntityDimensions.fixed(0.2f, 0.2f))
            .trackRangeBlocks(128)
            .trackedUpdateRate(1)
            .forceTrackedVelocityUpdates(true))
        .renderer(() -> NoopRenderer::new)
        .register();

    public static void register() {
        CreateNuclear.LOGGER.info("Registering ModEntity for {}", CreateNuclear.MOD_ID);
    }

}
