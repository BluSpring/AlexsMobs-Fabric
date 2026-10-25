package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelRockyRoller;
import com.github.alexthe666.alexsmobs.entity.EntityRockyRoller;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderRockyRoller extends MobRenderer<EntityRockyRoller, ModelRockyRoller> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/rocky_roller.png");
    private static final Identifier TEXTURE_ANGRY = Identifier.parse("alexsmobs:textures/entity/rocky_roller_angry.png");
    private static final Identifier TEXTURE_ROLLING = Identifier.parse("alexsmobs:textures/entity/rocky_roller_rolling.png");

    public RenderRockyRoller(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelRockyRoller(), 0.7F);
    }

    @Override
    protected void scale(EntityRockyRoller entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
    }


    @Override
    public Identifier getTextureLocation(EntityRockyRoller entity) {
        return entity.isRolling() ? TEXTURE_ROLLING : entity.isAngry() ? TEXTURE_ANGRY : TEXTURE;
    }
}
