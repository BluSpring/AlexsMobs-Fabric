package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelCrocodile;
import com.github.alexthe666.alexsmobs.entity.EntityCrocodile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class RenderCrocodile extends MobRenderer<EntityCrocodile, ModelCrocodile> {
    private static final Identifier TEXTURE_0 = Identifier.parse("alexsmobs:textures/entity/crocodile_0.png");
    private static final Identifier TEXTURE_1 = Identifier.parse("alexsmobs:textures/entity/crocodile_1.png");
    private static final Identifier TEXTURE_CROWN = Identifier.parse("alexsmobs:textures/entity/crocodile_crown.png");

    public RenderCrocodile(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelCrocodile(), 0.8F);
        this.addLayer(new CrownLayer(this));
    }

    @Override
    protected void scale(EntityCrocodile entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.9F, 0.9F, 0.9F);
    }


    @Override
    public Identifier getTextureLocation(EntityCrocodile entity) {
        return entity.isDesert() ? TEXTURE_1 : TEXTURE_0;
    }

    static class CrownLayer extends RenderLayer<EntityCrocodile, ModelCrocodile> {

        public CrownLayer(RenderCrocodile p_i50928_1_) {
            super(p_i50928_1_);
        }

        @Override
        public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, EntityCrocodile entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if (entitylivingbaseIn.isCrowned()) {
                VertexConsumer shoeBuffer = bufferIn.getBuffer(AMRenderTypes.entityCutoutNoCull(TEXTURE_CROWN));
                matrixStackIn.pushPose();
                this.getParentModel().renderToBuffer(matrixStackIn, shoeBuffer, packedLightIn, OverlayTexture.NO_OVERLAY, -1);
                matrixStackIn.popPose();
            }
        }
    }


}
