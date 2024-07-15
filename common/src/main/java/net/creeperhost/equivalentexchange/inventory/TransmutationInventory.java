package net.creeperhost.equivalentexchange.inventory;

import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.item.IKleinStarItem;
import net.creeperhost.equivalentexchange.impl.TransmutationTableHandler;
import net.creeperhost.equivalentexchange.network.packets.knowledge.RemoveKnowledgePacket;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TransmutationInventory implements Container
{
    private final NonNullList<ItemStack> items;
    @Nullable Player player;
    private String filter = "";
    private int page = 0;

    public TransmutationInventory(@Nullable Player player)
    {
        this.player = player;
        this.items = NonNullList.withSize(27, ItemStack.EMPTY);
        resetFilter();
        resetPage();
        updateInventory();
        setChanged();
    }

    @Override
    public int getContainerSize()
    {
        return items.size();
    }

    @Override
    public boolean isEmpty()
    {
        return false;
    }

    @Override
    public @NotNull ItemStack getItem(int i)
    {
        return  items.get(i);
    }

    @Override
    public @NotNull ItemStack removeItem(int i, int j)
    {
        ItemStack itemStack = ContainerHelper.removeItem(this.items, i, j);
        if(i >= 11 && i <= 26)
        {
            if(!player.level().isClientSide && EquivalentExchangeAPI.hasEmcValue(itemStack))
            {
                EquivalentExchangeAPI.getStorageHandler().removeEmcFor(player, EquivalentExchangeAPI.getEmcValue(itemStack) * j);
            }
        }
        if (!itemStack.isEmpty()) this.updateInventory();
        return itemStack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int i)
    {
        ItemStack itemStack = this.items.get(i);
        if (itemStack.isEmpty()) return ItemStack.EMPTY;

        this.items.set(i, ItemStack.EMPTY);
        return itemStack;
    }

    @Override
    public void setItem(int i, @NotNull ItemStack itemStack)
    {
        if(player == null) return;
        boolean client = player.level().isClientSide();

        if(!client && !EquivalentExchangeAPI.hasEmcValue(itemStack))
        {
            EquivalentExchange.LOGGER.error("Player tried adding item {} to transmutation inventory with no emc value", itemStack);
            return;
        }
        // SLOT: Burn item
        if(i == 0)
        {
            if(!getItem(i).isEmpty())
            {
                EquivalentExchange.LOGGER.error("Item stuck in burn slot {} with emc value {}", getItem(i).getDisplayName().getString(), EquivalentExchangeAPI.getEmcValue(itemStack));
            }
            //Create a new instance of the item in order to remove any extra data added in other ways
            EquivalentExchangeAPI.getKnowledgeHandler().addKnowledge(player, new ItemStack(itemStack.getItem()));

            double value = EquivalentExchangeAPI.getEmcValue(itemStack) * itemStack.getCount();
            if(itemStack.getItem() instanceof IKleinStarItem kleinStar)
            {
                value += kleinStar.getKleinStarStored(itemStack);
            }
            EquivalentExchangeAPI.getStorageHandler().addEmcFor(player, value);
            updateInventory();
            //return here so the item is not added to the items list
            return;
        }
        this.items.set(i, itemStack);
        if(!client)
        {
            //SLOT: Unlearn item
            if(i == 1)
            {
                EquivalentExchangeAPI.getKnowledgeHandler().removeKnowledge(player, itemStack);
                new RemoveKnowledgePacket(itemStack).sendTo((ServerPlayer) player);
            }
            //SLOTS: Center of the right side of tablet (2); left side of the tablet ([3..10])
            else if(i >= 2 && i <= 10)
            {
                if(itemStack.getItem() instanceof IKleinStarItem kleinStar)
                {
                    double starCurrent = kleinStar.getKleinStarStored(itemStack);
                    double playerCurrent = EquivalentExchangeAPI.getStorageHandler().getEmcValueFor(player);
                    //Fill klein star
                    if(i != 2 && starCurrent < kleinStar.getKleinStarMaxStorage(itemStack))
                    {
                        double starMax = kleinStar.getKleinStarMaxStorage(itemStack);
                        double starFree = starMax - starCurrent;
                        double starAdded = Math.min(playerCurrent, starFree);

                        kleinStar.setKleinStarEmc(itemStack, starCurrent + starAdded);
                        EquivalentExchangeAPI.getStorageHandler().removeEmcFor(player, starAdded);
                    }
                    //Drain klein star
                    else if(i == 2 && starCurrent > 0)
                    {
                        kleinStar.setKleinStarEmc(itemStack, 0);
                        EquivalentExchangeAPI.getStorageHandler().addEmcFor(player, starCurrent);
                    }
                }
                if(EquivalentExchangeAPI.hasEmcValue(itemStack))
                {
                    EquivalentExchangeAPI.getKnowledgeHandler().addKnowledge(player, new ItemStack(itemStack.getItem()));
                }
            }
            updateInventory();
        }
        setChanged();
    }

    @Override
    public void setChanged()
    {
    }

    public void updateInventory()
    {
        if(player == null) return;

        NonNullList<ItemStack> transmutations = TransmutationTableHandler.getTransmutationContent(page, filter, getItem(2), player);
        int start = 11;
        int end = 26;
        int i = start;
        for (ItemStack transmutation : transmutations)
        {
            if(i <= end)
            {
                this.items.set(i, transmutation.copy());
                i++;
            }
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player)
    {
        return true;
    }

    @Override
    public void clearContent()
    {
        this.items.clear();
        this.setChanged();
    }

    public CompoundTag serializeNBT()
    {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < items.size(); i++)
        {
            if (!items.get(i).isEmpty())
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
        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt)
    {
        ListTag tagList = nbt.getList("Items", 10);
        for (int i = 0; i < tagList.size(); i++)
        {
            CompoundTag itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < items.size())
            {
                items.set(slot, ItemStack.of(itemTags));
            }
        }
    }

    public void setFilter(String filter, boolean changed)
    {
        this.filter = filter;
        this.updateInventory();
    }

    public void resetFilter()
    {
        setFilter("", true);
    }

    public void setPage(int page)
    {
        this.page = page;
        this.updateInventory();
    }

    public int getPage()
    {
        return this.page;
    }

    public void resetPage()
    {
        setPage(0);
    }
}
