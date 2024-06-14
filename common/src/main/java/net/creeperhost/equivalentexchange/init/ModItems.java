package net.creeperhost.equivalentexchange.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.items.ItemAlchemyBag;
import net.creeperhost.equivalentexchange.items.ItemKleinStar;
import net.creeperhost.equivalentexchange.items.ItemPhilosophersStone;
import net.creeperhost.equivalentexchange.items.armour.ItemDmArmour;
import net.creeperhost.equivalentexchange.items.armour.ItemRmArmour;
import net.creeperhost.equivalentexchange.items.tools.*;
import net.creeperhost.equivalentexchange.items.toys.*;
import net.creeperhost.equivalentexchange.items.toys.amulets.ItemEvertide;
import net.creeperhost.equivalentexchange.items.toys.amulets.ItemVolcanite;
import net.creeperhost.equivalentexchange.items.toys.stones.ItemBodyStone;
import net.creeperhost.equivalentexchange.items.toys.stones.ItemLifeStone;
import net.creeperhost.equivalentexchange.items.toys.stones.ItemMindStone;
import net.creeperhost.equivalentexchange.items.toys.stones.ItemSoulStone;
import net.creeperhost.equivalentexchange.types.*;
import net.creeperhost.polylib.helpers.FuelHelper;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Constants.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> PHILOSOPHERS_STONE = ITEMS.register("philosophers_stone", ItemPhilosophersStone::new);

    public static final RegistrySupplier<Item> IRON_BAND = ITEMS.register("iron_band", () -> new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> COVALENCE_DUST_LOW = ITEMS.register(CovalenceDustTypes.LOW.getName(), () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> COVALENCE_DUST_MEDIUM = ITEMS.register(CovalenceDustTypes.MEDIUM.getName(), () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> COVALENCE_DUST_HIGH = ITEMS.register(CovalenceDustTypes.HIGH.getName(), () -> new Item(new Item.Properties()));

    //Fuels
    public static final RegistrySupplier<Item> ALCHEMICAL_COAL = ITEMS.register("alchemical_coal", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> MOBIUS_FUEL = ITEMS.register("mobius_fuel", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> AETERNALIS_FUEL = ITEMS.register("aeternalis_fuel", () -> new Item(new Item.Properties()));

    public static final RegistrySupplier<Item> ALCHEMICAL_COAL_BLOCK = ITEMS.register("alchemical_coal_block", () -> new BlockItem(ModBlocks.ALCHEMICAL_COAL.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> MOBIUS_FUEL_BLOCK = ITEMS.register("mobius_fuel_block", () -> new BlockItem(ModBlocks.MOBIUS_FUEL.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> AETERNALIS_FUEL_BLOCK = ITEMS.register("aeternalis_fuel_block", () -> new BlockItem(ModBlocks.AETERNALIS_FUEL.get(), new Item.Properties()));

    //Stars
    public static final RegistrySupplier<Item> EIN = ITEMS.register("klein_star_ein", () -> new ItemKleinStar(KleinStarTypes.EIN));
    public static final RegistrySupplier<Item> ZWEI = ITEMS.register("klein_star_zwei", () -> new ItemKleinStar(KleinStarTypes.ZWEI));
    public static final RegistrySupplier<Item> DREI = ITEMS.register("klein_star_drei", () -> new ItemKleinStar(KleinStarTypes.DREI));
    public static final RegistrySupplier<Item> VIER = ITEMS.register("klein_star_vier", () -> new ItemKleinStar(KleinStarTypes.VIER));
    public static final RegistrySupplier<Item> SPHERE = ITEMS.register("klein_star_sphere", () -> new ItemKleinStar(KleinStarTypes.SPHERE));
    public static final RegistrySupplier<Item> OMEGA = ITEMS.register("klein_star_omega", () -> new ItemKleinStar(KleinStarTypes.OMEGA));

    //Toys
    public static final RegistrySupplier<Item> REPAIR_TALISMAN = ITEMS.register("repair_talisman", () -> new ItemRepairTalisman());
    public static final RegistrySupplier<Item> TRANSMUTATION_TABLET = ITEMS.register("transmutation_tablet", () -> new ItemTransmutationTablet());
    public static final RegistrySupplier<Item> DENSE_GEM = ITEMS.register("dense_gem", () -> new ItemDenseGem());
    public static final RegistrySupplier<Item> DESTRUCTION_CATALYST = ITEMS.register("destruction_catalyst", () -> new ItemDestructionCatalyst());
    public static final RegistrySupplier<Item> WATCH_OF_FLOWING_TIME = ITEMS.register("time_watch", () -> new ItemWatch());

    public static final RegistrySupplier<Item> LIFE_STONE = ITEMS.register("life_stone", () -> new ItemLifeStone());
    public static final RegistrySupplier<Item> BODY_STONE = ITEMS.register("body_stone", () -> new ItemBodyStone());
    public static final RegistrySupplier<Item> MIND_STONE = ITEMS.register("mind_stone", () -> new ItemMindStone());
    public static final RegistrySupplier<Item> SOUL_STONE = ITEMS.register("soul_stone", () -> new ItemSoulStone());

    public static final RegistrySupplier<Item> EVERTIDE_AMULET = ITEMS.register("evertide_amulet", () -> new ItemEvertide());
    public static final RegistrySupplier<Item> VOLCANITE_AMULET = ITEMS.register("volcanite_amulet", () -> new ItemVolcanite());

    public static final RegistrySupplier<Item> DM_SWORD = ITEMS.register("dm_sword", () -> new ItemDmSword(EEToolTiers.DARK_MATTER, 2, 9, new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistrySupplier<Item> DM_PICKAXE = ITEMS.register("dm_pickaxe", () -> new ItemDmPickaxe(EEToolTiers.DARK_MATTER, 2, 9, new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistrySupplier<Item> DM_AXE = ITEMS.register("dm_axe", () -> new ItemDmAxe(EEToolTiers.DARK_MATTER, 2, 9, new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistrySupplier<Item> DM_SHOVEL = ITEMS.register("dm_shovel", () -> new ItemDmShovel(EEToolTiers.DARK_MATTER, 2, 9, new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistrySupplier<Item> DM_HOE = ITEMS.register("dm_hoe", () -> new ItemDmHoe(EEToolTiers.DARK_MATTER, 2, 9, new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistrySupplier<Item> DM_SHEARS = ITEMS.register("dm_shears", () -> new ItemDmShears(EEToolTiers.DARK_MATTER, new Item.Properties().stacksTo(1).fireResistant()));


    public static final RegistrySupplier<Item> RM_SWORD = ITEMS.register("rm_sword", () -> new ItemDmSword(EEToolTiers.RED_MATTER, 2, 9, new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistrySupplier<Item> RM_PICKAXE = ITEMS.register("rm_pickaxe", () -> new ItemDmPickaxe(EEToolTiers.RED_MATTER, 2, 9, new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistrySupplier<Item> RM_AXE = ITEMS.register("rm_axe", () -> new ItemDmAxe(EEToolTiers.RED_MATTER, 2, 9, new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistrySupplier<Item> RM_SHOVEL = ITEMS.register("rm_shovel", () -> new ItemDmShovel(EEToolTiers.RED_MATTER, 2, 9, new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistrySupplier<Item> RM_HOE = ITEMS.register("rm_hoe", () -> new ItemDmHoe(EEToolTiers.RED_MATTER, 2, 9, new Item.Properties().stacksTo(1).fireResistant()));
    public static final RegistrySupplier<Item> RM_SHEARS = ITEMS.register("rm_shears", () -> new ItemDmShears(EEToolTiers.RED_MATTER, new Item.Properties().stacksTo(1).fireResistant()));

    public static final RegistrySupplier<Item> DM_HELMET = ITEMS.register("dm_head", () -> new ItemDmArmour(ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistrySupplier<Item> DM_CHESTPLATE = ITEMS.register("dm_chest", () -> new ItemDmArmour(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistrySupplier<Item> DM_LEGGINGS = ITEMS.register("dm_legs", () -> new ItemDmArmour(ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistrySupplier<Item> DM_BOOTS = ITEMS.register("dm_feet", () -> new ItemDmArmour(ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistrySupplier<Item> RM_HELMET = ITEMS.register("rm_head", () -> new ItemRmArmour(ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistrySupplier<Item> RM_CHESTPLATE = ITEMS.register("rm_chest", () -> new ItemRmArmour(ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistrySupplier<Item> RM_LEGGINGS = ITEMS.register("rm_legs", () -> new ItemRmArmour(ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistrySupplier<Item> RM_BOOTS = ITEMS.register("rm_feet", () -> new ItemRmArmour(ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final Map<MatterTypes, Supplier<Item>> MATTER = Util.make(new LinkedHashMap<>(), map ->
    {
        for (MatterTypes value : MatterTypes.values())
        {
            map.put(value, ITEMS.register(value.getName() + "_matter", () -> new Item(new Item.Properties())));
        }
    });

    public static final Map<BagTypes, Supplier<Item>> BAGS = Util.make(new LinkedHashMap<>(), map ->
    {
        for (BagTypes value : BagTypes.values())
        {
            map.put(value, ITEMS.register(value.getName() + "_alchemical_bag", () -> new ItemAlchemyBag(value)));
        }
    });

    public static final Map<DiviningRodTypes, Supplier<Item>> DIVINING_RODS = Util.make(new LinkedHashMap<>(), map ->
    {
        for (DiviningRodTypes value : DiviningRodTypes.values())
        {
            map.put(value, ITEMS.register(value.getName(), () -> new ItemDiviningRod(value)));
        }
    });


    //ItemBlocks
    public static final RegistrySupplier<Item> ALCHEMICAL_CHEST_ITEM = ITEMS.register("alchemical_chest", () -> new BlockItem(ModBlocks.ALCHEMICAL_CHEST.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> TRANSMUTATION_TABLE = ITEMS.register("transmutation_table", () -> new BlockItem(ModBlocks.TRANSMUTATION_TABLE.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> CONDENSER_ITEM = ITEMS.register("condenser", () -> new BlockItem(ModBlocks.CONDENSER.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> NOVA_CATALYST = ITEMS.register("nova_catalyst", () -> new BlockItem(ModBlocks.NOVA_CATALYST.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> NOVA_CATACLYSM = ITEMS.register("nova_cataclysm", () -> new BlockItem(ModBlocks.NOVA_CATACLYSM.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> DM_PEDESTAL = ITEMS.register("dm_pedestal", () -> new BlockItem(ModBlocks.DM_PEDESTAL.get(), new Item.Properties()));

    public static final Map<MatterTypes, Supplier<BlockItem>> MATTER_BLOCK = Util.make(new LinkedHashMap<>(), map ->
    {
        for (MatterTypes value : MatterTypes.values())
        {
            map.put(value, ITEMS.register(value.getName() + "_matter_block", () -> new BlockItem(ModBlocks.MATTER_BLOCK.get(value).get(), new Item.Properties())));
        }
    });

    public static final Map<CollectorType, Supplier<BlockItem>> COLLECTOR = Util.make(new LinkedHashMap<>(), map ->
    {
        for (CollectorType value : CollectorType.values())
        {
            map.put(value, ITEMS.register(value.getName(), () -> new BlockItem(ModBlocks.COLLECTORS.get(value).get(), new Item.Properties())));
        }
    });

    public static final Map<RelayTypes, Supplier<BlockItem>> RELAYS = Util.make(new LinkedHashMap<>(), map ->
    {
        for (RelayTypes value : RelayTypes.values())
        {
            map.put(value, ITEMS.register(value.getName(), () -> new BlockItem(ModBlocks.RELAYS.get(value).get(), new Item.Properties())));
        }
    });


    public static void registerFuelValues()
    {
        int coalValue = 1_600;
        int alchValue = coalValue * 4;
        int mobiusValue = alchValue * 4;
        int aeternalisValue = mobiusValue * 4;

        FuelHelper.registerFuel(ALCHEMICAL_COAL.get(), alchValue);
        FuelHelper.registerFuel(MOBIUS_FUEL.get(), mobiusValue);
        FuelHelper.registerFuel(AETERNALIS_FUEL.get(), aeternalisValue);

        FuelHelper.registerFuel(ALCHEMICAL_COAL_BLOCK.get(), alchValue * 9);
        FuelHelper.registerFuel(MOBIUS_FUEL_BLOCK.get(), mobiusValue * 9);
        FuelHelper.registerFuel(AETERNALIS_FUEL_BLOCK.get(), aeternalisValue * 9);
    }
}
