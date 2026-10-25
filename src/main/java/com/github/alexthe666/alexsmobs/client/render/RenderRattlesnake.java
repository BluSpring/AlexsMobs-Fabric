package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelRattlesnake;
import com.github.alexthe666.alexsmobs.entity.EntityRattlesnake;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderRattlesnake extends MobRenderer<EntityRattlesnake, ModelRattlesnake> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/rattlesnake.png");

    public RenderRattlesnake(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelRattlesnake(), 0.2F);
    }

    @Override
    protected void scale(EntityRattlesnake entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
    }


    @Override
    public Identifier getTextureLocation(EntityRattlesnake entity) {
        return TEXTURE;
    }
}
