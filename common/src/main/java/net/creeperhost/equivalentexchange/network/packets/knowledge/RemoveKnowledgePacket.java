package net.creeperhost.equivalentexchange.network.packets.knowledge;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.impl.KnowledgeHandler;
import net.creeperhost.equivalentexchange.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

public class RemoveKnowledgePacket extends BaseS2CMessage
{
    ItemStack stack;

    public RemoveKnowledgePacket(ItemStack stack)
    {
        this.stack = stack;
    }

    public RemoveKnowledgePacket(FriendlyByteBuf buf)
    {
        this.stack = buf.readItem();
    }

    @Override
    public MessageType getType()
    {
        return PacketHandler.KNOWLEDGE_ADD;
    }

    @Override
    public void write(FriendlyByteBuf buf)
    {
        buf.writeItem(stack);
    }

    @Override
    public void handle(NetworkManager.PacketContext context)
    {
        if(context.getPlayer() == null) return;

        if(context.getPlayer().level().isClientSide)
        {
            EquivalentExchangeAPI.getKnowledgeHandler().removeKnowledge(context.getPlayer(), stack);
        }
    }
}
