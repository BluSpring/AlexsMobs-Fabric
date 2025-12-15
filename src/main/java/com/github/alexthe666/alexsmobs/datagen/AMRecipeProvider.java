package com.github.alexthe666.alexsmobs.datagen;

import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.misc.AMTagRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class AMRecipeProvider extends RecipeProvider {
    public AMRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output, HolderLookup.Provider registries) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, AMItemRegistry.ANIMAL_DICTIONARY)
            .requires(Items.BOOK)
            .requires(AMTagRegistry.ANIMAL_DICTIONARY_INGREDIENT)
            .requires(Tags.Items.DYES_GREEN)
            .unlockedBy(getHasName(AMItemRegistry.ANIMAL_DICTIONARY), has(Items.BOOK))
            .group("animal_dictionary")
            .save(output);

        oneToOneConversionRecipe(output, AMBlockRegistry.BANANA_SLUG_SLIME_BLOCK, AMItemRegistry.BANANA_SLUG_SLIME, "banana_slug_slime", 9);
        threeByThreePacker(output, RecipeCategory.MISC, AMBlockRegistry.BANANA_SLUG_SLIME_BLOCK, AMItemRegistry.BANANA_SLUG_SLIME);

        bannerPattern(output, AMItemRegistry.BANNER_PATTERN_AUSTRALIA_0, AMItemRegistry.KANGAROO_HIDE);
        bannerPattern(output, AMItemRegistry.BANNER_PATTERN_AUSTRALIA_1, AMItemRegistry.EMU_FEATHER);
        bannerPattern(output, AMItemRegistry.BANNER_PATTERN_BEAR, AMItemRegistry.BEAR_FUR);
        bannerPattern(output, AMItemRegistry.BANNER_PATTERN_BRAZIL, AMItemRegistry.SHED_SNAKE_SKIN);
        bannerPattern(output, AMItemRegistry.BANNER_PATTERN_NEW_MEXICO, AMItemRegistry.TARANTULA_HAWK_WING_FRAGMENT);

        oneToOneConversionRecipe(output, AMBlockRegistry.BISON_FUR_BLOCK, AMItemRegistry.BISON_FUR, "bison_fur", 9);
        threeByThreePacker(output, RecipeCategory.MISC, AMBlockRegistry.BISON_FUR_BLOCK, AMItemRegistry.BISON_FUR);
        twoByTwoPacker(output, RecipeCategory.MISC, Items.BROWN_WOOL, AMItemRegistry.BISON_FUR);

        carpet(output, AMBlockRegistry.BISON_CARPET, AMItemRegistry.BISON_FUR);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItemRegistry.BLOOD_SPRAYER)
            .group("blood_sprayer")
            .unlockedBy(getHasName(AMItemRegistry.BLOOD_SPRAYER), has(AMItemRegistry.MOSQUITO_PROBOSCIS))
            .pattern("SS ")
            .pattern("BBP")
            .pattern("B  ")
            .define('S', AMItemRegistry.BLOOD_SAC)
            .define('P', AMItemRegistry.MOSQUITO_PROBOSCIS)
            .define('B', Items.NETHER_BRICK)
            .save(output);

        food(output, AMItemRegistry.BOILED_EMU_EGG, AMItemRegistry.EMU_EGG, 0.15f, 200, 600, 100);

        oneToOneConversionRecipe(output, AMItemRegistry.FISH_BONES, Items.BONE_MEAL, "bone_meal", 2);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItemRegistry.CENTIPEDE_LEGGINGS)
            .group("centipede_leggings")
            .unlockedBy(getHasName(AMItemRegistry.CENTIPEDE_LEGGINGS), has(AMItemRegistry.CENTIPEDE_LEG))
            .pattern("SSS")
            .pattern("S S")
            .pattern("S S")
            .define('S', AMItemRegistry.CENTIPEDE_LEG)
            .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.TRANSPORTATION, AMItemRegistry.CHORUS_ON_A_STICK)
            .requires(Tags.Items.TOOLS_FISHING_ROD)
            .requires(Items.CHORUS_FRUIT)
            .unlockedBy(getHasName(AMItemRegistry.CHORUS_ON_A_STICK), has(Items.CHORUS_FRUIT))
            .group("chorus_on_a_stick")
            .save(output);

        threeByThreePacker(output, RecipeCategory.MISC, AMItemRegistry.COCKROACH_WING, AMItemRegistry.COCKROACH_WING_FRAGMENT);

        food(output, AMItemRegistry.COOKED_CATFISH, AMItemRegistry.RAW_CATFISH, 0.15f, 200, 600, 100);
        food(output, AMItemRegistry.COOKED_KANGAROO_MEAT, AMItemRegistry.KANGAROO_MEAT, 0.15f, 200, 600, 100);
        food(output, AMItemRegistry.COOKED_LOBSTER_TAIL, AMItemRegistry.LOBSTER_TAIL, 0.15f, 200, 600, 100);
        food(output, AMItemRegistry.COOKED_MOOSE_RIBS, AMItemRegistry.MOOSE_RIBS, 0.15f, 200, 600, 100);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItemRegistry.CROCODILE_CHESTPLATE)
            .group("crocodile_chestplate")
            .unlockedBy(getHasName(AMItemRegistry.CROCODILE_CHESTPLATE), has(AMItemRegistry.CROCODILE_SCUTE))
            .pattern("S S")
            .pattern("SSS")
            .pattern("SSS")
            .define('S', AMItemRegistry.CROCODILE_SCUTE)
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, AMItemRegistry.DIMENSIONAL_CARVER)
            .group("dimensional_carver")
            .unlockedBy(getHasName(AMItemRegistry.DIMENSIONAL_CARVER), has(AMItemRegistry.VOID_WORM_EYE))
            .pattern("MEM")
            .pattern(" X ")
            .pattern(" X ")
            .define('M', AMItemRegistry.VOID_WORM_MANDIBLE)
            .define('E', AMItemRegistry.VOID_WORM_EYE)
            .define('X', Tags.Items.INGOTS_NETHERITE)
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, AMItemRegistry.ECHOLOCATOR)
            .group("echolocator")
            .unlockedBy(getHasName(AMItemRegistry.ECHOLOCATOR), has(AMItemRegistry.AMBERGRIS))
            .pattern("CPC")
            .pattern("PAP")
            .pattern(" P ")
            .define('A', AMItemRegistry.AMBERGRIS)
            .define('C', AMItemRegistry.CACHALOT_WHALE_TOOTH)
            .define('P', Tags.Items.INGOTS_IRON)
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItemRegistry.EMU_LEGGINGS)
            .group("emu_leggings")
            .unlockedBy(getHasName(AMItemRegistry.EMU_LEGGINGS), has(AMItemRegistry.EMU_FEATHER))
            .pattern("FKF")
            .pattern("K K")
            .pattern("F F")
            .define('F', AMItemRegistry.EMU_FEATHER)
            .define('K', AMItemRegistry.KANGAROO_HIDE)
            .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, AMItemRegistry.ENDERIOPHAGE_ROCKET)
            .group("enderiophage_rocket")
            .unlockedBy(getHasName(AMItemRegistry.ENDERIOPHAGE_ROCKET), has(AMBlockRegistry.CAPSID))
            .pattern(" C ")
            .pattern(" I ")
            .pattern("EIE")
            .define('C', AMBlockRegistry.CAPSID)
            .define('E', Tags.Items.END_STONES)
            .define('I', Tags.Items.NUGGETS_IRON)
            .save(output);
    }

    private void food(RecipeOutput output, ItemLike cooked, ItemLike raw, float experience, int furnaceTime, int campfireTime, int smokerTime) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(raw), RecipeCategory.FOOD, cooked, experience, furnaceTime)
            .group(getItemName(cooked))
            .save(output, getItemName(cooked));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(raw), RecipeCategory.FOOD, cooked, experience, campfireTime)
            .group(getItemName(cooked))
            .save(output, getItemName(cooked) + "_campfire");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(raw), RecipeCategory.FOOD, cooked, experience, smokerTime)
            .group(getItemName(cooked))
            .save(output, getItemName(cooked) + "_smoke");
    }

    private void bannerPattern(RecipeOutput output, ItemLike pattern, ItemLike requirement) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, pattern)
            .requires(Items.PAPER)
            .requires(requirement)
            .unlockedBy(getHasName(pattern), has(requirement))
            .group("banner_pattern")
            .save(output);
    }
}
