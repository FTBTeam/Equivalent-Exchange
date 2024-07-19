package net.creeperhost.equivalentexchange.server;

import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.impl.AlchemicalBagHandler;
import net.creeperhost.equivalentexchange.impl.TransmutationTableHandler;
import net.creeperhost.equivalentexchange.network.packets.EmcValuesPacket;
import net.creeperhost.equivalentexchange.network.packets.PlayerEmcPacket;
import net.creeperhost.equivalentexchange.network.packets.knowledge.KnowledgePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class ServerEvents
{
    
    public static void onLevelSave(ServerLevel serverLevel)
    {
        AlchemicalBagHandler.onServerSaveEvent(serverLevel);
        save(serverLevel);
    }

    private static void save(ServerLevel serverLevel)
    {
        if(serverLevel != null && serverLevel.players() != null && !serverLevel.players().isEmpty())
        {
            //Minecraft is dumb and sometime this nonnull list ends up with a null value somehow...
            try
            {
                serverLevel.players().forEach(serverPlayer ->
                {
                    EquivalentExchangeAPI.getStorageHandler().saveEmcToFile(serverPlayer);
                    EquivalentExchangeAPI.getKnowledgeHandler().saveKnowledgeToFile(serverPlayer);
                });
            } catch (Exception ignored){}
        }

        AlchemicalBagHandler.onServerSaveEvent(serverLevel);
    }

    public static void onPlayerJoin(ServerPlayer serverPlayer)
    {
        TransmutationTableHandler.getTransmutationInventory(serverPlayer);
        AlchemicalBagHandler.onPlayerJoinEvent(serverPlayer);

        EquivalentExchangeAPI.getStorageHandler().loadPlayersEmcFromFile(serverPlayer);
        new PlayerEmcPacket(EquivalentExchangeAPI.getStorageHandler().getEmcValueFor(serverPlayer)).sendTo(serverPlayer);

        EquivalentExchangeAPI.getKnowledgeHandler().loadKnowledgeFromFile(serverPlayer);
        new KnowledgePacket(EquivalentExchangeAPI.getKnowledgeHandler().getKnowledgeList(serverPlayer));


        if(!EquivalentExchangeAPI.UpdatingValues) new EmcValuesPacket(EquivalentExchangeAPI.EMC_VALUES).sendTo(serverPlayer);
    }

    public static void onServerStop(MinecraftServer minecraftServer)
    {
        TransmutationTableHandler.clear();
        AlchemicalBagHandler.onServerStopEvent(minecraftServer);
        minecraftServer.getAllLevels().forEach(ServerEvents::save);
        EquivalentExchangeAPI.getStorageHandler().clear();
        EquivalentExchangeAPI.getKnowledgeHandler().clear();
    }

    public static void onServerLoad(ServerLevel serverLevel)
    {
    }
}
