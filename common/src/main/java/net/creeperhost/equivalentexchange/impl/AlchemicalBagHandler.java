package net.creeperhost.equivalentexchange.impl;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.inventory.AlchemicalBagInventory;
import net.creeperhost.equivalentexchange.types.BagTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.LevelResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class AlchemicalBagHandler
{
    //TODO this should might be better in the api so it can be accessed by other mods
    private static final HashMap<String, HashMap<BagTypes, AlchemicalBagInventory>> INVENTORIES = new HashMap<>();

    public static HashMap<BagTypes, AlchemicalBagInventory> getBagInventories(Player player)
    {
        if(!INVENTORIES.containsKey(player.getUUID().toString()))
        {
            HashMap<BagTypes, AlchemicalBagInventory> map = new HashMap<>();
            for (BagTypes value : BagTypes.values())
            {
                map.put(value, new AlchemicalBagInventory(player));
            }
            INVENTORIES.put(player.getUUID().toString(), map);
        }
        return INVENTORIES.get(player.getUUID().toString());
    }

    public static AlchemicalBagInventory getInventory(Player player, BagTypes bagType)
    {
        return getBagInventories(player).get(bagType);
    }

    public static void updateInventory(Player player, AlchemicalBagInventory alchemicalBagInventory, BagTypes bagTypes)
    {
        INVENTORIES.get(player.getUUID().toString()).put(bagTypes, alchemicalBagInventory);
    }

    public static void loadPlayersBagInventory(Player player, File file)
    {
        CompletableFuture.runAsync(() -> {
            if(player != null && !player.level().isClientSide)
            {
                if(file != null && file.exists())
                {
                    try
                    {
                        CompoundTag compoundTag = NbtIo.read(file.toPath());
                        if(compoundTag == null)
                        {
                            EquivalentExchange.LOGGER.error("unable to load file " + file.getAbsolutePath());
                            //TODO maybe keep a backup file to use if this ever happens?
                            return;
                        }
                        HashMap<BagTypes, AlchemicalBagInventory> map = new HashMap<>();
                        for (BagTypes value : BagTypes.values())
                        {
                           AlchemicalBagInventory alchemicalBagInventory = new AlchemicalBagInventory(player);
                           CompoundTag compoundTag1 = compoundTag.getCompound(value.getName());
                           alchemicalBagInventory.deserializeNBT(compoundTag1);
                           map.put(value, alchemicalBagInventory);
                        }
                        EquivalentExchange.LOGGER.info("Loading alchemicalBagInventory inventory for " + player.getName().getString());
                        INVENTORIES.put(player.getUUID().toString(), map);
                    }
                    catch (IOException e)
                    {
                        EquivalentExchange.LOGGER.info("Failed to load inventory for " + player.getName().getString());
//                        INVENTORIES.put(player.getUUID().toString(), new HashMap<>());
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void savePlayersBagInventory(Player player, File file)
    {
        if(player != null && !player.level().isClientSide)
        {
            if(file != null)
            {
                HashMap<BagTypes, AlchemicalBagInventory> map = getBagInventories(player);
                CompoundTag compoundTag = new CompoundTag();
                map.forEach((bagTypes, alchemicalBagInventory) -> compoundTag.put(bagTypes.getName(), alchemicalBagInventory.serializeNBT()));
                try
                {
                    EquivalentExchange.LOGGER.info("Saving bag inventory for " + player.getName().getString());
                    NbtIo.write(compoundTag, file.toPath());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    //Events
    public static void onServerSaveEvent(ServerLevel serverLevel)
    {
        if(serverLevel != null && serverLevel.players() != null && !serverLevel.players().isEmpty())
        {
            serverLevel.players().forEach(serverPlayer ->
            {
                Path transmutation_path = serverPlayer.server.getWorldPath(LevelResource.PLAYER_DATA_DIR).resolve(serverPlayer.getUUID().toString() + "_alchemical_bag.dat");
                savePlayersBagInventory(serverPlayer, transmutation_path.toFile());
            });
        }
    }

    public static void onPlayerJoinEvent(ServerPlayer player)
    {
        Path path = player.server.getWorldPath(LevelResource.PLAYER_DATA_DIR).resolve(player.getUUID().toString() + "_alchemical_bag.dat");
        if(path.toFile().exists())
        {
            EquivalentExchange.LOGGER.info("Loading bags for player {}", player.getDisplayName().getString());
            loadPlayersBagInventory(player, path.toFile());
        }
    }

    public static void onServerStopEvent(MinecraftServer minecraftServer)
    {
        EquivalentExchange.LOGGER.info("Cleaning up players alchemical bags");
        INVENTORIES.clear();
    }
}
