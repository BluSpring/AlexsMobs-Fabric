package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelDropBear;
import com.github.alexthe666.alexsmobs.entity.EntityDropBear;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

public class RenderDropBear extends MobRenderer<EntityDropBear, ModelDropBear> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/dropbear.png");
    private static final Identifier TEXTURE_EYES = Identifier.parse("alexsmobs:textures/entity/dropbear_eyes.png");

    public RenderDropBear(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelDropBear(), 0.7F);
        this.addLayer(new EyeLayer(this));
    }

    @Override
    protected void scale(EntityDropBear entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
    }


    @Override
    public Identifier getTextureLocation(EntityDropBear entity) {
        return TEXTURE;
    }

    static class EyeLayer extends RenderLayer<EntityDropBear, ModelDropBear> {

        public EyeLayer(RenderDropBear render) {
            super(render);
        }

        @Override
        public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, EntityDropBear entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.eyes(TEXTURE_EYES));
            this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0.0F), -1);

        }
    }
}