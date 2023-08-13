package com.github.alexthe666.alexsmobs.mixin;

import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockRenderDispatcher.class)
public interface BlockRenderDispatcherAccessor {
    @Accessor
    LiquidBlockRenderer getLiquidBlockRenderer();

    @Mutable
    @Accessor
    void setLiquidBlockRenderer(LiquidBlockRenderer liquidBlockRenderer);
}
