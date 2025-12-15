package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.citadel.client.model.container.JsonUtils;
import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;

import java.lang.reflect.Type;

public class CapsidRecipe {
    public static final Codec<CapsidRecipe> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            NonNullList.codecOf(Ingredient.CODEC)
                .fieldOf("ingredients")
                .forGetter(recipe -> recipe.ingredients),
            ItemStack.OPTIONAL_CODEC
                .optionalFieldOf("result", ItemStack.EMPTY)
                .forGetter(recipe -> recipe.result),
            Codec.INT
                .fieldOf("time")
                .forGetter(recipe -> recipe.time)
        )
            .apply(instance, CapsidRecipe::new)
    );

    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final int time;

    public CapsidRecipe(NonNullList<Ingredient> ingredients, ItemStack result, int time) {
        this.result = result;
        this.ingredients = ingredients;
        this.time = time;
    }

    public ItemStack getResult() {
        return result;
    }

    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getTime() {
        return time;
    }

    public boolean matches(ItemStack... stacks) {
        IntList taken = new IntArrayList();
        ItemStack[] copy = new ItemStack[stacks.length];
        for (int j = 0; j < copy.length; j++) {
            copy[j] = stacks[j].copy();
            for (Ingredient ingredient : ingredients) {
                if (ingredient.test(copy[j])) {
                    taken.add(j);
                    copy[j].shrink(1);
                }
            }
        }
        return taken.size() >= ingredients.size();
    }
}
