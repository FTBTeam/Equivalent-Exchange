package net.creeperhost.equivalentexchange.neoforge;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.items.tools.EEToolTiers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.TierSortingRegistry;

import java.util.Collections;
import java.util.List;

@Mod(Constants.MOD_ID)
public class EquivalentExchangeModNeoForge
{
    public EquivalentExchangeModNeoForge(IEventBus iEventBus)
    {
        EquivalentExchange.init();
        if(Platform.getEnvironment() == Env.CLIENT)
        {
            NeoForge.EVENT_BUS.register(ClientEvents.class);
            iEventBus.addListener(ClientEvents::registerReloadListeners);
        }

        TierSortingRegistry.registerTier(EEToolTiers.DARK_MATTER, new ResourceLocation(Constants.MOD_ID, "dark_matter"), List.of(Tiers.NETHERITE), Collections.emptyList());
        TierSortingRegistry.registerTier(EEToolTiers.RED_MATTER, new ResourceLocation(Constants.MOD_ID, "red_matter"), List.of(EEToolTiers.DARK_MATTER), Collections.emptyList());
    }
}
