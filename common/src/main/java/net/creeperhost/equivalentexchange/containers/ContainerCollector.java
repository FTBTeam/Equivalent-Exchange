package net.creeperhost.equivalentexchange.containers;

import net.creeperhost.equivalentexchange.blockentities.BlockEntityCollector;
import net.creeperhost.equivalentexchange.blockentities.BlockEntityCondenser;
import net.creeperhost.equivalentexchange.blocks.BlockCollector;
import net.creeperhost.equivalentexchange.containers.prefab.ContainerBase;
import net.creeperhost.equivalentexchange.containers.slots.GhostSlot;
import net.creeperhost.equivalentexchange.init.ModContainers;
import net.creeperhost.equivalentexchange.types.CollectorType;
import net.creeperhost.polylib.client.modulargui.lib.container.DataSync;
import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.DataManagerContainer;
import net.creeperhost.polylib.containers.ModularGuiContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.creeperhost.polylib.data.DataManagerBlock;
import net.creeperhost.polylib.data.serializable.DoubleData;
import net.creeperhost.polylib.data.serializable.IntData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class ContainerCollector extends ModularGuiContainerMenu implements DataManagerContainer
{
    public BlockEntityCollector blockEntity;
    public final SlotGroup main = createSlotGroup(0, 1, 3); //zone id is 0, Quick move to zone 1, then 3
    public final SlotGroup hotBar = createSlotGroup(0, 1, 3);
    public final SlotGroup armor = createSlotGroup(1, 3, 0); //zone id is 1, Quick move to zone 3, then 0
    public final SlotGroup offhand = createSlotGroup(2, 3, 0);

    public final DataSync<Double> energy;
    public final DataSync<Integer> light;


    public ContainerCollector(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv, (BlockEntityCollector) Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos()));
    }

    public ContainerCollector(int id, Inventory playerInv, BlockEntityCollector blockEntityCollector)
    {
        super(ModContainers.COLLECTOR_CONTAINER.get(), id, playerInv);
        this.blockEntity = blockEntityCollector;

        energy = new DataSync<>(this, new DoubleData(), () -> blockEntity.storedEMC.get());
        light = new DataSync<>(this, new IntData(), () -> blockEntity.lightLevel.get());

        main.addPlayerMain(inventory);
        hotBar.addPlayerBar(inventory);

        armor.addPlayerArmor(inventory);
        offhand.addPlayerOffhand(inventory);
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
