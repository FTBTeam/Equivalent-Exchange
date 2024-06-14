package net.creeperhost.equivalentexchange.network.packets;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class PlayerEmcPacket extends BaseS2CMessage
{
    double value;

    public PlayerEmcPacket(double value)
    {
        this.value = value;
    }

    public PlayerEmcPacket(FriendlyByteBuf friendlyByteBuf)
    {
        value = friendlyByteBuf.readDouble();
    }

    @Override
    public MessageType getType()
    {
        return PacketHandler.EMC_SYNC;
    }

    @Override
    public void write(FriendlyByteBuf buf)
    {
        buf.writeDouble(value);
    }

    @Override
    public void handle(NetworkManager.PacketContext context)
    {
        if(context.getPlayer() == null) return;

        Player player = context.getPlayer();
        EquivalentExchangeAPI.getStorageHandler().setEmcValueFor(player, value);
    }
}
