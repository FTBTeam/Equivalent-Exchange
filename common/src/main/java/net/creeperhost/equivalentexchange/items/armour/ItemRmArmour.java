package net.creeperhost.equivalentexchange.items.armour;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;

public class ItemRmArmour extends ItemEEArmour
{
    public ItemRmArmour(Type type, Properties properties)
    {
        super(RmArmourMaterial.INSTANCE, type, properties);
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
        if (type == Type.BOOTS && source.is(DamageTypeTags.IS_FALL))
        {
            return 5 / getPieceEffectiveness(type);
        }
        else if (type == Type.HELMET && source.is(DamageTypeTags.IS_DROWNING))
        {
            return 5 / getPieceEffectiveness(type);
        }
        if (source.is(DamageTypeTags.BYPASSES_ARMOR))
        {
            return 0;
        }
        if (type == Type.HELMET || type == Type.BOOTS)
        {
            return 100;
        }
        return 150;
    }
}
