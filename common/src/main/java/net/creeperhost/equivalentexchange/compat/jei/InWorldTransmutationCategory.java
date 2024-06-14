package net.creeperhost.equivalentexchange.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.creeperhost.equivalentexchange.api.recipe.InWorldTransmutation;
import net.creeperhost.equivalentexchange.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.LiquidBlock;
import org.jetbrains.annotations.NotNull;

public class InWorldTransmutationCategory implements IRecipeCategory<InWorldTransmutation>
{
    public static final Component TITLE = Component.translatable("equivalentexchange.transmutation.category");
    IGuiHelper guiHelper;
    public InWorldTransmutationCategory(IGuiHelper iGuiHelper)
    {
        this.guiHelper = iGuiHelper;
    }

    @Override
    public @NotNull Component getTitle()
    {
        return TITLE;
    }
    @Override
    public @NotNull RecipeType<InWorldTransmutation> getRecipeType()
    {
        return EERecipeTypes.TRANSMUTATION_RECIPE_TYPE;
    }

    @Override
    public @NotNull IDrawable getBackground()
    {
        return guiHelper.createDrawable(new ResourceLocation("jei", "textures/jei/gui/gui_vanilla.png"), 0, 222, 82, 34);
    }

    @Override
    public @NotNull IDrawable getIcon()
    {
        return guiHelper.createDrawableItemStack(new ItemStack(ModItems.PHILOSOPHERS_STONE.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, InWorldTransmutation recipe, IFocusGroup focuses)
    {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 7).addIngredients(Ingredient.of(recipe.getInput().getBlock().asItem()));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 62, 7).addIngredients(Ingredient.of(recipe.getResult().getBlock().asItem()));
    }
}
