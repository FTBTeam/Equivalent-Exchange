package net.creeperhost.equivalentexchange.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.recipes.RecipeShapelessKleinStar;
import net.creeperhost.equivalentexchange.recipes.RecipesCovalenceRepair;
import net.creeperhost.polylib.recipe.WrappedShapelessRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.block.Blocks;

public class ModRecipes
{
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Constants.MOD_ID, Registries.RECIPE_SERIALIZER);

    public static final RegistrySupplier<RecipeSerializer<?>> KLEIN = RECIPE_SERIALIZERS.register("crafting_shapeless_kleinstar",
            () -> new WrappedShapelessRecipeSerializer<>(RecipeShapelessKleinStar::new));

    public static final RegistrySupplier<RecipeSerializer<?>> COVALENCE_REPAIR = RECIPE_SERIALIZERS.register("covalence_repair",
            () -> new SimpleCraftingRecipeSerializer<>(RecipesCovalenceRepair::new));


    public static void init()
    {
        registerInWorldRecipes();
    }

    //TODO replace this with jsons
    public static void registerInWorldRecipes()
    {
        EquivalentExchangeAPI.registerInWorldTransmutationRecipe(Blocks.STONE.defaultBlockState(), Blocks.COBBLESTONE.defaultBlockState(), Blocks.GRASS_BLOCK.defaultBlockState());
        EquivalentExchangeAPI.registerInWorldTransmutationRecipe(Blocks.COBBLESTONE.defaultBlockState(), Blocks.STONE.defaultBlockState(), Blocks.GRASS_BLOCK.defaultBlockState());
        EquivalentExchangeAPI.registerInWorldTransmutationRecipe(Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.SAND.defaultBlockState(), Blocks.COBBLESTONE.defaultBlockState());
        EquivalentExchangeAPI.registerInWorldTransmutationRecipe(Blocks.DIRT.defaultBlockState(), Blocks.SAND.defaultBlockState(), Blocks.COBBLESTONE.defaultBlockState());
        EquivalentExchangeAPI.registerInWorldTransmutationRecipe(Blocks.SAND.defaultBlockState(), Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.COBBLESTONE.defaultBlockState());
        EquivalentExchangeAPI.registerInWorldTransmutationRecipe(Blocks.GRAVEL.defaultBlockState(), Blocks.SANDSTONE.defaultBlockState(), null);
        EquivalentExchangeAPI.registerInWorldTransmutationRecipe(Blocks.WATER.defaultBlockState(), Blocks.ICE.defaultBlockState(), null);
        EquivalentExchangeAPI.registerInWorldTransmutationRecipe(Blocks.LAVA.defaultBlockState(), Blocks.OBSIDIAN.defaultBlockState(), null);
        EquivalentExchangeAPI.registerInWorldTransmutationRecipe(Blocks.MELON.defaultBlockState(), Blocks.PUMPKIN.defaultBlockState(), null);
        EquivalentExchangeAPI.registerInWorldTransmutationRecipe(Blocks.GRANITE.defaultBlockState(), Blocks.DIORITE.defaultBlockState(), Blocks.ANDESITE.defaultBlockState());
        EquivalentExchangeAPI.registerInWorldTransmutationRecipe(Blocks.DIORITE.defaultBlockState(), Blocks.ANDESITE.defaultBlockState(), Blocks.GRANITE.defaultBlockState());
        EquivalentExchangeAPI.registerInWorldTransmutationRecipe(Blocks.ANDESITE.defaultBlockState(), Blocks.GRANITE.defaultBlockState(), Blocks.DIORITE.defaultBlockState());
    }
}
