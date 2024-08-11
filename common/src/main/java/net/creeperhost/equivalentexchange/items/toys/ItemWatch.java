package net.creeperhost.equivalentexchange.items.toys;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.TPSHelper;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeTags;
import net.creeperhost.equivalentexchange.api.item.IPedestalItem;
import net.creeperhost.equivalentexchange.blockentities.BlockEntityPedestal;
import net.creeperhost.equivalentexchange.blocks.BlockDarkMatterPedestal;
import net.creeperhost.equivalentexchange.items.interfaces.IActiveItem;
import net.creeperhost.equivalentexchange.items.interfaces.IChargeableItem;
import net.creeperhost.equivalentexchange.items.prefab.FuelUsingItem;
import net.creeperhost.polylib.helpers.LevelHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemWatch extends FuelUsingItem implements IPedestalItem, IChargeableItem, IActiveItem
{
    public ItemWatch()
    {
        super(new Properties().stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand)
    {
        ItemStack stack = player.getItemInHand(interactionHand);
        if(player.isCrouching())
        {
            toggleActive(stack);
            return InteractionResultHolder.success(stack);
        }
        return super.use(level, player, interactionHand);
    }

    public void toggleActive(ItemStack stack)
    {
        setActive(stack, !isActive(stack));
    }

    public boolean isActive(ItemStack stack)
    {
        return stack.getOrCreateTag().getBoolean("active");
    }

    public void setActive(ItemStack stack, boolean value)
    {
        stack.getOrCreateTag().putBoolean("active", value);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull Entity entity, int i, boolean bl)
    {
        super.inventoryTick(itemStack, level, entity, i, bl);
        if(!(entity instanceof Player player) || !EquivalentExchange.CONFIG_DATA.WatchFlowingTimeEnabled) return;
        if(!isActive(itemStack)) return;

        int charge = this.getCharge(itemStack);
        int bonusTicks;
        if (charge == 0)
        {
            bonusTicks = 8;
        }
        else if (charge == 1)
        {
            bonusTicks = 12;
        }
        else
        {
            bonusTicks = 16;
        }

        AABB aabb = player.getBoundingBox().inflate(8);

        randomTicks(level, bonusTicks, aabb);
        blockEntityTicks(level, bonusTicks, aabb);
    }

    @Override
    public void pedestalTick(Level level, BlockPos blockPos, ItemStack stack, ItemStack starStack)
    {
        if(!isActive(stack)) return;

        AABB aabb = new AABB(blockPos).inflate(8);

        //TODO move this to its own thing
        int charge = this.getCharge(stack);
        int bonusTicks;
        if (charge == 0)
        {
            bonusTicks = 8;
        }
        else if (charge == 1)
        {
            bonusTicks = 12;
        }
        else
        {
            bonusTicks = 16;
        }

        randomTicks(level, bonusTicks, aabb);
        blockEntityTicks(level, bonusTicks, aabb);
    }

    private void blockEntityTicks(Level level, int bonusTicks, AABB aabb)
    {
        if (aabb == null || bonusTicks == 0) return;

        for (BlockEntity blockEntity : getBlockEntitiesWithinAABB(level, aabb))
        {
            if (!blockEntity.isRemoved() && blockCheck(level, blockEntity.getBlockState()))
            {
                BlockPos pos = blockEntity.getBlockPos();
                if (level.shouldTickBlocksAt(ChunkPos.asLong(pos)))
                {
                    LevelChunk chunk = level.getChunkAt(pos);
                    LevelChunk.RebindableTickingBlockEntityWrapper tickingWrapper = chunk.tickersInLevel.get(pos);
                    if (tickingWrapper != null && !tickingWrapper.isRemoved())
                    {
                        if (tickingWrapper.ticker instanceof LevelChunk.BoundTickingBlockEntity tickingBE)
                        {
                            if (chunk.isTicking(pos))
                            {
                                ProfilerFiller profiler = level.getProfiler();
                                profiler.push(tickingWrapper::getType);
                                BlockState state = chunk.getBlockState(pos);
                                if (blockEntity.getType().isValid(state))
                                {
                                    for (int i = 0; i < bonusTicks; i++)
                                    {
                                        tickingBE.ticker.tick(level, pos, state, blockEntity);
                                    }
                                }
                                profiler.pop();
                            }
                        }
                        else
                        {
                            for (int i = 0; i < bonusTicks; i++)
                            {
                                tickingWrapper.tick();
                            }
                        }
                    }
                }
            }
        }
    }

    private List<BlockEntity> getBlockEntitiesWithinAABB(Level level, AABB aabb)
    {
        List<BlockEntity> list = new ArrayList<>();
        for (BlockPos pos : LevelHelper.getPositionsFromBox(aabb))
        {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity != null)
            {
                list.add(blockEntity);
            }
        }
        return list;
    }

    private void randomTicks(Level level, int bonusTicks, AABB aabb)
    {
        if (aabb == null || bonusTicks == 0 || !(level instanceof ServerLevel serverLevel)) return;

        for (BlockPos blockPos : LevelHelper.getPositionsFromBox(aabb))
        {
            if (serverLevel.isLoaded(blockPos))
            {
                BlockState state = serverLevel.getBlockState(blockPos);
                if (state.isRandomlyTicking() && blockCheck(serverLevel, state)) {
                    blockPos = blockPos.immutable();
                    for (int i = 0; i < bonusTicks; i++)
                    {
                        state.randomTick(serverLevel, blockPos, serverLevel.random);
                    }
                }
            }
        }
    }

    public boolean blockCheck(Level level, BlockState blockState)
    {
        if(level instanceof ServerLevel serverLevel)
        {
            if(TPSHelper.getMeanTPS(serverLevel) < EquivalentExchange.CONFIG_DATA.WatchOfFlowingTimeMinTps) return false;
        }

        Block block = blockState.getBlock();
        //Don't speed up out pedestal
        if(block instanceof BlockDarkMatterPedestal) return false;
        if(block instanceof LiquidBlock) return false;
        if(block instanceof BonemealableBlock) return false;
        if(EquivalentExchange.CONFIG_DATA.WatchOfFlowingTimeWhitelistMode)
        {
            return blockState.is(EquivalentExchangeTags.WATCH_WHITELIST);
        }
        else
        {
            return !blockState.is(EquivalentExchangeTags.WATCH_BLACKLIST);
        }
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
        return 2;
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
