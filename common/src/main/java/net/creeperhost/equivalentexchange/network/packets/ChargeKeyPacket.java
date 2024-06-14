package net.creeperhost.equivalentexchange.network.packets;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseC2SMessage;
import dev.architectury.networking.simple.MessageType;
import net.creeperhost.equivalentexchange.items.interfaces.IChargeableItem;
import net.creeperhost.equivalentexchange.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ChargeKeyPacket extends BaseC2SMessage
{
    boolean shiftDown;

    public ChargeKeyPacket(boolean shiftDown)
    {
        this.shiftDown = shiftDown;
    }

    public ChargeKeyPacket(FriendlyByteBuf friendlyByteBuf)
    {
        shiftDown = friendlyByteBuf.readBoolean();
    }

    @Override
    public MessageType getType()
    {
        return PacketHandler.CHARGE_KEY;
    }

    @Override
    public void write(FriendlyByteBuf buf)
    {
        buf.writeBoolean(shiftDown);
    }

    @Override
    public void handle(NetworkManager.PacketContext context)
    {
        if(context.getPlayer() == null) return;

        Player player = context.getPlayer();
        ItemStack stack = player.getMainHandItem();
        if(!stack.isEmpty() && stack.getItem() instanceof IChargeableItem item)
        {
            item.chargeKeyPressed(stack, player, InteractionHand.MAIN_HAND, shiftDown);
        }
    }
}
