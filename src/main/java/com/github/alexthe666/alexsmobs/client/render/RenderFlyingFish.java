package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelFlyingFish;
import com.github.alexthe666.alexsmobs.entity.EntityFlyingFish;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderFlyingFish extends MobRenderer<EntityFlyingFish, ModelFlyingFish> {
    private static final Identifier TEXTURE_0 = Identifier.parse("alexsmobs:textures/entity/flying_fish_0.png");
    private static final Identifier TEXTURE_1 = Identifier.parse("alexsmobs:textures/entity/flying_fish_1.png");
    private static final Identifier TEXTURE_2 = Identifier.parse("alexsmobs:textures/entity/flying_fish_2.png");

    public RenderFlyingFish(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelFlyingFish(), 0.2F);
    }

    @Override
    protected void scale(EntityFlyingFish entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.8F, 0.8F, 0.8F);
    }


    @Override
    public Identifier getTextureLocation(EntityFlyingFish entity) {
        switch (entity.getVariant()){
            case 0:
                return TEXTURE_0;
            case 1:
                return TEXTURE_1;
            case 2:
                return TEXTURE_2;
        }
        return TEXTURE_0;
    }
}
