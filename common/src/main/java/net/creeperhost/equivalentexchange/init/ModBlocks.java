package net.creeperhost.equivalentexchange.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.blockentities.*;
import net.creeperhost.equivalentexchange.blockentities.relays.BlockEntityRelayMK1;
import net.creeperhost.equivalentexchange.blockentities.relays.BlockEntityRelayMK2;
import net.creeperhost.equivalentexchange.blockentities.relays.BlockEntityRelayMK3;
import net.creeperhost.equivalentexchange.blocks.*;
import net.creeperhost.equivalentexchange.types.CollectorType;
import net.creeperhost.equivalentexchange.types.MatterTypes;
import net.creeperhost.equivalentexchange.types.RelayTypes;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Constants.MOD_ID, Registries.BLOCK);
    public static final DeferredRegister<BlockEntityType<?>> TILES_ENTITIES = DeferredRegister.create(Constants.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<Block> ALCHEMICAL_CHEST = BLOCKS.register("alchemical_chest", BlockAlchemicalChest::new);
    public static final RegistrySupplier<Block> TRANSMUTATION_TABLE = BLOCKS.register("transmutation_table", BlockTransmutationTable::new);

    public static final RegistrySupplier<Block> CONDENSER = BLOCKS.register("condenser", BlockCondenser::new);

    public static final RegistrySupplier<Block> ALCHEMICAL_COAL = BLOCKS.register("alchemical_coal_block", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F)));
    public static final RegistrySupplier<Block> MOBIUS_FUEL = BLOCKS.register("mobius_fuel_block", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F)));
    public static final RegistrySupplier<Block> AETERNALIS_FUEL = BLOCKS.register("aeternalis_fuel_block", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F)));

    public static final RegistrySupplier<Block> NOVA_CATALYST = BLOCKS.register("nova_catalyst", () -> new BlockNovaCatalyst(BlockBehaviour.Properties.of()));
    public static final RegistrySupplier<Block> NOVA_CATACLYSM = BLOCKS.register("nova_cataclysm", () -> new BlockNovaCataclysm(BlockBehaviour.Properties.of()));
    public static final RegistrySupplier<Block> DM_PEDESTAL = BLOCKS.register("dm_pedestal", () -> new BlockDarkMatterPedestal(BlockBehaviour.Properties.of().strength(2.0F)));

    public static final Map<CollectorType, Supplier<Block>> COLLECTORS = Util.make(new LinkedHashMap<>(), map ->
    {
        for (CollectorType value : CollectorType.values())
        {
            map.put(value, BLOCKS.register(value.getName(), () -> new BlockCollector(value)));
        }
    });

    public static final Map<RelayTypes, Supplier<Block>> RELAYS = Util.make(new LinkedHashMap<>(), map ->
    {
        for (RelayTypes value : RelayTypes.values())
        {
            map.put(value, BLOCKS.register(value.getName(), () -> new BlockRelay(value)));
        }
    });

    public static final Map<MatterTypes, Supplier<Block>> MATTER_BLOCK = Util.make(new LinkedHashMap<>(), map ->
    {
        for (MatterTypes value : MatterTypes.values())
        {
            map.put(value, BLOCKS.register(value.getName() + "_matter_block", () -> new Block(BlockBehaviour.Properties.of().strength(2.0F))));
        }
    });
    public static final RegistrySupplier<BlockEntityType<BlockEntityAlchemicalChest>> ALCHEMICAL_CHEST_TILE = TILES_ENTITIES.register("alchemical_chest",
            () -> BlockEntityType.Builder.of(BlockEntityAlchemicalChest::new, ModBlocks.ALCHEMICAL_CHEST.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<BlockEntityTransmutationTable>> TRANSMUTATION_TABLE_TILE = TILES_ENTITIES.register("transmutation_table",
            () -> BlockEntityType.Builder.of(BlockEntityTransmutationTable::new, ModBlocks.TRANSMUTATION_TABLE.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<BlockEntityCondenser>> CONDENSER_TILE = TILES_ENTITIES.register("condenser",
            () -> BlockEntityType.Builder.of(BlockEntityCondenser::new, ModBlocks.CONDENSER.get()).build(null));

    public static final RegistrySupplier<BlockEntityType<BlockEntityCollector>> COLLECTOR_TILE = TILES_ENTITIES.register("collector",
            () -> BlockEntityType.Builder.of(BlockEntityCollector::new, ModBlocks.COLLECTORS.values().stream().map(Supplier::get).toArray(Block[]::new)).build(null));



    public static final RegistrySupplier<BlockEntityType<BlockEntityRelayMK1>> RELAY_MK1_TILE = TILES_ENTITIES.register("relay_mk1",
            () -> BlockEntityType.Builder.of(BlockEntityRelayMK1::new, ModBlocks.RELAYS.get(RelayTypes.MK1).get()).build(null));

    public static final RegistrySupplier<BlockEntityType<BlockEntityRelayMK2>> RELAY_MK2_TILE = TILES_ENTITIES.register("relay_mk2",
            () -> BlockEntityType.Builder.of(BlockEntityRelayMK2::new, ModBlocks.RELAYS.get(RelayTypes.MK2).get()).build(null));

    public static final RegistrySupplier<BlockEntityType<BlockEntityRelayMK3>> RELAY_MK3_TILE = TILES_ENTITIES.register("relay_mk3",
            () -> BlockEntityType.Builder.of(BlockEntityRelayMK3::new, ModBlocks.RELAYS.get(RelayTypes.MK3).get()).build(null));


    public static final RegistrySupplier<BlockEntityType<BlockEntityPedestal>> PEDESTAL_TILE = TILES_ENTITIES.register("dm_pedestal",
            () -> BlockEntityType.Builder.of(BlockEntityPedestal::new, ModBlocks.DM_PEDESTAL.get()).build(null));
}
