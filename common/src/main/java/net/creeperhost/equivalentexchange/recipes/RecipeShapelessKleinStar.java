package net.creeperhost.equivalentexchange.recipes;

import net.creeperhost.equivalentexchange.init.ModRecipes;
import net.creeperhost.equivalentexchange.items.ItemKleinStar;
import net.creeperhost.polylib.recipe.WrappedShapelessRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;

public class RecipeShapelessKleinStar extends WrappedShapelessRecipe
{
    public RecipeShapelessKleinStar(ShapelessRecipe internal)
    {
        super(internal);
    }

    @NotNull
    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.KLEIN.get();
    }

    @NotNull
    @Override
    public ItemStack assemble(@NotNull CraftingContainer inv, @NotNull RegistryAccess registryAccess)
    {
        ItemStack result = getInternal().assemble(inv, registryAccess);
        double storedEMC = 0;
        for (int i = 0; i < inv.getContainerSize(); i++)
        {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem() instanceof ItemKleinStar itemKleinStar)
            {
                storedEMC += itemKleinStar.getStoredEmc(stack);
            }
        }
        if (storedEMC != 0 && result.getItem() instanceof ItemKleinStar itemKleinStar)
        {
            itemKleinStar.setStoredEmc(result, storedEMC);
        }
        return result;
    }

    @Override
    public boolean isSpecial()
    {
        return false;
    }
}
