package net.creeperhost.equivalentexchange.items.toys;

import net.creeperhost.equivalentexchange.api.EmcFormatter;
import net.creeperhost.equivalentexchange.api.emcstorage.IEmcItem;
import net.creeperhost.equivalentexchange.api.item.IKleinStarItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemBootsOfDirewolf extends ArmorItem implements IEmcItem
{
    private double capacity;
    private double cost = 20;

    public ItemBootsOfDirewolf()
    {
        super(ArmorMaterials.LEATHER, Type.BOOTS, new Properties().stacksTo(1));
        this.capacity = 1000;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int i, boolean bl)
    {
        super.inventoryTick(itemStack, level, entity, i, bl);
        if(entity instanceof Player player)
        {
            consumeEmc(itemStack, player);
            //Make sure the boots are equipped and not just in the players inventory
            if(player.getInventory().getArmor(0).getItem() == this)
            {
                //Let's not take fall damage while using the boots to fly
                player.resetFallDistance();
                if(player.getAbilities().flying)
                {
                    //Drain emc while the player is flying
                    if(getStoredEmc(itemStack) > cost)
                    {
                        extractEmc(itemStack, cost, false);
                    }
                    else
                    {
                        //Stop the player flying if they don't have fuel
                        player.getAbilities().mayfly = false;
                        player.getAbilities().flying = false;
                    }
                }
                //When the boot refuel with emc all the player to fly once again, This also catches a player logging back in as the events below don't fire
                else if(!player.getAbilities().mayfly && getStoredEmc(itemStack) > cost)
                {
                    player.getAbilities().mayfly = true;
                }
            }
        }
    }

    //TODO move this to a more generic place to avoid copy pasta
    public void consumeEmc(ItemStack itemStack, Player player)
    {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++)
        {
            ItemStack stack = player.getInventory().getItem(i);
            if(!stack.isEmpty() && stack.getItem() instanceof IKleinStarItem itemKleinStar)
            {
                double removed = receiveEmc(itemStack, Math.min(itemKleinStar.getKleinStarStored(stack), 1000), false);
                itemKleinStar.extractKleinStarEmc(stack, removed, false);
                break;
            }
        }
    }

    public void onEquip(Player player)
    {
        player.getAbilities().mayfly = true;
    }

    //Remove the players flying when the boots are unequipped
    public void onUnEquip(Player player)
    {
        if(!player.isCreative())
        {
            player.getAbilities().mayfly = false;
            player.getAbilities().flying = false;
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.literal(ChatFormatting.DARK_PURPLE + "Stored EMC" + ChatFormatting.WHITE + ": " + EmcFormatter.formatEmcValue(getStoredEmc(itemStack)) + " / " + EmcFormatter.formatEmcValue(getMaxStored(itemStack))));
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
            double emcReceived = Math.min(this.capacity - this.getStoredEmc(stack), Math.min(this.capacity, maxReceive));
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
        double emcExtracted = Math.min(this.getStoredEmc(stack), Math.min(this.capacity, maxExtract));
        if (!simulate)
        {
            double value = getStoredEmc(stack) - emcExtracted;
            setStoredEmc(stack, value);
        }
        return emcExtracted;
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

    //Let's not allow emc to be extracted from the boots
    @Override
    public boolean canExtract(ItemStack stack)
    {
        return false;
    }

    @Override
    public boolean canReceive(ItemStack stack)
    {
        return true;
    }
}
