package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.fabric.DeferredRegister;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class AMRecipeRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> DEF_REG = DeferredRegister.create(Registries.RECIPE_SERIALIZER, AlexsMobs.MODID);
    public static final RecipeSerializer<?> MIMICREAM_RECIPE = DEF_REG.register("mimicream_repair", () -> new SimpleCraftingRecipeSerializer<>(RecipeMimicreamRepair::new));
    public static final RecipeSerializer<?> BISON_UPGRADE = DEF_REG.register("bison_upgrade", () -> new SimpleCraftingRecipeSerializer<>(RecipeBisonUpgrade::new));

    public static void init(){
    }
}
