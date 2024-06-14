package net.creeperhost.equivalentexchange.blockentities.prefab;

import net.creeperhost.equivalentexchange.api.emcstorage.IEmcStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class EmcBlockEntity extends BlockEntity implements MenuProvider, IEmcStorage
{
    double emc;
    double capacity;
    double maxReceive;
    double maxExtract;

    public EmcBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, double emc, double capacity, double maxReceive, double maxExtract)
    {
        super(blockEntityType, blockPos, blockState);
        this.emc = emc;
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public EmcBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, double capacity)
    {
        this(blockEntityType, blockPos, blockState, 0, capacity, capacity, capacity);
    }

    public EmcBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, double capacity, double io)
    {
        this(blockEntityType, blockPos, blockState, 0, capacity, io, io);
    }

    @Override
    public double receiveEmc(double maxReceive, boolean simulate)
    {
        if(!this.canReceive()) return 0;

        double emcReceived = Math.min(getMaxStored() - this.emc, Math.min(getMaxReceive(), maxReceive));
        if (!simulate)
        {
            this.emc += emcReceived;
        }
        return emcReceived;
    }

    @Override
    public double extractEmc(double maxExtract, boolean simulate)
    {
        if (!this.canExtract()) return 0;

        double emcExtracted = Math.min(this.emc, Math.min(getMaxExtract(), maxExtract));
        if (!simulate)
        {
            this.emc -= emcExtracted;
        }
        return emcExtracted;
    }

    public double getMaxReceive()
    {
        return this.maxReceive;
    }

    public double getMaxExtract()
    {
        return this.maxExtract;
    }

    @Override
    public double getStoredEmc()
    {
        return emc;
    }

    @Override
    public double getMaxStored()
    {
        return capacity;
    }

    @Override
    public boolean canExtract()
    {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive()
    {
        return this.maxReceive > 0;
    }

    public void setCapacity(double capacity)
    {
        this.capacity = capacity;
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag compoundTag)
    {
        super.saveAdditional(compoundTag);
        compoundTag.putDouble("emc", emc);
    }

    @Override
    public void load(@NotNull CompoundTag compoundTag)
    {
        super.load(compoundTag);
        emc = compoundTag.getDouble("emc");
    }
}
