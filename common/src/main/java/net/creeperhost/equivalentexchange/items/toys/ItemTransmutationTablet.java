package net.creeperhost.equivalentexchange.items.toys;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import net.creeperhost.equivalentexchange.containers.ContainerTransmutationTable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemTransmutationTablet extends Item
{
    public ItemTransmutationTablet()
    {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand)
    {
        if(!level.isClientSide)
        {
            MenuRegistry.openExtendedMenu((ServerPlayer) player, new ContainerProvider());
        }
        return InteractionResultHolder.success(player.getItemInHand(interactionHand));
    }

    private record ContainerProvider() implements ExtendedMenuProvider
    {
        @Override
        public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player player)
        {
            return new ContainerTransmutationTable(windowId, playerInventory);
        }

        @NotNull
        @Override
        public Component getDisplayName()
        {
            return Component.literal("container.transmutation_table");
        }

        @Override
        public void saveExtraData(FriendlyByteBuf buf) {}
    }
}
