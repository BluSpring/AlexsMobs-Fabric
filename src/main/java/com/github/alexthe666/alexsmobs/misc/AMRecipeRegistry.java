package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.citadel.server.item.CitadelRecipes;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;

public class AMRecipeRegistry {
    public static final LazyRegistrar<RecipeSerializer<?>> DEF_REG = LazyRegistrar.create(Registry.RECIPE_SERIALIZER_REGISTRY, AlexsMobs.MODID);
    public static final RegistryObject<RecipeSerializer<?>> MIMICREAM_RECIPE = DEF_REG.register("mimicream_repair", () -> new SimpleRecipeSerializer<>(RecipeMimicreamRepair::new));
    public static final RegistryObject<RecipeSerializer<?>> BISON_UPGRADE = DEF_REG.register("bison_upgrade", () -> new SimpleRecipeSerializer<>(RecipeBisonUpgrade::new));

    public static void init(){
        CitadelRecipes.registerSmithingRecipe(new RecipeBisonUpgrade(new ResourceLocation("alexsmobs:bison_fur_upgrade")));
    }
}
