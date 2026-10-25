package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class AMRecipeRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> DEF_REG = DeferredRegister.create(Registries.RECIPE_SERIALIZER, AlexsMobs.MODID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<? extends CustomRecipe>> MIMICREAM_RECIPE = DEF_REG.register("mimicream_repair", () -> new RecipeSerializer<>(RecipeMimicreamRepair.CODEC, RecipeMimicreamRepair.STREAM_CODEC));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<? extends CustomRecipe>> BISON_UPGRADE = DEF_REG.register("bison_upgrade", () -> new RecipeSerializer<>(RecipeBisonUpgrade.CODEC, RecipeBisonUpgrade.STREAM_CODEC));

    public static void init(){
    }
}
