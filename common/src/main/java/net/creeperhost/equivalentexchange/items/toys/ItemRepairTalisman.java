package net.creeperhost.equivalentexchange.items.toys;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.items.prefab.FuelUsingItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemRepairTalisman extends FuelUsingItem
{
    double cost = EquivalentExchange.CONFIG_DATA.RepairTalismanCost;

    public ItemRepairTalisman()
    {
        super(new Properties().stacksTo(1));
    }

    int ticks = 0;
    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int i, boolean bl)
    {
        if(level.isClientSide()) return;

        ticks++;

        if(ticks == 200)
        {
            ticks = 0;
            if (entity instanceof Player player)
            {
                if(hasEnoughFuel(player, cost))
                {
                    repairAllItems(player.getInventory());
                }
            }
        }
    }

    public boolean canRepair(ItemStack stack)
    {
        if(stack.isEmpty()) return false;
        if(!stack.isDamageableItem()) return false;
        return stack.getDamageValue() > 0;
    }

    private void repairAllItems(Inventory inv)
    {
        for (int i = 0; i < inv.getContainerSize(); i++)
        {
            ItemStack invStack = inv.getItem(i);
            if (canRepair(invStack))
            {
                useFuel(inv.player, cost);
                invStack.setDamageValue(invStack.getDamageValue() - 1);
            }
        }
    }
}
