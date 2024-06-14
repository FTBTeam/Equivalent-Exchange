package net.creeperhost.equivalentexchange.network.packets;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseC2SMessage;
import dev.architectury.networking.simple.MessageType;
import net.creeperhost.equivalentexchange.containers.ContainerTransmutationTable;
import net.creeperhost.equivalentexchange.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class UpdateTransmutationFilter extends BaseC2SMessage
{
    String value;

    public UpdateTransmutationFilter(String value)
    {
        this.value = value;
    }

    public UpdateTransmutationFilter(FriendlyByteBuf friendlyByteBuf)
    {
        value = friendlyByteBuf.readUtf();
    }

    @Override
    public MessageType getType()
    {
        return PacketHandler.UPDATE_FILTER;
    }

    @Override
    public void write(FriendlyByteBuf buf)
    {
        buf.writeUtf(value);
    }

    @Override
    public void handle(NetworkManager.PacketContext context)
    {
        if(context.getPlayer() == null) return;

        Player player = context.getPlayer();
        if(player != null)
        {
            if(player.containerMenu != null && player.containerMenu instanceof ContainerTransmutationTable containerTransmutationTable)
            {
                containerTransmutationTable.getTransmutationInventory().setFilter(value, true);
            }
        }
    }
}
