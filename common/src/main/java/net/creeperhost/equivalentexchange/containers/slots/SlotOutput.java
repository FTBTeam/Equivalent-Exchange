package net.creeperhost.equivalentexchange.containers.slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SlotOutput extends Slot
{
    public SlotOutput(Container container, int i, int j, int k)
    {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack itemStack)
    {
        return false;
    }
}
