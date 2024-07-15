package net.creeperhost.equivalentexchange.impl;

import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.IKnowledgeHandler;
import net.creeperhost.equivalentexchange.api.events.KnowledgeChangedEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.LevelResource;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class KnowledgeHandler implements IKnowledgeHandler
{
    private static final HashMap<String, List<ItemStack>> KNOWLEDGE = new HashMap<>();

    @Override
    public Path getSavePath(Player player)
    {
        return getBaseSavePath().resolve(player.getUUID().toString() + "_knowledge.dat");
    }

    @Override
    public Path getBaseSavePath()
    {
        return FTBTeamsAPI.api().getManager().getServer().getWorldPath(LevelResource.PLAYER_DATA_DIR).resolve("knowledge");
    }

    @Override
    public List<ItemStack> getKnowledgeList(Player player)
    {
        if(!KNOWLEDGE.containsKey(player.getUUID().toString()))
        {
            KNOWLEDGE.put(player.getUUID().toString(), new ArrayList<>());
        }
        return KNOWLEDGE.get(player.getUUID().toString());
    }

    @Override
    public List<ItemStack> getKnowledgeList(UUID uuid)
    {
        if(!KNOWLEDGE.containsKey(uuid.toString()))
        {
            KNOWLEDGE.put(uuid.toString(), new ArrayList<>());
        }
        return KNOWLEDGE.get(uuid.toString());
    }

    @Override
    public void setKnowledgeList(Player player, List<ItemStack> list)
    {
        KNOWLEDGE.put(player.getUUID().toString(), list);
    }

    public void setKnowledgeList(UUID uuid, List<ItemStack> list)
    {
        KNOWLEDGE.put(uuid.toString(), list);
    }

    @Override
    public void addKnowledge(Player player, ItemStack stack)
    {
        List<ItemStack> stacks = getKnowledgeList(player);
        boolean shouldAdd = true;
        for (ItemStack itemStack : stacks)
        {
            if (itemStack.is(stack.getItem()))
            {
                shouldAdd = false;
                break;
            }
        }
        if(shouldAdd) stacks.add(stack);
        setKnowledgeList(player, stacks);
        KnowledgeChangedEvent.KNOWLEDGE_ADDED_EVENT.invoker().added(player, stack);
    }

    @Override
    public void addKnowledge(UUID uuid, ItemStack stack)
    {
        List<ItemStack> stacks = getKnowledgeList(uuid);
        boolean shouldAdd = true;
        for (ItemStack itemStack : stacks)
        {
            if (itemStack.is(stack.getItem()))
            {
                shouldAdd = false;
                break;
            }
        }
        if(shouldAdd) stacks.add(stack);
        setKnowledgeList(uuid, stacks);
    }

    @Override
    public void removeKnowledge(Player player, ItemStack stack)
    {
        List<ItemStack> stacks = getKnowledgeList(player);
        stacks.removeIf(itemStack -> itemStack.is(stack.getItem()));
        setKnowledgeList(player, stacks);
        KnowledgeChangedEvent.KNOWLEDGE_REMOVED_EVENT.invoker().removed(player, stack);
    }

    @Override
    public void removeKnowledge(UUID uuid, ItemStack stack)
    {
        List<ItemStack> stacks = getKnowledgeList(uuid);
        stacks.removeIf(itemStack -> itemStack.is(stack.getItem()));
        setKnowledgeList(uuid, stacks);
    }

    @Override
    public void saveKnowledgeToFile(Player player)
    {
        if(player != null && !player.level().isClientSide)
        {
            EquivalentExchange.LOGGER.info("Saving knowledge for player " + player.getName().getString());
            try
            {
                List<ItemStack> items = getKnowledgeList(player);
                ListTag nbtTagList = new ListTag();
                for (int i = 0; i < items.size(); i++)
                {
                    if (items.get(i) != null)
                    {
                        CompoundTag itemTag = new CompoundTag();
                        itemTag.putInt("Slot", i);
                        items.get(i).save(itemTag);
                        nbtTagList.add(itemTag);
                    }
                }
                CompoundTag nbt = new CompoundTag();
                nbt.put("Items", nbtTagList);
                nbt.putInt("Size", items.size());
                NbtIo.write(nbt, getSavePath(player));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void loadKnowledgeFromFile(Player player)
    {
        if(player != null && !player.level().isClientSide)
        {
            EquivalentExchange.LOGGER.info("Loading knowledge for player " + player.getName().getString());
            try
            {
                CompoundTag compoundTag = NbtIo.read(getSavePath(player));
                if (compoundTag == null)
                {
                    EquivalentExchange.LOGGER.error("unable to load file " + getSavePath(player));
                    KNOWLEDGE.put(player.getUUID().toString(), new ArrayList<>());
                    return;
                }
                List<ItemStack> items = new ArrayList<>();
                ListTag tagList = compoundTag.getList("Items", 10);
                for (int i = 0; i < tagList.size(); i++)
                {
                    CompoundTag itemTags = tagList.getCompound(i);
                    int slot = itemTags.getInt("Slot");
                    items.add(slot, ItemStack.of(itemTags));
                }
                KNOWLEDGE.put(player.getUUID().toString(), items);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void clear()
    {
        EquivalentExchange.LOGGER.info("Cleaning up players knowledge");
        KNOWLEDGE.clear();
    }
}
