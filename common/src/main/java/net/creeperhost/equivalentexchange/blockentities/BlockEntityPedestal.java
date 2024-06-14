package net.creeperhost.equivalentexchange.blockentities;

import net.creeperhost.equivalentexchange.api.item.IPedestalItem;
import net.creeperhost.equivalentexchange.blocks.BlockDarkMatterPedestal;
import net.creeperhost.equivalentexchange.containers.ContainerDarkMatterPedestal;
import net.creeperhost.equivalentexchange.init.ModBlocks;
import net.creeperhost.polylib.blocks.PolyBlockEntity;
import net.creeperhost.polylib.inventory.items.BlockInventory;
import net.creeperhost.polylib.inventory.items.PolyInventoryBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockEntityPedestal extends PolyBlockEntity implements PolyInventoryBlock, MenuProvider
{
    public final BlockInventory simpleItemInventory = new BlockInventory(this, 2);

    public BlockEntityPedestal(BlockPos pos, BlockState state)
    {
        super(ModBlocks.PEDESTAL_TILE.get(), pos, state);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.translatable("block.equivalentexchange.dm_pedestal");
    }

    int targetSlot = 0;
    int klienStarSlot = 1;
    public void tick()
    {
        if(level.getBlockState(getBlockPos()).getValue(BlockDarkMatterPedestal.IS_ACTIVE))
        {
            if (!getContainer(Direction.UP).getItem(targetSlot).isEmpty())
            {
                ItemStack targetStack = getContainer(Direction.UP).getItem(targetSlot);
                if (targetStack.getItem() instanceof IPedestalItem iPedestalItem)
                {
                    ItemStack klienStarStack = getContainer(Direction.UP).getItem(klienStarSlot);
                    iPedestalItem.pedestalTick(level, getBlockPos(), targetStack, klienStarStack);
                }
                spawnParticleTypes();
            }
        }
    }

    private void spawnParticleTypes()
    {
        int x = worldPosition.getX();
        int y = worldPosition.getY();
        int z = worldPosition.getZ();
        level.addParticle(ParticleTypes.FLAME, x + 0.2, y + 0.3, z + 0.2, 0, 0, 0);
        level.addParticle(ParticleTypes.FLAME, x + 0.2, y + 0.3, z + 0.5, 0, 0, 0);
        level.addParticle(ParticleTypes.FLAME, x + 0.2, y + 0.3, z + 0.8, 0, 0, 0);
        level.addParticle(ParticleTypes.FLAME, x + 0.5, y + 0.3, z + 0.2, 0, 0, 0);
        level.addParticle(ParticleTypes.FLAME, x + 0.5, y + 0.3, z + 0.8, 0, 0, 0);
        level.addParticle(ParticleTypes.FLAME, x + 0.8, y + 0.3, z + 0.2, 0, 0, 0);
        level.addParticle(ParticleTypes.FLAME, x + 0.8, y + 0.3, z + 0.5, 0, 0, 0);
        level.addParticle(ParticleTypes.FLAME, x + 0.8, y + 0.3, z + 0.8, 0, 0, 0);
        RandomSource rand = level.random;

        for (int i = 0; i < 3; ++i)
        {
            int j = rand.nextInt(2) * 2 - 1;
            int k = rand.nextInt(2) * 2 - 1;
            double d0 = (double) worldPosition.getX() + 0.5D + 0.25D * (double) j;
            double d1 = (float) worldPosition.getY() + rand.nextFloat();
            double d2 = (double) worldPosition.getZ() + 0.5D + 0.25D * (double) k;
            double d3 = rand.nextFloat() * (float) j;
            double d4 = ((double) rand.nextFloat() - 0.5D) * 0.125D;
            double d5 = rand.nextFloat() * (float) k;
            level.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
        }
    }

    @Override
    public Container getContainer(@Nullable Direction side)
    {
        return simpleItemInventory;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player)
    {
        return new ContainerDarkMatterPedestal(i, inventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt)
    {
        super.saveAdditional(nbt);
        simpleItemInventory.serialize(nbt);
    }

    @Override
    public void load(CompoundTag nbt)
    {
        super.load(nbt);
        simpleItemInventory.deserialize(nbt);
    }
}
