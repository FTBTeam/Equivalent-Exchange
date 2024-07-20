package net.creeperhost.equivalentexchange.client.renders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.creeperhost.equivalentexchange.items.ItemPhilosophersStone;
import net.creeperhost.equivalentexchange.items.interfaces.IOverlayItem;
import net.creeperhost.equivalentexchange.items.tools.ItemDestructionCatalyst;
import net.creeperhost.polylib.helpers.VectorHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BlockOverlayRender
{
    public static void render(PoseStack poseStack, ItemStack item)
    {
        final Minecraft mc = Minecraft.getInstance();
        if(mc.player == null) return;
        if(mc.level == null) return;

        MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
        int hitRange = 10;
        BlockHitResult lookingAt = VectorHelper.getLookingAt(mc.player, item.getItem() instanceof ItemPhilosophersStone ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE, hitRange);
        if(item.getItem() instanceof IOverlayItem iOverlayItem)
        {
            if(iOverlayItem.blockIgnored(mc.level.getBlockState(lookingAt.getBlockPos()))) return;
        }
        else if(mc.level.getBlockState(lookingAt.getBlockPos()) == Blocks.AIR.defaultBlockState())
        {
            return;
        }

        int range = 1;
        Color color = Color.WHITE;
        if(item.getItem() instanceof IOverlayItem iOverlayItem)
        {
            range = iOverlayItem.getRange(item);
            color = iOverlayItem.getColour(item);
        }

        Map<BlockPos, BlockState> coords = new HashMap<>();
        //Special case the philo stone as it only renders its overlay over blocks that can be transmuted
        if(item.getItem() instanceof ItemPhilosophersStone)
        {
            coords = ItemPhilosophersStone.getChanges(mc.level, lookingAt.getBlockPos(), mc.player, lookingAt.getDirection(), range);
        }
        else if(item.getItem() instanceof ItemDestructionCatalyst)
        {
            //TODO
            coords = ItemDestructionCatalyst.getChanges(mc.level, lookingAt.getBlockPos(), mc.player, lookingAt.getDirection(), range);
        }

        Vec3 view = mc.gameRenderer.getMainCamera().getPosition();

        poseStack.pushPose();
        poseStack.translate(-view.x(), -view.y(), -view.z());

        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);

        VertexConsumer builder = buffer.getBuffer(RenderTypes.BlockOverlay);

        Color finalColor = color;
        coords.forEach((blockPos, blockState) ->
        {
            poseStack.pushPose();
            poseStack.translate(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            poseStack.translate(-0.005f, -0.005f, -0.005f);
            poseStack.scale(1.01f, 1.01f, 1.01f);
            poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));

            Matrix4f positionMatrix = poseStack.last().pose();
            BlockOverlayRender.render(positionMatrix, builder, blockPos, finalColor);
            poseStack.popPose();
        });

        poseStack.popPose();
        buffer.endBatch(RenderTypes.BlockOverlay);

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }

    public static void render(Matrix4f matrix, VertexConsumer builder, BlockPos pos, Color color)
    {
        float red = color.getRed() / 255f, green = color.getGreen() / 255f, blue = color.getBlue() / 255f, alpha = .125f;

        float startX = 0, startY = 0, startZ = -1, endX = 1, endY = 1, endZ = 0;

        //down
        builder.vertex(matrix, startX, startY, startZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, endX, startY, startZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, endX, startY, endZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, startX, startY, endZ).color(red, green, blue, alpha).endVertex();

        //up
        builder.vertex(matrix, startX, endY, startZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, startX, endY, endZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, endX, endY, endZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, endX, endY, startZ).color(red, green, blue, alpha).endVertex();

        //east
        builder.vertex(matrix, startX, startY, startZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, startX, endY, startZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, endX, endY, startZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, endX, startY, startZ).color(red, green, blue, alpha).endVertex();

        //west
        builder.vertex(matrix, startX, startY, endZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, endX, startY, endZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, endX, endY, endZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, startX, endY, endZ).color(red, green, blue, alpha).endVertex();

        //south
        builder.vertex(matrix, endX, startY, startZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, endX, endY, startZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, endX, endY, endZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, endX, startY, endZ).color(red, green, blue, alpha).endVertex();

        //north
        builder.vertex(matrix, startX, startY, startZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, startX, startY, endZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, startX, endY, endZ).color(red, green, blue, alpha).endVertex();
        builder.vertex(matrix, startX, endY, startZ).color(red, green, blue, alpha).endVertex();
    }
}
