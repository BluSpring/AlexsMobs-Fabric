package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelSkelewag;
import com.github.alexthe666.alexsmobs.entity.EntitySkelewag;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;

public class RenderSkelewag extends MobRenderer<EntitySkelewag, ModelSkelewag> {
    private static final Identifier TEXTURE_0 = Identifier.parse("alexsmobs:textures/entity/skelewag_0.png");
    private static final Identifier TEXTURE_1 = Identifier.parse("alexsmobs:textures/entity/skelewag_1.png");

    public RenderSkelewag(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelSkelewag(), 0.5F);
    }

    @Override
    protected void scale(EntitySkelewag entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
    }

    @Override
    protected int getBlockLightLevel(EntitySkelewag entityIn, BlockPos partialTicks) {
        return Math.max(2, super.getBlockLightLevel(entityIn, partialTicks));
    }


    @Override
    public Identifier getTextureLocation(EntitySkelewag entity) {
        return entity.getVariant() == 1 ? TEXTURE_1 : TEXTURE_0;
    }
}
