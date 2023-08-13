package com.github.alexthe666.alexsmobs.mixin.elytra;

import com.github.alexthe666.alexsmobs.fabric.extensions.ElytraFlyingItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {
    @WrapOperation(method = "tryToStartFallFlying", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    public boolean am$useCustomElytra(ItemStack instance, Item item, Operation<Boolean> original) {
        if (instance.getItem() instanceof ElytraFlyingItem elytraFlyingItem) {
            return elytraFlyingItem.canElytraFly(instance, (LivingEntity) (Object) this);
        }

        return original.call(instance, item);
    }
}
