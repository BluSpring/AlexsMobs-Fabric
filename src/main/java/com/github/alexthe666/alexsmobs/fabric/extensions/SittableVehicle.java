package com.github.alexthe666.alexsmobs.fabric.extensions;

public interface SittableVehicle {
    default boolean shouldRiderSit() {
        return true;
    }
}
