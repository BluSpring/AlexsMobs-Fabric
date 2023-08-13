package com.github.alexthe666.alexsmobs.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Mob.class)
public interface MobAccessor {
    @Invoker
    void callRestoreLeashFromSave();

    @Accessor
    CompoundTag getLeashInfoTag();
}
