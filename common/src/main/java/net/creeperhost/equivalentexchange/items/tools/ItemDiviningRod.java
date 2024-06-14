package net.creeperhost.equivalentexchange.items.tools;

import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.types.DiviningRodTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemDiviningRod extends Item
{
    DiviningRodTypes diviningRodType;

    public ItemDiviningRod(DiviningRodTypes diviningRodType)
    {
        super(new Properties().stacksTo(1));
        this.diviningRodType = diviningRodType;
    }

    @Override
    public InteractionResult useOn(@NotNull UseOnContext useOnContext)
    {
        if(useOnContext.getPlayer() == null) return InteractionResult.FAIL;
        if(useOnContext.getLevel().isClientSide) return InteractionResult.SUCCESS;

        Level level = useOnContext.getLevel();
        Player player = useOnContext.getPlayer();
        BlockPos startPos = useOnContext.getClickedPos();
        List<Double> values = new ArrayList<>();

        for (int i = 0; i < diviningRodType.getRange(); i++)
        {
            BlockPos pos = startPos.relative(useOnContext.getClickedFace().getOpposite(), i);
            BlockState blockState = level.getBlockState(pos);
            if(blockState != null)
            {
                List<ItemStack> drops = Block.getDrops(blockState, (ServerLevel) level, pos, null, player, useOnContext.getItemInHand());
                if(drops.isEmpty()) continue;

                for (ItemStack drop : drops)
                {
                    if(EquivalentExchangeAPI.hasEmcValue(drop))
                    {
                        double value = EquivalentExchangeAPI.getEmcValue(drop);
                        values.add(value);
                    }
                }
            }
        }

        double highest = 0;
        for (Double value : values)
        {
            if(value > highest)
            {
                highest = value;
            }
        }

        player.displayClientMessage(Component.literal("Highest value: " + highest), false);
        return InteractionResult.SUCCESS;
    }
}
