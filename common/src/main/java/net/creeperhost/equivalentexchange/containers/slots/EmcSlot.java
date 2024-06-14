package net.creeperhost.equivalentexchange.containers.slots;

import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EmcSlot extends Slot
{
    public EmcSlot(Container container, int i, int j, int k)
    {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack itemStack)
    {
        return EquivalentExchangeAPI.hasEmcValue(itemStack);
    }
}
