package net.creeperhost.equivalentexchange.items.tools;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.items.prefab.FuelUsingItem;
import net.creeperhost.equivalentexchange.items.interfaces.IChargeableItem;
import net.creeperhost.equivalentexchange.items.interfaces.IOverlayItem;
import net.creeperhost.polylib.helpers.LevelHelper;
import net.creeperhost.polylib.helpers.VectorHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

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

    public boolean blockIgnored(BlockState blockState)
    {
        return blockIgnoredStatic(blockState);
    }

    public static boolean blockIgnoredStatic(BlockState blockState)
    {
        return blockState.is(BlockTags.FEATURES_CANNOT_REPLACE) || blockState.is(Blocks.AIR) || blockState.is(Blocks.WATER) || blockState.is(Blocks.LAVA);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemStack = player.getItemInHand(hand);

        if(!hasEnoughFuel(player, EquivalentExchange.CONFIG_DATA.DestructionCatalystUseCost)) return InteractionResultHolder.fail(itemStack);

        if (level.isClientSide) return InteractionResultHolder.success(itemStack);

        int hitRange = 5;
        BlockHitResult hitResult = VectorHelper.getLookingAt(player, ClipContext.Fluid.SOURCE_ONLY, hitRange);

        if(hitResult.getType() == HitResult.Type.BLOCK)
        {
            BlockPos targetPos = hitResult.getBlockPos();
            Direction targetFace = hitResult.getDirection();

            int range = getRange(itemStack);
            List<ItemStack> drops = new ArrayList<>();

            var positions = LevelHelper.getPositionsFromBox(LevelHelper.getAABBbox(targetPos, targetFace, range--));
            for (BlockPos pos : positions)
            {
                BlockState blockState = level.getBlockState(pos);
                if (!blockIgnored(blockState))
                {
                    drops.addAll(Block.getDrops(blockState, (ServerLevel) level, pos, null, player, itemStack));
                    level.removeBlock(pos, false);
                }
            }


            for (ItemStack drop : drops)
            {
                ItemEntity item = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), drop);
                level.addFreshEntity(item);
            }
            useFuel(player, EquivalentExchange.CONFIG_DATA.DestructionCatalystUseCost);
            return InteractionResultHolder.success(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
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
        if(stream == null)
        {
            return Collections.emptyMap();
        }
        Map<BlockPos, BlockState> changes = new HashMap<>();

        stream.forEach(currentPos ->
        {
            if(!blockIgnoredStatic(level.getBlockState(currentPos))) changes.put(currentPos.immutable(), level.getBlockState(currentPos));
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
