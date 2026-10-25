package com.github.alexthe666.alexsmobs.datagen;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.misc.AMTagRegistry;
import net.neoforged.neoforge.common.Tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class AMRecipeProvider extends RecipeProvider {
    public AMRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output, HolderLookup.Provider registries) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AMItemRegistry.ANIMAL_DICTIONARY.value())
            .requires(Items.BOOK)
            .requires(AMTagRegistry.ANIMAL_DICTIONARY_INGREDIENT)
            .requires(Tags.Items.DYES_GREEN)
            .unlockedBy(getHasName(AMItemRegistry.ANIMAL_DICTIONARY.value()), has(Items.BOOK))
            .group("animal_dictionary")
            .save(output);

        oneToOneConversionRecipe(output, AMItemRegistry.BANANA_SLUG_SLIME.value(), AMBlockRegistry.BANANA_SLUG_SLIME_BLOCK.value(), "banana_slug_slime", 9);
        threeByThreePacker(output, RecipeCategory.MISC, AMBlockRegistry.BANANA_SLUG_SLIME_BLOCK.value(), AMItemRegistry.BANANA_SLUG_SLIME.value());

        bannerPattern(output, AMItemRegistry.BANNER_PATTERN_AUSTRALIA_0, AMItemRegistry.KANGAROO_HIDE);
        bannerPattern(output, AMItemRegistry.BANNER_PATTERN_AUSTRALIA_1, AMItemRegistry.EMU_FEATHER);
        bannerPattern(output, AMItemRegistry.BANNER_PATTERN_BEAR, AMItemRegistry.BEAR_FUR);
        bannerPattern(output, AMItemRegistry.BANNER_PATTERN_BRAZIL, AMItemRegistry.SHED_SNAKE_SKIN);
        bannerPattern(output, AMItemRegistry.BANNER_PATTERN_NEW_MEXICO, AMItemRegistry.TARANTULA_HAWK_WING_FRAGMENT);

        oneToOneConversionRecipe(output, AMItemRegistry.BISON_FUR.value(), AMBlockRegistry.BISON_FUR_BLOCK.value(), "bison_fur", 9);
        threeByThreePacker(output, RecipeCategory.MISC, AMBlockRegistry.BISON_FUR_BLOCK.value(), AMItemRegistry.BISON_FUR.value());
        twoByTwoPacker(output, "bison_fur_to_wool", RecipeCategory.MISC, Items.BROWN_WOOL, AMItemRegistry.BISON_FUR.value());

        carpet(output, AMBlockRegistry.BISON_CARPET.value(), AMItemRegistry.BISON_FUR.value());

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItemRegistry.BLOOD_SPRAYER.value())
            .group("blood_sprayer")
            .unlockedBy(getHasName(AMItemRegistry.BLOOD_SPRAYER.value()), has(AMItemRegistry.MOSQUITO_PROBOSCIS.value()))
            .pattern("SS ")
            .pattern("BBP")
            .pattern("B  ")
            .define('S', AMItemRegistry.BLOOD_SAC.value())
            .define('P', AMItemRegistry.MOSQUITO_PROBOSCIS.value())
            .define('B', Items.NETHER_BRICK)
            .save(output);

        food(output, AMItemRegistry.BOILED_EMU_EGG.value(), AMItemRegistry.EMU_EGG.value(), 0.15f, 200, 600, 100);

        oneToOneConversionRecipe(output, AMItemRegistry.FISH_BONES.value(), Items.BONE_MEAL, "bone_meal", 2);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItemRegistry.CENTIPEDE_LEGGINGS.value())
            .group("centipede_leggings")
            .unlockedBy(getHasName(AMItemRegistry.CENTIPEDE_LEGGINGS.value()), has(AMItemRegistry.CENTIPEDE_LEG.value()))
            .pattern("SSS")
            .pattern("S S")
            .pattern("S S")
            .define('S', AMItemRegistry.CENTIPEDE_LEG.value())
            .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, AMItemRegistry.CHORUS_ON_A_STICK.value())
            .requires(Tags.Items.TOOLS_FISHING_ROD)
            .requires(Items.CHORUS_FRUIT)
            .unlockedBy(getHasName(AMItemRegistry.CHORUS_ON_A_STICK.value()), has(Items.CHORUS_FRUIT))
            .group("chorus_on_a_stick")
            .save(output);

        threeByThreePacker(output, RecipeCategory.MISC, AMItemRegistry.COCKROACH_WING.value(), AMItemRegistry.COCKROACH_WING_FRAGMENT.value());

        food(output, AMItemRegistry.COOKED_CATFISH.value(), AMItemRegistry.RAW_CATFISH.value(), 0.15f, 200, 600, 100);
        food(output, AMItemRegistry.COOKED_KANGAROO_MEAT.value(), AMItemRegistry.KANGAROO_MEAT.value(), 0.15f, 200, 600, 100);
        food(output, AMItemRegistry.COOKED_LOBSTER_TAIL.value(), AMItemRegistry.LOBSTER_TAIL.value(), 0.15f, 200, 600, 100);
        food(output, AMItemRegistry.COOKED_MOOSE_RIBS.value(), AMItemRegistry.MOOSE_RIBS.value(), 0.15f, 200, 600, 100);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItemRegistry.CROCODILE_CHESTPLATE.value())
            .group("crocodile_chestplate")
            .unlockedBy(getHasName(AMItemRegistry.CROCODILE_CHESTPLATE.value()), has(AMItemRegistry.CROCODILE_SCUTE.value()))
            .pattern("S S")
            .pattern("SSS")
            .pattern("SSS")
            .define('S', AMItemRegistry.CROCODILE_SCUTE.value())
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, AMItemRegistry.DIMENSIONAL_CARVER.value())
            .group("dimensional_carver")
            .unlockedBy(getHasName(AMItemRegistry.DIMENSIONAL_CARVER.value()), has(AMItemRegistry.VOID_WORM_EYE.value()))
            .pattern("MEM")
            .pattern(" X ")
            .pattern(" X ")
            .define('M', AMItemRegistry.VOID_WORM_MANDIBLE.value())
            .define('E', AMItemRegistry.VOID_WORM_EYE.value())
            .define('X', Tags.Items.INGOTS_NETHERITE)
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, AMItemRegistry.ECHOLOCATOR.value())
            .group("echolocator")
            .unlockedBy(getHasName(AMItemRegistry.ECHOLOCATOR.value()), has(AMItemRegistry.AMBERGRIS.value()))
            .pattern("CPC")
            .pattern("PAP")
            .pattern(" P ")
            .define('A', AMItemRegistry.AMBERGRIS.value())
            .define('C', AMItemRegistry.CACHALOT_WHALE_TOOTH.value())
            .define('P', Tags.Items.INGOTS_IRON)
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItemRegistry.EMU_LEGGINGS.value())
            .group("emu_leggings")
            .unlockedBy(getHasName(AMItemRegistry.EMU_LEGGINGS.value()), has(AMItemRegistry.EMU_FEATHER.value()))
            .pattern("FKF")
            .pattern("K K")
            .pattern("F F")
            .define('F', AMItemRegistry.EMU_FEATHER.value())
            .define('K', AMItemRegistry.KANGAROO_HIDE.value())
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItemRegistry.ENDERIOPHAGE_ROCKET.value())
            .group("enderiophage_rocket")
            .unlockedBy(getHasName(AMItemRegistry.ENDERIOPHAGE_ROCKET.value()), has(AMBlockRegistry.CAPSID.value()))
            .pattern(" C ")
            .pattern(" I ")
            .pattern("EIE")
            .define('C', AMBlockRegistry.CAPSID.value())
            .define('E', Tags.Items.END_STONES)
            .define('I', Tags.Items.NUGGETS_IRON)
            .save(output);
    }

    private void food(RecipeOutput output, ItemLike cooked, ItemLike raw, float experience, int furnaceTime, int campfireTime, int smokerTime) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(raw), RecipeCategory.FOOD, cooked, experience, furnaceTime)
            .group(getItemName(cooked))
            .unlockedBy("has_" + getItemName(raw), has(raw))
            .save(output, AlexsMobs.prefix(getItemName(cooked)));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(raw), RecipeCategory.FOOD, cooked, experience, campfireTime)
            .group(getItemName(cooked))
            .unlockedBy("has_" + getItemName(raw), has(raw))
            .save(output, AlexsMobs.prefix(getItemName(cooked) + "_campfire"));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(raw), RecipeCategory.FOOD, cooked, experience, smokerTime)
            .group(getItemName(cooked))
            .unlockedBy("has_" + getItemName(raw), has(raw))
            .save(output, AlexsMobs.prefix(getItemName(cooked) + "_smoke"));
    }

    private void bannerPattern(RecipeOutput output, ItemLike pattern, ItemLike requirement) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, pattern)
            .requires(Items.PAPER)
            .requires(requirement)
            .unlockedBy(getHasName(pattern), has(requirement))
            .group("banner_pattern")
            .save(output);
    }

    protected static void oneToOneConversionRecipe(RecipeOutput recipeOutput, ItemLike result, ItemLike ingredient, @Nullable String group) {
        oneToOneConversionRecipe(recipeOutput, result, ingredient, group, 1);
    }

    protected static void oneToOneConversionRecipe(RecipeOutput recipeOutput, ItemLike result, ItemLike ingredient, @Nullable String group, int resultCount) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result, resultCount)
            .requires(ingredient)
            .group(group)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(recipeOutput, AlexsMobs.prefix(getConversionRecipeName(result, ingredient)));
    }

    protected static void twoByTwoPacker(RecipeOutput recipeOutput, String name, RecipeCategory category, ItemLike packed, ItemLike unpacked) {
        ShapedRecipeBuilder.shaped(category, packed, 1)
            .define('#', unpacked)
            .pattern("##")
            .pattern("##")
            .unlockedBy(getHasName(unpacked), has(unpacked))
            .save(recipeOutput, AlexsMobs.prefix(name));
    }

    protected static void threeByThreePacker(RecipeOutput recipeOutput, RecipeCategory category, ItemLike packed, ItemLike unpacked, String criterionName) {
        ShapelessRecipeBuilder.shapeless(category, packed)
            .requires(unpacked, 9)
            .unlockedBy(criterionName, has(unpacked))
            .save(recipeOutput, AlexsMobs.prefix(getConversionRecipeName(packed, unpacked)));
    }

    protected static void threeByThreePacker(RecipeOutput recipeOutput, RecipeCategory category, ItemLike packed, ItemLike unpacked) {
        threeByThreePacker(recipeOutput, category, packed, unpacked, getHasName(unpacked));
    }
}
