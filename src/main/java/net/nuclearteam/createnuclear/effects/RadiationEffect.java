package net.nuclearteam.createnuclear.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.nuclearteam.createnuclear.effects.damageTypes.CNDamageSources;
import net.nuclearteam.createnuclear.item.armor.AntiRadiationArmorItem;
import net.nuclearteam.createnuclear.tags.CNTag;

/**
 * Represents the Radiation Effect applied to a LivingEntity.
 * This effect harms entities by modifying attributes like movement speed, attack damage, and attack speed,
 * and applies damage over time unless mitigated by anti-radiation armor or immunity.
 */
import java.util.stream.StreamSupport;

import net.minecraft.world.item.ItemStack;
import net.nuclearteam.createnuclear.CreateNuclear;

/**
 * Represents a harmful radiation status effect applied to living entities.
 * This effect reduces movement speed, attack damage, and attack speed,
 * and periodically inflicts magic damage unless the entity is immune
 * or wearing anti-radiation armor.
 */
public class RadiationEffect extends MobEffect {

    /**
     * Constructor for the RadiationEffect.
     * Initializes the effect with a harmful category and a specific color.
     * It also adds attribute modifiers to reduce movement speed, attack damage, and attack speed.
     */

    /**
     * Constructs the RadiationEffect with harmful category and color.
     * Also applies attribute modifiers to reduce speed, attack damage, and attack speed.
     */
    public RadiationEffect() {
        super(MobEffectCategory.HARMFUL, 15453236);  // The color code is arbitrary for this effect's visual representation
        // Attribute modifiers reduce movement speed, attack damage, and attack speed by 20% each
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", -0.2D, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", -0.2D, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", -0.2D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    /**
     * Determines whether the effect should be applied every tick.
     * In this case, the effect is applied every tick as long as the duration is greater than 0.
     *
     * @param duration The remaining duration of the effect.
     * @param amplifier The amplifier level of the effect.
     * @return True, meaning this effect will trigger every tick.
     */
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    /**
     * Applies the radiation effect to a LivingEntity.
     * It checks if the entity has anti-radiation armor, immunity to radiation, or applies damage otherwise.
     * <p>
     * The entity will not take damage if they have anti-radiation armor,
     * but will receive magic damage over time otherwise. The damage is amplified based on the effect level.
     *
     * @param livingEntity The entity affected by the radiation.
     * @param amplifier The amplifier level of the effect, determining the damage.
     */
    /**
     * Applies the radiation effect to the entity.
     * - Does nothing if the entity is immune via tag.
     * - Skips damage if the entity wears any anti-radiation armor.
     * - Otherwise, applies magic damage based on the amplifier.
     *
     * @param livingEntity The affected living entity.
     * @param amplifier    The strength (level) of the effect.
     */
    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        // If the entity is immune to radiation, remove the effect
        if (livingEntity.getType().is(CNTag.EntityTypeTags.IRRADIATED_IMMUNE.tag)) {
            livingEntity.removeEffect(this);
            return;
        }

        // Check if the entity is wearing any anti-radiation armor
        boolean isWearingAntiRadiationArmor = false;
        for (ItemStack armor : livingEntity.getArmorSlots()) {
            if (AntiRadiationArmorItem.Armor.isArmored(armor)) {
                isWearingAntiRadiationArmor = true;
                break;
            }
        }

        // If protected by armor, do not apply damage
        if (isWearingAntiRadiationArmor) {
            return;
        }

        // Apply radiation damage (magic type), scaled by amplifier
        int damage = 1 << amplifier;
        livingEntity.hurt(CNDamageSources.RADIANCE.create(livingEntity.level()), damage);
    }
}