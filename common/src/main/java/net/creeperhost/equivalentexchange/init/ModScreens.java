package net.creeperhost.equivalentexchange.init;

import dev.architectury.registry.menu.MenuRegistry;
import net.creeperhost.equivalentexchange.client.screens.*;
import net.creeperhost.equivalentexchange.client.screens.relays.ScreenRelayMK1;
import net.creeperhost.equivalentexchange.client.screens.relays.ScreenRelayMK2;
import net.creeperhost.equivalentexchange.client.screens.relays.ScreenRelayMK3;

public class ModScreens
{
    public static void init()
    {
        MenuRegistry.registerScreenFactory(ModContainers.ALCHEMICAL_CHEST_CONTAINER.get(), ScreenAlchemicalChest::create);
        MenuRegistry.registerScreenFactory(ModContainers.TRANSMUTATION_TABLE_CONTAINER.get(), ScreenTransmutationTable::new);
        MenuRegistry.registerScreenFactory(ModContainers.ALCHEMICAL_BAG_CONTAINER.get(), ScreenAlchemicalBag::create);
        MenuRegistry.registerScreenFactory(ModContainers.TARGET_CONTAINER.get(), ScreenTarget::new);
        MenuRegistry.registerScreenFactory(ModContainers.CONDENSER_CONTAINER.get(), ScreenCondenser::create);
        MenuRegistry.registerScreenFactory(ModContainers.COLLECTOR_CONTAINER.get(), ScreenCollector::create);
        MenuRegistry.registerScreenFactory(ModContainers.RELAY_CONTAINER_MK1.get(), ScreenRelayMK1::new);
        MenuRegistry.registerScreenFactory(ModContainers.RELAY_CONTAINER_MK2.get(), ScreenRelayMK2::new);
        MenuRegistry.registerScreenFactory(ModContainers.RELAY_CONTAINER_MK3.get(), ScreenRelayMK3::new);
        MenuRegistry.registerScreenFactory(ModContainers.DARK_MATTER_PEDESTAL_CONTAINER.get(), ScreenDarkMatterPedestal::create);
    }
}
