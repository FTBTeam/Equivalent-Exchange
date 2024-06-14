package net.creeperhost.equivalentexchange.api;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.Recipe;

public interface IEmcRecipeParser
{
    void setValueForRecipe(Recipe<?> recipe, RegistryAccess registryAccess);
}
