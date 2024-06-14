package net.creeperhost.equivalentexchange.dynemc.special;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public class ConcreteHandler
{
    public static void init()
    {
        Map<ItemStack, ItemStack> MAP = new HashMap<>();
        MAP.put(new ItemStack(Items.WHITE_CONCRETE), new ItemStack(Items.WHITE_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.ORANGE_CONCRETE), new ItemStack(Items.ORANGE_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.MAGENTA_CONCRETE), new ItemStack(Items.MAGENTA_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.LIGHT_BLUE_CONCRETE), new ItemStack(Items.LIGHT_BLUE_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.YELLOW_CONCRETE), new ItemStack(Items.YELLOW_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.LIME_CONCRETE), new ItemStack(Items.LIME_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.PINK_CONCRETE), new ItemStack(Items.PINK_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.GRAY_CONCRETE), new ItemStack(Items.GRAY_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.LIGHT_GRAY_CONCRETE), new ItemStack(Items.LIGHT_GRAY_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.CYAN_CONCRETE), new ItemStack(Items.CYAN_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.PURPLE_CONCRETE), new ItemStack(Items.PURPLE_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.BLUE_CONCRETE), new ItemStack(Items.BLUE_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.BROWN_CONCRETE), new ItemStack(Items.BROWN_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.GREEN_CONCRETE), new ItemStack(Items.GREEN_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.RED_CONCRETE), new ItemStack(Items.RED_CONCRETE_POWDER));
        MAP.put(new ItemStack(Items.BLACK_CONCRETE), new ItemStack(Items.BLACK_CONCRETE_POWDER));

        MAP.forEach((itemStack, itemStack2) ->
        {
            if(EquivalentExchangeAPI.hasEmcValue(itemStack) || EquivalentExchangeTags.isBlacklisted(itemStack))
            {
                EquivalentExchange.LOGGER.info("Not setting emc value for {} , Reason: already has EMC value", itemStack.getDisplayName().getString());
            }
            else
            {
                if(EquivalentExchangeAPI.hasEmcValue(itemStack2))
                {
                    double value = EquivalentExchangeAPI.getEmcValue(itemStack2);
                    EquivalentExchangeAPI.setEmcValue(itemStack, value);
                    EquivalentExchange.LOGGER.info("Setting value of {} to {}", value, itemStack.getDisplayName().getString());
                }
                else
                {
                    EquivalentExchange.LOGGER.info("Item {} does not have an emc value, Skipping {}", itemStack2.getDisplayName().getString(), itemStack.getDisplayName().getString());
                }
            }
        });
    }
}
