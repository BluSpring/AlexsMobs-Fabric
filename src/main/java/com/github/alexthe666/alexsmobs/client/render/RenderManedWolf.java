package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelManedWolf;
import com.github.alexthe666.alexsmobs.entity.EntityManedWolf;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderManedWolf extends MobRenderer<EntityManedWolf, ModelManedWolf> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/maned_wolf.png");
    private static final Identifier TEXTURE_ENDER = Identifier.parse("alexsmobs:textures/entity/maned_wolf_ender.png");

    public RenderManedWolf(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelManedWolf(), 0.45F);
    }

    @Override
    protected void scale(EntityManedWolf entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.85F, 0.85F, 0.85F);
    }


    @Override
    public Identifier getTextureLocation(EntityManedWolf entity) {
        return entity.isEnder() ? TEXTURE_ENDER : TEXTURE;
    }
}
