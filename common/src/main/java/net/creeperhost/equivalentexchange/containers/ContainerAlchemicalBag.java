package net.creeperhost.equivalentexchange.containers;

import net.creeperhost.equivalentexchange.impl.AlchemicalBagHandler;
import net.creeperhost.equivalentexchange.init.ModContainers;
import net.creeperhost.equivalentexchange.inventory.AlchemicalBagInventory;
import net.creeperhost.equivalentexchange.items.ItemAlchemyBag;
import net.creeperhost.equivalentexchange.network.packets.AlchemicalBagPacket;
import net.creeperhost.equivalentexchange.types.BagTypes;
import net.creeperhost.polylib.client.modulargui.lib.container.SlotGroup;
import net.creeperhost.polylib.containers.ModularGuiContainerMenu;
import net.creeperhost.polylib.containers.slots.PolySlot;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ContainerAlchemicalBag extends ModularGuiContainerMenu
{
    AlchemicalBagInventory alchemicalBagInventory;

    public final SlotGroup main = createSlotGroup(0, 1, 3); //zone id is 0, Quick move to zone 1, then 3
    public final SlotGroup hotBar = createSlotGroup(0, 1, 3);

    public final SlotGroup bag = createSlotGroup(3, 1, 0, 2);//zone id is 3, Quick move to zone 1, then 0, then 2


    public ContainerAlchemicalBag(int id, Inventory playerInv, FriendlyByteBuf extraData)
    {
        this(id, playerInv);
    }

    public ContainerAlchemicalBag(int id, Inventory playerInv)
    {
        super(ModContainers.ALCHEMICAL_BAG_CONTAINER.get(), id, playerInv);
        ItemStack bagStack = playerInv.player.getItemInHand(InteractionHand.MAIN_HAND);
        BagTypes bagType = null;

        if(bagStack.getItem() instanceof ItemAlchemyBag alchemyBag) bagType = alchemyBag.getBagType();

        if(playerInv.player instanceof ServerPlayer serverPlayer)
        {
            alchemicalBagInventory = AlchemicalBagHandler.getInventory(playerInv.player, bagType);
            new AlchemicalBagPacket(alchemicalBagInventory.serializeNBT(), bagType).sendTo(serverPlayer);
        }
        else
        {
            alchemicalBagInventory = AlchemicalBagHandler.getInventory(playerInv.player, bagType);
        }

        main.addPlayerMain(inventory);
        hotBar.addPlayerBar(inventory);

        bag.addSlots(104, 0, index -> new PolySlot(alchemicalBagInventory, index));
    }

    @Override
    public boolean stillValid(@NotNull Player player)
    {
        return true;
    }
}
