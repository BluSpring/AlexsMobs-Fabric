package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelSoulVulture;
import com.github.alexthe666.alexsmobs.client.render.layer.LayerSoulVultureGlow;
import com.github.alexthe666.alexsmobs.entity.EntitySoulVulture;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderSoulVulture extends MobRenderer<EntitySoulVulture, ModelSoulVulture> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/soul_vulture/soul_vulture.png");

    public RenderSoulVulture(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelSoulVulture(), 0.3F);
        this.addLayer(new LayerSoulVultureGlow(this));
    }

    @Override
    protected void scale(EntitySoulVulture entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      //  matrixStackIn.scale(1.2F, 1.2F, 1.2F);
    }


    @Override
    public Identifier getTextureLocation(EntitySoulVulture entity) {
        return TEXTURE;
    }
}
