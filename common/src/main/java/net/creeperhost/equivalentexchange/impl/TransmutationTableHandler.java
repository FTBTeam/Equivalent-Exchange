package net.creeperhost.equivalentexchange.impl;

import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.inventory.TransmutationInventory;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

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

    public static NonNullList<ItemStack> getTransmutationContent(int page, String search, ItemStack emcTarget, Player player)
    {
        int PAGE_SIZE = 16;
        List<ItemStack> knowledge = new ArrayList<>(EquivalentExchangeAPI.getKnowledgeHandler().getKnowledgeList(player));

        knowledge.removeIf(stack -> !EquivalentExchangeAPI.hasEmcValue(stack)); // might occur on init and/or after major changes
        // TODO corrupt/garbage knowledge deletion

        if(!search.isEmpty())
            knowledge.removeIf(stack -> !stack.getItem().getName(stack).getString().toLowerCase(Locale.ROOT).contains(search.toLowerCase()));

        double playerEmcValue = EquivalentExchangeAPI.getStorageHandler().getEmcValueFor(player);
        double emcCeiling = EquivalentExchangeAPI.hasEmcValue(emcTarget) ? Math.min(EquivalentExchangeAPI.getEmcValue(emcTarget), playerEmcValue) : playerEmcValue;
        knowledge.removeIf(stack -> EquivalentExchangeAPI.getEmcValue(stack) > emcCeiling);

        knowledge.sort((o1, o2) ->
        {
            double emc1 = EquivalentExchangeAPI.getEmcValue(o1);
            double emc2 = EquivalentExchangeAPI.getEmcValue(o2);
            return Double.compare(emc2, emc1);
        });

        NonNullList<ItemStack> itemStacks = NonNullList.withSize(PAGE_SIZE, ItemStack.EMPTY);
        int pageSkip = PAGE_SIZE * page;
        for (int i = pageSkip; i < Math.min(knowledge.size(), pageSkip + PAGE_SIZE); i++)
            itemStacks.set(i - pageSkip, knowledge.get(i));

        return itemStacks;
    }

    public static void clear()
    {
        INVENTORIES.clear();
    }
}
