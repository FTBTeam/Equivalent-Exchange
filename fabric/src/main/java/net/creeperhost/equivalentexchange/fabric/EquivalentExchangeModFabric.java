package net.creeperhost.equivalentexchange.fabric;

import dev.architectury.platform.Platform;
import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.client.EETextures;
import net.creeperhost.equivalentexchange.client.renders.BlockOverlayRender;
import net.creeperhost.equivalentexchange.items.interfaces.IOverlayItem;
import net.creeperhost.polylib.fabric.client.ResourceReloadListenerWrapper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class EquivalentExchangeModFabric implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        EquivalentExchange.init();
        if(Platform.getEnv() == EnvType.CLIENT)
        {
            ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new ResourceReloadListenerWrapper(
                    EETextures::getAtlasHolder, new ResourceLocation(Constants.MOD_ID, "gui_atlas_reload")));

            WorldRenderEvents.END.register(context ->
            {
                Player player = Minecraft.getInstance().player;
                if (player == null) return;
                ItemStack stack = player.getMainHandItem();
                if (stack.getItem() instanceof IOverlayItem)
                {
                    BlockOverlayRender.render(context.matrixStack(), stack);
                }
            });
        }
    }
}
