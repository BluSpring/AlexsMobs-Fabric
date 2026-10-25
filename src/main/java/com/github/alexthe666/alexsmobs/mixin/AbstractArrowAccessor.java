package com.github.alexthe666.alexsmobs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.entity.projectile.arrow.AbstractArrow;

@Mixin(AbstractArrow.class)
public interface AbstractArrowAccessor {
    @Invoker("setPierceLevel")
    void invokeSetPierceLevel(byte pierceLevel);

    @Invoker("getPierceLevel")
    byte invokeGetPierceLevel();
}
