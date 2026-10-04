package com.github.alexthe666.alexsmobs.effect;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import io.github.fabricators_of_create.porting_lib.brewing.BrewingRecipe;
import org.jetbrains.annotations.NotNull;

public class ProperBrewingRecipe extends BrewingRecipe {

    private final Ingredient input;
    private final Ingredient ingredient;
    private final ItemStack output;

    public ProperBrewingRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
        super(input, ingredient, output);
        this.input = input;
        this.ingredient = ingredient;
        this.output = output;
    }


    @Override
    public boolean isInput(@NotNull ItemStack stack) {
        if (stack == null) {
            return false;
        } else {
            ItemStack[] matchingStacks = input.getItems();
            if (matchingStacks.length == 0) {
                return stack.isEmpty();
            } else {
                for (ItemStack itemstack : matchingStacks) {
                    if (ItemStack.isSameItem(stack, itemstack) && ItemStack.isSameItemSameComponents(itemstack, stack)) {
                        return true;
                    }
                }

                return false;
            }
        }
    }

}
