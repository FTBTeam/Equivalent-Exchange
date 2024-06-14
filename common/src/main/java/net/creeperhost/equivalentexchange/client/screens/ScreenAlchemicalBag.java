package net.creeperhost.equivalentexchange.client.screens;

import net.creeperhost.equivalentexchange.containers.ContainerAlchemicalBag;
import net.creeperhost.polylib.client.PolyPalette;
import net.creeperhost.polylib.client.modulargui.ModularGui;
import net.creeperhost.polylib.client.modulargui.ModularGuiContainer;
import net.creeperhost.polylib.client.modulargui.elements.GuiElement;
import net.creeperhost.polylib.client.modulargui.elements.GuiSlots;
import net.creeperhost.polylib.client.modulargui.elements.GuiText;
import net.creeperhost.polylib.client.modulargui.elements.GuiTexture;
import net.creeperhost.polylib.client.modulargui.lib.Constraints;
import net.creeperhost.polylib.client.modulargui.lib.DynamicTextures;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerGuiProvider;
import net.creeperhost.polylib.client.modulargui.lib.container.ContainerScreenAccess;
import net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint;
import net.creeperhost.polylib.client.modulargui.sprite.PolyTextures;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import java.util.function.Function;

import static net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint.match;
import static net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint.relative;
import static net.creeperhost.polylib.client.modulargui.lib.geometry.GeoParam.*;
import static net.creeperhost.polylib.client.modulargui.lib.geometry.GeoParam.TOP;

public class ScreenAlchemicalBag extends ContainerGuiProvider<ContainerAlchemicalBag> implements DynamicTextures
{
    @Override
    public void makeTextures(Function<DynamicTexture, String> textures) {}

    @Override
    public GuiElement<?> createRootElement(ModularGui gui)
    {
        return PolyPalette.Flat.background(gui);
    }

    @Override
    public void buildGui(ModularGui modularGui, ContainerScreenAccess<ContainerAlchemicalBag> screenAccess)
    {
        var menu = screenAccess.getMenu();
        modularGui.initStandardGui(260, 250);
        modularGui.setGuiTitle(Component.translatable("item.equivalentexchange.alchemical_bag"));

        GuiElement<?> root = modularGui.getRoot();
        GuiTexture background = new GuiTexture(root, PolyTextures.get("dynamic/gui_vanilla"));
        Constraints.bind(background, root);

        GuiText title = new GuiText(root, modularGui.getGuiTitle())
                .constrain(TOP, relative(root.get(TOP), 8))
                .constrain(HEIGHT, Constraint.literal(8))
                .constrain(LEFT, match(root.get(LEFT)))
                .constrain(RIGHT, match(root.get(RIGHT)));

        var inventory = GuiSlots.player(background, screenAccess, menu.main, menu.hotBar);
        inventory.container
                .constrain(WIDTH, null)
                .constrain(LEFT, match(background.get(LEFT)))
                .constrain(RIGHT, match(background.get(RIGHT)))
                .constrain(BOTTOM, relative(background.get(BOTTOM), -10));


        int inputSpacing = 0;
        GuiSlots chestSlots = new GuiSlots(background, screenAccess, menu.bag, 13)
                .setXSlotSpacing(inputSpacing)
                .constrain(LEFT, relative(background.get(LEFT), 12))
                .constrain(BOTTOM, relative(inventory.container.get(TOP), -3));
    }

    public static ModularGuiContainer<ContainerAlchemicalBag> create(ContainerAlchemicalBag menu, Inventory inventory, Component component)
    {
        return new ModularGuiContainer<>(menu, inventory, new ScreenAlchemicalBag());
    }
}
