package net.creeperhost.equivalentexchange.api.item;

import net.minecraft.world.item.ItemStack;

public interface IKleinStarItem
{
    double getKleinStarStored(ItemStack itemStack);

    double getKleinStarMaxStorage(ItemStack itemStack);

    double extractKleinStarEmc(ItemStack stack, double maxExtract, boolean simulate);

    double insertKleinStarEmc(ItemStack stack, double amount, boolean simulate);

    void setKleinStarEmc(ItemStack itemStack, double amount);
}
