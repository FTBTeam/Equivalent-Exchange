package net.creeperhost.equivalentexchange.impl;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.IEmcStorageHandler;
import net.creeperhost.equivalentexchange.api.events.EmcChangedEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.LevelResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;

public class PlayerEmcStorage implements IEmcStorageHandler
{
    private static final HashMap<String, Double> STORED_EMC = new HashMap<>();

    @Override
    public Path getSavePath(Player player)
    {
        return getBaseSavePath().resolve(player.getUUID().toString() + "_emc.dat");
    }

    @Override
    public Path getBaseSavePath()
    {
        return FTBTeamsAPI.api().getManager().getServer().getWorldPath(LevelResource.PLAYER_DATA_DIR);
    }

    @Override
    public double getEmcValueFor(Player player)
    {
        if(!STORED_EMC.containsKey(player.getUUID().toString()))
        {
            STORED_EMC.put(player.getUUID().toString(), 0D);
        }
        return STORED_EMC.get(player.getUUID().toString());
    }

    @Override
    public double getEmcValueFor(UUID uuid)
    {
        if(!STORED_EMC.containsKey(uuid.toString()))
        {
            STORED_EMC.put(uuid.toString(), 0D);
        }
        return STORED_EMC.get(uuid.toString());
    }

    @Override
    public void setEmcValueFor(Player player, double value)
    {
        STORED_EMC.put(player.getUUID().toString(), value);
    }

    @Override
    public void setEmcValueFor(UUID uuid, double value)
    {
        STORED_EMC.put(uuid.toString(), value);
    }

    @Override
    public void addEmcFor(Player player, double value)
    {
        double currentValue = getEmcValueFor(player);
        double newValue = currentValue + value;
        setEmcValueFor(player, newValue);
        EmcChangedEvent.EMC_ADDED_EVENT.invoker().added(player, currentValue, value, newValue);
    }

    @Override
    public void addEmcFor(UUID uuid, double value)
    {
        double currentValue = getEmcValueFor(uuid);
        double newValue = currentValue + value;
        setEmcValueFor(uuid, newValue);
    }

    @Override
    public double removeEmcFor(Player player, double value)
    {
        double currentValue = getEmcValueFor(player);
        if(currentValue >= value)
        {
            double newValue = currentValue - value;
            setEmcValueFor(player, newValue);
            EmcChangedEvent.EMC_REMOVED_EVENT.invoker().removed(player, currentValue, value, newValue);
            return newValue;
        }
        return value;
    }

    @Override
    public double removeEmcFor(UUID uuid, double value)
    {
        double currentValue = getEmcValueFor(uuid);
        if(currentValue >= value)
        {
            double newValue = currentValue - value;
            setEmcValueFor(uuid, newValue);
            return newValue;
        }
        return value;
    }

    public  void saveEmcToFile(Player player)
    {
        if(player != null && !player.level().isClientSide)
        {
            EquivalentExchange.LOGGER.info("Saving stored emc for player " + player.getName().getString());
            try
            {
                double stored = getEmcValueFor(player);
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putDouble("stored", stored);
                NbtIo.write(compoundTag, getSavePath(player));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void loadPlayersEmcFromFile(Player player)
    {
        if(player != null && !player.level().isClientSide)
        {
            EquivalentExchange.LOGGER.info("Loading saved emc for player " + player.getName().getString());
            if(!getSavePath(player).toFile().exists())
            {
                getSavePath(player).toFile().mkdirs();
                saveEmcToFile(player);
            }
            try
            {
                CompoundTag compoundTag = NbtIo.read(getSavePath(player));
                if(compoundTag == null)
                {
                    EquivalentExchange.LOGGER.error("unable to load " + getSavePath(player));
                    return;
                }
                double value = compoundTag.getDouble("stored");
                STORED_EMC.put(player.getUUID().toString(), value);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void clear()
    {
        EquivalentExchange.LOGGER.info("Cleaning up players stored emc");
        STORED_EMC.clear();
    }

    @Override
    public void onChanged(Player player, double value)
    {

    }
}
