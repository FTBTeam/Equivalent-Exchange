package net.creeperhost.equivalentexchange.network.packets;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.creeperhost.equivalentexchange.impl.TransmutationTableHandler;
import net.creeperhost.equivalentexchange.inventory.TransmutationInventory;
import net.creeperhost.equivalentexchange.network.PacketHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class TransmutationInventoryPacket extends BaseS2CMessage
{
    CompoundTag compoundTag;

    public TransmutationInventoryPacket(CompoundTag compoundTag)
    {
        this.compoundTag = compoundTag;
    }

    public TransmutationInventoryPacket(FriendlyByteBuf friendlyByteBuf)
    {
        compoundTag = friendlyByteBuf.readNbt();
    }

    @Override
    public MessageType getType()
    {
        return PacketHandler.TRANSMUTATION_TABLE_PACKET;
    }

    @Override
    public void write(FriendlyByteBuf buf)
    {
        buf.writeNbt(compoundTag);
    }

    @Override
    public void handle(NetworkManager.PacketContext context)
    {
        Player player = context.getPlayer();
        if(player != null)
        {
            TransmutationInventory transmutationInventory = new TransmutationInventory(player);
            transmutationInventory.deserializeNBT(compoundTag);
            TransmutationTableHandler.updateInventory(player, transmutationInventory);
        }
    }
}
