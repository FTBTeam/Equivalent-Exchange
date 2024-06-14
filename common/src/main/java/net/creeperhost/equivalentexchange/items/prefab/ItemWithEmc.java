package net.creeperhost.equivalentexchange.items.prefab;

import net.creeperhost.equivalentexchange.api.EmcFormatter;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.emcstorage.IEmcItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemWithEmc extends Item implements IEmcItem
{
    double capacity;
    double maxReceive;
    double maxExtract;

    public ItemWithEmc(Properties properties, double capacity, double maxReceive, double maxExtract)
    {
        super(properties);
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack itemStack)
    {
        return true;
    }

    @Override
    public int getBarWidth(@NotNull ItemStack itemStack)
    {
        return Math.round((float) getStoredEmc(itemStack) * 13.0f / (float) getMaxStored(itemStack));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        if(capacity > 0)
            list.add(Component.literal(ChatFormatting.GOLD + "Stored EMC" + ChatFormatting.WHITE + ": " + EmcFormatter.formatEmcValue(getStoredEmc(itemStack)) + " / " + EmcFormatter.formatEmcValue(getMaxStored(itemStack))));
    }

    @Override
    public double receiveEmc(ItemStack stack, double maxReceive, boolean simulate)
    {
        if (!this.canReceive(stack))
        {
            return 0;
        }
        else
        {
            double emcReceived = Math.min(this.capacity - this.getStoredEmc(stack), Math.min(this.maxReceive, maxReceive));
            if (!simulate)
            {
                double value = getStoredEmc(stack) + emcReceived;
                setStoredEmc(stack, value);
            }
            return emcReceived;
        }
    }

    @Override
    public double extractEmc(ItemStack stack, double maxExtract, boolean simulate)
    {
        if (!this.canExtract(stack))
        {
            return 0;
        }
        else
        {
            double emcExtracted = Math.min(this.getStoredEmc(stack), Math.min(this.maxExtract, maxExtract));
            if (!simulate)
            {
                double value = getStoredEmc(stack) - emcExtracted;
                setStoredEmc(stack, value);
            }
            return emcExtracted;
        }
    }

    @Override
    public void setStoredEmc(ItemStack stack, double value)
    {
        stack.getOrCreateTag().putDouble("emc", value);
    }

    @Override
    public double getStoredEmc(ItemStack stack)
    {
        return stack.getOrCreateTag().getDouble("emc");
    }

    @Override
    public double getMaxStored(ItemStack stack)
    {
        return capacity;
    }

    @Override
    public boolean canExtract(ItemStack stack)
    {
        return maxExtract > 0;
    }

    @Override
    public boolean canReceive(ItemStack stack)
    {
        return maxReceive > 0;
    }
}
