package com.github.alexthe666.alexsmobs.mixin;

import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HopperBlockEntity.class)
public interface HopperBlockEntityAccessor {
    @Invoker
    boolean callIsOnCustomCooldown();

    @Invoker
    void callSetCooldown(int cooldownTime);
}
