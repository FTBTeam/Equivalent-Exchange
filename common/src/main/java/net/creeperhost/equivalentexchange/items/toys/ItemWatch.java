package net.creeperhost.equivalentexchange.items.toys;

import net.creeperhost.equivalentexchange.api.item.IPedestalItem;
import net.creeperhost.equivalentexchange.items.prefab.FuelUsingItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemWatch extends FuelUsingItem implements IPedestalItem
{
    public ItemWatch()
    {
        super(new Properties().stacksTo(1));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int i, boolean bl)
    {
        super.inventoryTick(itemStack, level, entity, i, bl);
    }

    @Override
    public void pedestalTick(Level level, BlockPos blockPos, ItemStack stack, ItemStack starStack)
    {

    }
}
