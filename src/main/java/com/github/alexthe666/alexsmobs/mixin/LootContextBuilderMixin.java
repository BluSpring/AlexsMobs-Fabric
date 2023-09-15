package com.github.alexthe666.alexsmobs.mixin;

import net.minecraft.world.level.storage.loot.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(LootContext.Builder.class)
public class LootContextBuilderMixin {
    @Redirect(method = "create", at = @At(value = "INVOKE", target = "Ljava/util/Set;isEmpty()Z"))
    public boolean am$passCustomLootContext(Set instance) {
        // inverts
        return true;
    }
}
