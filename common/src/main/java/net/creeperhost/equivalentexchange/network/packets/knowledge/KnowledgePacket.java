package net.creeperhost.equivalentexchange.network.packets.knowledge;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.impl.KnowledgeHandler;
import net.creeperhost.equivalentexchange.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class KnowledgePacket extends BaseS2CMessage
{
    List<ItemStack> list;

    public KnowledgePacket(List<ItemStack> list)
    {
        this.list = list;
    }

    public KnowledgePacket(FriendlyByteBuf friendlyByteBuf)
    {
        this.list = friendlyByteBuf.readList(friendlyByteBuf1 -> friendlyByteBuf.readItem());
    }

    @Override
    public MessageType getType()
    {
        return PacketHandler.KNOWLEDGE_SYNC;
    }

    @Override
    public void write(FriendlyByteBuf buf)
    {
        buf.writeCollection(list, FriendlyByteBuf::writeItem);
    }

    @Override
    public void handle(NetworkManager.PacketContext context)
    {
        if(context.getPlayer() == null) return;

        if(context.getPlayer().level().isClientSide)
        {
            EquivalentExchangeAPI.getKnowledgeHandler().setKnowledgeList(context.getPlayer(), list);
        }
    }
}
