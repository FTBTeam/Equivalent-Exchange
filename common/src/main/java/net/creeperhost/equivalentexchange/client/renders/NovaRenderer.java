package net.creeperhost.equivalentexchange.client.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class NovaRenderer<T extends PrimedTnt> extends EntityRenderer<T>
{
    private final Supplier<BlockState> supplier;
    private final BlockRenderDispatcher blockRenderer;


    public NovaRenderer(EntityRendererProvider.Context context, Supplier<BlockState> stateSupplier)
    {
        super(context);
        this.supplier = stateSupplier;
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(T entity, float f, float partialTick, PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int light)
    {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.5D, 0.0D);
        int fuse = entity.getFuse();
        if ((float) fuse - partialTick + 1.0F < 10.0F) {
            float h = 1.0F - ((float) fuse - partialTick + 1.0F) / 10.0F;
            h = Mth.clamp(h, 0.0F, 1.0F);
            h *= h;
            h *= h;
            float f1 = 1.0F + h * 0.3F;
            poseStack.scale(f1, f1, f1);
        }

        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.translate(-0.5D, -0.5D, 0.5D);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, Blocks.TNT.defaultBlockState(), poseStack, multiBufferSource, light, fuse / 5 % 2 == 0);

        TntMinecartRenderer.renderWhiteSolidBlock(this.blockRenderer, supplier.get(), poseStack, multiBufferSource, light, fuse / 5 % 2 == 0);
        poseStack.popPose();
        super.render(entity, f, partialTick, poseStack, multiBufferSource, light);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull T entity)
    {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
