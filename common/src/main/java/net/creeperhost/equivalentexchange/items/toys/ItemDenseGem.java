package net.creeperhost.equivalentexchange.items.toys;

import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.containers.ContainerTarget;
import net.creeperhost.equivalentexchange.items.interfaces.IActionItem;
import net.creeperhost.equivalentexchange.items.interfaces.IActiveItem;
import net.creeperhost.equivalentexchange.items.prefab.ItemToggleEmc;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemDenseGem extends ItemToggleEmc implements IActionItem, IActiveItem
{
    public ItemDenseGem()
    {
        super(new Properties().stacksTo(1), 10000000000D, 10000000000D, 10000000000D);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int j, boolean bl)
    {
        super.inventoryTick(itemStack, level, entity, j, bl);
        if(!isActive(itemStack)) return;

        if(entity instanceof Player player)
        {
            for (int i = 0; i < player.getInventory().getContainerSize(); i++)
            {
                ItemStack stack = player.getInventory().getItem(i);
                if(!stack.isEmpty() && EquivalentExchangeAPI.hasEmcValue(stack))
                {
                    double value = EquivalentExchangeAPI.getEmcValue(stack);
                    receiveEmc(itemStack, value, false);
                    stack.shrink(1);
                }
            }
        }
    }

    @Override
    public void onActionKeyPressed(@NotNull ItemStack stack, @NotNull Player player, InteractionHand hand)
    {
        if(!player.getCommandSenderWorld().isClientSide)
        {
            player.openMenu(new ContainerProvider(stack));
        }
    }

    private record ContainerProvider(ItemStack stack) implements MenuProvider
    {
        @NotNull
        @Override
        public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player player)
        {
            return new ContainerTarget(windowId, playerInventory, null);
        }

        @Override
        public @NotNull Component getDisplayName()
        {
            return stack.getHoverName();
        }
    }
}
