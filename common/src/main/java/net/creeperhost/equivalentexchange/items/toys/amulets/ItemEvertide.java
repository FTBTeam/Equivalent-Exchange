package net.creeperhost.equivalentexchange.items.toys.amulets;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.item.IPedestalItem;
import net.creeperhost.equivalentexchange.entities.EntityEvertideProjectile;
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
import org.jetbrains.annotations.NotNull;

public class ItemEvertide extends FuelUsingItem implements IPedestalItem, IActionItem
{
    public ItemEvertide()
    {
        super(new Properties().stacksTo(1));
    }

    public @NotNull InteractionResult useOn(UseOnContext useOnContext)
    {
        Player player = useOnContext.getPlayer();
        if(player == null) return InteractionResult.FAIL;

        Level level = useOnContext.getLevel();
        BlockPos clickedPos = useOnContext.getClickedPos();
        Direction sideHit = useOnContext.getClickedFace();
        //TODO check for tanks and insert into tank here

        if(hasEnoughFuel(player, EquivalentExchange.CONFIG_DATA.EvertideWaterCost))
        {
            BlockPos placePos = clickedPos.relative(sideHit, 1);
            level.setBlock(placePos, Blocks.WATER.defaultBlockState(), 3);
            useFuel(player, EquivalentExchange.CONFIG_DATA.EvertideWaterCost);
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

    }

    @Override
    public void onActionKeyPressed(@NotNull ItemStack stack, @NotNull Player player, InteractionHand hand)
    {
        Level level = player.level();

        EntityEvertideProjectile ent = new EntityEvertideProjectile(player, level);
        ent.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.5F, 1);
        level.addFreshEntity(ent);
        player.swing(hand);
    }
}
