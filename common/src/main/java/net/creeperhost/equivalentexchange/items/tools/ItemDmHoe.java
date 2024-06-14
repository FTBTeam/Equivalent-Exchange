package net.creeperhost.equivalentexchange.items.tools;

import net.creeperhost.equivalentexchange.items.interfaces.IChargeableItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;

public class ItemDmHoe extends HoeItem implements IChargeableItem
{
    public ItemDmHoe(Tier tier, int i, float f, Properties properties)
    {
        super(tier, i, f, properties);
    }

    @Override
    public boolean isBarVisible(ItemStack itemStack)
    {
        return true;
    }

    @Override
    public int getBarWidth(@NotNull ItemStack itemStack)
    {
        return Math.round((float) getCharge(itemStack) * 13.0f / (float) this.maxCharge(itemStack));
    }

    @Override
    public int getCharge(@NotNull ItemStack stack)
    {
        CompoundTag tag = stack.getOrCreateTag();
        if(!tag.contains("charge"))
        {
            tag.putInt("charge", 0);
        }
        return tag.getInt("charge");
    }

    @Override
    public void setCharge(@NotNull ItemStack stack, int value)
    {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("charge", value);
    }

    @Override
    public int maxCharge(@NotNull ItemStack stack)
    {
        return 5;
    }

    @Override
    public void chargeKeyPressed(@NotNull ItemStack stack, @NotNull Player player, InteractionHand hand, boolean shiftKeyDown)
    {
        if(!shiftKeyDown)
        {
            if(getCharge(stack) < maxCharge(stack)) setCharge(stack, getCharge(stack) + 1);
        }
        else
        {
            if(getCharge(stack) > 0) setCharge(stack, getCharge(stack) - 1);
        }
    }

    //Enchants
    @Override
    public boolean isEnchantable(@NotNull ItemStack stack)
    {
        return false;
    }
}
