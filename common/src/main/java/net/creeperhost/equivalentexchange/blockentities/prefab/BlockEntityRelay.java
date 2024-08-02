package net.creeperhost.equivalentexchange.blockentities.prefab;

import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.emcstorage.IEmcItem;
import net.creeperhost.equivalentexchange.api.emcstorage.IEmcStorage;
import net.creeperhost.equivalentexchange.api.item.IKleinStarItem;
import net.creeperhost.polylib.inventory.item.ItemInventoryBlock;
import net.creeperhost.polylib.inventory.item.SimpleItemInventory;
import net.creeperhost.polylib.inventory.items.BlockInventory;
import net.creeperhost.polylib.inventory.items.PolyInventoryBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class BlockEntityRelay extends EmcBlockEntity implements PolyInventoryBlock
{
    public BlockInventory simpleItemInventory;

    public ContainerData containerData = new ContainerData()
    {
        @Override
        public int get(int i)
        {
            switch (i)
            {
                case 0:
                    return (int) getMaxStored();
                case 1:
                    return (int) getStoredEmc();
            }
            return 0;
        }

        @Override
        public void set(int i, int j) {}

        @Override
        public int getCount()
        {
            return 2;
        }
    };

    public abstract double getTransferRate();


    public BlockEntityRelay(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, double capacity)
    {
        super(blockEntityType, blockPos, blockState, capacity);
    }

    public void tick()
    {
        if(level == null) return;
        if(level.isClientSide()) return;
        moveItems();
        burnItem();
        charge();
        discharge();
    }

    int ticks = 0;

    public void discharge()
    {
        ticks++;

        if (ticks >= 20)
        {
            ticks = 0;
            for (Direction value : Direction.values())
            {
                BlockPos blockPos = getBlockPos().relative(value);
                if(level.getBlockEntity(blockPos) != null && level.getBlockEntity(blockPos) instanceof IEmcStorage iEmcStorage && iEmcStorage.canReceive())
                {
                    double removed = iEmcStorage.receiveEmc(Math.min(getStoredEmc(), getTransferRate()), false);
                    extractEmc(removed, false);
                }
            }
        }
    }

    public void burnItem()
    {
        if(!getContainer(Direction.UP).getItem(0).isEmpty())
        {
            ItemStack stack = getContainer(Direction.UP).getItem(0);
            if(stack.getItem() instanceof IKleinStarItem itemKleinStar)
            {
                if(itemKleinStar.getKleinStarStored(stack) > 0)
                {
                    double energyRemoved = receiveEmc(Math.min(itemKleinStar.getKleinStarStored(stack), getTransferRate()), false);
                    itemKleinStar.extractKleinStarEmc(stack, energyRemoved, false);
                }
                return;
            }
            double value = EquivalentExchangeAPI.getEmcValue(stack);
            if(value == -1) return;

            double inserted = receiveEmc(value, true);
            if(inserted == value)
            {
                receiveEmc(value, false);
                getContainer(Direction.UP).getItem(0).shrink(1);
            }
        }
    }

    public void charge()
    {
        if(!getContainer(Direction.UP).getItem(1).isEmpty())
        {
            if(getContainer(Direction.UP).getItem(1).getItem() instanceof IEmcItem iEmcItem)
            {
                ItemStack stack = getContainer(Direction.UP).getItem(1);
                if(iEmcItem.canReceive(stack))
                {
                    double energyRemoved = iEmcItem.receiveEmc(stack, Math.min(getStoredEmc(), getTransferRate()), false);
                    extractEmc(energyRemoved, false);
                }
            }
        }
    }

    public void moveItems()
    {
        if(getContainer(Direction.UP).getItem(0).isEmpty())
        {
            for (int i = 0; i < getContainer(Direction.UP).getContainerSize(); i++)
            {
                //Ignore the first 2 slots
                if(i > 1)
                {
                    if(!getContainer(Direction.UP).getItem(i).isEmpty())
                    {
                        ItemStack stack = getContainer(Direction.UP).getItem(i);
                        getContainer(Direction.UP).setItem(0, stack);
                        getContainer(Direction.UP).setItem(i, ItemStack.EMPTY);
                        getContainer(Direction.UP).setChanged();
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        simpleItemInventory.serialize(compoundTag);
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        getContainer(Direction.UP);
        simpleItemInventory.deserialize(compoundTag);
    }
}
