package net.creeperhost.equivalentexchange.containers;

import net.creeperhost.equivalentexchange.containers.prefab.ContainerBase;
import net.creeperhost.equivalentexchange.init.ModContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class ContainerTarget extends ContainerBase
{
    public ContainerTarget(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv);

        for (int l = 0; l < 3; ++l)
        {
            for (int k = 0; k < 9; ++k)
            {
                this.addSlot(new Slot(playerInv, k + l * 9 + 9, 6 + k * 18, l * 18 + 56));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1)
        {
            this.addSlot(new Slot(playerInv, i1, 6 + i1 * 18, 114));
        }
    }

    public ContainerTarget(int id, Inventory playerInv)
    {
        super(ModContainers.TARGET_CONTAINER.get(), id);
    }

    @Override
    public boolean stillValid(@NotNull Player player)
    {
        return true;
    }
}
