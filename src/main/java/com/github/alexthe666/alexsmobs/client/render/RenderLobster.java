package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelLobster;
import com.github.alexthe666.alexsmobs.entity.EntityLobster;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderLobster extends MobRenderer<EntityLobster, ModelLobster> {
    private static final Identifier TEXTURE_RED = Identifier.parse("alexsmobs:textures/entity/lobster_red.png");
    private static final Identifier TEXTURE_BLUE = Identifier.parse("alexsmobs:textures/entity/lobster_blue.png");
    private static final Identifier TEXTURE_YELLOW = Identifier.parse("alexsmobs:textures/entity/lobster_yellow.png");
    private static final Identifier TEXTURE_REDBLUE = Identifier.parse("alexsmobs:textures/entity/lobster_redblue.png");
    private static final Identifier TEXTURE_BLACK = Identifier.parse("alexsmobs:textures/entity/lobster_black.png");
    private static final Identifier TEXTURE_WHITE = Identifier.parse("alexsmobs:textures/entity/lobster_white.png");

    public RenderLobster(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelLobster(), 0.25F);
    }

    @Override
    protected void scale(EntityLobster entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
    }


    @Override
    public Identifier getTextureLocation(EntityLobster entity) {
        return switch (entity.getVariant()) {
            case 1 -> TEXTURE_BLUE;
            case 2 -> TEXTURE_YELLOW;
            case 3 -> TEXTURE_REDBLUE;
            case 4 -> TEXTURE_BLACK;
            case 5 -> TEXTURE_WHITE;
            default -> TEXTURE_RED;
        };
    }
}
