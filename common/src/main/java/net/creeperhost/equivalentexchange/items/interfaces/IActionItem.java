package net.creeperhost.equivalentexchange.items.interfaces;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IActionItem
{
    void onActionKeyPressed(@NotNull ItemStack stack, @NotNull Player player, InteractionHand hand);
}
