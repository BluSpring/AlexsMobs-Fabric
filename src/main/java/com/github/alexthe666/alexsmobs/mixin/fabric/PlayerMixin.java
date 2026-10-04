package com.github.alexthe666.alexsmobs.mixin.fabric;

import com.github.alexthe666.alexsmobs.fabric.ForcedPoseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements ForcedPoseEntity {
    @Unique
    private Pose alexsmobs$forcedPose;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public Pose alexsmobs$getForcedPose() {
        return this.alexsmobs$forcedPose;
    }

    @Override
    public void alexsmobs$setForcedPose(Pose pose) {
        this.alexsmobs$forcedPose = pose;
    }

    @Inject(method = "updatePlayerPose", at = @At("HEAD"), cancellable = true)
    public void am$forcePose(CallbackInfo ci) {
        if (this.alexsmobs$forcedPose != null) {
            this.setPose(this.alexsmobs$forcedPose);
            ci.cancel();
        }
    }
}
