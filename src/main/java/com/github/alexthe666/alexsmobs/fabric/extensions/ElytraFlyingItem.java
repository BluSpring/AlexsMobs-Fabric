package com.github.alexthe666.alexsmobs.fabric.extensions;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public interface ElytraFlyingItem {
    default boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return stack.getItem() == Items.ELYTRA && ElytraItem.isFlyEnabled(stack);
    }

    default boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        return canElytraFly(stack, entity);
    }
}
