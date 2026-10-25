package com.github.alexthe666.alexsmobs.client.render.layer;

import com.github.alexthe666.alexsmobs.client.model.ModelSoulVulture;
import com.github.alexthe666.alexsmobs.client.render.AMRenderTypes;
import com.github.alexthe666.alexsmobs.client.render.RenderSoulVulture;
import com.github.alexthe666.alexsmobs.entity.EntitySoulVulture;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;

public class LayerSoulVultureGlow extends RenderLayer<EntitySoulVulture, ModelSoulVulture> {
    private static final Identifier TEXTURE_GLOW = Identifier.parse("alexsmobs:textures/entity/soul_vulture/soul_vulture_glow.png");
    private static final Identifier TEXTURE_0 = Identifier.parse("alexsmobs:textures/entity/soul_vulture/soul_vulture_flames_0.png");
    private static final Identifier TEXTURE_1 = Identifier.parse("alexsmobs:textures/entity/soul_vulture/soul_vulture_flames_1.png");
    private static final Identifier TEXTURE_2 = Identifier.parse("alexsmobs:textures/entity/soul_vulture/soul_vulture_flames_2.png");

    public LayerSoulVultureGlow(RenderSoulVulture renderSoulVulture) {
        super(renderSoulVulture);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, EntitySoulVulture entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getParentModel().renderToBuffer(matrixStackIn, bufferIn.getBuffer(AMRenderTypes.getGhost(TEXTURE_GLOW)), 240, LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0.0F), -1);
        if(entitylivingbaseIn.hasSoulHeart()){
            this.getParentModel().renderToBuffer(matrixStackIn, bufferIn.getBuffer(AMRenderTypes.getGhost(getFlames(entitylivingbaseIn.tickCount))), 240, LivingEntityRenderer.getOverlayCoords(entitylivingbaseIn, 0.0F), -1);
        }
    }

    private Identifier getFlames(int tickCount) {
        final int i = tickCount / 3 % 3;
        return switch (i) {
            case 2 -> TEXTURE_2;
            case 1 -> TEXTURE_1;
            default -> TEXTURE_0;
        };
    }
}
