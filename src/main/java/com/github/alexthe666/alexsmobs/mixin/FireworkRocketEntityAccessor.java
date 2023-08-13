package com.github.alexthe666.alexsmobs.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.OptionalInt;

@Mixin(FireworkRocketEntity.class)
public interface FireworkRocketEntityAccessor {
    @Accessor
    static EntityDataAccessor<ItemStack> getDATA_ID_FIREWORKS_ITEM() {
        throw new UnsupportedOperationException();
    }

    @Accessor
    static EntityDataAccessor<OptionalInt> getDATA_ATTACHED_TO_TARGET() {
        throw new UnsupportedOperationException();
    }

    @Accessor
    int getLifetime();

    @Accessor
    void setLifetime(int lifetime);
}
