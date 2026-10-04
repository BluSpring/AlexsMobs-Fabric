package com.github.alexthe666.alexsmobs.mixin.fabric.event;

import com.github.alexthe666.alexsmobs.event.ServerEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import net.fabricmc.fabric.api.util.TriState;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {
    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @WrapOperation(method = "checkDespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getNearestPlayer(Lnet/minecraft/world/entity/Entity;D)Lnet/minecraft/world/entity/player/Player;"))
    public Player am$runDespawnEventCheck(Level level, Entity entity, double range, Operation<Player> original) {
        var result = ServerEvents.onEntityDespawnAttempt(this);

        if (result == TriState.FALSE) {
            this.noActionTime = 0;
            return null;
        } else if (result == TriState.TRUE) {
            this.discard();
            return null;
        } else {
            return original.call(level, entity, range);
        }
    }
}
