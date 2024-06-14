package net.creeperhost.equivalentexchange.containers;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import org.jetbrains.annotations.NotNull;

public class PhilosophersStoneContainer extends CraftingMenu
{
    public PhilosophersStoneContainer(int windowId, Inventory invPlayer, ContainerLevelAccess worldPosCallable)
    {
        super(windowId, invPlayer, worldPosCallable);
    }

    @Override
    public boolean stillValid(@NotNull Player player)
    {
        return true;
    }
}
