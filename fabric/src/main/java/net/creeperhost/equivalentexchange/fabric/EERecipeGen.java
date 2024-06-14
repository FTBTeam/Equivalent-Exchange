package net.creeperhost.equivalentexchange.fabric;

import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.init.ModItems;
import net.creeperhost.equivalentexchange.types.*;
import net.creeperhost.polylib.fabric.datagen.ModuleType;
import net.creeperhost.polylib.fabric.datagen.providers.PolyRecipeProvider;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import static net.minecraft.data.recipes.RecipeProvider.has;

public class EERecipeGen
{
    public static void init(PolyRecipeProvider provider)
    {
        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PHILOSOPHERS_STONE.get())
                .define('S', Items.GLOWSTONE)
                .define('R', Items.REDSTONE)
                .define('D', Items.DIAMOND)
                .pattern("SRS")
                .pattern("RDR")
                .pattern("SRS")
                .unlockedBy("has_diamond", has(Items.DIAMOND)), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MATTER.get(MatterTypes.RED).get())
                .define('A', ModItems.AETERNALIS_FUEL.get())
                .define('D', ModItems.MATTER.get(MatterTypes.DARK).get())
                .pattern("AAA")
                .pattern("DDD")
                .pattern("AAA")
                .unlockedBy("has_diamond", has(Items.DIAMOND)), ModuleType.COMMON);

        provider.add(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ALCHEMICAL_COAL.get())
                .requires(Items.COAL, 4)
                .requires(ModItems.PHILOSOPHERS_STONE.get())
                .unlockedBy("has_philostone", has(ModItems.PHILOSOPHERS_STONE.get())), ModuleType.COMMON);

        provider.add(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MOBIUS_FUEL.get())
                .requires(ModItems.ALCHEMICAL_COAL.get(), 4)
                .requires(ModItems.PHILOSOPHERS_STONE.get())
                .unlockedBy("has_philostone", has(ModItems.PHILOSOPHERS_STONE.get())), ModuleType.COMMON);

        provider.add(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.AETERNALIS_FUEL.get())
                .requires(ModItems.MOBIUS_FUEL.get(), 4)
                .requires(ModItems.PHILOSOPHERS_STONE.get())
                .unlockedBy("has_philostone", has(ModItems.PHILOSOPHERS_STONE.get())), ModuleType.COMMON);

        provider.add(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COVALENCE_DUST_LOW.get())
                .requires(Items.CHARCOAL)
                .requires(Items.COBBLESTONE, 8)
                .unlockedBy("has_philostone", has(ModItems.PHILOSOPHERS_STONE.get())), ModuleType.COMMON);

        provider.add(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COVALENCE_DUST_MEDIUM.get())
                .requires(Items.IRON_INGOT)
                .requires(Items.REDSTONE)
                .unlockedBy("has_philostone", has(ModItems.PHILOSOPHERS_STONE.get())), ModuleType.COMMON);

        provider.add(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COVALENCE_DUST_HIGH.get())
                .requires(Items.DIAMOND)
                .requires(Items.COAL)
                .unlockedBy("has_philostone", has(ModItems.PHILOSOPHERS_STONE.get())), ModuleType.COMMON);

        provider.add(createCompressionRecipe(ModItems.ALCHEMICAL_COAL_BLOCK.get(), ModItems.ALCHEMICAL_COAL.get()), ModuleType.COMMON);
        provider.add(createCompressionRecipe(ModItems.MOBIUS_FUEL_BLOCK.get(), ModItems.MOBIUS_FUEL.get()), ModuleType.COMMON);
        provider.add(createCompressionRecipe(ModItems.AETERNALIS_FUEL_BLOCK.get(), ModItems.AETERNALIS_FUEL.get()), ModuleType.COMMON);

        provider.add(createCompressionRecipe(ModItems.MATTER_BLOCK.get(MatterTypes.DARK).get(), ModItems.MATTER.get(MatterTypes.DARK).get()), ModuleType.COMMON);
        provider.add(createCompressionRecipe(ModItems.MATTER_BLOCK.get(MatterTypes.RED).get(), ModItems.MATTER.get(MatterTypes.RED).get()), ModuleType.COMMON);

        provider.add(createSurroundRecipe(ModItems.IRON_BAND.get(), Items.IRON_INGOT, Items.LAVA_BUCKET), ModuleType.COMMON);

        provider.add(createSurroundRecipe(ModItems.DIVINING_RODS.get(DiviningRodTypes.LOW_DIVINING_ROD).get(),
                ModItems.COVALENCE_DUST_LOW.get(), Items.STICK), ModuleType.COMMON);

        provider.add(createSurroundRecipe(ModItems.DIVINING_RODS.get(DiviningRodTypes.MEDIUM_DIVINING_ROD).get(),
                ModItems.COVALENCE_DUST_MEDIUM.get(), ModItems.DIVINING_RODS.get(DiviningRodTypes.LOW_DIVINING_ROD).get()), ModuleType.COMMON);

        provider.add(createSurroundRecipe(ModItems.DIVINING_RODS.get(DiviningRodTypes.HIGH_DIVINING_ROD).get(),
                ModItems.COVALENCE_DUST_HIGH.get(), ModItems.DIVINING_RODS.get(DiviningRodTypes.MEDIUM_DIVINING_ROD).get()), ModuleType.COMMON);

        provider.add(createSurroundRecipe(ModItems.MATTER.get(MatterTypes.DARK).get(),
                ModItems.AETERNALIS_FUEL.get(), Items.DIAMOND_BLOCK), ModuleType.COMMON);

        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.BLACK).get(), Items.BLACK_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.BLUE).get(), Items.BLUE_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.BROWN).get(), Items.BROWN_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.CYAN).get(), Items.CYAN_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.GRAY).get(), Items.GRAY_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.GREEN).get(), Items.GREEN_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.LIGHT_BLUE).get(), Items.LIGHT_BLUE_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.LIGHT_GRAY).get(), Items.LIGHT_GRAY_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.LIME).get(), Items.LIME_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.MAGENTA).get(), Items.MAGENTA_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.ORANGE).get(), Items.ORANGE_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.PINK).get(), Items.PINK_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.PURPLE).get(), Items.PURPLE_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.RED).get(), Items.RED_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.WHITE).get(), Items.WHITE_WOOL), ModuleType.COMMON);
        provider.add(createBagRecipe(ModItems.BAGS.get(BagTypes.YELLOW).get(), Items.YELLOW_WOOL), ModuleType.COMMON);

        provider.add(createSurroundRecipe(ModItems.EIN.get(), ModItems.AETERNALIS_FUEL.get(), Items.DIAMOND), ModuleType.COMMON);


        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COLLECTOR.get(CollectorType.BASIC).get())
                .define('G', Items.GLOWSTONE)
                .define('D', Items.DIAMOND_BLOCK)
                .define('F', Items.FURNACE)
                .pattern("GDG")
                .pattern("GFG")
                .pattern("GGG")
                .unlockedBy("has_diamond", has(Items.DIAMOND)), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COLLECTOR.get(CollectorType.DARK).get())
                .define('G', Items.GLOWSTONE)
                .define('D', ModItems.MATTER_BLOCK.get(MatterTypes.DARK).get())
                .define('F', ModItems.COLLECTOR.get(CollectorType.BASIC).get())
                .pattern("GDG")
                .pattern("GFG")
                .pattern("GGG")
                .unlockedBy("has_diamond", has(Items.DIAMOND)), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COLLECTOR.get(CollectorType.RED).get())
                .define('G', Items.GLOWSTONE)
                .define('D', ModItems.MATTER_BLOCK.get(MatterTypes.RED).get())
                .define('F', ModItems.COLLECTOR.get(CollectorType.DARK).get())
                .pattern("GDG")
                .pattern("GFG")
                .pattern("GGG")
                .unlockedBy("has_diamond", has(Items.DIAMOND)), ModuleType.COMMON);


        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RELAYS.get(RelayTypes.MK1).get())
                .define('G', Items.OBSIDIAN)
                .define('D', Items.DIAMOND_BLOCK)
                .define('F', Items.FURNACE)
                .pattern("GDG")
                .pattern("GFG")
                .pattern("GGG")
                .unlockedBy("has_diamond", has(Items.DIAMOND)), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RELAYS.get(RelayTypes.MK2).get())
                .define('G', Items.OBSIDIAN)
                .define('D', ModItems.MATTER_BLOCK.get(MatterTypes.DARK).get())
                .define('F', ModItems.RELAYS.get(RelayTypes.MK1).get())
                .pattern("GDG")
                .pattern("GFG")
                .pattern("GGG")
                .unlockedBy("has_diamond", has(Items.DIAMOND)), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RELAYS.get(RelayTypes.MK3).get())
                .define('G', Items.OBSIDIAN)
                .define('D', ModItems.MATTER_BLOCK.get(MatterTypes.RED).get())
                .define('F', ModItems.RELAYS.get(RelayTypes.MK2).get())
                .pattern("GDG")
                .pattern("GFG")
                .pattern("GGG")
                .unlockedBy("has_diamond", has(Items.DIAMOND)), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CONDENSER_ITEM.get())
                .define('O', Items.OBSIDIAN)
                .define('D', Items.DIAMOND)
                .define('C', ModItems.ALCHEMICAL_CHEST_ITEM.get())
                .pattern("ODO")
                .pattern("DCD")
                .pattern("ODO")
                .unlockedBy("has_diamond", has(Items.DIAMOND)), ModuleType.COMMON);

        provider.add(ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModItems.NOVA_CATALYST.get(), 2)
                .requires(Items.TNT)
                .requires(ModItems.MOBIUS_FUEL.get())
                .unlockedBy("has_tnt", has(Items.TNT)), ModuleType.COMMON);

        provider.add(ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, ModItems.NOVA_CATACLYSM.get(), 2)
                .requires(ModItems.NOVA_CATALYST.get())
                .requires(ModItems.AETERNALIS_FUEL.get())
                .unlockedBy("has_catalyst", has(ModItems.NOVA_CATALYST.get())), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DESTRUCTION_CATALYST.get())
                .pattern("NMN")
                .pattern("MFM")
                .pattern("NMN")
                .define('F', Items.FLINT_AND_STEEL)
                .define('M', ModItems.MOBIUS_FUEL.get())
                .define('N', ModItems.NOVA_CATALYST.get())
                .unlockedBy("has_catalyst", has(ModItems.NOVA_CATALYST.get())), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.MIND_STONE.get())
                .pattern("BBB")
                .pattern("RLR")
                .pattern("BBB")
                .define('R', ModItems.MATTER.get(MatterTypes.RED).get())
                .define('B', Items.BOOK)
                .define('L', Items.LAPIS_LAZULI)
                .unlockedBy("has_matter", has(ModItems.MATTER.get(MatterTypes.RED).get())), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SOUL_STONE.get())
                .pattern("GGG")
                .pattern("RLR")
                .pattern("GGG")
                .define('R', ModItems.MATTER.get(MatterTypes.RED).get())
                .define('G', Items.GLOWSTONE_DUST)
                .define('L', Items.LAPIS_LAZULI)
                .unlockedBy("has_matter", has(ModItems.MATTER.get(MatterTypes.RED).get())), ModuleType.COMMON);

        provider.add(ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ModItems.LIFE_STONE.get())
                .requires(ModItems.BODY_STONE.get())
                .requires(ModItems.SOUL_STONE.get())
                .unlockedBy("has_body", has(ModItems.BODY_STONE.get()))
                .unlockedBy("has_soul", has(ModItems.SOUL_STONE.get())), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BODY_STONE.get())
                .pattern("SSS")
                .pattern("RLR")
                .pattern("SSS")
                .define('R', ModItems.MATTER.get(MatterTypes.RED).get())
                .define('S', Items.SUGAR)
                .define('L', Items.LAPIS_LAZULI)
                .unlockedBy("has_matter", has(ModItems.MATTER.get(MatterTypes.RED).get())), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.DM_PEDESTAL.get())
                .pattern("RDR")
                .pattern("RDR")
                .pattern("DDD")
                .define('R', ModItems.MATTER_BLOCK.get(MatterTypes.RED).get())
                .define('D', ModItems.MATTER_BLOCK.get(MatterTypes.DARK).get())
                .unlockedBy("has_matter", has(ModItems.MATTER.get(MatterTypes.RED).get())), ModuleType.COMMON);

        provider.add(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MATTER.get(MatterTypes.DARK).get(), 9)
                .requires(ModItems.MATTER_BLOCK.get(MatterTypes.DARK).get())
                .unlockedBy("has_matter", has(ModItems.MATTER.get(MatterTypes.DARK).get())),
                new ResourceLocation(Constants.MOD_ID, "darkmatterblock_to_matter"), ModuleType.COMMON);

        provider.add(ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MATTER.get(MatterTypes.RED).get(), 9)
                .requires(ModItems.MATTER_BLOCK.get(MatterTypes.RED).get())
                .unlockedBy("has_matter", has(ModItems.MATTER.get(MatterTypes.RED).get())),
                new ResourceLocation(Constants.MOD_ID, "redmatterblock_to_matter"), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.VOLCANITE_AMULET.get())
                .pattern("LLL")
                .pattern("DDD")
                .pattern("LLL")
                .define('D', ModItems.MATTER.get(MatterTypes.DARK).get())
                .define('L', Items.LAVA_BUCKET)
                .unlockedBy("has_matter", has(ModItems.MATTER.get(MatterTypes.DARK).get())), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.EVERTIDE_AMULET.get())
                .pattern("WWW")
                .pattern("DDD")
                .pattern("WWW")
                .define('D', ModItems.MATTER.get(MatterTypes.DARK).get())
                .define('W', Items.WATER_BUCKET)
                .unlockedBy("has_matter", has(ModItems.MATTER.get(MatterTypes.DARK).get())), ModuleType.COMMON);


        addDarkMatterRecipes(provider);
        addRedMatterRecipes(provider);
    }

    private static void addDarkMatterRecipes(PolyRecipeProvider provider)
    {
        ItemLike darkMatter = ModItems.MATTER.get(MatterTypes.DARK).get();
        var hasMatter = has(darkMatter);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DM_HELMET.get())
                .pattern("MMM")
                .pattern("M M")
                .define('M', darkMatter)
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DM_CHESTPLATE.get())
                .pattern("M M")
                .pattern("MMM")
                .pattern("MMM")
                .define('M', darkMatter)
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DM_LEGGINGS.get())
                .pattern("MMM")
                .pattern("M M")
                .pattern("M M")
                .define('M', darkMatter)
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DM_BOOTS.get())
                .pattern("M M")
                .pattern("M M")
                .define('M', darkMatter)
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DM_AXE.get())
                .pattern("MM")
                .pattern("MD")
                .pattern(" D")
                .define('D', Items.DIAMOND)
                .define('M', darkMatter)
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DM_PICKAXE.get())
                .pattern("MMM")
                .pattern(" D ")
                .pattern(" D ")
                .define('D', Items.DIAMOND)
                .define('M', darkMatter)
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DM_SHOVEL.get())
                .pattern("M")
                .pattern("D")
                .pattern("D")
                .define('D', Items.DIAMOND)
                .define('M', darkMatter)
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DM_SWORD.get())
                .pattern("M")
                .pattern("M")
                .pattern("D")
                .define('D', Items.DIAMOND)
                .define('M', ModItems.MATTER.get(MatterTypes.DARK).get())
                .unlockedBy("has_matter", has(ModItems.MATTER.get(MatterTypes.DARK).get())), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DM_HOE.get())
                .pattern("MM")
                .pattern(" D")
                .pattern(" D")
                .define('D', Items.DIAMOND)
                .define('M', darkMatter)
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);
        //Shears
        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DM_SHEARS.get())
                .pattern(" M")
                .pattern("D ")
                .define('D', Items.DIAMOND)
                .define('M', darkMatter)
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);
        //Hammer
