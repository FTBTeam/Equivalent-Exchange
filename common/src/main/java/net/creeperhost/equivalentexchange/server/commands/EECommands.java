package net.creeperhost.equivalentexchange.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class EECommands
{
    public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registry, Commands.CommandSelection selection)
    {
        register(dispatcher, registry);
        //TODO
//        commandSourceStackCommandDispatcher.register(CommandKnowledge.register());
        dispatcher.register(CommandEmcReset.register());
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext) {
        dispatcher.register(
                LiteralArgumentBuilder.<CommandSourceStack>literal("ee")
                        .then(CommandEmcReset.register())
                        .then(CommandKnowledge.register(buildContext)));
    }
}
