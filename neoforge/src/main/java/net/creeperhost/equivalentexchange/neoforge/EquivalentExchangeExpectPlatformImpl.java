package net.creeperhost.equivalentexchange.neoforge;

import net.creeperhost.equivalentexchange.EquivalentExchangeExpectPlatform;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class EquivalentExchangeExpectPlatformImpl
{
    /**
     * This is our actual method to {@link EquivalentExchangeExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
