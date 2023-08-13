package com.github.alexthe666.alexsmobs.mixin.event;

import com.github.alexthe666.alexsmobs.fabric.event.MobDespawnCallback;
import com.github.alexthe666.alexsmobs.fabric.event.SpecialSpawnCallback;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {
    protected MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @WrapOperation(method = "checkDespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getNearestPlayer(Lnet/minecraft/world/entity/Entity;D)Lnet/minecraft/world/entity/player/Player;"))
    public Player am$runDespawnEventCheck(Level level, Entity entity, double range, Operation<Player> original) {
        var result = MobDespawnCallback.EVENT.invoker().shouldAllowDespawn((Mob) (Object) this).asMinecraft();

        if (result == InteractionResult.FAIL) {
            this.noActionTime = 0;
            return null;
        } else if (result == InteractionResult.SUCCESS) {
            this.discard();
            return null;
        } else {
            return original.call(level, entity, range);
        }
    }

    @Inject(method = "finalizeSpawn", at = @At("HEAD"), cancellable = true)
    public void am$callSpecialSpawnCheck(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData, CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        if (SpecialSpawnCallback.EVENT.invoker().doSpecialSpawn((Mob) (Object) this, level, this.getX(), this.getY(), this.getZ(), null, reason))
            cir.setReturnValue(null);
    }
}
