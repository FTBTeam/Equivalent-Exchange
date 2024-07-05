package net.creeperhost.equivalentexchange.client.screens;

import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.api.EmcFormatter;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.IEmcScreen;
import net.creeperhost.equivalentexchange.containers.ContainerTransmutationTable;
import net.creeperhost.equivalentexchange.impl.TransmutationTableHandler;
import net.creeperhost.equivalentexchange.network.packets.UpdateTransmutationFilter;
import net.creeperhost.equivalentexchange.network.packets.UpdateTransmutationPage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.Locale;

public class ScreenTransmutationTable extends AbstractContainerScreen<ContainerTransmutationTable> implements IEmcScreen
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Constants.MOD_ID, "textures/gui/transmutation_table.png");
    private final Player player;
    private EditBox searchBox;
    private Button leftButton;
    private Button rightButton;
    private int page = 0;

    public ScreenTransmutationTable(ContainerTransmutationTable abstractContainerMenu, Inventory inventory, Component component)
    {
        super(abstractContainerMenu, inventory, component);
        this.player = inventory.player;
        this.imageWidth = 228;
        this.imageHeight = 196;
        this.page = 0;
    }

    @Override
    protected void init()
    {
        super.init();
        searchBox = new EditBox(this.font, leftPos + 88, topPos + 8, 45, 10, Component.literal(""));
        searchBox.setValue("");

        leftButton = Button.builder(Component.literal("<"), button ->
                changePage(this.page -1)).pos(leftPos + 135, topPos + 100).size(15, 15).build();

        rightButton = Button.builder(Component.literal(">"), button ->
                changePage(this.page +1)).pos(leftPos + 180, topPos + 100).size(15, 15).build();

        addRenderableWidget(leftButton);
        addRenderableWidget(rightButton);
    }

    public void changePage(int page)
    {
        if(page >= 0)
        {
            this.page = page;
            new UpdateTransmutationPage(page).sendToServer();
        }
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY)
    {
        int i = (width - imageWidth) / 2;
        int j = (height - imageHeight) / 2;
        guiGraphics.blit(GUI_TEXTURE, i, j, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int i, int j) {}

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.searchBox.render(guiGraphics, mouseX, mouseY, partialTicks);
        if(minecraft != null && minecraft.player != null)
        {
            guiGraphics.drawString(Minecraft.getInstance().font, Component.literal("EMC:"), leftPos + 6, topPos + 88, 0x404040, false);
            guiGraphics.drawString(Minecraft.getInstance().font, Component.literal(EmcFormatter.tidyValue(EquivalentExchangeAPI.getStorageHandler().getEmcValueFor(minecraft.player))), leftPos + 6, topPos + 100, 0x404040, false);

            guiGraphics.drawString(Minecraft.getInstance().font, Component.literal("" + page),
                    leftPos + 162, topPos + 106, 0x404040, false);

            renderTooltip(guiGraphics, mouseX, mouseY);

            if (isInRect(leftPos + 6, topPos + 100, 80, 10, mouseX, mouseY))
            {
                guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.literal("EMC: " + EmcFormatter.getFormatter().format(EquivalentExchangeAPI.getStorageHandler().getEmcValueFor(minecraft.player))), mouseX, mouseY);
            }
        }
    }

    public boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY)
    {
        return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if (searchBox.isFocused())
        {
            if (keyCode == GLFW.GLFW_KEY_ESCAPE)
            {
                searchBox.setFocused(false);
                return true;
            }
            if (searchBox.keyPressed(keyCode, scanCode, modifiers))
            {
                updateFilter();
                return true;
            }
            return false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char c, int keyCode)
    {
        if (searchBox.isFocused())
        {
            if (searchBox.charTyped(c, keyCode))
            {
                //If the filter reacted from to a character being typed, then something happened and we should update the filter
                updateFilter();
                return true;
            }
            return false;
        }
        return super.charTyped(c, keyCode);
    }

    @Override
    public boolean mouseClicked(double x, double y, int mouseButton)
    {
        if (searchBox.isMouseOver(x, y))
        {
            searchBox.setFocused(true);
            if (mouseButton == 1)
            {
                this.searchBox.setValue("");
                updateFilter();
            }
            return this.searchBox.mouseClicked(x, y, mouseButton);
        }
        return super.mouseClicked(x, y, mouseButton);
    }

    private void updateFilter()
    {
        String search = searchBox.getValue().toLowerCase(Locale.ROOT);
        TransmutationTableHandler.getTransmutationInventory(player).setFilter(search, true);
        new UpdateTransmutationFilter(search).sendToServer();
    }
}
