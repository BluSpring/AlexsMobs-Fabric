package com.github.alexthe666.alexsmobs.mixin;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Camera.class)
public interface CameraAccessor {
    @Invoker
    void callMove(double distanceOffset, double verticalOffset, double horizontalOffset);
}
