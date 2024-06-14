package net.creeperhost.equivalentexchange.containers.relays;

import net.creeperhost.equivalentexchange.blockentities.relays.BlockEntityRelayMK3;
import net.creeperhost.equivalentexchange.containers.prefab.ContainerBase;
import net.creeperhost.equivalentexchange.containers.slots.EmcSlot;
import net.creeperhost.equivalentexchange.containers.slots.SlotEmcStorage;
import net.creeperhost.equivalentexchange.init.ModContainers;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class ContainerRelayMK3 extends ContainerBase
{
    private ContainerData containerData;

    public ContainerRelayMK3(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv, (BlockEntityRelayMK3) Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(3));
    }

    public ContainerRelayMK3(int id, Inventory playerInv, BlockEntityRelayMK3 blockEntityRelay, ContainerData containerData)
    {
        super(ModContainers.RELAY_CONTAINER_MK3.get(), id);
        this.containerData = containerData;

        //Burn Slot
        this.addSlot(new EmcSlot(blockEntityRelay.getContainer(Direction.UP), 0, 104, 58));

        //Charge slot
        this.addSlot(new SlotEmcStorage(blockEntityRelay.getContainer(Direction.UP), 1, 164, 58));

        int counter = 2;
        for (int i = 3; i >= 0; i--)
        {
            for (int j = 4; j >= 0; j--)
            {
                this.addSlot(new Slot(blockEntityRelay.getContainer(Direction.UP), counter++, 28 + i * 18, 18 + j * 18));
            }
        }

        for (int l = 0; l < 3; ++l)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlot(new Slot(playerInv, k + l * 9 + 9,  8 + k * 18 + 18, l * 18 + 95 + 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18 + 18, 153 + 18));
        }

        addDataSlots(containerData);
    }

    @Override
    public boolean stillValid(@NotNull Player player)
    {
        return true;
    }

    public int getStoredEMC()
    {
        return this.containerData.get(1);
    }

    public int getMaxEMC()
    {
        return this.containerData.get(0);
    }
}
