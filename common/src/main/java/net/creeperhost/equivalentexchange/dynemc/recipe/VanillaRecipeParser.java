package net.creeperhost.equivalentexchange.dynemc.recipe;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeTags;
import net.creeperhost.equivalentexchange.api.IEmcRecipeParser;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;

import java.util.ArrayList;
import java.util.List;

public class VanillaRecipeParser implements IEmcRecipeParser
{
    @Override
    public void setValueForRecipe(Recipe<?> recipe, RegistryAccess registryAccess)
    {
        if(recipe.isSpecial())
        {
            if(EquivalentExchange.CONFIG_DATA.DEBUG_PRINT)
                EquivalentExchange.LOGGER.info("Unable to parse recipe {} due to it being special", recipe.getSerializer().toString());
            return;
        }
        if(recipe.isIncomplete())
        {
            if(EquivalentExchange.CONFIG_DATA.DEBUG_PRINT)
                EquivalentExchange.LOGGER.info("Unable to parse recipe {} due to it being incomplete", recipe.getSerializer().toString());
            return;
        }
        ItemStack output = recipe.getResultItem(registryAccess).copy();
        if(output.isEmpty())
        {
            if(EquivalentExchange.CONFIG_DATA.DEBUG_PRINT)
                EquivalentExchange.LOGGER.info("Unable to parse recipe {} due to output being empty", recipe.getSerializer().toString());
            return;
        }
        if(EquivalentExchangeTags.isBlacklisted(output))
        {
            if(EquivalentExchange.CONFIG_DATA.DEBUG_PRINT)
                EquivalentExchange.LOGGER.info("tried to add emc value to blacklisted item skipping {}", output.getDisplayName().getString());
            return;
        }
        if(EquivalentExchangeAPI.hasEmcValue(output))
        {
            if(EquivalentExchange.CONFIG_DATA.DEBUG_PRINT)
                EquivalentExchange.LOGGER.info("{} already has emc value skipping", output.getDisplayName().getString());
            return;
        }

        EquivalentExchange.LOGGER.info("Recipe Group {} {}", recipe.getGroup(), recipe.getType().toString());

        List<ItemStack> validRecipeItems = new ArrayList<>();

        for (Ingredient ingredient : recipe.getIngredients())
        {
            ItemStack[] slot = ingredient.getItems();
            //Find the first stack with a valid emc value and add it to the list
            ItemStack valid = parseArray(slot, false);
            //Using the barrier as a null value
            if(!valid.is(Items.BARRIER))
            {
                validRecipeItems.add(valid);
            }
            else
            {
                if(EquivalentExchange.CONFIG_DATA.DEBUG_PRINT)
                    EquivalentExchange.LOGGER.info("Unable to find an item with an emc value for one of the input slots for recipes {}", output.getDisplayName().getString());
            }
        }

        if(!validRecipeItems.isEmpty())
        {
            if(validRecipeItems.size() < recipe.getIngredients().stream().filter(ingredient -> !ingredient.isEmpty()).count())
            {
                EquivalentExchange.LOGGER.info("Unable to set emc value on {} as validRecipeItems has less items than recipe size", output.getDisplayName().getString());
                return;
            }
            double total = math(validRecipeItems);
            if(total > 0)
            {
                double value = total;
                if(output.getCount() > 1)
                {
                    if(EquivalentExchange.CONFIG_DATA.DEBUG_PRINT)
                        EquivalentExchange.LOGGER.info("Count for {} is more than 1, calculating value for total {}", output.getDisplayName().getString(), total);
                    value = total / output.getCount();
                }
                if(EquivalentExchange.CONFIG_DATA.DEBUG_PRINT)
                    EquivalentExchange.LOGGER.info("Setting {} emc to {} from VanillaRecipeParser", output.getDisplayName().getString(), value);
                EquivalentExchangeAPI.setEmcValue(output, value);
            }
        }
    }

    private double math(List<ItemStack> list)
    {
        double value = 0;
        for (ItemStack itemStack : list)
        {
            if(itemStack.getItem().hasCraftingRemainingItem() && itemStack.is(itemStack.getItem().getCraftingRemainingItem()))
            {
                if(EquivalentExchange.CONFIG_DATA.DEBUG_PRINT)
                    EquivalentExchange.LOGGER.info("item {} is a container item that returns itself, not adding value to total", itemStack.getDisplayName().getString());
                continue;
            }
            //No point in doing an api call for an empty stack
            if(itemStack.isEmpty()) continue;
            value += EquivalentExchangeAPI.getEmcValue(itemStack);
        }
        return value;
    }

    private ItemStack parseArray(ItemStack[] list, boolean debug)
    {
        boolean canBeEmpty = false;

        for (ItemStack itemStack : list)
        {
            if(debug)
            {
                EquivalentExchange.LOGGER.info(itemStack.getDisplayName().getString());
            }
            //Empty stacks can be valid in crafting recipes
            if(itemStack.isEmpty())
            {
                canBeEmpty = true;
                continue;
            }

            if(EquivalentExchangeAPI.hasEmcValue(itemStack))
            {
                return itemStack;
            }
        }
        //Return barrier when there is no valid item
        if(canBeEmpty) return ItemStack.EMPTY;
        return new ItemStack(Items.BARRIER);
    }
}
