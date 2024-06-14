package net.creeperhost.equivalentexchange.blockentities;

import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.blockentities.prefab.EmcBlockEntity;
import net.creeperhost.equivalentexchange.containers.ContainerCondenser;
import net.creeperhost.equivalentexchange.init.ModBlocks;
import net.creeperhost.polylib.data.DataManagerBlock;
import net.creeperhost.polylib.data.TileDataManager;
import net.creeperhost.polylib.data.serializable.DoubleData;
import net.creeperhost.polylib.inventory.items.BlockInventory;
import net.creeperhost.polylib.inventory.items.ContainerAccessControl;
import net.creeperhost.polylib.inventory.items.PolyInventoryBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockEntityCondenser extends EmcBlockEntity implements DataManagerBlock, PolyInventoryBlock
{
    private final BlockInventory simpleItemInventory = new BlockInventory(this, 92)
            .setStackValidator((integer, itemStack) -> EquivalentExchangeAPI.hasEmcValue(itemStack));

    private final TileDataManager<BlockEntityCondenser> dataManager = new TileDataManager<>(this);
    public DoubleData storedEMC = dataManager.register("stored_emc", new DoubleData(0.0D), SYNC);
    public DoubleData targetEMC = dataManager.register("target_emc", new DoubleData(0.0D), SYNC);

    public BlockEntityCondenser(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.CONDENSER_TILE.get(), blockPos, blockState, 0, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
    }

    private final int TARGET_SLOT = 91;

    public void tick()
    {
        dataManager.tick();
        if(!getContainer(Direction.UP).getItem(TARGET_SLOT).isEmpty())
        {
            ItemStack targetStack = getContainer(Direction.UP).getItem(TARGET_SLOT);
            if(!EquivalentExchangeAPI.hasEmcValue(targetStack)) return;
            //Eat other items and add them to sored emc
            for (int i = 0; i <= 90; i++)
            {
                if(getContainer(Direction.UP).getItem(i).isEmpty()) continue;
                ItemStack stack = getContainer(Direction.UP).getItem(i);
                if(!EquivalentExchangeAPI.hasEmcValue(stack)) continue;
                if(stack.is(getContainer(Direction.UP).getItem(TARGET_SLOT).getItem())) continue;

                double value = EquivalentExchangeAPI.getEmcValue(stack) * stack.getCount();
                receiveEmc(value, false);
                getContainer(Direction.UP).setItem(i, ItemStack.EMPTY);
            }

            if(getStoredEmc() >= EquivalentExchangeAPI.getEmcValue(targetStack))
            {
                ItemStack stack = targetStack.copy();
                stack.setCount(1);
                ItemStack inserted = moveOutput(stack);
                if(inserted.isEmpty())
                {
                    extractEmc(EquivalentExchangeAPI.getEmcValue(targetStack), false);
                }
            }
        }
        storedEMC.set(getStoredEmc());
        if(getContainer(Direction.UP).getItem(TARGET_SLOT).isEmpty())
        {
            targetEMC.set(0D);
        }
        else
        {
            targetEMC.set(EquivalentExchangeAPI.getEmcValue(getContainer(Direction.UP).getItem(TARGET_SLOT)));
        }
    }

    @Override
    public boolean canReceive()
    {
        return true;
    }

    public ItemStack moveOutput(ItemStack stack)
    {
        for (int i = 0; i <= 90; i++)
        {
            if(getContainer(Direction.UP).getItem(i).isEmpty())
            {
                getContainer(Direction.UP).setItem(i, stack);
                return ItemStack.EMPTY;
            }
            else
            {
                if(ItemStack.isSameItemSameTags(stack, getContainer(Direction.UP).getItem(i)))
                {
                    int count = getContainer(Direction.UP).getItem(i).getCount();
                    int max = getContainer(Direction.UP).getItem(i).getMaxStackSize();
                    if(count < max)
                    {
                        int newCount = count + 1;
                        stack.setCount(newCount);
                        getContainer(Direction.UP).setItem(i, stack);
                        return ItemStack.EMPTY;
                    }
                }
            }
        }
        return stack;
    }

    @Override
    public @NotNull Component getDisplayName()
    {
        return Component.translatable("block.equivalentexchange.condenser");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player)
    {
        return new ContainerCondenser(id, inventory, this);
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag)
    {
        super.load(compoundTag);
        simpleItemInventory.deserialize(compoundTag);
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
        simpleItemInventory.serialize(compoundTag);
    }

    //Network
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag()
    {
        return saveWithoutMetadata();
    }

    @Override
    public TileDataManager<?> getDataManager()
    {
        return dataManager;
    }

    @Override
    public Container getContainer(@Nullable Direction side)
    {
        return new ContainerAccessControl(simpleItemInventory, 0, 92)
                .containerRemoveCheck((integer, itemStack) -> integer != TARGET_SLOT);
    }
}
