package net.creeperhost.equivalentexchange.items.interfaces;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IChargeableItem
{
    int getCharge(@NotNull ItemStack stack);

    void setCharge(@NotNull ItemStack stack, int value);

    int maxCharge(@NotNull ItemStack stack);

    void chargeKeyPressed(@NotNull ItemStack stack, @NotNull Player player, InteractionHand hand, boolean shiftKeyDown);
}
