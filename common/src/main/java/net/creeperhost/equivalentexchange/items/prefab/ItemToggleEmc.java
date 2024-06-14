package net.creeperhost.equivalentexchange.items.prefab;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemToggleEmc extends ItemWithEmc
{
    public ItemToggleEmc(Properties properties, double capacity, double maxReceive, double maxExtract)
    {
        super(properties, capacity, maxReceive, maxExtract);
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
}
