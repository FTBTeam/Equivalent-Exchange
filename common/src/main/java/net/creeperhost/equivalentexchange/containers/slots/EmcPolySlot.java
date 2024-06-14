package net.creeperhost.equivalentexchange.containers.slots;

import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class EmcPolySlot extends PolySlot {
    public EmcPolySlot(Container container, int index) {
        super(container, index);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return EquivalentExchangeAPI.hasEmcValue(itemStack);
    }
}
