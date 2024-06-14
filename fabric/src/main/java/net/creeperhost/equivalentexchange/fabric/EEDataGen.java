package net.creeperhost.equivalentexchange.fabric;

import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeTags;
import net.creeperhost.equivalentexchange.client.EEKeyBindings;
import net.creeperhost.equivalentexchange.init.ModBlocks;
import net.creeperhost.equivalentexchange.init.ModCreativeTabs;
import net.creeperhost.equivalentexchange.init.ModItems;
import net.creeperhost.equivalentexchange.types.BagTypes;
import net.creeperhost.equivalentexchange.types.DiviningRodTypes;
import net.creeperhost.equivalentexchange.types.MatterTypes;
import net.creeperhost.polylib.fabric.datagen.ModuleType;
import net.creeperhost.polylib.fabric.datagen.providers.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class EEDataGen implements DataGeneratorEntrypoint
{
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
    {
        var pack = fabricDataGenerator.createPack();

        pack.addProvider((output, registriesFuture) ->
        {
            PolyLanguageProvider languageProvider = new PolyLanguageProvider(output, ModuleType.COMMON);
            register(languageProvider);
            return languageProvider;
        });

        pack.addProvider((output, registriesFuture) ->
        {
            PolyModelProvider modelProvider = new PolyModelProvider(output, ModuleType.COMMON);
            register(modelProvider);
            return modelProvider;
        });

        var blockTagProv = pack.addProvider((output, registriesFuture) ->
        {
            PolyBlockTagProvider blockTagProvider = new PolyBlockTagProvider(output, registriesFuture, ModuleType.COMMON);
            ModBlocks.BLOCKS.forEach(blockRegistrySupplier -> blockTagProvider.add(BlockTags.MINEABLE_WITH_PICKAXE, blockRegistrySupplier.get(), ModuleType.COMMON));
            return blockTagProvider;
        });

        pack.addProvider((output, registriesFuture) ->
        {
            PolyItemTagProvider itemTagProvider = new PolyItemTagProvider(output, registriesFuture, blockTagProv, ModuleType.COMMON);
            register(itemTagProvider);
            return itemTagProvider;
        });

        pack.addProvider((output, registriesFuture) ->
        {
            PolyRecipeProvider recipeProvider = new PolyRecipeProvider(output, ModuleType.COMMON);
            EERecipeGen.init(recipeProvider);
            return recipeProvider;
        });
        pack.addProvider((output, registriesFuture) ->
        {
            PolyBlockLootProvider blockLootProvider = new PolyBlockLootProvider(output, ModuleType.COMMON);
            ModBlocks.BLOCKS.forEach(blockRegistrySupplier -> blockLootProvider.addSelfDrop(blockRegistrySupplier.get(), ModuleType.COMMON));
            return blockLootProvider;
        });
    }

    public void register(PolyModelProvider provider)
    {
        provider.addSimpleBlockModel(ModBlocks.AETERNALIS_FUEL.get(), new ResourceLocation("equivalentexchange:block/aeternalis_fuel"), ModuleType.COMMON);
        provider.addSimpleBlockModel(ModBlocks.MOBIUS_FUEL.get(), new ResourceLocation("equivalentexchange:block/mobius_fuel_block"), ModuleType.COMMON);
        provider.addSimpleBlockModel(ModBlocks.ALCHEMICAL_COAL.get(), new ResourceLocation("equivalentexchange:block/alchemical_coal_block"), ModuleType.COMMON);

        provider.addSimpleBlockModel(ModBlocks.MATTER_BLOCK.get(MatterTypes.DARK).get(), new ResourceLocation("equivalentexchange:block/dark_matter_block"), ModuleType.COMMON);
        provider.addSimpleBlockModel(ModBlocks.MATTER_BLOCK.get(MatterTypes.RED).get(), new ResourceLocation("equivalentexchange:block/red_matter_block"), ModuleType.COMMON);

        provider.addItemModel(ModItems.PHILOSOPHERS_STONE.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.IRON_BAND.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);

        provider.addItemModel(ModItems.COVALENCE_DUST_LOW.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.COVALENCE_DUST_MEDIUM.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.COVALENCE_DUST_HIGH.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.REPAIR_TALISMAN.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.MOBIUS_FUEL.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.ALCHEMICAL_COAL.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.AETERNALIS_FUEL.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.DESTRUCTION_CATALYST.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.MATTER.get(MatterTypes.DARK).get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.MATTER.get(MatterTypes.RED).get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.TRANSMUTATION_TABLET.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);

        provider.addItemModel(ModItems.EIN.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.ZWEI.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.DREI.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.VIER.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.SPHERE.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.OMEGA.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);

        provider.addItemModel(ModItems.VOLCANITE_AMULET.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.EVERTIDE_AMULET.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);

        provider.addItemModel(ModItems.DM_SWORD.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.DM_PICKAXE.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.DM_AXE.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.DM_SHOVEL.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.DM_HOE.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.DM_SHEARS.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);

        provider.addItemModel(ModItems.RM_SWORD.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.RM_PICKAXE.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.RM_AXE.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.RM_SHOVEL.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.RM_HOE.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.RM_SHEARS.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);

        provider.addItemModel(ModItems.DM_HELMET.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.DM_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.DM_LEGGINGS.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.DM_BOOTS.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);

        provider.addItemModel(ModItems.RM_HELMET.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.RM_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.RM_LEGGINGS.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);
        provider.addItemModel(ModItems.RM_BOOTS.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON);

        ModItems.DIVINING_RODS.values().forEach(itemSupplier -> provider.addItemModel(itemSupplier.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON));
        ModItems.BAGS.values().forEach(itemSupplier -> provider.addItemModel(itemSupplier.get(), ModelTemplates.FLAT_ITEM, ModuleType.COMMON));
    }

    public void register(PolyItemTagProvider polyItemTagProvider)
    {
        TagKey<Item> emc_deny = EquivalentExchangeTags.EMC_DENYLIST_ITEM;

        polyItemTagProvider.add(emc_deny, Items.BEDROCK, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.COAL_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.DEEPSLATE_COAL_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.IRON_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.DEEPSLATE_IRON_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.COPPER_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.DEEPSLATE_COPPER_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.GOLD_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.DEEPSLATE_GOLD_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.REDSTONE_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.DEEPSLATE_REDSTONE_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.EMERALD_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.DEEPSLATE_EMERALD_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.LAPIS_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.DEEPSLATE_LAPIS_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.DIAMOND_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.DEEPSLATE_DIAMOND_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.NETHER_GOLD_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.NETHER_QUARTZ_ORE, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.RAW_IRON, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.RAW_COPPER, ModuleType.COMMON);
        polyItemTagProvider.add(emc_deny, Items.RAW_GOLD, ModuleType.COMMON);

        polyItemTagProvider.add(EquivalentExchangeTags.COVALENCE_DUST, ModItems.COVALENCE_DUST_LOW.get(), ModuleType.COMMON);
        polyItemTagProvider.add(EquivalentExchangeTags.COVALENCE_DUST, ModItems.COVALENCE_DUST_MEDIUM.get(), ModuleType.COMMON);
        polyItemTagProvider.add(EquivalentExchangeTags.COVALENCE_DUST, ModItems.COVALENCE_DUST_HIGH.get(), ModuleType.COMMON);

    }

    public void register(PolyLanguageProvider languageProvider)
    {
        languageProvider.add(ModCreativeTabs.CREATIVE_TAB.get(), "Equivalent Exchange 4", ModuleType.COMMON);
        languageProvider.add(Constants.SHIFT_TEXT, "Hold <Shift> for more info", ModuleType.COMMON);
        languageProvider.add("tooltip.equivalentexchange.mind_stone", "Stores Experience", ModuleType.COMMON);

        languageProvider.add(ModItems.PHILOSOPHERS_STONE.get(), "Philosophers Stone", ModuleType.COMMON);
        languageProvider.add(ModItems.MATTER.get(MatterTypes.DARK).get(), "Dark Matter", ModuleType.COMMON);
        languageProvider.add(ModItems.MATTER.get(MatterTypes.RED).get(), "Red Matter", ModuleType.COMMON);
        languageProvider.add(ModItems.ALCHEMICAL_COAL.get(), "Alchemical Coal", ModuleType.COMMON);
        languageProvider.add(ModItems.MOBIUS_FUEL.get(), "Mobius Fuel", ModuleType.COMMON);
        languageProvider.add(ModItems.AETERNALIS_FUEL.get(), "Aeternalis Fuel", ModuleType.COMMON);

        languageProvider.add(ModItems.EIN.get(), "Klein Star Ein", ModuleType.COMMON);
        languageProvider.add(ModItems.ZWEI.get(), "Klein Star Zwei", ModuleType.COMMON);
        languageProvider.add(ModItems.DREI.get(), "Klein Star Drei", ModuleType.COMMON);
        languageProvider.add(ModItems.VIER.get(), "Klein Star Vier", ModuleType.COMMON);
        languageProvider.add(ModItems.SPHERE.get(), "Klein Star Sphere", ModuleType.COMMON);
        languageProvider.add(ModItems.OMEGA.get(), "Klein Star Omega", ModuleType.COMMON);

        languageProvider.add(ModItems.COVALENCE_DUST_LOW.get(), "Covalence Dust Low", ModuleType.COMMON);
        languageProvider.add(ModItems.COVALENCE_DUST_MEDIUM.get(), "Covalence Dust Medium", ModuleType.COMMON);
        languageProvider.add(ModItems.COVALENCE_DUST_HIGH.get(), "Covalence Dust High", ModuleType.COMMON);
        languageProvider.add(ModItems.IRON_BAND.get(), "Iron Band", ModuleType.COMMON);
        languageProvider.add(ModItems.REPAIR_TALISMAN.get(), "Repair Talisman", ModuleType.COMMON);
        languageProvider.add(ModItems.TRANSMUTATION_TABLET.get(), "Transmutation Tablet", ModuleType.COMMON);
        languageProvider.add(ModItems.DENSE_GEM.get(), "Gem of eternal density", ModuleType.COMMON);
        languageProvider.add(ModItems.DIVINING_RODS.get(DiviningRodTypes.LOW_DIVINING_ROD).get(), "Low Grade Diving Rod", ModuleType.COMMON);
        languageProvider.add(ModItems.DIVINING_RODS.get(DiviningRodTypes.MEDIUM_DIVINING_ROD).get(), "Medium Grade Diving Rod", ModuleType.COMMON);
        languageProvider.add(ModItems.DIVINING_RODS.get(DiviningRodTypes.HIGH_DIVINING_ROD).get(), "High Grade Diving Rod", ModuleType.COMMON);
        languageProvider.add(ModItems.DESTRUCTION_CATALYST.get(), "Destruction Catalyst", ModuleType.COMMON);
        languageProvider.add(ModItems.WATCH_OF_FLOWING_TIME.get(), "Watch Of Flowing Time", ModuleType.COMMON);
        languageProvider.add(ModItems.LIFE_STONE.get(), "Life Stone", ModuleType.COMMON);
        languageProvider.add(ModItems.BODY_STONE.get(), "Body Stone", ModuleType.COMMON);
        languageProvider.add(ModItems.MIND_STONE.get(), "Mind Stone", ModuleType.COMMON);
        languageProvider.add(ModItems.SOUL_STONE.get(), "Soul Stone", ModuleType.COMMON);

        languageProvider.add("item.equivalentexchange.alchemical_bag", "Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.BLACK).get(), "Black Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.BLUE).get(), "Blue Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.BROWN).get(), "Brown Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.CYAN).get(), "Cyan Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.GRAY).get(), "Grey Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.GREEN).get(), "Green Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.LIGHT_BLUE).get(), "Light blue Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.LIGHT_GRAY).get(), "Light gray Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.LIME).get(), "Lime Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.MAGENTA).get(), "Magenta Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.ORANGE).get(), "Orange Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.PINK).get(), "Pink Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.PURPLE).get(), "Purple Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.RED).get(), "Red Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.WHITE).get(), "White Alchemy Bag", ModuleType.COMMON);
        languageProvider.add(ModItems.BAGS.get(BagTypes.YELLOW).get(), "Yellow Alchemy Bag", ModuleType.COMMON);

        languageProvider.add(ModItems.VOLCANITE_AMULET.get(), "Volcanite Amulet", ModuleType.COMMON);
        languageProvider.add(ModItems.EVERTIDE_AMULET.get(), "Evertide Amulet", ModuleType.COMMON);

        languageProvider.add("block.equivalentexchange.alchemical_chest", "Alchemical Chest", ModuleType.COMMON);
        languageProvider.add("block.equivalentexchange.transmutation_table", "Transmutation Table", ModuleType.COMMON);
        languageProvider.add("block.equivalentexchange.personal_emc_link", "Personal Emc Link", ModuleType.COMMON);
        languageProvider.add("block.equivalentexchange.dark_matter_block", "Block of Dark Matter", ModuleType.COMMON);
        languageProvider.add("block.equivalentexchange.red_matter_block", "Block of Red Matter", ModuleType.COMMON);
        languageProvider.add("block.equivalentexchange.alchemical_coal_block", "Block of Alchemical Coal", ModuleType.COMMON);
        languageProvider.add("block.equivalentexchange.mobius_fuel_block", "Block of Mobius Fuel", ModuleType.COMMON);
        languageProvider.add("block.equivalentexchange.aeternalis_fuel_block", "Block of Aeternalis Fuel", ModuleType.COMMON);
        languageProvider.add("block.equivalentexchange.condenser", "Condenser", ModuleType.COMMON);

        languageProvider.add("block.equivalentexchange.basic_collector", "Basic Collector", ModuleType.COMMON);
        languageProvider.add("block.equivalentexchange.dark_matter_collector", "Dark Matter Collector", ModuleType.COMMON);
        languageProvider.add("block.equivalentexchange.red_matter_collector", "Red Matter Collector", ModuleType.COMMON);

        languageProvider.add("block.equivalentexchange.basic_relay", "Basic Relay", ModuleType.COMMON);
        languageProvider.add("block.equivalentexchange.dark_matter_relay", "Dark Matter Relay", ModuleType.COMMON);
        languageProvider.add("block.equivalentexchange.red_matter_relay", "Red Matter Relay", ModuleType.COMMON);

        languageProvider.add("block.equivalentexchange.nova_catalyst", "Nova Catalyst", ModuleType.COMMON);
        languageProvider.add("block.equivalentexchange.nova_cataclysm", "Nova Cataclysm", ModuleType.COMMON);

        languageProvider.add("tooltip.equivalentexchange.equivalentexchange:soul_stone", "Heals the player", ModuleType.COMMON);
        languageProvider.add("tooltip.equivalentexchange.equivalentexchange:body_stone", "Restores the players hunger", ModuleType.COMMON);
        languageProvider.add("tooltip.equivalentexchange.equivalentexchange:mind_stone", "Store's xp", ModuleType.COMMON);
        languageProvider.add("tooltip.equivalentexchange.equivalentexchange:life_stone", "Heals and feeds the player", ModuleType.COMMON);

        languageProvider.add(ModItems.DM_SWORD.get(), "Dark Matter Sword", ModuleType.COMMON);
        languageProvider.add(ModItems.DM_PICKAXE.get(), "Dark Matter Pickaxe", ModuleType.COMMON);
        languageProvider.add(ModItems.DM_AXE.get(), "Dark Matter Axe", ModuleType.COMMON);
        languageProvider.add(ModItems.DM_SHOVEL.get(), "Dark Matter Shovel", ModuleType.COMMON);
        languageProvider.add(ModItems.DM_HOE.get(), "Dark Matter Hoe", ModuleType.COMMON);
        languageProvider.add(ModItems.DM_SHEARS.get(), "Dark Matter Shears", ModuleType.COMMON);

        languageProvider.add(ModItems.DM_HELMET.get(), "Dark Matter Helmet", ModuleType.COMMON);
        languageProvider.add(ModItems.DM_CHESTPLATE.get(), "Dark Matter Chestplate", ModuleType.COMMON);
        languageProvider.add(ModItems.DM_LEGGINGS.get(), "Dark Matter Leggings", ModuleType.COMMON);
        languageProvider.add(ModItems.DM_BOOTS.get(), "Dark Matter Boots", ModuleType.COMMON);

        languageProvider.add(ModItems.RM_HELMET.get(), "Red Matter Helmet", ModuleType.COMMON);
        languageProvider.add(ModItems.RM_CHESTPLATE.get(), "Red Matter Chestplate", ModuleType.COMMON);
        languageProvider.add(ModItems.RM_LEGGINGS.get(), "Red Matter Leggings", ModuleType.COMMON);
        languageProvider.add(ModItems.RM_BOOTS.get(), "Red Matter Boots", ModuleType.COMMON);

        languageProvider.add(ModItems.RM_SWORD.get(), "Red Matter Sword", ModuleType.COMMON);
        languageProvider.add(ModItems.RM_PICKAXE.get(), "Red Matter Pickaxe", ModuleType.COMMON);
        languageProvider.add(ModItems.RM_AXE.get(), "Red Matter Axe", ModuleType.COMMON);
        languageProvider.add(ModItems.RM_SHOVEL.get(), "Red Matter Shovel", ModuleType.COMMON);
        languageProvider.add(ModItems.RM_HOE.get(), "Red Matter Hoe", ModuleType.COMMON);
        languageProvider.add(ModItems.RM_SHEARS.get(), "Red Matter Shears", ModuleType.COMMON);

        languageProvider.add(EEKeyBindings.ee_category, "Equivalent Exchange", ModuleType.COMMON);
        languageProvider.add(EEKeyBindings.ACTION_KEY.getName(), "Action", ModuleType.COMMON);
        languageProvider.add(EEKeyBindings.CHARGE_KEY.getName(), "Charge", ModuleType.COMMON);

        languageProvider.add(ModItems.DM_PEDESTAL.get(), "Dark Matter Pedestal", ModuleType.COMMON);


        //JEI
        languageProvider.add("equivalentexchange.transmutation.category", "Transmutation Recipe", ModuleType.COMMON);
    }
}
