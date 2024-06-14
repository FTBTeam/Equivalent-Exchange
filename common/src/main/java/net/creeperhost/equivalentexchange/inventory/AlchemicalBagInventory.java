package net.creeperhost.equivalentexchange.inventory;

import com.google.common.collect.Lists;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class AlchemicalBagInventory implements Container
{
    private final int size;
    private final NonNullList<ItemStack> items;
    private List<ContainerListener> listeners;
    private Player player;

    public AlchemicalBagInventory(Player player)
    {
        this.player = player;
        this.size = 104;
        this.items = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public AlchemicalBagInventory(ItemStack... itemStacks)
    {
        this.size = itemStacks.length;
        this.items = NonNullList.of(ItemStack.EMPTY, itemStacks);
    }

    public void addListener(ContainerListener containerListener)
    {
        if (this.listeners == null) this.listeners = Lists.newArrayList();
        this.listeners.add(containerListener);
    }

    public void removeListener(ContainerListener containerListener)
    {
        this.listeners.remove(containerListener);
    }

    @Override
    public ItemStack getItem(int i)
    {
        return i >= 0 && i < this.items.size() ? (ItemStack)this.items.get(i) : ItemStack.EMPTY;
    }

    public List<ItemStack> removeAllItems()
    {
        List<ItemStack> list = this.items.stream().filter((itemStack) -> !itemStack.isEmpty()).collect(Collectors.toList());
        this.clearContent();
        return list;
    }

    @Override
    public ItemStack removeItem(int i, int j)
    {
        ItemStack itemStack = ContainerHelper.removeItem(this.items, i, j);
        if (!itemStack.isEmpty()) this.setChanged();
        return itemStack;
    }

    public ItemStack removeItemType(Item item, int i)
    {
        ItemStack itemStack = new ItemStack(item, 0);

        for(int j = this.size - 1; j >= 0; --j)
        {
            ItemStack itemStack2 = this.getItem(j);
            if (itemStack2.getItem().equals(item))
            {
                int k = i - itemStack.getCount();
                ItemStack itemStack3 = itemStack2.split(k);
                itemStack.grow(itemStack3.getCount());
                if (itemStack.getCount() == i)
                {
                    break;
                }
            }
        }

        if (!itemStack.isEmpty()) this.setChanged();
        return itemStack;
    }

    public ItemStack addItem(ItemStack itemStack)
    {
        ItemStack itemStack2 = itemStack.copy();
        this.moveItemToOccupiedSlotsWithSameType(itemStack2);
        if (itemStack2.isEmpty())
        {
            return ItemStack.EMPTY;
        }
        else
        {
            this.moveItemToEmptySlots(itemStack2);
            return itemStack2.isEmpty() ? ItemStack.EMPTY : itemStack2;
        }
    }

    public boolean canAddItem(ItemStack itemStack)
    {
        boolean bl = false;

        for (ItemStack itemStack2 : this.items)
        {
            if (itemStack2.isEmpty() || ItemStack.isSameItemSameTags(itemStack2, itemStack) && itemStack2.getCount() < itemStack2.getMaxStackSize())
            {
                bl = true;
                break;
            }
        }
        return bl;
    }

    @Override
    public ItemStack removeItemNoUpdate(int i)
    {
        ItemStack itemStack = (ItemStack)this.items.get(i);
        if (itemStack.isEmpty())
        {
            return ItemStack.EMPTY;
        }
        else
        {
            this.items.set(i, ItemStack.EMPTY);
            return itemStack;
        }
    }

    @Override
    public void setItem(int i, @NotNull ItemStack itemStack)
    {
        this.items.set(i, itemStack);
        this.setChanged();
    }

    @Override
    public int getContainerSize()
    {
        return this.size;
    }

    @Override
    public boolean isEmpty()
    {
        Iterator<ItemStack> var1 = this.items.iterator();

        ItemStack itemStack;
        do
        {
            if (!var1.hasNext())
            {
                return true;
            }
            itemStack = var1.next();
        } while(itemStack.isEmpty());

        return false;
    }

    @Override
    public void setChanged()
    {

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


    @Override
    public String toString()
    {
        return this.items.stream().filter((itemStack) -> !itemStack.isEmpty()).toList().toString();
    }

    private void moveItemToEmptySlots(ItemStack itemStack)
    {
        for(int i = 0; i < this.size; ++i)
        {
            ItemStack itemStack2 = this.getItem(i);
            if (itemStack2.isEmpty())
            {
                this.setItem(i, itemStack.copy());
                itemStack.setCount(0);
                return;
            }
        }
    }

    private void moveItemToOccupiedSlotsWithSameType(ItemStack itemStack)
    {
        for(int i = 0; i < this.size; ++i)
        {
            ItemStack itemStack2 = this.getItem(i);
            if (ItemStack.isSameItemSameTags(itemStack2, itemStack))
            {
                this.moveItemsBetweenStacks(itemStack, itemStack2);
                if (itemStack.isEmpty())
                {
                    return;
                }
            }
        }
    }

    private void moveItemsBetweenStacks(ItemStack itemStack, ItemStack itemStack2)
    {
        int i = Math.min(this.getMaxStackSize(), itemStack2.getMaxStackSize());
        int j = Math.min(itemStack.getCount(), i - itemStack2.getCount());
        if (j > 0)
        {
            itemStack2.grow(j);
            itemStack.shrink(j);
            this.setChanged();
        }
    }

    public CompoundTag serializeNBT()
    {
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
}
