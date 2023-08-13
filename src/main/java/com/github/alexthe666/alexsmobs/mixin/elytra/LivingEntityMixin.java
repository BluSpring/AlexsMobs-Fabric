package com.github.alexthe666.alexsmobs.mixin.elytra;

import com.github.alexthe666.alexsmobs.fabric.extensions.ElytraFlyingItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Shadow protected int fallFlyTicks;

    @WrapOperation(method = "updateFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    public boolean am$useCustomElytra(ItemStack instance, Item item, Operation<Boolean> original, @Share("alexsmobs$flag") LocalIntRef flag) {
        flag.set(-1);
        if (instance.getItem() instanceof ElytraFlyingItem elytraFlyingItem) {
            if (elytraFlyingItem.canElytraFly(instance, (LivingEntity) (Object) this))
                flag.set(elytraFlyingItem.elytraFlightTick(instance, (LivingEntity) (Object) this, this.fallFlyTicks) ? 1 : 0);
            return false;
        }

        return original.call(instance, item);
    }

    @ModifyArg(method = "updateFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;setSharedFlag(IZ)V"))
    public boolean am$setFlag(boolean original, @Share("alexsmobs$flag") LocalIntRef flag) {
        if (flag.get() != -1)
            return flag.get() == 1;

        return original;
    }
}
