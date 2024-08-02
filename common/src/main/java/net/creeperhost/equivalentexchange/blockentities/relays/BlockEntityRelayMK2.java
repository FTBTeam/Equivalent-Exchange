package net.creeperhost.equivalentexchange.blockentities.relays;

import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.blockentities.prefab.BlockEntityRelay;
import net.creeperhost.equivalentexchange.containers.relays.ContainerRelayMK2;
import net.creeperhost.equivalentexchange.init.ModBlocks;
import net.creeperhost.polylib.inventory.item.SerializableContainer;
import net.creeperhost.polylib.inventory.item.SimpleItemInventory;
import net.creeperhost.polylib.inventory.items.BlockInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockEntityRelayMK2 extends BlockEntityRelay
{
    public BlockEntityRelayMK2(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.RELAY_MK2_TILE.get(), blockPos, blockState, 500000);
    }

    @Override
    public @NotNull Component getDisplayName()
    {
        return Component.translatable(Constants.MOD_ID + ".relay_mk2");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player)
    {
        return new ContainerRelayMK2(i, inventory, this, containerData);
    }

    @Override
    public Container getContainer(@Nullable Direction side) {
        return simpleItemInventory == null ? this.simpleItemInventory = new BlockInventory(this, 14) : this.simpleItemInventory;
    }

    @Override
    public double getTransferRate() {
        return EquivalentExchange.CONFIG_DATA.DarkMatterRelayTransferRate;
    }
}