//        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.DM_HAMMER.get())
//                .pattern("MDM")
//                .pattern(" D ")
//                .pattern(" D ")
//                .define('D', Items.DIAMOND)
//                .define('M', darkMatter)
//                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);
    }

    private static void addRedMatterRecipes(PolyRecipeProvider provider)
    {
        ItemLike redMatter = ModItems.MATTER.get(MatterTypes.RED).get();
        ItemLike darkMatter = ModItems.MATTER.get(MatterTypes.DARK).get();

        var hasMatter = has(redMatter);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.RM_HELMET.get())
                .pattern("MMM")
                .pattern("MDM")
                .define('M', redMatter)
                .define('D', ModItems.DM_HELMET.get())
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.RM_CHESTPLATE.get())
                .pattern("MDM")
                .pattern("MMM")
                .pattern("MMM")
                .define('M', redMatter)
                .define('D', ModItems.DM_CHESTPLATE.get())
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.RM_LEGGINGS.get())
                .pattern("MMM")
                .pattern("MDM")
                .pattern("M M")
                .define('M', redMatter)
                .define('D', ModItems.DM_LEGGINGS.get())
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.RM_BOOTS.get())
                .pattern("MDM")
                .pattern("M M")
                .define('M', redMatter)
                .define('D', ModItems.DM_BOOTS.get())
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.RM_AXE.get())
                .pattern("RR")
                .pattern("RA")
                .pattern(" M")
                .define('R', redMatter)
                .define('M', darkMatter)
                .define('A', ModItems.DM_AXE.get())
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.RM_PICKAXE.get())
                .pattern("RRR")
                .pattern(" P ")
                .pattern(" M ")
                .define('R', redMatter)
                .define('M', darkMatter)
                .define('P', ModItems.DM_PICKAXE.get())
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.RM_SHOVEL.get())
                .pattern("R")
                .pattern("S")
                .pattern("M")
                .define('R', redMatter)
                .define('M', darkMatter)
                .define('S', ModItems.DM_SHOVEL.get())
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.RM_SWORD.get())
                .pattern("R")
                .pattern("R")
                .pattern("S")
                .define('R', redMatter)
                .define('S', ModItems.DM_SWORD.get())
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.RM_HOE.get())
                .pattern("RR")
                .pattern(" H")
                .pattern(" M")
                .define('R', redMatter)
                .define('M', darkMatter)
                .define('H', ModItems.DM_HOE.get())
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.RM_SHEARS.get())
                .pattern(" R")
                .pattern("S ")
                .define('R', redMatter)
                .define('S', ModItems.DM_SHEARS.get())
                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);

        //Hammer
