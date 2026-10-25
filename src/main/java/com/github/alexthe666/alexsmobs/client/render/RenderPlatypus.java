package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelPlatypus;
import com.github.alexthe666.alexsmobs.entity.EntityPlatypus;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

public class RenderPlatypus extends MobRenderer<EntityPlatypus, ModelPlatypus> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/platypus.png");
    private static final Identifier TEXTURE_PERRY = Identifier.parse("alexsmobs:textures/entity/platypus_perry.png");

    public RenderPlatypus(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelPlatypus(), 0.45F);
        this.addLayer(new FedoraLayer(this));
    }

    @Override
    protected void scale(EntityPlatypus entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
         matrixStackIn.scale(0.9F, 0.9F, 0.9F);
    }

    @Override
    public Identifier getTextureLocation(EntityPlatypus entity) {
        return entity.isPerry() ? TEXTURE_PERRY : TEXTURE;
    }

    static class FedoraLayer extends RenderLayer<EntityPlatypus, ModelPlatypus> {
        private final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/platypus_fedora.png");

        public FedoraLayer(RenderPlatypus renderGrizzlyBear) {
            super(renderGrizzlyBear);
        }

        @Override
        public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, EntityPlatypus entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if(entitylivingbaseIn.hasFedora()){
                VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutout(TEXTURE));
                this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0.0F), -1);
            }
        }
    }
}
