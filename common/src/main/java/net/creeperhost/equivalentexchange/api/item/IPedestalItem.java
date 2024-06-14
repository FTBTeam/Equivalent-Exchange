package net.creeperhost.equivalentexchange.api.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IPedestalItem
{
    void pedestalTick(Level level, BlockPos blockPos, ItemStack stack, ItemStack starStack);
}
