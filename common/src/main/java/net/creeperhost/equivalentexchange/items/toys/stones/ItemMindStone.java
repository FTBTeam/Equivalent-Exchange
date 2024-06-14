package net.creeperhost.equivalentexchange.items.toys.stones;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.item.IPedestalItem;
import net.creeperhost.equivalentexchange.items.interfaces.IActiveItem;
import net.creeperhost.equivalentexchange.polylib.ExperienceHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemMindStone extends ItemStone implements IActiveItem, IPedestalItem
{
    private static final int TRANSFER_RATE = 50;

    public ItemMindStone()
    {
        super();
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int i, boolean bl)
    {
        super.inventoryTick(itemStack, level, entity, i, bl);

        if(isActive(itemStack) && entity instanceof Player player)
        {
            int playerExp = Math.min(ExperienceHelper.getPlayersXP(player), TRANSFER_RATE);
            addStoredXP(itemStack, playerExp);
            ExperienceHelper.removeXP(player, playerExp);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand)
    {
        ItemStack stack = player.getItemInHand(interactionHand);
        if(!player.isCrouching() && !isActive(stack))
        {
            int amount = removeStoredXP(stack, TRANSFER_RATE);
            if(amount > 0)
            {
                ExperienceHelper.addXP(player, amount);
                return InteractionResultHolder.success(stack);
            }
        }
        return super.use(level, player, interactionHand);
    }

    public int getStoredXP(ItemStack itemStack)
    {
        return itemStack.getOrCreateTag().getInt("exp");
    }

    public void setStoredXP(ItemStack itemStack, int value)
    {
        itemStack.getOrCreateTag().putInt("exp", value);
    }

    public void addStoredXP(ItemStack stack, int value)
    {
        long result = (long) getStoredXP(stack) + value;
        if (result > Integer.MAX_VALUE) result = Integer.MAX_VALUE;
        setStoredXP(stack, (int) result);
    }

    public int removeStoredXP(ItemStack stack, int value)
    {
        int currentXP = getStoredXP(stack);
        int result;
        int returnResult;
        if (currentXP < value)
        {
            result = 0;
            returnResult = currentXP;
        } else
        {
            result = currentXP - value;
            returnResult = value;
        }

        setStoredXP(stack, result);
        return returnResult;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.literal(ChatFormatting.GREEN + "Stored EXP " + getStoredXP(itemStack)));
        list.add(Component.literal(ChatFormatting.GREEN + "Levels " + ExperienceHelper.getLvlForXP(getStoredXP(itemStack))));
    }

    @Override
    public void pedestalTick(Level level, BlockPos blockPos, ItemStack stack, ItemStack starStack)
    {
        AABB effectRadius = new AABB(blockPos).inflate(EquivalentExchange.CONFIG_DATA.LifeStoneRange);
        for (ExperienceOrb orb : level.getEntitiesOfClass(ExperienceOrb.class, effectRadius))
        {
            int value = orb.getValue();
            addStoredXP(stack, value);
            orb.discard();
        }
    }
}
