package com.github.alexthe666.alexsmobs.mixin.fabric;

import java.util.SequencedMap;

import com.github.alexthe666.alexsmobs.ClientProxy;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;

@Mixin(RenderBuffers.class)
public abstract class RenderBuffersMixin {
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;immediate(Lcom/mojang/blaze3d/vertex/ByteBufferBuilder;)Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;"))
    private void registerCustomRenderBuffers(int bufferCount, CallbackInfo ci, @Local SequencedMap<RenderType, ByteBufferBuilder> bufferMap) {
        ClientProxy.onRegisterRenderBuffers(renderType -> bufferMap.put(renderType, new ByteBufferBuilder(renderType.bufferSize())));
    }
}
