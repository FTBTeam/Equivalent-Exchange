package net.creeperhost.equivalentexchange.client.screens;

import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.api.IEmcScreen;
import net.creeperhost.equivalentexchange.client.EETextures;
import net.creeperhost.equivalentexchange.containers.ContainerCollector;
import net.creeperhost.equivalentexchange.containers.ContainerCondenser;
import net.creeperhost.equivalentexchange.types.CollectorType;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint.*;
import static net.creeperhost.polylib.client.modulargui.lib.geometry.Constraint.match;
import static net.creeperhost.polylib.client.modulargui.lib.geometry.GeoParam.*;
import static net.creeperhost.polylib.client.modulargui.lib.geometry.GeoParam.RIGHT;

public class ScreenCollector extends ContainerGuiProvider<ContainerCollector> implements DynamicTextures, IEmcScreen
{
    @Override
    public void makeTextures(Function<DynamicTexture, String> textures) {}

    @Override
    public GuiElement<?> createRootElement(ModularGui gui)
    {
        return PolyPalette.Flat.background(gui);
    }

    @Override
    public void buildGui(ModularGui modularGui, ContainerScreenAccess<ContainerCollector> screenAccess)
    {
        var menu = screenAccess.getMenu();
        var blockEntity = menu.blockEntity;

        modularGui.initStandardGui(180, 180);
        modularGui.setGuiTitle(Component.translatable("block.equivalentexchange.basic_collector"));

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


        GuiProgressIcon light = new GuiProgressIcon(background)
                .setBackground(EETextures.get("widgets/emc_bar_empty"))
                .setAnimated(EETextures.get("widgets/emc_bar_full"))
                .setProgress(() -> (menu.light.get() / 16D))
                .setTooltipSingle(() -> Component.literal(menu.light.get() + " / 16 Light Level"))
                .setTooltipDelay(0)
                .constrain(LEFT, relative(background.get(LEFT), 45))
                .constrain(TOP, relative(background.get(TOP), +40))
                .constrain(WIDTH, literal(90))
                .constrain(HEIGHT, literal(16));


        GuiProgressIcon progress = new GuiProgressIcon(background)
                .setBackground(EETextures.get("widgets/emc_bar_empty"))
                .setAnimated(EETextures.get("widgets/emc_bar_full"))
                .setProgress(() -> (menu.energy.get() / blockEntity.getMaxStored()))
                .setTooltipSingle(() -> Component.literal(menu.energy.get() + " / " + blockEntity.getMaxStored() + " EMC"))
                .setTooltipDelay(0)
                .constrain(LEFT, relative(background.get(LEFT), 45))
                .constrain(TOP, relative(background.get(TOP), +60))
                .constrain(WIDTH, literal(90))
                .constrain(HEIGHT, literal(16));


        GuiText emc = new GuiText(root, () -> Component.literal(menu.energy.get() + " / " + blockEntity.getMaxStored() + " EMC"))
                .constrain(TOP, relative(progress.get(TOP), 25))
                .constrain(HEIGHT, Constraint.literal(2))
                .constrain(LEFT, match(root.get(LEFT)))
                .constrain(RIGHT, match(root.get(RIGHT)));
    }

    public static ModularGuiContainer<ContainerCollector> create(ContainerCollector menu, Inventory inventory, Component component)
    {
        return new ModularGuiContainer<>(menu, inventory, new ScreenCollector());
    }
}
