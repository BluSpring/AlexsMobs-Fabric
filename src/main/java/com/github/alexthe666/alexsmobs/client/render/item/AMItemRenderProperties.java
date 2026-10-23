package com.github.alexthe666.alexsmobs.client.render.item;

import com.github.alexthe666.alexsmobs.client.render.AMItemstackRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;

public class AMItemRenderProperties implements IClientItemExtensions {

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return new AMItemstackRenderer();
    }
}
