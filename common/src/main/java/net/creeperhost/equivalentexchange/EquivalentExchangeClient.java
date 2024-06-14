package net.creeperhost.equivalentexchange;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.creeperhost.equivalentexchange.client.ClientEvents;
import net.creeperhost.equivalentexchange.client.EEKeyBindings;
import net.creeperhost.equivalentexchange.client.renders.PedestalRender;
import net.creeperhost.equivalentexchange.init.ModBlocks;
import net.creeperhost.equivalentexchange.init.ModCreativeTabs;
import net.creeperhost.equivalentexchange.init.ModItems;
import net.creeperhost.equivalentexchange.items.interfaces.IActiveItem;
import net.minecraft.resources.ResourceLocation;

public class EquivalentExchangeClient
{
    public static void registerItemProperties()
    {
        ModItems.ITEMS.forEach(itemRegistrySupplier ->
        {
            if(itemRegistrySupplier.get() instanceof IActiveItem iActiveItem)
                ItemPropertiesRegistry.register(itemRegistrySupplier.get(), new ResourceLocation(Constants.MOD_ID, "active"), (itemStack, clientLevel, livingEntity, i)
                        -> iActiveItem.isActive(itemStack) ? 1.0F : 0.0F);
        });
    }

    public static void init()
    {
        ClientLifecycleEvent.CLIENT_SETUP.register(ClientEvents::onClientSetup);
        ClientTooltipEvent.ITEM.register(ClientEvents::toolTipEvent);
        ClientRawInputEvent.KEY_PRESSED.register(ClientEvents::keyPressed);
        ClientGuiEvent.RENDER_HUD.register(ClientEvents::onHudRender);
        EEKeyBindings.register();

        //noinspection unchecked,UnstableApiUsage
        ModItems.ITEMS.forEach(itemRegistrySupplier -> CreativeTabRegistry.append(ModCreativeTabs.CREATIVE_TAB, itemRegistrySupplier));
    }
}
