package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelCaiman;
import com.github.alexthe666.alexsmobs.entity.EntityCaiman;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderCaiman extends MobRenderer<EntityCaiman, ModelCaiman> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/caiman.png");

    public RenderCaiman(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelCaiman(), 0.4F);
    }

    @Override
    public Identifier getTextureLocation(EntityCaiman entity) {
        return TEXTURE;
    }
}
