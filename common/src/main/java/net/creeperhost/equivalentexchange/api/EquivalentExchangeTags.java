package net.creeperhost.equivalentexchange.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class EquivalentExchangeTags
{
    public static TagKey<Item> EMC_DENYLIST_ITEM = TagKey.create(Registries.ITEM, new ResourceLocation("equivalentexchange", "emcdenylist"));
    public static TagKey<Item> COVALENCE_DUST = TagKey.create(Registries.ITEM, new ResourceLocation("equivalentexchange", "covalence_dust"));
    public static TagKey<Block> NEEDS_DARK_MATTER_TOOL = TagKey.create(Registries.BLOCK, new ResourceLocation("equivalentexchange", "needs_dark_matter_tools"));
    public static TagKey<Block> NEEDS_RED_MATTER_TOOL = TagKey.create(Registries.BLOCK, new ResourceLocation("equivalentexchange", "needs_red_matter_tools"));


    public static boolean isBlacklisted(ItemStack stack)
    {
        return stack.is(EMC_DENYLIST_ITEM);
    }
}
