package net.creeperhost.equivalentexchange.api;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.events.EmcRegisterEvent;
import net.creeperhost.equivalentexchange.api.recipe.InWorldTransmutation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;

import java.text.NumberFormat;
import java.util.*;

import org.jetbrains.annotations.Nullable;


public class EquivalentExchangeAPI
{
    public static IEmcStorageHandler iEmcStorageHandler;
    public static IKnowledgeHandler iKnowledgeHandler;

    public static HashMap<ResourceLocation, Double> EMC_VALUES = new HashMap<>();
    public static boolean UpdatingValues = false;
    public static List<InWorldTransmutation> IN_WORLD_TRANSMUTATION_RECIPES = new ArrayList<>();

    public static Map<RecipeType<?>, IEmcRecipeParser> PARSERS = new HashMap<>();

    @Deprecated
    public static void registerInWorldTransmutationRecipe(BlockState input, BlockState output, @Nullable BlockState altOutput)
    {
        InWorldTransmutation inWorldTransmutation = new InWorldTransmutation(input, output, altOutput);
        IN_WORLD_TRANSMUTATION_RECIPES.add(inWorldTransmutation);
    }

    /**
     * @param recipe The #RecipeType that will be used by your #IEmcRecipeParser
     * @param parser The #IEmcRecipeParser you will use with the #RecipeType
     */
    public static void registerParser(RecipeType<?> recipe, IEmcRecipeParser parser)
    {
        if(!PARSERS.containsKey(recipe))
        {
            PARSERS.put(recipe, parser);
        }
    }

    /**
     * @param itemStack The itemStack you are checking
     * @return true if the #ItemStack has an EMC value
     */
    public static boolean hasEmcValue(ItemStack itemStack)
    {
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        return EMC_VALUES.containsKey(resourceLocation);
    }

    /**
     * @param item The #Item to set an EMC value for
     * @param value The Value to assign to the #Item
     */
    public static void setEmcValue(Item item, double value)
    {
        ItemStack stack = new ItemStack(item);
        setEmcValue(stack, value);
    }

    /**
     * @param itemStack The #ItemStack to set an EMC value for
     * @param value The Value to assign to the #ItemStack
     */
    public static void setEmcValue(ItemStack itemStack, double value)
    {
        EmcRegisterEvent.EMC_SET_EVENT.invoker().set(itemStack);

        if(value <= 0)
        {
            EquivalentExchange.LOGGER.error("Tried to set emc value for {} to value of 0 or less {}", itemStack.getDisplayName().getString(), value);
            return;
        }

        if(EquivalentExchangeTags.isBlacklisted(itemStack)) return;

        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        if(!hasEmcValue(itemStack)) EMC_VALUES.put(resourceLocation, value);
    }

    /**
     * @param itemStack The #ItemStack to get EMC value from
     * @return Will return the EMC value for the #ItemStack, If the #ItemStack does not have an EMC value return -1
     */
    public static double getEmcValue(ItemStack itemStack)
    {
        if(hasEmcValue(itemStack))
        {
            ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
            return EMC_VALUES.get(resourceLocation);
        }
        return 0;
    }

    public static IEmcStorageHandler getStorageHandler()
    {
        return iEmcStorageHandler;
    }

    public static IKnowledgeHandler getKnowledgeHandler()
    {
        return iKnowledgeHandler;
    }
}
