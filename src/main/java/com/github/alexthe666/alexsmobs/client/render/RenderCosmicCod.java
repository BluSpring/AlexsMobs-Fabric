package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelCosmicCod;
import com.github.alexthe666.alexsmobs.client.render.layer.LayerBasicGlow;
import com.github.alexthe666.alexsmobs.entity.EntityCosmicCod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class RenderCosmicCod extends MobRenderer<EntityCosmicCod, EntityModel<EntityCosmicCod>> {
    private static final Identifier TEXTURE = Identifier.parse("alexsmobs:textures/entity/cosmic_cod.png");
    private static final Identifier TEXTURE_EYES = Identifier.parse("alexsmobs:textures/entity/cosmic_cod_eyes.png");

    public RenderCosmicCod(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelCosmicCod(), 0.25F);
        this.addLayer(new LayerBasicGlow<>(this, TEXTURE_EYES));
    }

    @Override
    public Identifier getTextureLocation(EntityCosmicCod entity) {
        return TEXTURE;
    }
}

