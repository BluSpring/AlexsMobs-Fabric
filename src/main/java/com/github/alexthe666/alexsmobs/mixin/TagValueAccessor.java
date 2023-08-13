package com.github.alexthe666.alexsmobs.mixin;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Ingredient.TagValue.class)
public interface TagValueAccessor {
    @Invoker("<init>")
    static Ingredient.TagValue createTagValue(TagKey<Item> tag) {
        throw new UnsupportedOperationException();
    }
}
