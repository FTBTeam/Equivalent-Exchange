package net.creeperhost.equivalentexchange.impl;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.inventory.TransmutationInventory;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.LevelResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TransmutationTableHandler
{
    private static final HashMap<String, TransmutationInventory> INVENTORIES = new HashMap<>();

    public static TransmutationInventory getTransmutationInventory(Player player)
    {
        if(!INVENTORIES.containsKey(player.getUUID().toString()))
        {
            TransmutationInventory transmutationInventory = new TransmutationInventory(player);
            INVENTORIES.put(player.getUUID().toString(), transmutationInventory);
        }
        return INVENTORIES.get(player.getUUID().toString());
    }

    public static void updateInventory(Player player, TransmutationInventory transmutationInventory)
    {
        INVENTORIES.put(player.getUUID().toString(), transmutationInventory);
    }

    public static NonNullList<ItemStack> getTransmutationContent(int page, String search, Player player)
    {
        NonNullList<ItemStack> itemStacks = NonNullList.withSize(16, ItemStack.EMPTY);
        List<ItemStack> knowledge = new ArrayList<>(EquivalentExchangeAPI.getKnowledgeHandler().getKnowledgeList(player));

        int pageSkip = 0;
        if(page > 0) pageSkip = 16 * page;
        int skipCount = 0;

        if(!search.isEmpty())
        {
            List<ItemStack> copy = new ArrayList<>();
            for (ItemStack stack : knowledge)
            {
                if(stack.getItem().getName(stack).getString().toLowerCase(Locale.ROOT).contains(search))
                {
                    skipCount++;
                    if(skipCount < pageSkip) continue;
                    copy.add(stack.copy());
                }
            }

            knowledge = copy;
        }

        knowledge.sort((o1, o2) ->
        {
            double emc1 = EquivalentExchangeAPI.getEmcValue(o1);
            double emc2 = EquivalentExchangeAPI.getEmcValue(o2);
            return Double.compare(emc2, emc1);
        });

        int i = 0;
        double playersEMC = EquivalentExchangeAPI.getStorageHandler().getEmcValueFor(player);
        for (ItemStack stack : knowledge)
        {
            if(stack.isEmpty() && i <= 15 || EquivalentExchangeAPI.hasEmcValue(stack) && playersEMC >= EquivalentExchangeAPI.getEmcValue(stack) && i <= 15)
            {
                if(pageSkip > 0) {
                    skipCount++;
                    if (skipCount < pageSkip) continue;
                }
                stack.setCount(1);
                itemStacks.set(i, stack);
                if(i >= 15)
                {
                    break;
                }
                i++;
            }
        }
        return itemStacks;
    }

    public static void clear()
    {
        INVENTORIES.clear();
    }
}
