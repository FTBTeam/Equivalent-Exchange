package net.creeperhost.equivalentexchange.blockentities;

import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.emcstorage.IEmcStorage;
import net.creeperhost.equivalentexchange.blockentities.prefab.EmcBlockEntity;
import net.creeperhost.equivalentexchange.blocks.BlockCollector;
import net.creeperhost.equivalentexchange.containers.ContainerCollector;
import net.creeperhost.equivalentexchange.init.ModBlocks;
import net.creeperhost.polylib.data.DataManagerBlock;
import net.creeperhost.polylib.data.TileDataManager;
import net.creeperhost.polylib.data.serializable.ByteData;
import net.creeperhost.polylib.data.serializable.DoubleData;
import net.creeperhost.polylib.data.serializable.IntData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockEntityCollector extends EmcBlockEntity implements DataManagerBlock
{
    private final TileDataManager<BlockEntityCollector> dataManager = new TileDataManager<>(this);
    public DoubleData storedEMC = dataManager.register("stored_emc", new DoubleData(0.0D), SYNC);
    public IntData lightLevel = dataManager.register("light_level", new IntData(0), SYNC);


    public BlockEntityCollector(BlockPos blockPos, BlockState blockState)
    {
        super(ModBlocks.COLLECTOR_TILE.get(), blockPos, blockState, 0, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
    }

    @Override
    public double getMaxStored()
    {
        if(level == null) return 0;

        if(level.getBlockState(getBlockPos()).getBlock() instanceof BlockCollector blockCollector)
        {
            return blockCollector.getCollectorType().getMaxStorage();
        }
        return 0;
    }

    int ticks = 0;
    public void tick()
    {
        if(level == null) return;

        if(!level.isClientSide)
        {
            ticks++;

            if (ticks >= 20)
            {
                ticks = 0;
                lightLevel.set(level.getMaxLocalRawBrightness(worldPosition.above()) + 1);
                BlockState state = getBlockState();
                if(state.getBlock() instanceof BlockCollector blockCollector)
                {
                    //Get the base value and times it by 20 due to only running every second
                    double generation = blockCollector.getCollectorType().getGeneration() * 20;
                    receiveEmc(generation, false);

                    for (Direction value : Direction.values())
                    {
                        BlockPos blockPos = getBlockPos().relative(value);
                        if(level.getBlockEntity(blockPos) != null && level.getBlockEntity(blockPos) instanceof IEmcStorage iEmcStorage && iEmcStorage.canReceive())
                        {
                            double removed = iEmcStorage.receiveEmc(Math.min(getStoredEmc(), blockCollector.getCollectorType().getGeneration() * 20), false);
                            extractEmc(removed, false);
                        }
                    }
                }
            }
            storedEMC.set(getStoredEmc());
        }
    }

    @Override
    public @NotNull Component getDisplayName()
    {
        return Component.translatable(Constants.MOD_ID + ".collector");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, @NotNull Inventory inventory, @NotNull Player player)
    {
        return new ContainerCollector(i, inventory, this);
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag)
    {
        super.load(compoundTag);
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
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
}
