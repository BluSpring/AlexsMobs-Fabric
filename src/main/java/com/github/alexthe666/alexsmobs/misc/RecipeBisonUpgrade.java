package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import com.github.alexthe666.alexsmobs.component.AMDataComponentRegistry;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;

public class RecipeBisonUpgrade extends CustomRecipe {
    public static final RecipeBisonUpgrade INSTANCE = new RecipeBisonUpgrade();
    public static final MapCodec<RecipeBisonUpgrade> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, RecipeBisonUpgrade> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public RecipeBisonUpgrade() {
        super();
    }


    private ItemStack createBoots(CraftingInput container){
        ItemStack boots = ItemStack.EMPTY;
        int fur = 0;
        for (int j = 0; j < container.size(); ++j) {
            ItemStack itemstack1 = container.getItem(j);
            if (itemstack1.is(AMBlockRegistry.BISON_FUR_BLOCK.get().asItem())) {
                fur++;
            }
        }
        if(fur == 1){
            for (int j = 0; j < container.size(); ++j) {
                ItemStack itemstack1 = container.getItem(j);
                boolean notFurred = !itemstack1.has(AMDataComponentRegistry.BISON_FUR);
                Equippable equipable = itemstack1.get(DataComponents.EQUIPPABLE);
                if (!itemstack1.isEmpty() && notFurred && (itemstack1.getEquipmentSlot() == EquipmentSlot.FEET || (equipable != null && equipable.slot() == EquipmentSlot.FEET))) {
                    boots = itemstack1;
                }
            }
            if(!boots.isEmpty()){
                ItemStack stack = boots.copy();
                stack.set(AMDataComponentRegistry.BISON_FUR, Unit.INSTANCE);
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean matches(CraftingInput inv, Level worldIn) {
        return !createBoots(inv).isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput container) {
        return createBoots(container);
    }

//    @Override
//    public boolean canCraftInDimensions(int x, int y) {
//        return x * y >= 2;
//    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return AMRecipeRegistry.BISON_UPGRADE.get();
    }
}
