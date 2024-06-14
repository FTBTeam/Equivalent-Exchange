package net.creeperhost.equivalentexchange.blocks.prefab;

import net.creeperhost.polylib.blocks.PolyEntityBlock;
import net.creeperhost.polylib.inventory.items.PolyInventoryBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EEEntityBlock extends PolyEntityBlock
{
    public EEEntityBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl)
    {
        if (!blockState.is(blockState2.getBlock()))
        {
            BlockEntity blockentity = level.getBlockEntity(blockPos);
            if (blockentity instanceof PolyInventoryBlock polyInventoryBlock)
            {
                Containers.dropContents(level, blockPos, polyInventoryBlock.getContainer(Direction.UP));
                level.updateNeighbourForOutputSignal(blockPos, this);
            }
            super.onRemove(blockState, level, blockPos, blockState2, bl);
        }
    }
}
