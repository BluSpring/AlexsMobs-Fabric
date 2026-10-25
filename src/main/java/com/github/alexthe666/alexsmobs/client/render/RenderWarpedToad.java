package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelWarpedToad;
import com.github.alexthe666.alexsmobs.client.render.layer.LayerWarpedToadGlow;
import com.github.alexthe666.alexsmobs.entity.EntityWarpedToad;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderWarpedToad extends MobRenderer<EntityWarpedToad, ModelWarpedToad> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/warped_toad.png");
    private static final Identifier TEXTURE_BLINKING = Identifier.parse("alexsmobs:textures/entity/warped_toad_blink.png");
    private static final Identifier TEXTURE_PEPE = Identifier.parse("alexsmobs:textures/entity/warped_toad_pepe.png");
    private static final Identifier TEXTURE_PEPE_BLINKING = Identifier.parse("alexsmobs:textures/entity/warped_toad_pepe_blink.png");

    public RenderWarpedToad(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelWarpedToad(), 0.85F);
        this.addLayer(new LayerWarpedToadGlow(this));
    }

    @Override
    protected void scale(EntityWarpedToad entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(1.25F, 1.25F, 1.25F);
    }


    @Override
    public Identifier getTextureLocation(EntityWarpedToad entity) {
        if(entity.isBased()){
            return entity.isBlinking() ? TEXTURE_PEPE_BLINKING : TEXTURE_PEPE;
        }else{
            return entity.isBlinking() ? TEXTURE_BLINKING : TEXTURE;
        }
    }
}
