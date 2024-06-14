package net.creeperhost.equivalentexchange.blocks;

import dev.architectury.registry.menu.MenuRegistry;
import net.creeperhost.equivalentexchange.blockentities.prefab.BlockEntityRelay;
import net.creeperhost.equivalentexchange.blockentities.relays.BlockEntityRelayMK1;
import net.creeperhost.equivalentexchange.blockentities.relays.BlockEntityRelayMK2;
import net.creeperhost.equivalentexchange.blockentities.relays.BlockEntityRelayMK3;
import net.creeperhost.equivalentexchange.blocks.prefab.PolyEntityBlockFacing;
import net.creeperhost.equivalentexchange.types.RelayTypes;
import net.creeperhost.polylib.blocks.BlockFacing;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
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

public class BlockRelay extends PolyEntityBlockFacing
{
    RelayTypes relayTypes;

    public BlockRelay(RelayTypes relayTypes)
    {
        super(Properties.of().strength(2.0F).lightLevel(value -> 14));
        this.relayTypes = relayTypes;
    }

    public RelayTypes getRelayType()
    {
        return relayTypes;
    }


    @Override
    public @NotNull InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult)
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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type)
    {
        return (level1, blockPos, blockState, t) ->
        {
            if (t instanceof BlockEntityRelay relay)
            {
                relay.tick();
            }
        };
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState)
    {
        switch (relayTypes)
        {
            case MK1:
                return new BlockEntityRelayMK1(blockPos, blockState);
            case MK2:
                return new BlockEntityRelayMK2(blockPos, blockState);
            case MK3:
                return new BlockEntityRelayMK3(blockPos, blockState);
        }
        return null;
    }
}
