package com.github.alexthe666.alexsmobs.mixin.fabric.event;

import com.github.alexthe666.alexsmobs.client.event.ClientEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;shouldShowName(Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean checkShouldRenderNametag(EntityRenderer<T> instance, T entity, Operation<Boolean> original) {
        if (!ClientEvents.onRenderNameplate(entity).orElse(true))
            return false;

        return original.call(instance, entity);
    }
}
