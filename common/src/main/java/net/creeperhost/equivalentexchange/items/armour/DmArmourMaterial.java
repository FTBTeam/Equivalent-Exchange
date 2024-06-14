package net.creeperhost.equivalentexchange.items.armour;

import net.creeperhost.equivalentexchange.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class DmArmourMaterial implements ArmorMaterial
{
    public static final DmArmourMaterial INSTANCE = new DmArmourMaterial();

    @Override
    public int getDurabilityForType(@NotNull ArmorItem.Type type)
    {
        return 0;
    }

    @Override
    public int getDefenseForType(@NotNull ArmorItem.Type type)
    {
        return switch (type)
        {
            case BOOTS -> 3;
            case LEGGINGS -> 6;
            case CHESTPLATE -> 8;
            case HELMET -> 3;
        };
    }

    @Override
    public int getEnchantmentValue()
    {
        return 0;
    }

    @NotNull
    @Override
    public SoundEvent getEquipSound()
    {
        return SoundEvents.ARMOR_EQUIP_DIAMOND;
    }

    @NotNull
    @Override
    public Ingredient getRepairIngredient()
    {
        return Ingredient.EMPTY;
    }

    @NotNull
    @Override
    public String getName()
    {
        return new ResourceLocation(Constants.MOD_ID, "dark_matter").toString();
    }

    @Override
    public float getToughness()
    {
        return 2;
    }

    @Override
    public float getKnockbackResistance()
    {
        return 0.1F;
    }
}
