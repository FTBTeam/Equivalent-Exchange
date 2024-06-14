package net.creeperhost.equivalentexchange.neoforge;

import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.client.renders.EntitySpriteRender;
import net.creeperhost.equivalentexchange.init.ModEntities;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NeoForgeEvents
{
    @SubscribeEvent
    public static void event(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(ModEntities.VOLCANITE_PROJECTILE.get(), context -> new EntitySpriteRender<>(context, new ResourceLocation(Constants.MOD_ID, "textures/entity/volcanite_orb.png")));
        event.registerEntityRenderer(ModEntities.EVERTIDE_PROJECTILE.get(), context -> new EntitySpriteRender<>(context, new ResourceLocation(Constants.MOD_ID, "textures/entity/evertide_orb.png")));
    }
}
