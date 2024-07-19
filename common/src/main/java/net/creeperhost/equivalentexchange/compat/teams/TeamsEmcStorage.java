package net.creeperhost.equivalentexchange.compat.teams;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
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
import java.util.Optional;
import java.util.UUID;

public class TeamsEmcStorage implements IEmcStorageHandler
{
    private static final HashMap<String, Double> STORED_EMC = new HashMap<>();

    @Override
    public Path getSavePath(Player player)
    {
        return FTBTeamsAPI.api().getManager().getServer().getWorldPath(LevelResource.ROOT).resolve("ftbteams/emc/").resolve(getPlayerTeam(player).getTeamId().toString() + "_emc.dat");
    }

    private Team getPlayerTeam(Player player)
    {
        if(player.level().isClientSide())
        {
            return FTBTeamsAPI.api().getClientManager().selfTeam();
        }
        return FTBTeamsAPI.api().getManager().getTeamForPlayerID(player.getUUID()).get();
    }

    @Override
    public double getEmcValueFor(Player player)
    {
        if(!STORED_EMC.containsKey(getPlayerTeam(player).getTeamId().toString()))
        {
            STORED_EMC.put(getPlayerTeam(player).getTeamId().toString(), 0D);
        }
        return STORED_EMC.get(getPlayerTeam(player).getTeamId().toString());
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
        STORED_EMC.put(getPlayerTeam(player).getTeamId().toString(), value);
    }

    @Override
    public void setEmcValueFor(UUID uuid, double value)
    {
        STORED_EMC.put(uuid.toString(), value);
    }

    @Override
    public void addEmcFor(UUID uuid, double value)
    {
        double currentValue = getEmcValueFor(uuid);
        double newValue = currentValue + value;
        setEmcValueFor(uuid, newValue);
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
            getSavePath(player).getParent().toFile().mkdirs();

            EquivalentExchange.LOGGER.info("Saving stored emc for team " + getPlayerTeam(player).getTeamId().toString());
            try
            {
                double stored = getEmcValueFor(player);
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putDouble("stored", stored);
                NbtIo.write(compoundTag, getSavePath(player));
                EquivalentExchange.LOGGER.info("Saving value {} to {}", stored, getSavePath(player));
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
            EquivalentExchange.LOGGER.info("Loading saved emc for team " + getPlayerTeam(player).getTeamId().toString());
            if(!getSavePath(player).toFile().exists())
            {
                try
                {
                    getSavePath(player).toFile().createNewFile();
                    saveEmcToFile(player);
                } catch (Exception e){}
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
                EquivalentExchange.LOGGER.info("Loaded value {} for player {}", value, player.getDisplayName().getString());
                STORED_EMC.put(getPlayerTeam(player).getTeamId().toString(), value);
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