//        provider.add(ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.RM_HAMMER.get())
//                .pattern("RMR")
//                .pattern(" H ")
//                .pattern(" M ")
//                .define('R', redMatter)
//                .define('M', darkMatter)
//                .define('H', ModItems.DM_HAMMER.get())
//                .unlockedBy("has_matter", hasMatter), ModuleType.COMMON);
    }

    private static ShapedRecipeBuilder createBagRecipe(ItemLike out, ItemLike wool)
    {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, out)
                .define('W', wool)
                .define('C', ModItems.ALCHEMICAL_CHEST_ITEM.get())
                .define('D', ModItems.COVALENCE_DUST_HIGH.get())

                .pattern("DDD")
                .pattern("WCW")
                .pattern("WWW")
                .unlockedBy("has_log", has(ItemTags.LOGS));
    }

    private static ShapedRecipeBuilder createCompressionRecipe(ItemLike out, ItemLike in)
    {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, out)
                .define('#', in)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_log", has(ItemTags.LOGS));
    }

    private static ShapedRecipeBuilder createSurroundRecipe(ItemLike out, ItemLike in, ItemLike center)
    {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, out)
                .define('#', in)
                .define('c', center)
                .pattern("###")
                .pattern("#c#")
                .pattern("###")
                .unlockedBy("has_log", has(ItemTags.LOGS));
    }
}
