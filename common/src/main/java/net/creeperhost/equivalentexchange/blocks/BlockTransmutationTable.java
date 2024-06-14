package net.creeperhost.equivalentexchange.blocks;

import dev.architectury.registry.menu.MenuRegistry;
import net.creeperhost.equivalentexchange.blockentities.BlockEntityTransmutationTable;
import net.creeperhost.polylib.blocks.BlockFacing;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockTransmutationTable extends BlockFacing
{
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 4, 16);

    public BlockTransmutationTable()
    {
        super(Properties.of().strength(2.0F));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState)
    {
        return new BlockEntityTransmutationTable(blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult)
    {
        if (!level.isClientSide)
        {
            MenuRegistry.openExtendedMenu((ServerPlayer) player, (MenuProvider) level.getBlockEntity(blockPos), friendlyByteBuf -> friendlyByteBuf.writeBlockPos(blockPos));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public RenderShape getRenderShape(@NotNull BlockState blockState)
    {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext)
    {
        return SHAPE;
    }
}
