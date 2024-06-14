package net.creeperhost.equivalentexchange.containers.slots;

import net.creeperhost.equivalentexchange.api.emcstorage.IEmcItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotEmcStorage extends Slot
{
    public SlotEmcStorage(Container container, int i, int j, int k)
    {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack)
    {
        return itemStack.getItem() instanceof IEmcItem;
    }
}
