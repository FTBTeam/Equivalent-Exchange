package net.creeperhost.equivalentexchange.items.toys.stones;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.init.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemSoulStone extends ItemStone
{
    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int i, boolean bl)
    {
        super.inventoryTick(itemStack, level, entity, i, bl);
        if(isActive(itemStack) && entity instanceof Player player)
        {
            if(player.getHealth() < player.getMaxHealth() && hasEnoughFuel(player, EquivalentExchange.CONFIG_DATA.LifeStoneHealCost))
            {
                if (level.isClientSide)
                {
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), ModSounds.HEAL.get(), SoundSource.PLAYERS, 1, 1);
                } else
                {
                    player.heal(2.0F);
                    useFuel(player, EquivalentExchange.CONFIG_DATA.LifeStoneHealCost);
                }
            }
        }
    }
}
