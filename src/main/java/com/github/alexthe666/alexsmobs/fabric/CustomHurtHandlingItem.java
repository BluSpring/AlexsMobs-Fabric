package com.github.alexthe666.alexsmobs.fabric;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;

public interface CustomHurtHandlingItem {
    boolean canBeHurtBy(ItemStack stack, DamageSource source);
}
