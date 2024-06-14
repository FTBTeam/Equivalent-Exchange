package net.creeperhost.equivalentexchange.containers.slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GhostSlot extends Slot
{
    public GhostSlot(Container container, int i, int j, int k)
    {
        super(container, i, j, k);
    }

    @Override
    public int getMaxStackSize()
    {
        return 1;
    }

    @Override
    public int getMaxStackSize(@NotNull ItemStack itemStack)
    {
        return 1;
    }
}
