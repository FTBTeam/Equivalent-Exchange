package net.creeperhost.equivalentexchange.items;

import net.creeperhost.equivalentexchange.api.item.IKleinStarItem;
import net.creeperhost.equivalentexchange.items.prefab.ItemWithEmc;
import net.creeperhost.equivalentexchange.types.KleinStarTypes;
import net.minecraft.world.item.ItemStack;

public class ItemKleinStar extends ItemWithEmc implements IKleinStarItem
{
    KleinStarTypes kleinStarTypes;

    public ItemKleinStar(KleinStarTypes kleinStarTypes)
    {
        super(new Properties().stacksTo(1), kleinStarTypes.maxStorage, kleinStarTypes.maxStorage, kleinStarTypes.maxStorage);
        this.kleinStarTypes = kleinStarTypes;
    }

    @Override
    public double getKleinStarStored(ItemStack itemStack)
    {
        return getStoredEmc(itemStack);
    }

    @Override
    public double getKleinStarMaxStorage(ItemStack itemStack)
    {
        return getMaxStored(itemStack);
    }

    @Override
    public double extractKleinStarEmc(ItemStack stack, double maxExtract, boolean simulate)
    {
        return extractEmc(stack, maxExtract, simulate);
    }

    @Override
    public double insertKleinStarEmc(ItemStack stack, double amount, boolean simulate)
    {
        return receiveEmc(stack, amount, simulate);
    }

    @Override
    public void setKleinStarEmc(ItemStack itemStack, double amount)
    {
        setStoredEmc(itemStack, amount);
    }
}
