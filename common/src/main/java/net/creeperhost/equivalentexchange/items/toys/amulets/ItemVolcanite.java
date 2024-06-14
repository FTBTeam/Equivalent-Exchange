package net.creeperhost.equivalentexchange.items.toys.amulets;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.item.IKleinStarItem;
import net.creeperhost.equivalentexchange.api.item.IPedestalItem;
import net.creeperhost.equivalentexchange.entities.EntityVolcaniteProjectile;
import net.creeperhost.equivalentexchange.init.ModSounds;
import net.creeperhost.equivalentexchange.items.prefab.FuelUsingItem;
import net.creeperhost.equivalentexchange.items.interfaces.IActionItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ServerLevelData;
import org.jetbrains.annotations.NotNull;

public class ItemVolcanite extends FuelUsingItem implements IPedestalItem, IActionItem
{
    public ItemVolcanite()
    {
        super(new Properties().stacksTo(1));
    }

    @Override
    public boolean hasCraftingRemainingItem()
    {
        return true;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext)
    {
        Player player = useOnContext.getPlayer();
        if(player == null) return InteractionResult.FAIL;

        Level level = useOnContext.getLevel();
        BlockPos clickedPos = useOnContext.getClickedPos();
        Direction sideHit = useOnContext.getClickedFace();
        //TODO check for tanks and insert into tank here

        if(hasEnoughFuel(player, EquivalentExchange.CONFIG_DATA.VolcaniteLavaCost))
        {
            BlockPos placePos = clickedPos.relative(sideHit, 1);
            level.setBlock(placePos, Blocks.LAVA.defaultBlockState(), 3);
            useFuel(player, EquivalentExchange.CONFIG_DATA.VolcaniteLavaCost);
            if(level.isClientSide())
            {
                level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.TRANSMUTE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }
            return InteractionResult.SUCCESS;
        }

        return super.useOn(useOnContext);
    }

    @Override
    public void pedestalTick(Level level, BlockPos blockPos, ItemStack stack, ItemStack starStack)
    {
        if(!level.isClientSide())
        {
            if(starStack.getItem() instanceof IKleinStarItem iKleinStarItem)
            {
                double stored = iKleinStarItem.getKleinStarStored(starStack);
                if(stored >= EquivalentExchange.CONFIG_DATA.VolcanitePedestalCost)
                {
                    if (level.getLevelData() instanceof ServerLevelData serverLevelData)
                    {
                        if(serverLevelData.isRaining() || serverLevelData.isThundering())
                        {
                            serverLevelData.setRainTime(0);
                            serverLevelData.setThunderTime(0);
                            serverLevelData.setRaining(false);
                            serverLevelData.setThundering(false);
                            iKleinStarItem.extractKleinStarEmc(starStack, EquivalentExchange.CONFIG_DATA.VolcanitePedestalCost, false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onActionKeyPressed(@NotNull ItemStack stack, @NotNull Player player, InteractionHand hand)
    {
        Level level = player.level();

//        level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.WATER_MAGIC.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        EntityVolcaniteProjectile ent = new EntityVolcaniteProjectile(player, level);
        ent.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.5F, 1);
        level.addFreshEntity(ent);
        player.swing(hand);
    }
}
