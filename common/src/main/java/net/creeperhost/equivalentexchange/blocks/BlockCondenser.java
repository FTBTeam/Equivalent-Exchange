package net.creeperhost.equivalentexchange.blocks;

import dev.architectury.registry.menu.MenuRegistry;
import net.creeperhost.equivalentexchange.blockentities.BlockEntityCondenser;
import net.creeperhost.equivalentexchange.blocks.prefab.BlockChest;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockCondenser extends BlockChest
{
    public BlockCondenser()
    {
        super(Properties.of().strength(2.0F));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type)
    {
        return (level1, blockPos, blockState, t) ->
        {
            if (t instanceof BlockEntityCondenser condenser)
            {
                condenser.tick();
            }
        };
    }

    @Override
    public InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult)
    {
        if (!level.isClientSide)
        {
            MenuRegistry.openExtendedMenu((ServerPlayer) player, (MenuProvider) level.getBlockEntity(blockPos), friendlyByteBuf -> friendlyByteBuf.writeBlockPos(blockPos));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState)
    {
        return new BlockEntityCondenser(blockPos, blockState);
    }
}
