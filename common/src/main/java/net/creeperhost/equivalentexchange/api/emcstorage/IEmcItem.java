package net.creeperhost.equivalentexchange.api.emcstorage;

import net.minecraft.world.item.ItemStack;

public interface IEmcItem
{
    double receiveEmc(ItemStack stack, double maxReceive, boolean simulate);

    double extractEmc(ItemStack stack, double maxExtract, boolean simulate);

    double getStoredEmc(ItemStack stack);

    void setStoredEmc(ItemStack stack, double value);

    double getMaxStored(ItemStack stack);

    boolean canExtract(ItemStack stack);

    boolean canReceive(ItemStack stack);
}
