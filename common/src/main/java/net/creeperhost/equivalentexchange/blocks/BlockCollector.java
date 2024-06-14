package net.creeperhost.equivalentexchange.blocks;

import dev.architectury.registry.menu.MenuRegistry;
import net.creeperhost.equivalentexchange.api.EmcFormatter;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.blockentities.BlockEntityCollector;
import net.creeperhost.equivalentexchange.blocks.prefab.PolyEntityBlockFacing;
import net.creeperhost.equivalentexchange.types.CollectorType;
import net.creeperhost.polylib.blocks.BlockFacing;
import net.creeperhost.polylib.blocks.PolyEntityBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockCollector extends PolyEntityBlockFacing
{
    CollectorType collectorType;

    public BlockCollector(CollectorType collectorType)
    {
        super(Properties.of().strength(2.0F).lightLevel(value -> 14));
        this.collectorType = collectorType;
    }

    public CollectorType getCollectorType()
    {
        return collectorType;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type)
    {
        return (level1, blockPos, blockState, t) ->
        {
            if (t instanceof BlockEntityCollector collector)
            {
                collector.tick();
            }
        };
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
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState)
    {
        return new BlockEntityCollector(blockPos, blockState);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable BlockGetter blockGetter, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, blockGetter, list, tooltipFlag);
        list.add(Component.literal(ChatFormatting.GREEN + "Generates " + EmcFormatter.formatEmcValue(getCollectorType().getGeneration()) + " emc"));
    }
}
