package net.creeperhost.equivalentexchange.blocks;

import net.creeperhost.equivalentexchange.entities.EntityNovaCatalyst;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockNovaCatalyst extends TntBlock
{
    public BlockNovaCatalyst(Properties properties)
    {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter, List<Component> list, TooltipFlag tooltipFlag)
    {
        super.appendHoverText(itemStack, blockGetter, list, tooltipFlag);
        list.add(Component.literal("WIP"));
    }

    //    @Override
//    public void onPlace(BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, BlockState blockState2, boolean bl)
//    {
//        if (!blockState2.is(blockState.getBlock()))
//        {
//            if (level.hasNeighborSignal(blockPos))
//            {
//                novaExplode(level, blockPos);
//                level.removeBlock(blockPos, false);
//            }
//        }
//    }
//
//    @Override
//    public void neighborChanged(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Block block, @NotNull BlockPos blockPos2, boolean bl)
//    {
//        if (level.hasNeighborSignal(blockPos))
//        {
//            novaExplode(level, blockPos);
//            level.removeBlock(blockPos, false);
//        }
//    }
//
//    private void novaExplode(Level level, BlockPos blockPos)
//    {
//        novaExplode(level, blockPos, null);
//    }
//
//    private void novaExplode(Level level, BlockPos blockPos, @Nullable LivingEntity livingEntity)
//    {
//        if (!level.isClientSide)
//        {
//            EntityNovaCatalyst primedTnt = new EntityNovaCatalyst(level, (double)blockPos.getX() + 0.5, (double)blockPos.getY(), (double)blockPos.getZ() + 0.5, livingEntity);
//            level.addFreshEntity(primedTnt);
//            level.playSound(null, primedTnt.getX(), primedTnt.getY(), primedTnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
//            level.gameEvent(livingEntity, GameEvent.PRIME_FUSE, blockPos);
//        }
//    }
}
