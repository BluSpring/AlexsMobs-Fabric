package com.github.alexthe666.alexsmobs.client.render;

import javax.annotation.Nullable;

import com.github.alexthe666.alexsmobs.client.model.ModelGuster;
import com.github.alexthe666.alexsmobs.entity.EntityGuster;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class RenderGuster extends MobRenderer<EntityGuster, ModelGuster> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/guster.png");
    private static final Identifier TEXTURE_GOOGLY = Identifier.parse("alexsmobs:textures/entity/guster_silly.png");
    private static final Identifier TEXTURE_EYES = Identifier.parse("alexsmobs:textures/entity/guster_eye.png");
    private static final Identifier TEXTURE_RED = Identifier.parse("alexsmobs:textures/entity/guster_red.png");
    private static final Identifier TEXTURE_SOUL = Identifier.parse("alexsmobs:textures/entity/guster_soul.png");
    private static final Identifier TEXTURE_SOUL_EYES = Identifier.parse("alexsmobs:textures/entity/guster_eye_soul.png");

    public RenderGuster(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelGuster(), 0.25F);
        this.addLayer(new GusterEyesLayer(this));
    }

    @Override
    @Nullable
    protected RenderType getRenderType(EntityGuster p_230496_1_, boolean p_230496_2_, boolean p_230496_3_, boolean p_230496_4_) {
        Identifier resourcelocation = this.getTextureLocation(p_230496_1_);
        if (p_230496_3_) {
            return RenderType.entityTranslucent(resourcelocation);
        } else if (p_230496_2_) {
            return RenderType.entityTranslucent(resourcelocation);
        } else {
            return p_230496_4_ ? RenderType.outline(resourcelocation) : null;
        }
    }


    @Override
    public Identifier getTextureLocation(EntityGuster entity) {
        return entity.isGooglyEyes() ? TEXTURE_GOOGLY : entity.getVariant() == 2 ? TEXTURE_SOUL : entity.getVariant() == 1 ? TEXTURE_RED : TEXTURE;
    }

    static class GusterEyesLayer extends EyesLayer<EntityGuster, ModelGuster> {

        public GusterEyesLayer(RenderGuster p_i50928_1_) {
            super(p_i50928_1_);
        }

        @Override
        public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, EntityGuster entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if(!entitylivingbaseIn.isGooglyEyes()){
                VertexConsumer ivertexbuilder = bufferIn.getBuffer(entitylivingbaseIn.getVariant() == 2 ? AMRenderTypes.getEyesNoCull(TEXTURE_SOUL_EYES) : AMRenderTypes.getEyesNoCull(TEXTURE_EYES));
                this.getParentModel().renderToBuffer(matrixStackIn, ivertexbuilder, 15728640, OverlayTexture.NO_OVERLAY, -1);
            }
        }

        @Override
        public RenderType renderType() {
            return AMRenderTypes.getEyesNoCull(TEXTURE_EYES);
        }
    }
}
