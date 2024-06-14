package net.creeperhost.equivalentexchange.api.events;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.ItemStack;

public interface EmcRegisterEvent
{
    Event<PreEvent> EMC_PRE_START = EventFactory.createEventResult();
    Event<PostEvent> EMC_POST_START = EventFactory.createEventResult();

    Event<Set> EMC_SET_EVENT = EventFactory.createEventResult();

    interface PreEvent
    {
        void preStart(ResourceManager resourceManager);
    }

    interface PostEvent
    {
        void postStart(ResourceManager resourceManager);
    }

    //Fired before the emc value is set to the itemstack
    interface Set
    {
        void set(ItemStack stack);
    }
}
