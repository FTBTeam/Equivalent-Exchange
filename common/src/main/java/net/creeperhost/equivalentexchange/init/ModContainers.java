package net.creeperhost.equivalentexchange.init;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.containers.*;
import net.creeperhost.equivalentexchange.containers.relays.ContainerRelayMK1;
import net.creeperhost.equivalentexchange.containers.relays.ContainerRelayMK2;
import net.creeperhost.equivalentexchange.containers.relays.ContainerRelayMK3;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public class ModContainers
{
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Constants.MOD_ID, Registries.MENU);
    public static final RegistrySupplier<MenuType<ContainerAlchemicalChest>> ALCHEMICAL_CHEST_CONTAINER = CONTAINERS.register("container_alchemical_chest", () -> MenuRegistry.ofExtended(ContainerAlchemicalChest::new));
    public static final RegistrySupplier<MenuType<ContainerTransmutationTable>> TRANSMUTATION_TABLE_CONTAINER = CONTAINERS.register("container_transmutation_table", () -> MenuRegistry.ofExtended(ContainerTransmutationTable::new));
    public static final RegistrySupplier<MenuType<ContainerAlchemicalBag>> ALCHEMICAL_BAG_CONTAINER = CONTAINERS.register("container_alchemical_bag", () -> MenuRegistry.ofExtended(ContainerAlchemicalBag::new));

    public static final RegistrySupplier<MenuType<ContainerTarget>> TARGET_CONTAINER = CONTAINERS.register("container_target", () -> MenuRegistry.ofExtended(ContainerTarget::new));

    public static final RegistrySupplier<MenuType<ContainerCondenser>> CONDENSER_CONTAINER = CONTAINERS.register("container_condenser", () -> MenuRegistry.ofExtended(ContainerCondenser::new));

    public static final RegistrySupplier<MenuType<ContainerCollector>> COLLECTOR_CONTAINER = CONTAINERS.register("container_collector", () -> MenuRegistry.ofExtended(ContainerCollector::new));

    public static final RegistrySupplier<MenuType<ContainerRelayMK1>> RELAY_CONTAINER_MK1 = CONTAINERS.register("container_relay_mk1", () -> MenuRegistry.ofExtended(ContainerRelayMK1::new));
    public static final RegistrySupplier<MenuType<ContainerRelayMK2>> RELAY_CONTAINER_MK2 = CONTAINERS.register("container_relay_mk2", () -> MenuRegistry.ofExtended(ContainerRelayMK2::new));
    public static final RegistrySupplier<MenuType<ContainerRelayMK3>> RELAY_CONTAINER_MK3 = CONTAINERS.register("container_relay_mk3", () -> MenuRegistry.ofExtended(ContainerRelayMK3::new));

    public static final RegistrySupplier<MenuType<ContainerDarkMatterPedestal>> DARK_MATTER_PEDESTAL_CONTAINER = CONTAINERS.register("dm_pedestal", () -> MenuRegistry.ofExtended(ContainerDarkMatterPedestal::new));




}
