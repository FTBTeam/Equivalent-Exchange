package net.creeperhost.equivalentexchange.items.armour;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ArmorItem;

public class ItemDmArmour extends ItemEEArmour
{
    public ItemDmArmour(Type type, Properties properties)
    {
        super(DmArmourMaterial.INSTANCE, type, properties);
    }

    @Override
    public float getFullSetBaseReduction()
    {
        return 0.8F;
    }

    @Override
    public float getMaxDamageAbsorb(Type type, DamageSource source)
    {
        if (source.is(DamageTypeTags.IS_EXPLOSION))
        {
            return 350;
        }
        if (type == ArmorItem.Type.BOOTS && source.is(DamageTypeTags.IS_FALL))
        {
            return 5 / getPieceEffectiveness(type);
        }
        else if (type == ArmorItem.Type.HELMET && source.is(DamageTypeTags.IS_DROWNING))
        {
            return 5 / getPieceEffectiveness(type);
        }
        if (source.is(DamageTypeTags.BYPASSES_ARMOR))
        {
            return 0;
        }
        if (type == ArmorItem.Type.HELMET || type == ArmorItem.Type.BOOTS)
        {
            return 100;
        }
        return 150;
    }
}
