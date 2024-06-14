package net.creeperhost.equivalentexchange.containers;

import net.creeperhost.equivalentexchange.blockentities.BlockEntityAlchemicalChest;
import net.creeperhost.equivalentexchange.init.ModContainers;
import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.DataManagerContainer;
import net.creeperhost.polylib.containers.ModularGuiContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.creeperhost.polylib.data.DataManagerBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class ContainerAlchemicalChest extends ModularGuiContainerMenu implements DataManagerContainer
{
    public final BlockEntityAlchemicalChest blockEntity;
    public final SlotGroup main = createSlotGroup(0, 1, 3); //zone id is 0, Quick move to zone 1, then 3
    public final SlotGroup hotBar = createSlotGroup(0, 1, 3);
    public final SlotGroup armor = createSlotGroup(1, 3, 0); //zone id is 1, Quick move to zone 3, then 0
    public final SlotGroup offhand = createSlotGroup(2, 3, 0);

    public final SlotGroup chest = createSlotGroup(3, 1, 0, 2);//zone id is 3, Quick move to zone 1, then 0, then 2


    public ContainerAlchemicalChest(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv, (BlockEntityAlchemicalChest) Minecraft.getInstance().level.getBlockEntity(extraData.readBlockPos()));
    }

    public ContainerAlchemicalChest(int id, Inventory playerInv, BlockEntityAlchemicalChest blockEntityAlchemicalChest)
    {
        super(ModContainers.ALCHEMICAL_CHEST_CONTAINER.get(), id, playerInv);
        this.blockEntity = blockEntityAlchemicalChest;

        main.addPlayerMain(inventory);
        hotBar.addPlayerBar(inventory);

        armor.addPlayerArmor(inventory);
        offhand.addPlayerOffhand(inventory);

        chest.addSlots(104, 0, index -> new PolySlot(blockEntity.getContainer(Direction.UP), index));
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
