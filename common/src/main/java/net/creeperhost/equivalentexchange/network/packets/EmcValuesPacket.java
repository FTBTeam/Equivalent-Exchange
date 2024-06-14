package net.creeperhost.equivalentexchange.network.packets;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public class EmcValuesPacket extends BaseS2CMessage
{
    HashMap<ResourceLocation, Double> EMC_VALUES;

    public EmcValuesPacket(HashMap<ResourceLocation, Double> values)
    {
        this.EMC_VALUES = values;
    }

    public EmcValuesPacket(FriendlyByteBuf friendlyByteBuf)
    {
        this.EMC_VALUES = (HashMap<ResourceLocation, Double>) friendlyByteBuf.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readDouble);
    }

    @Override
    public MessageType getType()
    {
        return PacketHandler.EMC_VALUE_SYNC;
    }

    @Override
    public void write(FriendlyByteBuf buf)
    {
        buf.writeMap(EMC_VALUES, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeDouble);
    }

    @Override
    public void handle(NetworkManager.PacketContext context)
    {
        if(context.getPlayer() == null) return;
        if(context.getPlayer().level().isClientSide)
        {
            EquivalentExchange.LOGGER.info("New emv values sent from server, Updating");
            EquivalentExchangeAPI.EMC_VALUES = EMC_VALUES;
        }
    }
}
