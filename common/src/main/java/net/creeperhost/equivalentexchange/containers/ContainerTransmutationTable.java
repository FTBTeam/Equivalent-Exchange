package net.creeperhost.equivalentexchange.containers;

import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.containers.prefab.ContainerBase;
import net.creeperhost.equivalentexchange.containers.slots.EmcSlot;
import net.creeperhost.equivalentexchange.containers.slots.SlotOutput;
import net.creeperhost.equivalentexchange.impl.TransmutationTableHandler;
import net.creeperhost.equivalentexchange.init.ModContainers;
import net.creeperhost.equivalentexchange.inventory.TransmutationInventory;
import net.creeperhost.equivalentexchange.network.packets.PlayerEmcPacket;
import net.creeperhost.equivalentexchange.network.packets.TransmutationInventoryPacket;
import net.creeperhost.equivalentexchange.network.packets.knowledge.KnowledgePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ContainerTransmutationTable extends ContainerBase
{
    TransmutationInventory transmutationInventory;

    public ContainerTransmutationTable(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv);
    }

    public ContainerTransmutationTable(int id, Inventory playerInv)
    {
        super(ModContainers.TRANSMUTATION_TABLE_CONTAINER.get(), id);

        if(playerInv.player instanceof ServerPlayer serverPlayer)
        {
            transmutationInventory = TransmutationTableHandler.getTransmutationInventory(playerInv.player);

            new PlayerEmcPacket(EquivalentExchangeAPI.getStorageHandler().getEmcValueFor(serverPlayer)).sendTo(serverPlayer);
            new KnowledgePacket(EquivalentExchangeAPI.getKnowledgeHandler().getKnowledgeList(serverPlayer)).sendTo(serverPlayer);
            new TransmutationInventoryPacket(transmutationInventory.serializeNBT()).sendTo(serverPlayer);
        }
        else
        {
            transmutationInventory = TransmutationTableHandler.getTransmutationInventory(playerInv.player);
        }

        //Burn slot
        this.addSlot(new EmcSlot(transmutationInventory, 0, 107, 97));
        //Forget slot
        this.addSlot(new EmcSlot(transmutationInventory, 1, 89, 97));
        //Target slot
        this.addSlot(new EmcSlot(transmutationInventory, 2, 158, 49));

        //Left side
        this.addSlot(new EmcSlot(transmutationInventory, 3, 43, 23));
        this.addSlot(new EmcSlot(transmutationInventory, 4, 34, 41));
        this.addSlot(new EmcSlot(transmutationInventory, 5, 52, 41));
        this.addSlot(new EmcSlot(transmutationInventory, 6, 16, 50));
        this.addSlot(new EmcSlot(transmutationInventory, 7, 70, 50));
        this.addSlot(new EmcSlot(transmutationInventory, 8, 34, 59));
        this.addSlot(new EmcSlot(transmutationInventory, 9, 52, 59));
        this.addSlot(new EmcSlot(transmutationInventory, 10, 43, 77));

        //Right side
        this.addSlot(new SlotOutput(transmutationInventory, 11, 158, 9));
        this.addSlot(new SlotOutput(transmutationInventory, 12, 176, 13));
        this.addSlot(new SlotOutput(transmutationInventory, 13, 193, 30));
        this.addSlot(new SlotOutput(transmutationInventory, 14, 199, 50));
        this.addSlot(new SlotOutput(transmutationInventory, 15, 193, 70));
        this.addSlot(new SlotOutput(transmutationInventory, 16, 176, 87));
        this.addSlot(new SlotOutput(transmutationInventory, 17, 158, 91));
        this.addSlot(new SlotOutput(transmutationInventory, 18, 140, 87));
        this.addSlot(new SlotOutput(transmutationInventory, 19, 123, 70));
        this.addSlot(new SlotOutput(transmutationInventory, 20, 116, 50));
        this.addSlot(new SlotOutput(transmutationInventory, 21, 123, 30));
        this.addSlot(new SlotOutput(transmutationInventory, 22, 140, 13));
        this.addSlot(new SlotOutput(transmutationInventory, 23, 158, 31));
        this.addSlot(new SlotOutput(transmutationInventory, 24, 177, 50));
        this.addSlot(new SlotOutput(transmutationInventory, 25, 158, 69));
        this.addSlot(new SlotOutput(transmutationInventory, 26, 139, 50));


        for (int l = 0; l < 3; ++l)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlot(new Slot(playerInv, k + l * 9 + 9, 35 + k * 18, l * 18 + 117));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlot(new Slot(playerInv, i1, 35 + i1 * 18, 175));
        }
    }

    @Override
    public void removed(@NotNull Player player)
    {
        super.removed(player);
        TransmutationTableHandler.updateInventory(player, transmutationInventory);
    }

    @Override
    public boolean stillValid(@NotNull Player player)
    {
        return true;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int slotIndex)
    {
        if(player.level().isClientSide) return ItemStack.EMPTY;

        int burnID = 0;
        //Is from the players inventory
        if(slotIndex >= 27 && slotIndex <= 62)
        {
            Slot slot = slots.get(slotIndex);
            ItemStack stack = slot.getItem();
            if(!stack.isEmpty() && EquivalentExchangeAPI.hasEmcValue(stack))
            {
                if(stack.isEmpty()) return ItemStack.EMPTY;
                slots.get(burnID).set(stack);
                slot.set(ItemStack.EMPTY);
                return ItemStack.EMPTY;
            }
        }
        //Right side of transmutation inventory
        if(slotIndex >= 11 && slotIndex <= 26)
        {
            ItemStack stack = slots.get(slotIndex).getItem().copy();
            double playersEMC = EquivalentExchangeAPI.getStorageHandler().getEmcValueFor(player);
            int requestAmount = stack.getMaxStackSize();
            double cost = EquivalentExchangeAPI.getEmcValue(stack) * requestAmount;
            if(playersEMC >= cost)
            {
                stack.setCount(requestAmount);
                player.addItem(stack);
                EquivalentExchangeAPI.getStorageHandler().removeEmcFor(player, cost);
                return ItemStack.EMPTY;
            }
            else
            {
                //TODO calculate how many of the item the player can afford and give them that amount
                return ItemStack.EMPTY;
            }
        }

        return ItemStack.EMPTY;
    }

    public TransmutationInventory getTransmutationInventory()
    {
        return transmutationInventory;
    }
}
