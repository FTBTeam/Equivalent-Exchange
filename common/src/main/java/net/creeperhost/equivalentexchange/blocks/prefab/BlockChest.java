package net.creeperhost.equivalentexchange.blocks.prefab;

import net.creeperhost.polylib.blocks.BlockFacing;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public abstract class BlockChest extends PolyEntityBlockFacing
{
    private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

    public BlockChest(Properties properties)
    {
        super(properties);
    }

    @NotNull
    @Override
    @Deprecated
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx)
    {
        return SHAPE;
    }

    @Override
    @Deprecated
    public RenderShape getRenderShape(@NotNull BlockState state)
    {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, BlockState blockState1, boolean p_51542_)
    {
        if (!blockState.is(blockState1.getBlock()))
        {
            BlockEntity blockentity = level.getBlockEntity(blockPos);
            if (blockentity instanceof Container)
            {
                Containers.dropContents(level, blockPos, (Container) blockentity);
                level.updateNeighbourForOutputSignal(blockPos, this);
            }
            super.onRemove(blockState, level, blockPos, blockState1, p_51542_);
        }
    }
}
