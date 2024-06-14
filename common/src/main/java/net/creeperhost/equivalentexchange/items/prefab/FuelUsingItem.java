package net.creeperhost.equivalentexchange.items.prefab;

import net.creeperhost.equivalentexchange.api.item.IKleinStarItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FuelUsingItem extends Item
{
    public FuelUsingItem(Properties properties)
    {
        super(properties);
    }

    public ItemStack findFuel(Player player)
    {
        for (ItemStack item : player.getInventory().items)
        {
            if(item.getItem() instanceof IKleinStarItem)
            {
                return item;
            }
        }
        return ItemStack.EMPTY;
    }

    public void useFuel(Player player, double amount)
    {
        ItemStack stack = findFuel(player);
        if(stack.isEmpty()) return;
        if(stack.getItem() instanceof IKleinStarItem itemKleinStar)
        {
            itemKleinStar.extractKleinStarEmc(stack, amount, false);
        }
    }

    public boolean hasEnoughFuel(Player player, double amount)
    {
        ItemStack stack = findFuel(player);
        if(stack.getItem() instanceof IKleinStarItem itemKleinStar)
        {
            double storedEmc = itemKleinStar.getKleinStarStored(stack);
            return storedEmc >= amount;
        }
        return false;
    }
}
