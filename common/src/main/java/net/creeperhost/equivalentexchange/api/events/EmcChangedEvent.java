package net.creeperhost.equivalentexchange.api.events;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.world.entity.player.Player;

public interface EmcChangedEvent
{
    Event<EmcChangedEvent.Added> EMC_ADDED_EVENT = EventFactory.createEventResult();
    Event<EmcChangedEvent.Removed> EMC_REMOVED_EVENT = EventFactory.createEventResult();

    interface Added
    {
        void added(Player player, double current, double added, double newValue);
    }

    interface Removed
    {
        void removed(Player player, double current, double removed, double newValue);
    }
}
