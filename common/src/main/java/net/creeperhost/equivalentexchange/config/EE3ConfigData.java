package net.creeperhost.equivalentexchange.config;

import net.creeperhost.polylib.blue.endless.jankson.Comment;
import net.creeperhost.polylib.config.ConfigData;

public class EE3ConfigData extends ConfigData
{
    @Comment("Enable ProjectE compat, This will allow EE4 to load ProjectE emc values provided by other mods")
    public boolean ProjectECompat = false;

    @Comment("Create EMC values for non emc items using their recipes")
    public boolean UseRecipeManager = true;

    @Comment("Collectors require their owner to be online to function")
    public boolean CollectorsRequireOnline = true;

    @Comment("Emc cost to repair 1 damage using the repair talisman")
    public double RepairTalismanCost = 10;

    @Comment("Destruction catalyst cost when breaking blocks")
    public double DestructionCatalystUseCost = 100;

    @Comment("Print debug logs")
    public boolean DEBUG_PRINT = false;

    @Comment("Disable EMC tooltip in other inventories (This will also mean JEI)")
    public boolean DisableEmcTooltip = false;

    @Comment("Cost of using the life stone to heal in EMC")
    public double LifeStoneHealCost = 100;

    @Comment("Cost of using the life stone to eat in EMC")
    public double LifeStoneEatCost = 100;

    @Comment("Life Stone effect range on Pedestal")
    public int LifeStoneRange = 5;

    @Comment("Volcanite create lave cost in EMC")
    public double VolcaniteLavaCost = 100;

    @Comment("Volcanite spawn projectile cost in EMC")
    public double VolcaniteSpawnProjectileCost = 500;

    @Comment("Volcanite Pedestal cost in EMC")
    public double VolcanitePedestalCost = 1000;

    @Comment("Evertide create water cost in EMC")
    public double EvertideWaterCost = 100;

    @Comment("Evertide spawn projectile cost in EMC")
    public double EvertideSpawnProjectileCost = 500;

    @Comment("Evertide Pedestal cost in EMC")
    public double EvertidePedestalCost = 1000;
}
