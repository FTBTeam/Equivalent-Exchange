package net.creeperhost.equivalentexchange.server.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.impl.KnowledgeHandler;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.List;

public class CommandKnowledge
{
    public static LiteralArgumentBuilder<CommandSourceStack> register(CommandBuildContext commandBuildContext)
    {
        return Commands.literal("knowledge").requires(cs -> cs.hasPermission(3))
                .then(Commands.argument("targets", EntityArgument.players()).executes(cs -> CommandKnowledge.execute(cs, EntityArgument.getPlayers(cs, "targets"), "list", new ItemInput(ItemStack.EMPTY.getItem().arch$holder(), new CompoundTag())))
                .then(Commands.argument("arg1", StringArgumentType.string()).executes(cs -> CommandKnowledge.execute(cs, EntityArgument.getPlayers(cs, "targets"), StringArgumentType.getString(cs, "arg1"), new ItemInput(ItemStack.EMPTY.getItem().arch$holder(), new CompoundTag())))
                .then(Commands.argument("item", ItemArgument.item(commandBuildContext)).executes(cs -> CommandKnowledge.execute(cs, EntityArgument.getPlayers(cs, "targets"), StringArgumentType.getString(cs, "arg1"), ItemArgument.getItem(cs, "item"))))));
    }


    private static int execute(CommandContext<CommandSourceStack> cs, Collection<ServerPlayer> targets, String arg1, ItemInput item)
    {
        switch (arg1)
        {
            case "list":
                for (ServerPlayer target : targets)
                {
                    List<ItemStack> list = EquivalentExchangeAPI.iKnowledgeHandler.getKnowledgeList(target);
                    if(list.isEmpty())
                    {
                        cs.getSource().sendFailure(Component.literal("Knowledge list is empty"));
                    }
                    for (ItemStack stack : list)
                    {
                        if(!stack.isEmpty())
                            cs.getSource().sendSuccess(() -> Component.literal(stack.getItem().getName(stack).getString()), false);
                    }
                }
                break;
            case "add":
                for (ServerPlayer target : targets)
                {
                    ItemStack stack = ItemStack.EMPTY;
                    try
                    {
                        stack = item.createItemStack(1, false);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    if(!stack.isEmpty())
                    {
                        EquivalentExchangeAPI.getKnowledgeHandler().addKnowledge(target, stack);
                        ItemStack finalStack = stack;
                        cs.getSource().sendSuccess(() -> Component.literal("Added knowledge for " + finalStack.getItem().getName(finalStack).getString() + " to " + target.getDisplayName().getString()), false);
                    }
                    else
                    {
                        cs.getSource().sendFailure(Component.literal("ItemStack.EMPTY is not a valid stack, Skipping"));
                    }
                }
                break;
            default:
                cs.getSource().sendFailure(Component.literal("Invalid argument"));
                break;
        }
        return 0;
    }
}
