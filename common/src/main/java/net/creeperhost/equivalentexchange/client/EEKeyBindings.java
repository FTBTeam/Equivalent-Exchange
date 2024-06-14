package net.creeperhost.equivalentexchange.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;

public class EEKeyBindings
{
    public static final String ee_category = "equivalentexchange.keybindings.category";
    public static final KeyMapping ACTION_KEY = new KeyMapping("equivalentexchange.keybindings.action",
            InputConstants.Type.KEYSYM, InputConstants.KEY_C, ee_category);

    public static final KeyMapping CHARGE_KEY = new KeyMapping("equivalentexchange.keybindings.charge",
            InputConstants.Type.KEYSYM, InputConstants.KEY_V, ee_category);

    public static void register()
    {
        KeyMappingRegistry.register(ACTION_KEY);
        KeyMappingRegistry.register(CHARGE_KEY);
    }

}
