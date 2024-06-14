package net.creeperhost.equivalentexchange.compat.jei;

import mezz.jei.api.recipe.RecipeType;
import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.api.recipe.InWorldTransmutation;

public class EERecipeTypes
{
    public static final RecipeType<InWorldTransmutation> TRANSMUTATION_RECIPE_TYPE =
            RecipeType.create(Constants.MOD_ID, "transmutation", InWorldTransmutation.class);
}
