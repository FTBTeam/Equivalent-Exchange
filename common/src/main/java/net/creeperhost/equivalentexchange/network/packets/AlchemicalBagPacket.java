package net.creeperhost.equivalentexchange.network.packets;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.creeperhost.equivalentexchange.impl.AlchemicalBagHandler;
import net.creeperhost.equivalentexchange.inventory.AlchemicalBagInventory;
import net.creeperhost.equivalentexchange.network.PacketHandler;
import net.creeperhost.equivalentexchange.types.BagTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class AlchemicalBagPacket extends BaseS2CMessage
{
//    AlchemicalBagInventory inventory;
    CompoundTag compoundTag;
    BagTypes bagTypes;

    public AlchemicalBagPacket(CompoundTag compoundTag, BagTypes bagType)
    {
        this.compoundTag = compoundTag;
//        this.inventory = inventory;
        this.bagTypes = bagType;
    }

    public AlchemicalBagPacket(FriendlyByteBuf friendlyByteBuf)
    {
        compoundTag = friendlyByteBuf.readNbt();
//        AlchemicalBagInventory inv = new AlchemicalBagInventory();
//        inv.deserializeNBT(friendlyByteBuf.readNbt());
        bagTypes = BagTypes.valueOf(friendlyByteBuf.readUtf());
//        inventory = inv;
    }

    @Override
    public MessageType getType()
    {
        return PacketHandler.ALCHEMICAL_BAG_PACKET;
    }

    @Override
    public void write(FriendlyByteBuf buf)
    {
        buf.writeNbt(compoundTag);
//        buf.writeNbt(inventory.serializeNBT());
        buf.writeUtf(bagTypes.name());
    }

    @Override
    public void handle(NetworkManager.PacketContext context)
    {
        Player player = context.getPlayer();
        if(player != null)
        {
            AlchemicalBagInventory alchemicalBagInventory = new AlchemicalBagInventory(player);
            alchemicalBagInventory.deserializeNBT(compoundTag);
            AlchemicalBagHandler.updateInventory(player, alchemicalBagInventory, bagTypes);
        }
    }
}
