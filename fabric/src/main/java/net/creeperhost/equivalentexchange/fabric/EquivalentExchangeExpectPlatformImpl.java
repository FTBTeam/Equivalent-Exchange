package net.creeperhost.equivalentexchange.fabric;

import net.creeperhost.equivalentexchange.EquivalentExchangeExpectPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class EquivalentExchangeExpectPlatformImpl
{
    /**
     * This is our actual method to {@link EquivalentExchangeExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
