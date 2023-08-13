package com.github.alexthe666.alexsmobs.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EnderDragon.class)
public interface EnderDragonAccessor {
    @Invoker
    boolean callReallyHurt(DamageSource damageSource, float amount);
}
