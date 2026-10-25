package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelGiantSquid;
import com.github.alexthe666.alexsmobs.entity.EntityGiantSquid;
import com.github.alexthe666.alexsmobs.entity.EntityGiantSquidPart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

public class RenderGiantSquid extends MobRenderer<EntityGiantSquid, ModelGiantSquid> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/giant_squid.png");
    private static final Identifier TEXTURE_BLUE = Identifier
            .parse("alexsmobs:textures/entity/giant_squid_blue.png");
    private static final Identifier TEXTURE_DEPRESSURIZED = Identifier
            .parse("alexsmobs:textures/entity/giant_squid_depressurized.png");

    public RenderGiantSquid(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelGiantSquid(), 1F);
        this.addLayer(new LayerDepressurization(this));
    }

    @Override
    protected float getFlipDegrees(EntityGiantSquid squid) {
        return 0.0F;
    }

    @Override
    public boolean shouldRender(EntityGiantSquid livingEntityIn, Frustum camera, double camX, double camY,
                                double camZ) {
        if (livingEntityIn.isCaptured() && livingEntityIn.isAlive()) {
            return false;
        }
        if (super.shouldRender(livingEntityIn, camera, camX, camY, camZ)) {
            return true;
        } else {
            for (EntityGiantSquidPart part : livingEntityIn.allParts) {
                if (camera.isVisible(part.getBoundingBox())) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    protected void scale(EntityGiantSquid entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
    }

    @Override
    public Identifier getTextureLocation(EntityGiantSquid entity) {
        return entity.isBlue() ? TEXTURE_BLUE : TEXTURE;
    }

    static class LayerDepressurization extends RenderLayer<EntityGiantSquid, ModelGiantSquid> {

        public LayerDepressurization(RenderGiantSquid render) {
            super(render);
        }

        @Override
        public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn,
                           EntityGiantSquid squid, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                           float netHeadYaw, float headPitch) {
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityTranslucent(TEXTURE_DEPRESSURIZED));
            float alpha = squid.prevDepressurization
                    + (squid.getDepressurization() - squid.prevDepressurization) * partialTicks;
            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn,
                    LivingEntityRenderer.getOverlayCoords(squid, 0.0F), AMColorUtil.packColor(1.0F, 1.0F, 1.0F, alpha));
        }
    }
}
