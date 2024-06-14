package net.creeperhost.equivalentexchange.neoforge;

import net.creeperhost.equivalentexchange.client.EETextures;
import net.creeperhost.equivalentexchange.client.renders.BlockOverlayRender;
import net.creeperhost.equivalentexchange.items.interfaces.IOverlayItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;


public class ClientEvents
{
    @SubscribeEvent
    static void renderWorldLastEvent(RenderLevelStageEvent event)
    {
        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_BLOCK_ENTITIES)
        {
            Player player = Minecraft.getInstance().player;
            if (player == null) return;
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof IOverlayItem)
            {
                BlockOverlayRender.render(event.getPoseStack(), stack);
            }
        }
    }

    static void registerReloadListeners(RegisterClientReloadListenersEvent event)
    {
        event.registerReloadListener(EETextures.getAtlasHolder());
    }
}
