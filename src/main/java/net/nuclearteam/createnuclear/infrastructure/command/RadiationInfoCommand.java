package net.nuclearteam.createnuclear.infrastructure.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.nuclearteam.createnuclear.content.radiation.capability.IRadiationCapability;
import net.nuclearteam.createnuclear.foundation.utility.CreateNuclearLang;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class RadiationInfoCommand {
    private static final String ARGUMENT_NAME = "targets";

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("radiationInfo")
            .requires(cs -> cs.hasPermission(2))
            .then(Commands
                .argument(ARGUMENT_NAME, EntityArgument.entities())
                .executes(RadiationInfoCommand::runInfos)
            )
        ;
    }

    private static int runInfos(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        final int COUNT_CARACTERE = 40;
        Collection<? extends Entity> targets = EntityArgument.getEntities(ctx, ARGUMENT_NAME);

        for (Entity target : targets) {
            // Radiation state lives on LivingEntity through LivingEntityRadiationMixin.
            if (!(target instanceof IRadiationCapability radiationCapability)) continue;

            MutableComponent component = CreateNuclearLang.builder()
                .add(Component.literal("=".repeat(COUNT_CARACTERE)))
                .newLine()
                .add(CreateNuclearLang.translate("command.radiation.info.entity", target.getDisplayName()))
                .newLine()
                .add(CreateNuclearLang.translate("command.radiation.info.radiation", radiationCapability.getRadiation()))
                .newLine()
                .add(CreateNuclearLang.translate("command.radiation.info.last.biome", convertResourceLocationToTranslateBiome(radiationCapability.getLastBiomeLocation())))
                .newLine()
                .add(CreateNuclearLang.translate("command.radiation.info.contagion.dose", radiationCapability.getContagionDose()))
                .newLine()
                .add(CreateNuclearLang.translate("command.radiation.info.contagion.ticks", radiationCapability.getContagionTicks()))
                .newLine()
                .add(Component.literal("=".repeat(COUNT_CARACTERE)))
                .component();

            ctx.getSource().sendSuccess(() -> component, false);
        }

        return Command.SINGLE_SUCCESS;
    }

    private static Component convertResourceLocationToTranslateBiome(@Nullable ResourceLocation biomeLoc) {
        return biomeLoc != null
            ? Component.translatable(String.join(".", "biome", biomeLoc.getNamespace(), biomeLoc.getPath()))
            : CreateNuclearLang.translateDirect("command.radiation.info.last.biome.unknown")
        ;
    }
}
