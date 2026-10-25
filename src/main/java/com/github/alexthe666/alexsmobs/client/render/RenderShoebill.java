package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelShoebill;
import com.github.alexthe666.alexsmobs.entity.EntityShoebill;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderShoebill extends MobRenderer<EntityShoebill, ModelShoebill> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/shoebill.png");

    public RenderShoebill(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelShoebill(), 0.3F);
    }

    @Override
    protected void scale(EntityShoebill entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
    }

    @Override
    public Identifier getTextureLocation(EntityShoebill entity) {
        return TEXTURE;
    }
}
