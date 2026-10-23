package com.github.alexthe666.alexsmobs.mixin.fabric;

import com.github.alexthe666.alexsmobs.fabric.SittableVehicleEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
    @Shadow public M model;

    @WrapOperation(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isPassenger()Z", ordinal = 0))
    private boolean am$shouldEntitySit(LivingEntity instance, Operation<Boolean> op) {
        var isPassenger = op.call(instance);

        return isPassenger && (instance.getVehicle() != null && (!(instance.getVehicle() instanceof SittableVehicleEntity sittableVehicle) || sittableVehicle.shouldRiderSit()));
    }

    @WrapOperation(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = {
        @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isPassenger()Z", ordinal = 1),
        @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isPassenger()Z", ordinal = 2),
    })
    private boolean am$shouldEntitySit2(LivingEntity instance, Operation<Boolean> op) {
        return op.call(instance) && this.model.riding;
    }
}
