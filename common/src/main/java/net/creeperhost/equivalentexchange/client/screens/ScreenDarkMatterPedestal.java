package net.creeperhost.equivalentexchange.client.screens;

import net.creeperhost.equivalentexchange.api.IEmcScreen;
import net.creeperhost.equivalentexchange.client.EETextures;
import net.creeperhost.equivalentexchange.containers.ContainerDarkMatterPedestal;
import net.creeperhost.polylib.client.PolyPalette;
import net.creeperhost.polylib.client.modulargui.ModularGui;
import net.creeperhost.polylib.client.modulargui.ModularGuiContainer;
import net.creeperhost.polylib.client.modulargui.elements.*;
import net.creeperhost.polylib.client.modulargui.lib.Constraints;
import net.creeperhost.polylib.client.modulargui.lib.DynamicTextures;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerGuiProvider;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerScreenAccess;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint;
import net.creeperhost.polylib.client.modulargui.sprite.PolyTextures;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.function.Function;

import static net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint.*;
import static net.creeperhost.polylib.client.modulargui.lib.geometry.GeoParam.*;

public class ScreenDarkMatterPedestal extends ContainerGuiProvider<ContainerDarkMatterPedestal> implements DynamicTextures, IEmcScreen
{
    @Override
    public void makeTextures(Function<DynamicTexture, String> textures) {}

    @Override
    public GuiElement<?> createRootElement(ModularGui gui)
    {
        return PolyPalette.Flat.background(gui);
    }

    @Override
    public void buildGui(ModularGui modularGui, ContainerScreenAccess<ContainerDarkMatterPedestal> screenAccess)
    {
        var menu = screenAccess.getMenu();
        var blockEntity = menu.blockEntity;

        modularGui.initStandardGui(180, 180);
        modularGui.setGuiTitle(Component.translatable("block.equivalentexchange.dm_pedestal"));

        GuiElement<?> root = modularGui.getRoot();
        GuiTexture background = new GuiTexture(root, PolyTextures.get("dynamic/gui_vanilla"));
        Constraints.bind(background, root);

        GuiText title = new GuiText(background, modularGui.getGuiTitle())
                .setTextColour(0x404040)
                .setShadow(false)
                .constrain(TOP, relative(background.get(TOP), 10))
                .constrain(HEIGHT, Constraint.literal(8))
                .constrain(LEFT, relative(background.get(LEFT), 5))
                .constrain(RIGHT, relative(background.get(RIGHT), -5));

        var inventory = GuiSlots.player(background, screenAccess, menu.main, menu.hotBar);
        inventory.container
                .constrain(WIDTH, null)
                .constrain(LEFT, match(background.get(LEFT)))
                .constrain(RIGHT, match(background.get(RIGHT)))
                .constrain(BOTTOM, relative(background.get(BOTTOM), -10));

        GuiSlots targetSlot = new GuiSlots(background, screenAccess, menu.target, 1)
                .constrain(LEFT, midPoint(background.get(LEFT), background.get(RIGHT), -8))
                .setEmptyIcon(slot -> EETextures.get("slots/target"))
                .constrain(BOTTOM, relative(title.get(BOTTOM), 30));

        GuiSlots klienStarSlot = new GuiSlots(background, screenAccess, menu.star, 1)
                .constrain(LEFT, midPoint(background.get(LEFT), background.get(RIGHT), -8))
                .constrain(BOTTOM, relative(title.get(BOTTOM), 50));
    }

    public static ModularGuiContainer<ContainerDarkMatterPedestal> create(ContainerDarkMatterPedestal menu, Inventory inventory, Component component)
    {
        return new ModularGuiContainer<>(menu, inventory, new ScreenDarkMatterPedestal());
    }
}
