package net.creeperhost.equivalentexchange.blockentities;

import net.creeperhost.equivalentexchange.containers.ContainerTransmutationTable;
import net.creeperhost.equivalentexchange.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockEntityTransmutationTable extends BlockEntity implements MenuProvider
{
    public BlockEntityTransmutationTable(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.TRANSMUTATION_TABLE_TILE.get(), blockPos, blockState);
    }

    @Override
    public @NotNull Component getDisplayName()
    {
        return Component.translatable("block.equivalentexchange.transmutation_table");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory, @NotNull Player player)
    {
        return new ContainerTransmutationTable(id, inventory);
    }
}
