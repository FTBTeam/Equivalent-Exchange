package net.creeperhost.equivalentexchange;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.events.EmcChangedEvent;
import net.creeperhost.equivalentexchange.api.events.EmcRegisterEvent;
import net.creeperhost.equivalentexchange.compat.CompatHandler;
import net.creeperhost.equivalentexchange.config.EE3ConfigData;
import net.creeperhost.equivalentexchange.dynemc.DynamicEmc;
import net.creeperhost.equivalentexchange.dynemc.special.ConcreteHandler;
import net.creeperhost.equivalentexchange.impl.PlayerEmcStorage;
import net.creeperhost.equivalentexchange.init.*;
import net.creeperhost.equivalentexchange.network.PacketHandler;
import net.creeperhost.equivalentexchange.network.packets.PlayerEmcPacket;
import net.creeperhost.equivalentexchange.server.ServerEvents;
import net.creeperhost.equivalentexchange.server.commands.EECommands;
import net.creeperhost.polylib.config.ConfigBuilder;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EquivalentExchange
{
    public static Logger LOGGER = LogManager.getLogger();
    public static ConfigBuilder CONFIG_BUILDER;
    public static EE3ConfigData CONFIG_DATA;

    public static void init()
    {
        setupConfigs();
        CompatHandler.init();

        DynamicEmc.init();
        ModBlocks.BLOCKS.register();
        ModBlocks.TILES_ENTITIES.register();
        ModItems.ITEMS.register();
        ModCreativeTabs.CREATIVE_MODE_TABS.register();
        ModContainers.CONTAINERS.register();
        ModSounds.SOUNDS.register();
        ModRecipes.RECIPE_SERIALIZERS.register();
        ModEntities.ENTITY_TYPES.register();
        ModRecipes.init();
        PacketHandler.init();

        if(Platform.getEnvironment() == Env.CLIENT) EquivalentExchangeClient.init();

        CommandRegistrationEvent.EVENT.register(EECommands::registerCommand);

        PlayerEvent.PLAYER_JOIN.register(ServerEvents::onPlayerJoin);
        LifecycleEvent.SERVER_LEVEL_SAVE.register(ServerEvents::onLevelSave);

        LifecycleEvent.SERVER_STOPPED.register(ServerEvents::onServerStop);
        LifecycleEvent.SERVER_STARTED.register(server -> ModItems.registerFuelValues());

        LifecycleEvent.SERVER_LEVEL_LOAD.register(ServerEvents::onServerLoad);

        EmcRegisterEvent.EMC_PRE_START.register(resourceManager -> ConcreteHandler.init());
        EmcRegisterEvent.EMC_POST_START.register(resourceManager -> ConcreteHandler.init());
    }

    public static void setupConfigs()
    {
        if(!Constants.EE_CONFIG_FOLDER.toFile().exists())
        {
            boolean created = Constants.EE_CONFIG_FOLDER.toFile().mkdirs();
            if(created)
            {
                LOGGER.info("Created " + Constants.MOD_ID + " Config folder");
            }
        }
        CONFIG_BUILDER = new ConfigBuilder(Constants.MOD_ID, Constants.EE_CONFIG_FILE, new EE3ConfigData());
        CONFIG_DATA = (EE3ConfigData) CONFIG_BUILDER.getConfigData();
    }
}
