package net.creeperhost.equivalentexchange.client.screens;

import net.creeperhost.equivalentexchange.api.IEmcScreen;
import net.creeperhost.equivalentexchange.client.EETextures;
import net.creeperhost.equivalentexchange.containers.ContainerCondenser;
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

public class ScreenCondenser extends ContainerGuiProvider<ContainerCondenser> implements DynamicTextures, IEmcScreen
{
    @Override
    public void makeTextures(Function<DynamicTexture, String> textures) {}

    @Override
    public GuiElement<?> createRootElement(ModularGui gui)
    {
        return PolyPalette.Flat.background(gui);
    }

    @Override
    public void buildGui(ModularGui modularGui, ContainerScreenAccess<ContainerCondenser> screenAccess)
    {
        var menu = screenAccess.getMenu();
        var blockEntity = menu.blockEntity;

        modularGui.initStandardGui(260, 250);
        modularGui.setGuiTitle(Component.translatable("block.equivalentexchange.condenser"));

        GuiElement<?> root = modularGui.getRoot();
        GuiTexture background = new GuiTexture(root, PolyTextures.get("dynamic/gui_vanilla"));
        Constraints.bind(background, root);

        var inventory = GuiSlots.player(background, screenAccess, menu.main, menu.hotBar);
        inventory.container
                .constrain(WIDTH, null)
                .constrain(LEFT, match(background.get(LEFT)))
                .constrain(RIGHT, match(background.get(RIGHT)))
                .constrain(BOTTOM, relative(background.get(BOTTOM), -10));

        int inputSpacing = 0;
        GuiSlots chestSlots = new GuiSlots(background, screenAccess, menu.chest, 13)
                .setXSlotSpacing(inputSpacing)
                .constrain(LEFT, relative(background.get(LEFT), 12))
                .constrain(BOTTOM, relative(inventory.container.get(TOP), -3));

        GuiSlots targetSlots = new GuiSlots(background, screenAccess, menu.target, 1)
                .constrain(LEFT, relative(background.get(LEFT), 12))
                .setEmptyIcon(slot -> EETextures.get("slots/target"))
                .constrain(BOTTOM, relative(chestSlots.get(TOP), -3));


        GuiProgressIcon progress = new GuiProgressIcon(background)
                .setBackground(EETextures.get("widgets/emc_bar_empty"))
                .setAnimated(EETextures.get("widgets/emc_bar_full"))
                .setProgress(() -> (menu.stored.get() / menu.targetdata.get()))
                .setTooltipSingle(() -> Component.literal(menu.stored.get() + " / " + menu.targetdata.get() + " EMC"))
                .setTooltipDelay(0)
                .constrain(LEFT, relative(background.get(LEFT), 32))
                .constrain(BOTTOM, relative(chestSlots.get(TOP), -4))
                .constrain(WIDTH, literal(90))
                .constrain(HEIGHT, literal(16));

        GuiText emc = new GuiText(root, () -> Component.literal(menu.stored.get() + " EMC"))
                .constrain(TOP, midPoint(targetSlots.get(TOP), targetSlots.get(BOTTOM)))
                .constrain(HEIGHT, Constraint.literal(1))
                .constrain(LEFT, relative(progress.get(LEFT), 20))
                .constrain(WIDTH, match(root.get(RIGHT)));
    }

    public static ModularGuiContainer<ContainerCondenser> create(ContainerCondenser menu, Inventory inventory, Component component)
    {
        return new ModularGuiContainer<>(menu, inventory, new ScreenCondenser());
    }
}
