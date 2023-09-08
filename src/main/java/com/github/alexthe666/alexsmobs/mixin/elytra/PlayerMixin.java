package com.github.alexthe666.alexsmobs.mixin.elytra;

import com.github.alexthe666.alexsmobs.fabric.extensions.ElytraFlyingItem;
import com.github.alexthe666.alexsmobs.fabric.extensions.ForcedPoseEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements ForcedPoseEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @WrapOperation(method = "tryToStartFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    public boolean am$useCustomElytra(ItemStack instance, Item item, Operation<Boolean> original) {
        if (instance.getItem() instanceof ElytraFlyingItem elytraFlyingItem) {
            return elytraFlyingItem.canElytraFly(instance, (LivingEntity) (Object) this);
        }

        return original.call(instance, item);
    }

    @Inject(method = "updatePlayerPose", at = @At("HEAD"), cancellable = true)
    public void am$forcePose(CallbackInfo ci) {
        if (this.forcedPose != null) {
            this.setPose(this.forcedPose);
            ci.cancel();
        }
    }

    @Unique
    private Pose forcedPose;

    @Override
    public Pose getForcedPose() {
        return this.forcedPose;
    }

    @Override
    public void setForcedPose(Pose pose) {
        this.forcedPose = pose;
    }
}
