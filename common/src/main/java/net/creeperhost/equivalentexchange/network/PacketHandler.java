package net.creeperhost.equivalentexchange.network;

import dev.architectury.networking.simple.MessageType;
import dev.architectury.networking.simple.SimpleNetworkManager;
import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.network.packets.*;
import net.creeperhost.equivalentexchange.network.packets.knowledge.AddKnowledgePacket;
import net.creeperhost.equivalentexchange.network.packets.knowledge.KnowledgePacket;
import net.creeperhost.equivalentexchange.network.packets.knowledge.RemoveKnowledgePacket;

public interface PacketHandler
{
    SimpleNetworkManager NET = SimpleNetworkManager.create(Constants.MOD_ID);
    MessageType ACTION_KEY = NET.registerC2S("action_key", ActionKeyPacket::new);
    MessageType CHARGE_KEY = NET.registerC2S("charge_key", ChargeKeyPacket::new);
    MessageType UPDATE_FILTER = NET.registerC2S("update_filter", UpdateTransmutationFilter::new);

    MessageType EMC_SYNC = NET.registerS2C("emc_sync", PlayerEmcPacket::new);
    MessageType EMC_VALUE_SYNC = NET.registerS2C("emc_value_sync", EmcValuesPacket::new);
    MessageType KNOWLEDGE_SYNC = NET.registerS2C("knowledge_sync", KnowledgePacket::new);
    MessageType KNOWLEDGE_ADD = NET.registerS2C("knowledge_add", AddKnowledgePacket::new);
    MessageType KNOWLEDGE_REMOVE = NET.registerS2C("knowledge_remove", RemoveKnowledgePacket::new);

    MessageType TRANSMUTATION_TABLE_PACKET = NET.registerS2C("transmutation_table_sync", TransmutationInventoryPacket::new);
    MessageType ALCHEMICAL_BAG_PACKET = NET.registerS2C("alchemical_bag_sync", AlchemicalBagPacket::new);

    static void init() {}
}
