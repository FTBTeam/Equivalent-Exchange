package net.creeperhost.equivalentexchange.items.tools;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.items.prefab.FuelUsingItem;
import net.creeperhost.equivalentexchange.items.interfaces.IChargeableItem;
import net.creeperhost.equivalentexchange.items.interfaces.IOverlayItem;
import net.creeperhost.polylib.helpers.LevelHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

public class ItemDestructionCatalyst extends FuelUsingItem implements IOverlayItem, IChargeableItem
{
    public ItemDestructionCatalyst()
    {
        super(new Properties().stacksTo(1));
    }

    public boolean excludedFromOverlay(BlockState blockState)
    {
        return blockIgnored(blockState);
    }

    public static boolean blockIgnored(BlockState blockState)
    {
        return blockState.is(BlockTags.FEATURES_CANNOT_REPLACE) || blockState.is(Blocks.AIR) || blockState.is(Blocks.WATER) || blockState.is(Blocks.LAVA);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext useOnContext)
    {
        if(hasEnoughFuel(useOnContext.getPlayer(), EquivalentExchange.CONFIG_DATA.DestructionCatalystUseCost))
        {
            Level level = useOnContext.getLevel();
            if (level.isClientSide) return InteractionResult.SUCCESS;
            if (useOnContext.getPlayer() == null) return InteractionResult.FAIL;
            Player player = useOnContext.getPlayer();
            BlockPos blockPos = useOnContext.getClickedPos();
            Direction direction = useOnContext.getClickedFace();
            List<ItemStack> drops = new ArrayList<>();

            int range = getRange(useOnContext.getItemInHand());

            var positions = LevelHelper.getPositionsFromBox(LevelHelper.getAABBbox(blockPos, direction, range--));
            for (BlockPos pos : positions)
            {
                BlockState blockState = level.getBlockState(pos);
                if (!blockIgnored(blockState))
                {
                    drops.addAll(Block.getDrops(blockState, (ServerLevel) level, pos, null, player, useOnContext.getItemInHand()));
                    level.removeBlock(pos, false);
                }
            }


            for (ItemStack drop : drops)
            {
                ItemEntity item = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), drop);
                level.addFreshEntity(item);
            }
            useFuel(useOnContext.getPlayer(), EquivalentExchange.CONFIG_DATA.DestructionCatalystUseCost);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public static Map<BlockPos, BlockState> getChanges(Level level, BlockPos pos, Player player, Direction sideHit, int charge)
    {
        Stream<BlockPos> stream = null;
        if(sideHit == Direction.SOUTH)
        {
            stream = BlockPos.betweenClosedStream(pos.offset(-1, -1, -charge), pos.offset(1, 1, 0));
        }
        if(sideHit == Direction.NORTH)
        {
            stream = BlockPos.betweenClosedStream(pos.offset(-1, -1, charge), pos.offset(1, 1, 0));
        }
        if(sideHit == Direction.EAST)
        {
            stream = BlockPos.betweenClosedStream(pos.offset(0, -1, -1), pos.offset(-charge, 1, 1));
        }
        if(sideHit == Direction.WEST)
        {
            stream = BlockPos.betweenClosedStream(pos.offset(charge, -1, -1), pos.offset(0, 1, 1));
        }
        if(sideHit == Direction.DOWN)
        {
            stream = BlockPos.betweenClosedStream(pos.offset(-1, 0, -1), pos.offset(1, charge, 1));
        }
        if(sideHit == Direction.UP)
        {
            stream = BlockPos.betweenClosedStream(pos.offset(-1, 0, -1), pos.offset(1, -charge, 1));
        }
        if (stream == null)
        {
            return Collections.emptyMap();
        }
        Map<BlockPos, BlockState> changes = new HashMap<>();

        stream.forEach(currentPos ->
        {
            if(!blockIgnored(level.getBlockState(currentPos)))
                changes.put(currentPos.immutable(), level.getBlockState(currentPos));
        });
        return changes;
    }

    @Override
    public int getRange(ItemStack stack)
    {
        return getCharge(stack);
    }

    @Override
    public Color getColour(ItemStack stack)
    {
        return Color.RED;
    }

    @Override
    public int getCharge(@NotNull ItemStack stack)
    {
        CompoundTag tag = stack.getOrCreateTag();
        if(!tag.contains("charge"))
        {
            tag.putInt("charge", 0);
        }
        return tag.getInt("charge");
    }

    @Override
    public void setCharge(@NotNull ItemStack stack, int value)
    {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt("charge", value);
    }

    @Override
    public int maxCharge(@NotNull ItemStack stack)
    {
        return 5;
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack itemStack)
    {
        return true;
    }

    @Override
    public int getBarWidth(@NotNull ItemStack itemStack)
    {
        return Math.round((float) getCharge(itemStack) * 13.0f / (float) this.maxCharge(itemStack));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        list.add(Component.literal("Range: " + getRange(itemStack)));
    }

    @Override
    public void chargeKeyPressed(@NotNull ItemStack stack, @NotNull Player player, InteractionHand hand, boolean shiftKeyDown)
    {
        if(!shiftKeyDown)
        {
            if(getCharge(stack) < maxCharge(stack)) setCharge(stack, getCharge(stack) + 1);
        }
        else
        {
            if(getCharge(stack) > 0) setCharge(stack, getCharge(stack) - 1);
        }
    }
}
