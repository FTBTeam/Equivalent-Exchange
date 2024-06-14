package net.creeperhost.equivalentexchange.items.interfaces;

import net.minecraft.world.item.ItemStack;

public interface IActiveItem
{
    default boolean isActive(ItemStack stack)
    {
        if(stack.getTag() == null) return false;
        if(stack.getTag().contains("active"))
        {
            return stack.getTag().getBoolean("active");
        }
        return false;
    }
}
