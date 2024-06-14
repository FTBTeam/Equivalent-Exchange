package net.creeperhost.equivalentexchange.items.toys.stones;

import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.items.prefab.FuelUsingItem;
import net.creeperhost.equivalentexchange.items.interfaces.IActiveItem;
import net.creeperhost.polylib.helpers.RegistryNameHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemStone extends FuelUsingItem implements IActiveItem
{
    public ItemStone()
    {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand)
    {
        ItemStack stack = player.getItemInHand(interactionHand);
        if(player.isCrouching())
        {
            toggleActive(stack);
            return InteractionResultHolder.success(stack);
        }
        return super.use(level, player, interactionHand);
    }

    public void toggleActive(ItemStack stack)
    {
        setActive(stack, !isActive(stack));
    }

    public boolean isActive(ItemStack stack)
    {
        return stack.getOrCreateTag().getBoolean("active");
    }

    public void setActive(ItemStack stack, boolean value)
    {
        stack.getOrCreateTag().putBoolean("active", value);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag)
    {
        if(Screen.hasShiftDown())
        {
            String name = RegistryNameHelper.getRegistryName(itemStack.getItem()).get().toString();
            list.add(Component.translatable("tooltip.equivalentexchange." + name));
        }
        else
        {
            super.appendHoverText(itemStack, level, list, tooltipFlag);
            list.add(Component.translatable(Constants.SHIFT_TEXT));
        }
    }
}
