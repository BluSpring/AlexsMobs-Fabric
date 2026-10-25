package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelHammerheadShark;
import com.github.alexthe666.alexsmobs.entity.EntityHammerheadShark;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderHammerheadShark extends MobRenderer<EntityHammerheadShark, ModelHammerheadShark> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/hammerhead_shark.png");

    public RenderHammerheadShark(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelHammerheadShark(), 0.8F);
    }

    @Override
    protected void scale(EntityHammerheadShark entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
    }


    @Override
    public Identifier getTextureLocation(EntityHammerheadShark entity) {
        return TEXTURE;
    }
}
