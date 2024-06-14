package net.creeperhost.equivalentexchange.network.packets;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseC2SMessage;
import dev.architectury.networking.simple.MessageType;
import net.creeperhost.equivalentexchange.items.interfaces.IActionItem;
import net.creeperhost.equivalentexchange.network.PacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ActionKeyPacket extends BaseC2SMessage
{
    public ActionKeyPacket() {}

    public ActionKeyPacket(FriendlyByteBuf friendlyByteBuf) {}

    @Override
    public MessageType getType()
    {
        return PacketHandler.ACTION_KEY;
    }

    @Override
    public void write(FriendlyByteBuf buf) {}

    @Override
    public void handle(NetworkManager.PacketContext context)
    {
        if(context.getPlayer() == null) return;

        Player player = context.getPlayer();
        ItemStack stack = player.getMainHandItem();
        if(!stack.isEmpty() && stack.getItem() instanceof IActionItem item)
        {
            item.onActionKeyPressed(stack, player, InteractionHand.MAIN_HAND);
        }
    }
}
