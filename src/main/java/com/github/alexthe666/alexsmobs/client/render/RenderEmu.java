package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelEmu;
import com.github.alexthe666.alexsmobs.entity.EntityEmu;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderEmu extends MobRenderer<EntityEmu, ModelEmu> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/emu.png");
    private static final Identifier TEXTURE_BABY = Identifier.parse("alexsmobs:textures/entity/emu_baby.png");
    private static final Identifier TEXTURE_BLONDE = Identifier.parse("alexsmobs:textures/entity/emu_blonde.png");
    private static final Identifier TEXTURE_BLONDE_BABY = Identifier.parse("alexsmobs:textures/entity/emu_baby_blonde.png");
    private static final Identifier TEXTURE_BLUE = Identifier.parse("alexsmobs:textures/entity/emu_blue.png");

    public RenderEmu(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelEmu(), 0.45F);
    }

    @Override
    protected void scale(EntityEmu entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        matrixStackIn.scale(0.85F, 0.85F, 0.85F);
    }


    @Override
    public Identifier getTextureLocation(EntityEmu entity) {
        if(entity.getVariant() == 2){
            return entity.isBaby() ? TEXTURE_BLONDE_BABY : TEXTURE_BLONDE;
        }
        if(entity.getVariant() == 1 && !entity.isBaby()){
            return  TEXTURE_BLUE;
        }
        return entity.isBaby() ? TEXTURE_BABY : TEXTURE;
    }
}
