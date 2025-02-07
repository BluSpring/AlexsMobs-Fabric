package com.github.alexthe666.alexsmobs.client.render.tile;

import com.github.alexthe666.alexsmobs.block.BlockTransmutationTable;
import com.github.alexthe666.alexsmobs.client.model.ModelTransmutationTable;
import com.github.alexthe666.alexsmobs.client.render.AMRenderTypes;
import com.github.alexthe666.alexsmobs.tileentity.TileEntityTransmutationTable;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexMultiConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class RenderTransmutationTable<T extends TileEntityTransmutationTable> implements BlockEntityRenderer<T> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("alexsmobs:textures/entity/farseer/transmutation_table.png");
    private static final ResourceLocation OVERLAY = new ResourceLocation("alexsmobs:textures/entity/farseer/transmutation_table_overlay.png");
    private static final ResourceLocation GLOW_TEXTURE = new ResourceLocation("alexsmobs:textures/entity/farseer/transmutation_table_glow.png");
    private static ModelTransmutationTable MODEL = new ModelTransmutationTable(0F);
    private static ModelTransmutationTable OVERLAY_MODEL = new ModelTransmutationTable(0.01F);

    public RenderTransmutationTable(BlockEntityRendererProvider.Context rendererDispatcherIn) {
    }

    @Override
    public void render(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrixStackIn.pushPose();
        Direction dir = tileEntityIn.getBlockState().getValue(BlockTransmutationTable.FACING);
        if(dir == Direction.NORTH){
            matrixStackIn.translate(0.5, 1.5F, 0.5F);
        }else if(dir == Direction.EAST){
            matrixStackIn.translate(0.5F, 1.5F, 0.5F);
        }else if(dir == Direction.SOUTH){
            matrixStackIn.translate(0.5, 1.5F, 0.5F);
        }else if(dir == Direction.WEST){
            matrixStackIn.translate(0.5F, 1.5F, 0.5F);
        }
        float ageInTicks = partialTicks + tileEntityIn.ticksExisted;
        
        matrixStackIn.mulPose(dir.getOpposite().getRotation());
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        matrixStackIn.pushPose();
        MODEL.animate(tileEntityIn, partialTicks);
        MODEL.renderToBuffer(matrixStackIn, bufferIn.getBuffer(RenderType.entityTranslucent(TEXTURE)), combinedLightIn, combinedOverlayIn, 1, 1, 1, 1);
        MODEL.renderToBuffer(matrixStackIn, bufferIn.getBuffer(AMRenderTypes.getEyesAlphaEnabled(GLOW_TEXTURE)), 240, combinedOverlayIn, 1, 1, 1, 0.5F + (float)Math.sin(ageInTicks * 0.05F) * 0.25F);
        VertexConsumer staticyOverlay = VertexMultiConsumer.create(bufferIn.getBuffer(AMRenderTypes.STATIC_PORTAL), bufferIn.getBuffer(RenderType.entityCutoutNoCull(OVERLAY)));
        OVERLAY_MODEL.animate(tileEntityIn, partialTicks);
        OVERLAY_MODEL.renderToBuffer(matrixStackIn, staticyOverlay, combinedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        matrixStackIn.popPose();
        matrixStackIn.popPose();
    }


    private static void vertex(VertexConsumer p_114090_, Matrix4f p_114091_, Matrix3f p_114092_, int p_114093_, float p_114094_, float p_114095_, int p_114096_, int p_114097_) {
        p_114090_.vertex(p_114091_, p_114094_, p_114095_, 0.0F).color(255, 255, 255, 100).uv((float) p_114096_, (float) p_114097_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_114093_).normal(p_114092_, 0.0F, 1.0F, 0.0F).endVertex();
    }

}
