package net.creeperhost.equivalentexchange.api;

import net.minecraft.world.entity.player.Player;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public interface IEmcStorageHandler
{
    Path getSavePath(Player player);

    double getEmcValueFor(Player player);

    double getEmcValueFor(UUID uuid);

    void setEmcValueFor(Player player, double value);

    void setEmcValueFor(UUID uuid, double value);

    void addEmcFor(Player player, double value);

    void addEmcFor(UUID uuid, double value);


    double removeEmcFor(Player player, double value);

    double removeEmcFor(UUID uuid, double value);


    void onChanged(Player player, double value);

    void saveEmcToFile(Player player);

    void loadPlayersEmcFromFile(Player player);

    void clear();
}
