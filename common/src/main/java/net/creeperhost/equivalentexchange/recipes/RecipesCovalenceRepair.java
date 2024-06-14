package net.creeperhost.equivalentexchange.recipes;

import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeTags;
import net.creeperhost.equivalentexchange.init.ModRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RecipesCovalenceRepair extends CustomRecipe
{
    public RecipesCovalenceRepair(CraftingBookCategory category)
    {
        super(category);
    }

    @Nullable
    private RepairTargetInfo findIngredients(CraftingContainer inv)
    {
        List<ItemStack> dust = new ArrayList<>();
        ItemStack tool = ItemStack.EMPTY;
        for (int i = 0; i < inv.getContainerSize(); i++)
        {
            ItemStack input = inv.getItem(i);
            if (!input.isEmpty())
            {
                if (input.is(EquivalentExchangeTags.COVALENCE_DUST))
                {
                    dust.add(input);
                }
                else if (tool.isEmpty() && input.isDamageableItem() && input.isDamageableItem() && input.getDamageValue() > 0)
                {
                    tool = input;
                }
                else
                {
                    return null;
                }
            }
        }
        if (tool.isEmpty() || dust.isEmpty())
        {
            return null;
        }
        return new RepairTargetInfo(tool, dust.stream().mapToDouble(EquivalentExchangeAPI::getEmcValue).sum());
    }

    @Override
    public boolean matches(@NotNull CraftingContainer inv, @NotNull Level level)
    {
        RepairTargetInfo targetInfo = findIngredients(inv);
        return targetInfo != null && targetInfo.emcPerDurability <= targetInfo.dustEmc;
    }

    @NotNull
    @Override
    public ItemStack assemble(@NotNull CraftingContainer inv, @NotNull RegistryAccess registryAccess)
    {
        RepairTargetInfo targetInfo = findIngredients(inv);
        if (targetInfo == null)
        {
            return ItemStack.EMPTY;
        }
        ItemStack output = targetInfo.tool.copy();
        output.setDamageValue((int) Math.max(output.getDamageValue() - targetInfo.dustEmc / targetInfo.emcPerDurability, 0));
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height)
    {
        return width > 1 || height > 1;
    }

    @NotNull
    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return ModRecipes.COVALENCE_REPAIR.get();
    }

    private static class RepairTargetInfo
    {
        private final ItemStack tool;
        private final long emcPerDurability;
        private final double dustEmc;

        public RepairTargetInfo(ItemStack tool, double emc)
        {
            this.tool = tool;
            this.dustEmc = emc;
            this.emcPerDurability = Constants.getEMCPerDurability(tool);
        }
    }
}
