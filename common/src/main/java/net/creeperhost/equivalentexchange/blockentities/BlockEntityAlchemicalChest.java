package net.creeperhost.equivalentexchange.blockentities;

import net.creeperhost.equivalentexchange.containers.ContainerAlchemicalChest;
import net.creeperhost.equivalentexchange.init.ModBlocks;
import net.creeperhost.polylib.blocks.PolyBlockEntity;
import net.creeperhost.polylib.inventory.items.BlockInventory;
import net.creeperhost.polylib.inventory.items.PolyInventoryBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockEntityAlchemicalChest extends PolyBlockEntity implements PolyInventoryBlock, MenuProvider
{
    private BlockInventory simpleItemInventory = new BlockInventory(this, 104);

    public BlockEntityAlchemicalChest(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.ALCHEMICAL_CHEST_TILE.get(), blockPos, blockState);
    }

    @Override
    public @NotNull Component getDisplayName()
    {
        return Component.translatable("block.equivalentexchange.alchemical_chest");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        return new ContainerAlchemicalChest(id, inventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
        simpleItemInventory.serialize(compoundTag);
    }

    @Override
    public void load(CompoundTag compoundTag)
    {
        super.load(compoundTag);
        simpleItemInventory.deserialize(compoundTag);
    }

    @Override
    public Container getContainer(@Nullable Direction side)
    {
        return simpleItemInventory;
    }
}
