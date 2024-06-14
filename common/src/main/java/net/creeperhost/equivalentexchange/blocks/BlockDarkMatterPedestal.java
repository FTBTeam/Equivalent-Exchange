package net.creeperhost.equivalentexchange.blocks;

import dev.architectury.registry.menu.MenuRegistry;
import net.creeperhost.equivalentexchange.blockentities.BlockEntityPedestal;
import net.creeperhost.equivalentexchange.blocks.prefab.EEEntityBlock;
import net.creeperhost.equivalentexchange.blocks.prefab.PolyEntityBlockFacing;
import net.creeperhost.polylib.blocks.PolyEntityBlock;
import net.creeperhost.polylib.inventory.items.PolyInventoryBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockDarkMatterPedestal extends EEEntityBlock
{
    public static final BooleanProperty IS_ACTIVE = BooleanProperty.create("is_active");

    private static final VoxelShape SHAPE = Shapes.or(Block.box(3, 0, 3, 13, 2, 13),
            Shapes.or(Block.box(6, 2, 6, 10, 9, 10), Block.box(5, 9, 5, 11, 10, 11)));

    public BlockDarkMatterPedestal(Properties properties)
    {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(IS_ACTIVE, false).setValue(IS_ACTIVE, false));
    }

    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context)
    {
        return this.defaultBlockState().setValue(IS_ACTIVE, false);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(IS_ACTIVE);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type)
    {
        return (level1, blockPos, blockState, t) ->
        {
            if (t instanceof BlockEntityPedestal pedestal)
            {
                pedestal.tick();
            }
        };
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult)
    {
        if (!level.isClientSide)
        {
            if(!player.isShiftKeyDown())
            {
                MenuRegistry.openExtendedMenu((ServerPlayer) player, (MenuProvider) level.getBlockEntity(blockPos), friendlyByteBuf -> friendlyByteBuf.writeBlockPos(blockPos));
                return InteractionResult.SUCCESS;
            }
            else
            {
                boolean currentState = blockState.getValue(IS_ACTIVE);
                BlockState state = blockState.setValue(IS_ACTIVE, !currentState);
                level.setBlock(blockPos, state, 3);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx)
    {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState)
    {
        return new BlockEntityPedestal(blockPos, blockState);
    }
}
