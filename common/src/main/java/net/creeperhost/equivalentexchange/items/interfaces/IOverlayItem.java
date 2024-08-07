package net.creeperhost.equivalentexchange.items.interfaces;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;

public interface IOverlayItem
{
    int getRange(ItemStack stack);

    Color getColour(ItemStack stack);

    boolean blockIgnored(BlockState block);
}
