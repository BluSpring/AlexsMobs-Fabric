package com.github.alexthe666.alexsmobs.mixin;

import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChunkRenderDispatcher.RenderChunk.class)
public interface RenderChunkAccessor {
    @Accessor
    boolean isDirty();

    @Accessor
    void setDirty(boolean dirty);
}
