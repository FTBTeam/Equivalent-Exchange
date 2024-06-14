package net.creeperhost.equivalentexchange.api.events;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface KnowledgeChangedEvent
{
    Event<KnowledgeChangedEvent.Added> KNOWLEDGE_ADDED_EVENT = EventFactory.createEventResult();
    Event<KnowledgeChangedEvent.Removed> KNOWLEDGE_REMOVED_EVENT = EventFactory.createEventResult();

    interface Added
    {
        void added(Player player, ItemStack stack);
    }

    interface Removed
    {
        void removed(Player player, ItemStack stack);
    }
}
