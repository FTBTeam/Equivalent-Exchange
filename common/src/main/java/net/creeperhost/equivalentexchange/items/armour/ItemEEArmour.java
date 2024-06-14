package net.creeperhost.equivalentexchange.items.armour;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class ItemEEArmour extends ArmorItem
{
    public ItemEEArmour(ArmorMaterial armorMaterial, Type type, Properties properties)
    {
        super(armorMaterial, type, properties);
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack)
    {
        return false;
    }

    /**
     * Minimum percent damage will be reduced to if the full set is worn
     */
    public abstract float getFullSetBaseReduction();

    /**
     * Gets the max damage that a piece of this armor in a given slot can absorb of a specific type.
     *
     * @apiNote A value of zero means that there is no special bonus blocking powers for that damage type, and the piece's base reduction will be get used instead by the
     * damage calculation event.
     */
    public abstract float getMaxDamageAbsorb(ArmorItem.Type type, DamageSource source);

    /**
     * Gets the overall effectiveness of a given slots piece.
     */
    public float getPieceEffectiveness(ArmorItem.Type type)
    {
        if (type == ArmorItem.Type.BOOTS || type == ArmorItem.Type.HELMET)
        {
            return 0.2F;
        }
        else if (type == ArmorItem.Type.CHESTPLATE || type == ArmorItem.Type.LEGGINGS)
        {
            return 0.3F;
        }
        return 0;
    }
}
