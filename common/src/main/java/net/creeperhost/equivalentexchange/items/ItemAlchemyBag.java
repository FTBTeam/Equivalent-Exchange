package net.creeperhost.equivalentexchange.items;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import net.creeperhost.equivalentexchange.containers.ContainerAlchemicalBag;
import net.creeperhost.equivalentexchange.types.BagTypes;
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

public class ItemAlchemyBag extends Item
{
    BagTypes bagType;

    public ItemAlchemyBag(BagTypes bagType)
    {
        super(new Properties().stacksTo(1));
        this.bagType = bagType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand)
    {
        if(!level.isClientSide)
        {
            MenuRegistry.openExtendedMenu((ServerPlayer) player, new ItemAlchemyBag.ContainerProvider());
        }
        return InteractionResultHolder.success(player.getItemInHand(interactionHand));
    }

    public BagTypes getBagType()
    {
        return bagType;
    }

    private record ContainerProvider() implements ExtendedMenuProvider
    {
        @Override
        public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player player)
        {
            return new ContainerAlchemicalBag(windowId, playerInventory);
        }

        @NotNull
        @Override
        public Component getDisplayName()
        {
            return Component.literal("container.alchemy_bag");
        }

        @Override
        public void saveExtraData(FriendlyByteBuf buf) {}
    }
}
