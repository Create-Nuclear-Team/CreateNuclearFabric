package net.nuclearteam.createnuclear.content.multiblockRefactor.core;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.nuclearteam.createnuclear.CNEffects;
import net.nuclearteam.createnuclear.CreateNuclear;
import net.nuclearteam.createnuclear.content.multiblock.IHeat;
import net.nuclearteam.createnuclear.content.multiblock.controller.ReactorControllerBlockEntity;
import net.nuclearteam.createnuclear.infrastructure.config.CNConfigs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExplosionController {
    private int cooldownTicks = 0;
    private final Set<BlockPos> radiationZone = new HashSet<>();

    private float calculateExplosionRadius(int countUraniumRod) {
        return CNConfigs.common().explode.size.getF() + countUraniumRod;
    }

    private void applyRadiationEffects(Level level, BlockPos center, float radius) {
        AABB explosionArea = new AABB(
            center.getX() - radius, center.getY() - radius, center.getZ() - radius,
            center.getX() + radius, center.getY() + radius, center.getZ() + radius
        );

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, explosionArea);

        for (LivingEntity entity : entities) {
            double distance = entity.distanceToSqr(center.getX() + 0.5, center.getY() + 0.5, center.getZ() + 0.5);
            if (distance <= radius * radius) {
                entity.addEffect(new MobEffectInstance(CNEffects.RADIATION.get(), 20*20, 1));
            }
        }
    }

    private void explodeReactorCore(Level level, BlockPos pos, float radius) {
        level.explode(null, pos.getX(), pos.getY(), pos.getZ(), radius, Level.ExplosionInteraction.BLOCK);
    }

    public void calculate(ReactorControllerBlockEntity reactorController, BlockPos pos) {
        int heat = (int) reactorController.configuredPattern.getOrCreateTag().getDouble("heat");
        if (IHeat.HeatLevel.of(heat) == IHeat.HeatLevel.DANGER) {
            if (cooldownTicks >= CNConfigs.common().explode.time.get()) { // 300 ticks = 15 secondes
                float explosionRadius = calculateExplosionRadius(reactorController.countUraniumRod);
                explodeReactorCore(Minecraft.getInstance().level, pos, explosionRadius);
            } else {
                cooldownTicks++;
                CreateNuclear.LOGGER.warn("Countdown: {} ticks", cooldownTicks);
            }
        } else {
            cooldownTicks = 0;
        }
    }
}
