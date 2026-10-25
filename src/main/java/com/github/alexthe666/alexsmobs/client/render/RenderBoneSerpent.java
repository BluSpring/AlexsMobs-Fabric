package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelBoneSerpentHead;
import com.github.alexthe666.alexsmobs.entity.EntityBoneSerpent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderBoneSerpent extends MobRenderer<EntityBoneSerpent, ModelBoneSerpentHead> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/bone_serpent_head.png");

    public RenderBoneSerpent(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelBoneSerpentHead(), 0.3F);
    }

    @Override
    protected void scale(EntityBoneSerpent entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      //  matrixStackIn.scale(1.2F, 1.2F, 1.2F);
    }


    @Override
    public Identifier getTextureLocation(EntityBoneSerpent entity) {
        return TEXTURE;
    }
}
