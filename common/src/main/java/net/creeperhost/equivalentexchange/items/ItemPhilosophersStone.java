package net.creeperhost.equivalentexchange.items;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.recipe.InWorldTransmutation;
import net.creeperhost.equivalentexchange.containers.PhilosophersStoneContainer;
import net.creeperhost.equivalentexchange.init.ModSounds;
import net.creeperhost.equivalentexchange.items.interfaces.IActionItem;
import net.creeperhost.equivalentexchange.items.interfaces.IChargeableItem;
import net.creeperhost.equivalentexchange.items.interfaces.IOverlayItem;
import net.creeperhost.polylib.helpers.VectorHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ItemPhilosophersStone extends Item implements IActionItem, IChargeableItem, IOverlayItem
{
    public ItemPhilosophersStone()
    {
        super(new Properties().stacksTo(1));
        this.craftingRemainingItem = this;
    }

    @Override
    public boolean excludedFromOverlay(BlockState blockState)
    {
        return blockState.isAir();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        ItemStack itemstack = player.getItemInHand(hand);

        int hitRange = 5;
        BlockHitResult hitResult = VectorHelper.getLookingAt(player, ClipContext.Fluid.SOURCE_ONLY, hitRange);

        if (hitResult.getType() == HitResult.Type.BLOCK)
        {
            BlockPos targetPos = hitResult.getBlockPos();
            Direction targetFace = hitResult.getDirection();
            BlockState targetState = level.getBlockState(targetPos);

            boolean isSneaking = player.isShiftKeyDown();
            BlockState result = getTransmutation(targetState, isSneaking);

            if (result == null) return InteractionResultHolder.pass(itemstack);
            Map<BlockPos, BlockState> changes = getChanges(level, targetPos, player, targetFace, getCharge(itemstack));
            changes.forEach((blockPos, blockState) -> level.setBlock(blockPos, blockState, 3));
            level.playSound(player, player.getX(), player.getY(), player.getZ(), ModSounds.TRANSMUTE.get(), SoundSource.PLAYERS, 1, 1);
            return InteractionResultHolder.success(itemstack);
        }
        // Quite possible, in the future, we might:
        // else if (hitResult.getType() == HitResult.Type.ENTITY) {}
        // else if (hitResult.getType() == HitResult.Type.MISS) {}
        return InteractionResultHolder.pass(itemstack);
    }

    public static @Nullable BlockState getTransmutation(BlockState blockState, boolean sneaking)
    {
        for (InWorldTransmutation inWorldTransmutationRecipe : EquivalentExchangeAPI.IN_WORLD_TRANSMUTATION_RECIPES)
        {
            if(inWorldTransmutationRecipe.getInput() == blockState)
            {
                return sneaking ? inWorldTransmutationRecipe.getAltResult() : inWorldTransmutationRecipe.getResult();
            }
        }
        return null;
    }

    public static Map<BlockPos, BlockState> getChanges(Level level, BlockPos pos, Player player, Direction sideHit, int charge)
    {
        BlockState targeted = level.getBlockState(pos);
        boolean isSneaking = player.isShiftKeyDown();
        @Nullable BlockState result = getTransmutation(targeted, isSneaking);
        if (result == null)
        {
            return Collections.emptyMap();
        }
        Stream<BlockPos> stream = null;
        if (sideHit == Direction.UP || sideHit == Direction.DOWN)
        {
            stream = BlockPos.betweenClosedStream(pos.offset(-charge, 0, -charge), pos.offset(charge, 0, charge));
        }
        else if (sideHit == Direction.EAST || sideHit == Direction.WEST)
        {
            stream = BlockPos.betweenClosedStream(pos.offset(0, -charge, -charge), pos.offset(0, charge, charge));
        }
        else if (sideHit == Direction.SOUTH || sideHit == Direction.NORTH)
        {
            stream = BlockPos.betweenClosedStream(pos.offset(-charge, -charge, 0), pos.offset(charge, charge, 0));
        }
        if (stream == null)
        {
            return Collections.emptyMap();
        }
        Map<BlockState, BlockState> conversions = new Object2ObjectArrayMap<>();
        conversions.put(targeted, result);
        Map<BlockPos, BlockState> changes = new HashMap<>();
        Block targetBlock = targeted.getBlock();

        stream.forEach(currentPos -> {
            BlockState state = level.getBlockState(currentPos);
            if (state.is(targetBlock))
            {
                @Nullable
                BlockState actualResult;
                if (conversions.containsKey(state))
                {
                    actualResult = conversions.get(state);
                }
                else
                {
                    conversions.put(state, actualResult = getTransmutation(state, isSneaking));
                }
                if (actualResult != null)
                {
                    changes.put(currentPos.immutable(), actualResult);
                }
            }
        });
        return changes;
    }

    @Override
    public boolean hasCraftingRemainingItem()
    {
        return true;
    }

    @Override
    public void onActionKeyPressed(@NotNull ItemStack stack, @NotNull Player player, InteractionHand hand)
    {
        if (!player.getCommandSenderWorld().isClientSide)
        {
            player.openMenu(new ContainerProvider(stack));
        }
    }

    @Override
    public void chargeKeyPressed(@NotNull ItemStack stack, @NotNull Player player, InteractionHand hand, boolean shiftKeyDown)
    {
        if (!shiftKeyDown)
        {
            if (getCharge(stack) < maxCharge(stack))
                setCharge(stack, getCharge(stack) + 1);
        }
        else
        {
            if (getCharge(stack) > 0)
                setCharge(stack, getCharge(stack) - 1);
        }
    }

    @Override
    public int getCharge(@NotNull ItemStack stack)
    {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains("charge"))
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
    public int getRange(ItemStack stack)
    {
        return getCharge(stack);
    }

    @Override
    public Color getColour(ItemStack stack)
    {
        return Color.WHITE;
    }

    private record ContainerProvider(ItemStack stack) implements MenuProvider {
        @NotNull
        @Override
        public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player player)
        {
            return new PhilosophersStoneContainer(windowId, playerInventory, ContainerLevelAccess.create(player.getCommandSenderWorld(), player.blockPosition()));
        }

        @Override
        public @NotNull Component getDisplayName()
        {
            return stack.getHoverName();
        }
    }
}
