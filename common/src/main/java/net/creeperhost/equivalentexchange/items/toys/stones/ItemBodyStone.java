package net.creeperhost.equivalentexchange.items.toys.stones;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.init.ModSounds;
import net.creeperhost.equivalentexchange.items.interfaces.IActiveItem;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class ItemBodyStone extends ItemStone implements IActiveItem
{
    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl)
    {
        super.inventoryTick(itemStack, level, entity, i, bl);
        if(isActive(itemStack) && entity instanceof Player player && isActive(itemStack))
        {
            if(player.getFoodData().needsFood() && hasEnoughFuel(player, EquivalentExchange.CONFIG_DATA.LifeStoneEatCost))
            {
                if (level.isClientSide)
                {
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), ModSounds.HEAL.get(), SoundSource.PLAYERS, 1, 1);
                } else
                {
                    player.getFoodData().eat(2, 10);
                    level.gameEvent(player, GameEvent.EAT, player.getEyePosition());
                    useFuel(player, EquivalentExchange.CONFIG_DATA.LifeStoneEatCost);
                }
            }
        }
    }
}
