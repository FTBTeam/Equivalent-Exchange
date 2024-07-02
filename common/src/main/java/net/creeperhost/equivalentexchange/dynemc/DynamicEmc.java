package net.creeperhost.equivalentexchange.dynemc;

import com.google.common.base.Stopwatch;
import dev.architectury.event.events.common.LifecycleEvent;
import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.IEmcRecipeParser;
import net.creeperhost.equivalentexchange.api.events.EmcRegisterEvent;
import net.creeperhost.equivalentexchange.dynemc.json.DynEmcMapper;
import net.creeperhost.equivalentexchange.dynemc.json.ProjecteEmcMapper;
import net.creeperhost.equivalentexchange.dynemc.recipe.VanillaRecipeParser;
import net.creeperhost.equivalentexchange.network.packets.EmcValuesPacket;
import net.creeperhost.polylib.helpers.ItemTagHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class DynamicEmc
{
    public static void init()
    {
        EquivalentExchangeAPI.UpdatingValues = true;

        EquivalentExchangeAPI.registerParser(RecipeType.CRAFTING, new VanillaRecipeParser());
        EquivalentExchangeAPI.registerParser(RecipeType.SMELTING, new VanillaRecipeParser());
        EquivalentExchangeAPI.registerParser(RecipeType.BLASTING, new VanillaRecipeParser());
        EquivalentExchangeAPI.registerParser(RecipeType.CAMPFIRE_COOKING, new VanillaRecipeParser());
        EquivalentExchangeAPI.registerParser(RecipeType.SMOKING, new VanillaRecipeParser());

        EquivalentExchangeAPI.registerParser(RecipeType.STONECUTTING, new VanillaRecipeParser());

        LifecycleEvent.SERVER_STARTED.register(instance ->
        {
            EmcRegisterEvent.EMC_PRE_START.invoker().preStart(instance.getResourceManager());

            DynEmcMapper.init(instance.getResourceManager());

            if(EquivalentExchange.CONFIG_DATA.ProjectECompat) ProjecteEmcMapper.init(instance.getResourceManager());

            if (EquivalentExchange.CONFIG_DATA.UseRecipeManager)
            {
                CompletableFuture.runAsync(() ->
                {
                    try
                    {
                        Thread.sleep(5000);
                        RecipeManager recipeManager = instance.getRecipeManager();
                        Stopwatch stopwatch = Stopwatch.createStarted();
                        AtomicInteger retries = new AtomicInteger(10);
                        while (retries.get() > 0) {
                            try {
                                BuiltInRegistries.RECIPE_TYPE.forEach(recipeType ->
                                {
                                    RegistryAccess registryAccess = RegistryAccess.EMPTY;
                                    String resourceLocation = recipeType.toString();
                                    if (recipeType != null) {
                                        IEmcRecipeParser parser = EquivalentExchangeAPI.PARSERS.get(recipeType);
                                        if (parser == null) {
                                            if (EquivalentExchange.CONFIG_DATA.DEBUG_PRINT)
                                                EquivalentExchange.LOGGER.error("Unable to find parser for {} Skipping", recipeType.toString());
                                        } else {
                                            if(EquivalentExchange.CONFIG_DATA.DEBUG_PRINT)
                                                EquivalentExchange.LOGGER.info("Start set values for RecipeType: {}", resourceLocation);
                                            Set<RecipeHolder<?>> recipes = findRecipesByType(recipeType, recipeManager);
                                            recipes.forEach(recipe -> parser.setValueForRecipe(recipe.value(), registryAccess));
                                        }
                                    }
                                });
                                retries.getAndDecrement();
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //Special handlers
                        EmcRegisterEvent.EMC_POST_START.invoker().postStart(instance.getResourceManager());
                        EquivalentExchangeAPI.UpdatingValues = false;
                        new EmcValuesPacket(EquivalentExchangeAPI.EMC_VALUES).sendToAll(instance);

                        EquivalentExchange.LOGGER.info("Finished generating values for recipes after {}ms", stopwatch.elapsed().toMillis());
                        stopwatch.stop();

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    public static void generateForItem(String resourceLocation, double value)
    {
        try
        {
            Item item = BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(resourceLocation));
            if (item != null)
            {
                ItemStack stack = new ItemStack(item);
                if(!stack.isEmpty())
                    EquivalentExchangeAPI.setEmcValue(stack, value);
            }
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void generateForTag(String resourceLocation, double value)
    {
        ItemTagHelper.getValues(resourceLocation).forEach(itemHolder -> EquivalentExchangeAPI.setEmcValue(new ItemStack(itemHolder.value()), value));
    }

    private static Set<RecipeHolder<?>> findRecipesByType(RecipeType<?> typeIn, RecipeManager recipeManager)
    {
        return recipeManager.getRecipes().stream().filter(recipe -> recipe.value().getType() == typeIn).collect(Collectors.toSet());
    }
}
