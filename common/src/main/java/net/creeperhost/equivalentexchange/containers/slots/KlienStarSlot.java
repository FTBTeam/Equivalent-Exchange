package net.creeperhost.equivalentexchange.containers.slots;

import net.creeperhost.equivalentexchange.api.item.IKleinStarItem;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class KlienStarSlot extends PolySlot
{
    public KlienStarSlot(Container container, int index)
    {
        super(container, index);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack itemStack)
    {
        return itemStack.getItem() instanceof IKleinStarItem;
    }
}
