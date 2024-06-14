package net.creeperhost.equivalentexchange.server.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class CommandEmcReset
{
    public static LiteralArgumentBuilder<CommandSourceStack> register()
    {
        return Commands.literal("emc_reset").requires(cs -> cs.hasPermission(3))
                .then(Commands.argument("targets", EntityArgument.players()).executes(cs -> CommandEmcReset.execute(cs, EntityArgument.getPlayers(cs, "targets"))));
    }

    private static int execute(CommandContext<CommandSourceStack> cs, Collection<ServerPlayer> targets)
    {
        targets.forEach(serverPlayer -> EquivalentExchangeAPI.getStorageHandler().setEmcValueFor(serverPlayer, 0));
        return 0;
    }

}
