package com.github.alexthe666.alexsmobs.mixin;

import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.ItemStackComponentizationFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStackComponentizationFix.class)
public abstract class ItemStackComponentizationFixMixin {
    @Inject(method = "fixItemStack", at = @At("TAIL"))
    private static void alexsmobs$fixItemsAndTranslateNbtIntoComponents(ItemStackComponentizationFix.ItemStackData itemStackData, Dynamic<?> tag, CallbackInfo ci) {
        // Automatically translates all items to their new data components, useful for servers that are updating from 1.20 -> 1.21
        itemStackData.removeTag("BisonFur").result().ifPresent(dyn -> itemStackData.setComponent("alexsmobs:bison_fur", tag.emptyMap()));
        itemStackData.moveTagToComponent("DisplayEntityType", "alexsmobs:display_entity_type");
        itemStackData.moveTagToComponent("DisplayMobFlags", "alexsmobs:display_mob_flags");
    }
}
