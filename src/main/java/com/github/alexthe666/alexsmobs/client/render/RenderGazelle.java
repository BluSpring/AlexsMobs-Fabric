package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelGazelle;
import com.github.alexthe666.alexsmobs.entity.EntityGazelle;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderGazelle extends MobRenderer<EntityGazelle, ModelGazelle> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/gazelle.png");

    public RenderGazelle(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelGazelle(), 0.4F);
    }

    @Override
    protected void scale(EntityGazelle entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.8F, 0.8F, 0.8F);
    }


    @Override
    public Identifier getTextureLocation(EntityGazelle entity) {
        return TEXTURE;
    }
}
