package com.github.alexthe666.alexsmobs.mixin.fabric;

import com.github.alexthe666.alexsmobs.fabric.CustomHurtHandlingItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Inject(method = "canBeHurtBy", at = @At("HEAD"), cancellable = true)
    private void checkCanBeHurt(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        if (this.getItem() instanceof CustomHurtHandlingItem hurtHandlingItem && !hurtHandlingItem.canBeHurtBy((ItemStack) (Object) this, damageSource)) {
            cir.setReturnValue(false);
        }
    }
}
