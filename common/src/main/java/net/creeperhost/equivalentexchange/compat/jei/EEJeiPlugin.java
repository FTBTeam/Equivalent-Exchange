package net.creeperhost.equivalentexchange.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.recipe.InWorldTransmutation;
import net.creeperhost.equivalentexchange.init.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class EEJeiPlugin implements IModPlugin
{
    private static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "jei_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        registry.addRecipeCategories(new InWorldTransmutationCategory(jeiHelpers.getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry)
    {
        registry.addRecipeCatalyst(new ItemStack(ModItems.PHILOSOPHERS_STONE.get()), RecipeTypes.CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(ModItems.PHILOSOPHERS_STONE.get()), EERecipeTypes.TRANSMUTATION_RECIPE_TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry)
    {
        registry.addRecipes(EERecipeTypes.TRANSMUTATION_RECIPE_TYPE, getTransmutationJEIList());
    }

    //This is used to add "alt" outputs into jei
    public List<InWorldTransmutation> getTransmutationJEIList()
    {
        List<InWorldTransmutation> list = new ArrayList<>();

        for (InWorldTransmutation inWorldTransmutationRecipe : EquivalentExchangeAPI.IN_WORLD_TRANSMUTATION_RECIPES)
        {
            list.add(inWorldTransmutationRecipe);
            if(inWorldTransmutationRecipe.getAltResult() != null)
            {
                list.add(new InWorldTransmutation(inWorldTransmutationRecipe.getInput(), inWorldTransmutationRecipe.getAltResult(), inWorldTransmutationRecipe.getResult()));
            }
        }
        return list;
    }

    @Override
    public @NotNull ResourceLocation getPluginUid()
    {
        return ID;
    }
}
