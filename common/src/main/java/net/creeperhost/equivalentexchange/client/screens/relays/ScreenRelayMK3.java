package net.creeperhost.equivalentexchange.client.screens.relays;

import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.api.IEmcScreen;
import net.creeperhost.equivalentexchange.containers.relays.ContainerRelayMK3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class ScreenRelayMK3 extends AbstractContainerScreen<ContainerRelayMK3> implements IEmcScreen
{
    public ScreenRelayMK3(ContainerRelayMK3 containerRelay, Inventory inventory, Component component)
    {
        super(containerRelay, inventory, component);
        this.imageWidth = 212;
        this.imageHeight = 194;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY)
    {
        int i = (width - imageWidth) / 2;
        int j = (height - imageHeight) / 2;

        //TODO clean this up
        ResourceLocation texture = new ResourceLocation(Constants.MOD_ID, "textures/gui/relay3.png");
        int emcShift = 105;
        int v = 195;

        guiGraphics.blit(texture, i, j, 0, 0, imageWidth, imageHeight);

        int progress = (int) ((double) menu.getStoredEMC() / menu.getMaxEMC() * 102);
        guiGraphics.blit(texture, leftPos + emcShift, topPos + 6, 30, v, progress, 10);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int i, int j) {}

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks)
    {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);

        if(isInRect(leftPos + 64, topPos + 4, 100, 14, mouseX, mouseY))
        {
            guiGraphics.renderTooltip(Minecraft.getInstance().font, Component.literal("EMC: " + getMenu().getStoredEMC() + " / " +  getMenu().getMaxEMC()), mouseX, mouseY);
        }
    }

    public boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY)
    {
        return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
    }
}
