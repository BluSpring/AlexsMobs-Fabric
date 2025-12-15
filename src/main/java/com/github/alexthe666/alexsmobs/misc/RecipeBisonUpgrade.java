package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class RecipeBisonUpgrade extends CustomRecipe {

    public RecipeBisonUpgrade(CraftingBookCategory category) {
        super(category);
    }


    private ItemStack createBoots(CraftingInput input){
        ItemStack boots = ItemStack.EMPTY;
        int fur = 0;
        for (int j = 0; j < input.size(); ++j) {
            ItemStack itemstack1 = input.getItem(j);
            if (itemstack1.is(AMBlockRegistry.BISON_FUR_BLOCK.get().asItem())) {
                fur++;
            }
        }
        if(fur == 1){
            for (int j = 0; j < input.size(); ++j) {
                ItemStack itemstack1 = input.getItem(j);
                boolean notFurred = !itemstack1.has(AMDataComponentTypeRegistry.BISON_FUR);
                if (!itemstack1.isEmpty() && notFurred && itemstack1.getEquipmentSlot() == EquipmentSlot.FEET) {
                    boots = itemstack1;
                }
            }
            if(!boots.isEmpty()){
                ItemStack stack = boots.copy();
                stack.set(AMDataComponentTypeRegistry.BISON_FUR, Unit.INSTANCE);
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return !createBoots(input).isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider provider) {
        return createBoots(input);
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return x * y >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return AMRecipeRegistry.BISON_UPGRADE.get();
    }
}
