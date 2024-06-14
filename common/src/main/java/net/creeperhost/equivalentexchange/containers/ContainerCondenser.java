package net.creeperhost.equivalentexchange.containers;

import net.creeperhost.equivalentexchange.blockentities.BlockEntityCondenser;
import net.creeperhost.equivalentexchange.containers.slots.EmcPolySlot;
import net.creeperhost.equivalentexchange.containers.slots.GhostSlot;
import net.creeperhost.equivalentexchange.init.ModContainers;
import net.creeperhost.polylib.client.modulargui.lib.container.DataSync;
import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.DataManagerContainer;
import net.creeperhost.polylib.containers.ModularGuiContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.creeperhost.polylib.data.DataManagerBlock;
import net.creeperhost.polylib.data.serializable.DoubleData;
import net.creeperhost.polylib.data.serializable.IntData;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class ContainerCondenser extends ModularGuiContainerMenu implements DataManagerContainer
{
    public BlockEntityCondenser blockEntity;
    public final SlotGroup main = createSlotGroup(0, 1, 3); //zone id is 0, Quick move to zone 1, then 3
    public final SlotGroup hotBar = createSlotGroup(0, 1, 3);
    public final SlotGroup armor = createSlotGroup(1, 3, 0); //zone id is 1, Quick move to zone 3, then 0
    public final SlotGroup offhand = createSlotGroup(2, 3, 0);

    public final SlotGroup chest = createSlotGroup(3, 1, 0, 2);//zone id is 3, Quick move to zone 1, then 0, then 2

    public final SlotGroup target = createSlotGroup(4, 1, 0, 2);

    public final DataSync<Double> stored;
    public final DataSync<Double> targetdata;


    public ContainerCondenser(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv, (BlockEntityCondenser) Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos()));
    }

    public ContainerCondenser(int id, Inventory playerInv, BlockEntityCondenser blockEntityCondenser)
    {
        super(ModContainers.CONDENSER_CONTAINER.get(), id, playerInv);
        this.blockEntity = blockEntityCondenser;

        stored = new DataSync<>(this, new DoubleData(), () -> blockEntity.storedEMC.get());
        targetdata = new DataSync<>(this, new DoubleData(), () -> blockEntity.targetEMC.get());

        main.addPlayerMain(inventory);
        hotBar.addPlayerBar(inventory);

        armor.addPlayerArmor(inventory);
        offhand.addPlayerOffhand(inventory);

        chest.addSlots(91, 0, index -> new EmcPolySlot(blockEntityCondenser.getContainer(Direction.UP), index));
        target.addSlots(1, 91, index -> new EmcPolySlot(blockEntityCondenser.getContainer(Direction.UP), index));
    }

    @Override
    public void clicked(int slotId, int dragType, @NotNull ClickType clickType, @NotNull Player player)
    {
        if(slotId >= 0 && getSlot(slotId) instanceof GhostSlot)
        {
            if(!getCarried().isEmpty())
            {
                ItemStack stack = getCarried().copy();
                stack.setCount(1);
                setItem(slotId, 1, stack);
            }
            else
            {
                setItem(slotId, 1, ItemStack.EMPTY);
            }
            return;
        }
        super.clicked(slotId, dragType, clickType, player);
    }

    @Override
    public boolean stillValid(@NotNull Player player)
    {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BlockEntity & DataManagerBlock> T getBlockEntity()
    {
        return (T) blockEntity;
    }
}
