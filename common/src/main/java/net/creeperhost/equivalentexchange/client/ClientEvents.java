package net.creeperhost.equivalentexchange.client;

import dev.architectury.event.EventResult;
import dev.architectury.platform.Platform;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.EquivalentExchange;
import net.creeperhost.equivalentexchange.EquivalentExchangeClient;
import net.creeperhost.equivalentexchange.api.EmcFormatter;
import net.creeperhost.equivalentexchange.api.EquivalentExchangeAPI;
import net.creeperhost.equivalentexchange.api.IEmcScreen;
import net.creeperhost.equivalentexchange.api.recipe.InWorldTransmutation;
import net.creeperhost.equivalentexchange.client.renders.EntitySpriteRender;
import net.creeperhost.equivalentexchange.client.renders.PedestalRender;
import net.creeperhost.equivalentexchange.init.ModBlocks;
import net.creeperhost.equivalentexchange.init.ModEntities;
import net.creeperhost.equivalentexchange.init.ModScreens;
import net.creeperhost.equivalentexchange.init.ModSounds;
import net.creeperhost.equivalentexchange.items.ItemPhilosophersStone;
import net.creeperhost.equivalentexchange.items.interfaces.IActionItem;
import net.creeperhost.equivalentexchange.items.interfaces.IChargeableItem;
import net.creeperhost.equivalentexchange.network.packets.ActionKeyPacket;
import net.creeperhost.equivalentexchange.network.packets.ChargeKeyPacket;
import net.creeperhost.polylib.helpers.VectorHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

import org.jetbrains.annotations.Nullable;

public class ClientEvents
{
    public static void toolTipEvent(ItemStack stack, List<Component> components, TooltipFlag tooltipFlag)
    {
        if(!EquivalentExchange.CONFIG_DATA.DisableEmcTooltip)
        {
            calculate(stack, components);
        }
        else
        {
            Screen screen = Minecraft.getInstance().screen;
            if(screen != null && screen instanceof IEmcScreen)
            {
                calculate(stack, components);
            }
        }
    }

    private static void calculate(ItemStack stack, List<Component> components)
    {
        if (stack != null && !stack.isEmpty() && EquivalentExchangeAPI.hasEmcValue(stack))
        {
            double emcValue = EquivalentExchangeAPI.getEmcValue(stack);
            components.add(buildEmcTooltip(emcValue));
            if (stack.getCount() > 1)
            {
                components.add(buildEmcTooltip(emcValue * stack.getCount()));
            }
        }
    }

    private static Component buildEmcTooltip(double emcValue)
    {
        return Component.literal(ChatFormatting.DARK_PURPLE + "EMC" + ChatFormatting.WHITE + ": " + EmcFormatter.formatEmcValue(emcValue));
    }

    public static EventResult keyPressed(Minecraft minecraft, int keyCode, int scanCode, int action, int modifier)
    {
        boolean shiftDown = modifier == 1;
        if(minecraft.screen == null && minecraft.player != null && minecraft.level != null)
        {
            Player player = minecraft.player;
            Level level = minecraft.level;
            ItemStack stack = minecraft.player.getMainHandItem();
            if(EEKeyBindings.ACTION_KEY.isDown() && !stack.isEmpty() && stack.getItem() instanceof IActionItem)
            {
                new ActionKeyPacket().sendToServer();
            }
            else if(EEKeyBindings.CHARGE_KEY.isDown() && !stack.isEmpty() && stack.getItem() instanceof IChargeableItem)
            {
                new ChargeKeyPacket(shiftDown).sendToServer();
                if(shiftDown)
                {
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), ModSounds.UNCHARGE.get(), SoundSource.PLAYERS, 1, 1);
                } else
                {
                    level.playSound(player, player.getX(), player.getY(), player.getZ(), ModSounds.CHARGE.get(), SoundSource.PLAYERS, 1, 1);
                }
            }
        }
        return EventResult.pass();
    }

    public static void onHudRender(GuiGraphics guiGraphics, float v)
    {
        Minecraft minecraft = Minecraft.getInstance();
        if(minecraft.player == null) return;
        if(minecraft.level == null) return;

        if(!minecraft.options.hideGui)
        {
            ItemStack stack = minecraft.player.getMainHandItem();
            if(stack != null && !stack.isEmpty() && stack.getItem() instanceof ItemPhilosophersStone stone)
            {
                int range = 5;
                BlockHitResult lookingAt = VectorHelper.getLookingAt(minecraft.player, ClipContext.Fluid.NONE, range);
                BlockPos blockPos = lookingAt.getBlockPos();
                if(minecraft.level.getBlockState(blockPos) != null && !minecraft.level.getBlockState(blockPos).isAir())
                {
                    BlockState inputState = minecraft.level.getBlockState(blockPos);
                    ItemStack renderStack = ItemStack.EMPTY;

                    for (InWorldTransmutation inWorldTransmutation : EquivalentExchangeAPI.IN_WORLD_TRANSMUTATION_RECIPES)
                    {
                        if(inWorldTransmutation.getInput() != null && inputState.equals(inWorldTransmutation.getInput()))
                        {
                            @Nullable BlockState out = minecraft.player.isShiftKeyDown() ? inWorldTransmutation.getAltResult() : inWorldTransmutation.getResult();
                            if (out == null) continue;
                            renderStack = new ItemStack(out.getBlock());
                            break;
                        }
                    }
                    if(!renderStack.isEmpty())
                    {
                        int x = 5;
                        int y = 5;
                        guiGraphics.pose().pushPose();
                        guiGraphics.pose().translate(x, y, 0);
                        guiGraphics.renderItem(renderStack, x, y);
                        guiGraphics.pose().popPose();
                    }
                }
            }
        }
    }

    public static void onClientSetup(Minecraft minecraft)
    {
        ModScreens.init();
        EquivalentExchangeClient.registerItemProperties();

        BlockEntityRendererRegistry.register(ModBlocks.PEDESTAL_TILE.get(), context -> new PedestalRender());

        if(Platform.isFabric()) {
            EntityRendererRegistry.register(ModEntities.VOLCANITE_PROJECTILE, context -> new EntitySpriteRender<>(context, new ResourceLocation(Constants.MOD_ID, "textures/entity/volcanite_orb.png")));
            EntityRendererRegistry.register(ModEntities.EVERTIDE_PROJECTILE, context -> new EntitySpriteRender<>(context, new ResourceLocation(Constants.MOD_ID, "textures/entity/evertide_orb.png")));
        }
    }
}
