package com.github.alexthe666.alexsmobs.mixin;

import com.github.alexthe666.alexsmobs.fabric.extensions.DamageableItem;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract Item getItem();

    @ModifyArg(method = "hurtAndBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;hurt(ILnet/minecraft/util/RandomSource;Lnet/minecraft/server/level/ServerPlayer;)Z"))
    public <T extends LivingEntity> int am$modifyBreakAmount(int amount, @Local T entity, @Local Consumer<T> onBroken) {
        if (this.getItem() instanceof DamageableItem damageableItem) {
            return damageableItem.damageItem((ItemStack) (Object) this, amount, entity, onBroken);
        }

        return amount;
    }
}
