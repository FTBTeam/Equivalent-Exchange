package net.creeperhost.equivalentexchange.compat;

import dev.architectury.platform.Platform;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.event.PlayerJoinedPartyTeamEvent;
import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.events.EmcChangedEvent;
import net.creeperhost.equivalentexchange.api.events.KnowledgeChangedEvent;
import net.creeperhost.equivalentexchange.compat.teams.TeamsKnowledgeHandler;
import net.creeperhost.equivalentexchange.impl.KnowledgeHandler;
import net.creeperhost.equivalentexchange.impl.PlayerEmcStorage;
import net.creeperhost.equivalentexchange.compat.teams.TeamsEmcStorage;
import net.creeperhost.equivalentexchange.network.packets.PlayerEmcPacket;
import net.creeperhost.equivalentexchange.network.packets.knowledge.KnowledgePacket;
import net.fabricmc.api.EnvType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class CompatHandler
{
    public static void init()
    {
        if(Platform.isModLoaded("ftbteams"))
        {
            EquivalentExchange.LOGGER.info("ftb teams detected, starting teams compat");
            EquivalentExchangeAPI.iEmcStorageHandler = new TeamsEmcStorage();
            EquivalentExchangeAPI.iKnowledgeHandler = new TeamsKnowledgeHandler();

            if(Platform.getEnv() == EnvType.SERVER)
            {
                EmcChangedEvent.EMC_ADDED_EVENT.register((player, current, added, newValue) ->
                {
                    Team team = FTBTeamsAPI.api().getManager().getTeamForPlayerID(player.getUUID()).get();
                    team.getMembers().forEach(uuid ->
                    {
                        ServerPlayer serverPlayer = FTBTeamsAPI.api().getManager().getServer().getPlayerList().getPlayer(uuid);
                        if(serverPlayer != null)
                        {
                            new PlayerEmcPacket(newValue).sendTo(serverPlayer);
                        }
                    });
                });

                EmcChangedEvent.EMC_REMOVED_EVENT.register((player, current, removed, newValue) ->
                {
                    Team team = FTBTeamsAPI.api().getManager().getTeamForPlayerID(player.getUUID()).get();
                    team.getMembers().forEach(uuid ->
                    {
                        ServerPlayer serverPlayer = FTBTeamsAPI.api().getManager().getServer().getPlayerList().getPlayer(uuid);
                        if(serverPlayer != null)
                            new PlayerEmcPacket(newValue).sendTo(serverPlayer);
                    });
                });

                KnowledgeChangedEvent.KNOWLEDGE_ADDED_EVENT.register((player, stack) ->
                {
                    Team team = FTBTeamsAPI.api().getManager().getTeamForPlayerID(player.getUUID()).get();
                    team.getMembers().forEach(uuid ->
                    {
                        ServerPlayer serverPlayer = FTBTeamsAPI.api().getManager().getServer().getPlayerList().getPlayer(uuid);
                        EquivalentExchange.LOGGER.info("Adding knowledge for {} to player {}", stack.getDisplayName().getString(), serverPlayer.getDisplayName().getString());
                        if(serverPlayer != null)
                            new KnowledgePacket(EquivalentExchangeAPI.iKnowledgeHandler.getKnowledgeList(player)).sendTo(serverPlayer);
                    });
                });

                KnowledgeChangedEvent.KNOWLEDGE_REMOVED_EVENT.register((player, stack) ->
                {
                    Team team = FTBTeamsAPI.api().getManager().getTeamForPlayerID(player.getUUID()).get();
                    team.getMembers().forEach(uuid ->
                    {
                        ServerPlayer serverPlayer = FTBTeamsAPI.api().getManager().getServer().getPlayerList().getPlayer(uuid);
                        if(serverPlayer != null)
                            new KnowledgePacket(EquivalentExchangeAPI.iKnowledgeHandler.getKnowledgeList(player)).sendTo(serverPlayer);
                    });
                });
                PlayerJoinedPartyTeamEvent.PLAYER_JOINED_PARTY.register(playerJoinedPartyTeamEvent ->
                {
                    try
                    {
                        Player player = playerJoinedPartyTeamEvent.getPlayer();
                        Team lastTeam = playerJoinedPartyTeamEvent.getPreviousTeam();
                        if (lastTeam.isPlayerTeam())
                        {
                            double lastEMC = EquivalentExchangeAPI.getStorageHandler().getEmcValueFor(lastTeam.getTeamId());
                            EquivalentExchangeAPI.getStorageHandler().addEmcFor(player, lastEMC);
                            EquivalentExchangeAPI.getStorageHandler().setEmcValueFor(lastTeam.getTeamId(), 0);

                            var old = EquivalentExchangeAPI.getKnowledgeHandler().getKnowledgeList(lastTeam.getTeamId());
                            var list = EquivalentExchangeAPI.getKnowledgeHandler().getKnowledgeList(player);
                            for (ItemStack itemStack : old)
                            {
                                if (!list.contains(itemStack))
                                    EquivalentExchangeAPI.getKnowledgeHandler().addKnowledge(player, itemStack);
                            }
                        }
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                });
            }
        }
        else
        {
            EquivalentExchangeAPI.iEmcStorageHandler = new PlayerEmcStorage();
            EquivalentExchangeAPI.iKnowledgeHandler = new KnowledgeHandler();

            if(Platform.getEnv() == EnvType.SERVER)
            {
                EmcChangedEvent.EMC_ADDED_EVENT.register((player, current, added, newValue) ->
                {
                    if (!player.level().isClientSide())
                    {
                        new PlayerEmcPacket(newValue).sendTo((ServerPlayer) player);
                    }
                });

                EmcChangedEvent.EMC_REMOVED_EVENT.register((player, current, removed, newValue) ->
                {
                    if (!player.level().isClientSide())
                    {
                        new PlayerEmcPacket(newValue).sendTo((ServerPlayer) player);
                    }
                });

                KnowledgeChangedEvent.KNOWLEDGE_ADDED_EVENT.register((player, stack) ->
                {
                    if(!player.level().isClientSide()) new KnowledgePacket(EquivalentExchangeAPI.iKnowledgeHandler.getKnowledgeList(player)).sendTo((ServerPlayer) player);
                });

                KnowledgeChangedEvent.KNOWLEDGE_REMOVED_EVENT.register((player, stack) ->
                {
                    if(!player.level().isClientSide()) new KnowledgePacket(EquivalentExchangeAPI.iKnowledgeHandler.getKnowledgeList(player)).sendTo((ServerPlayer) player);
                });
            }
        }
    }
}
