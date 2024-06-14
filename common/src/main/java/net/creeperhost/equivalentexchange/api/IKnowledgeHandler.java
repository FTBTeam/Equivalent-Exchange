package net.creeperhost.equivalentexchange.api;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public interface IKnowledgeHandler
{
    Path getSavePath(Player player);

    Path getBaseSavePath();

    List<ItemStack> getKnowledgeList(Player player);

    List<ItemStack> getKnowledgeList(UUID uuid);

    void setKnowledgeList(Player player, List<ItemStack> list);

    void addKnowledge(UUID uuid, ItemStack stack);

    void addKnowledge(Player player, ItemStack stack);

    void removeKnowledge(UUID uuid, ItemStack stack);

    void removeKnowledge(Player player, ItemStack stack);

    void saveKnowledgeToFile(Player player);

    void loadKnowledgeFromFile(Player player);

    void clear();
}
