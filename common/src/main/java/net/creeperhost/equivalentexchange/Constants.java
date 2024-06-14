package net.creeperhost.equivalentexchange;

import dev.architectury.platform.Platform;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Range;

import java.nio.file.Path;

public class Constants
{
    public static final String MOD_ID = "equivalentexchange";
    public static final Path EE_CONFIG_FOLDER = Platform.getConfigFolder().resolve(Constants.MOD_ID);
    public static final Path EE_CONFIG_FILE = EE_CONFIG_FOLDER.resolve(MOD_ID + ".json");
    public static final String SHIFT_TEXT = "tooltip.equivalentexchange.hold_shift";
    public static final double MAX_EMC = 18^100;

    //TODO move this somewhere that makes sense
    @Range(from = 0, to = Long.MAX_VALUE)
    public static long getEMCPerDurability(ItemStack stack)
    {
        if (stack.isEmpty())
        {
            return 0;
        }
        else if (stack.isDamageableItem())
        {
            ItemStack stackCopy = stack.copy();
            stackCopy.setDamageValue(0);
            long emc = (long) Math.ceil(EquivalentExchangeAPI.getEmcValue(stackCopy) / (double) stack.getMaxDamage());
            return Math.max(emc, 1);
        }
        return 1;
    }
}
