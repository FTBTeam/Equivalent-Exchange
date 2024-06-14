package net.creeperhost.equivalentexchange.client.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.creeperhost.equivalentexchange.blockentities.BlockEntityPedestal;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PedestalRender implements BlockEntityRenderer<BlockEntityPedestal>
{
    @Override
    public void render(BlockEntityPedestal blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlayLight)
    {
        ItemStack stack = blockEntity.getContainer(Direction.UP).getItem(0);
        if(!stack.isEmpty())
        {
            poseStack.pushPose();
            poseStack.translate(0.5, 0.7, 0.5);
            long gameTime = blockEntity.getLevel().getGameTime();
            poseStack.translate(0, Mth.sin((gameTime + f) / 10.0F) * 0.1 + 0.1, 0);
            poseStack.scale(0.75F, 0.75F, 0.75F);
            float angle = (gameTime + f) / ((float) SharedConstants.TICKS_PER_SECOND) * Mth.RAD_TO_DEG;
            poseStack.mulPose(Axis.YP.rotationDegrees(angle));
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, light, overlayLight, poseStack, multiBufferSource, blockEntity.getLevel(), (int) blockEntity.getBlockPos().asLong());
            poseStack.popPose();
        }
    }
}
