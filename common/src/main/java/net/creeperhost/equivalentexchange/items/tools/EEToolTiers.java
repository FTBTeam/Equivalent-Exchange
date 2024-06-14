package net.creeperhost.equivalentexchange.items.tools;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class EEToolTiers
{
    public static final Tier DARK_MATTER = new Tier()
    {
        @Override
        public int getUses()
        {
            return 0;
        }

        @Override
        public float getSpeed()
        {
            return 14;
        }

        @Override
        public float getAttackDamageBonus()
        {
            return 3;
        }

        @Override
        public int getLevel()
        {
            return 4;
        }

        @Override
        public int getEnchantmentValue()
        {
            return 0;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient()
        {
            return Ingredient.EMPTY;
        }
    };

    public static final Tier RED_MATTER = new Tier()
    {
        @Override
        public int getUses()
        {
            return 0;
        }

        @Override
        public float getSpeed()
        {
            return 16;
        }

        @Override
        public float getAttackDamageBonus()
        {
            return 4;
        }

        @Override
        public int getLevel()
        {
            return 5;
        }

        @Override
        public int getEnchantmentValue()
        {
            return 0;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient()
        {
            return Ingredient.EMPTY;
        }
    };
}
