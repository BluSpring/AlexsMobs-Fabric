package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import com.github.alexthe666.alexsmobs.enchantment.AMEnchantmentRegistry;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import java.lang.reflect.Field;
import java.util.List;

public class AMItemGroup {
    private static final AMItemGroup deferred = new AMItemGroup();

    public static final CreativeModeTab INSTANCE = FabricItemGroupBuilder
            .create(new ResourceLocation(AlexsMobs.MODID, "items"))
            .icon(deferred::makeIcon)
            .appendItems(deferred::fillItemList)
            .build();

    private AMItemGroup() {
        super();
    }

    public ItemStack makeIcon() {
        return new ItemStack(AMItemRegistry.TAB_ICON.get());
    }

    @Environment(EnvType.CLIENT)
    public void fillItemList(List<ItemStack> items) {
        //super.fillItemList(items);
        try {
            for (Field f : AMEffectRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Potion) {
                    ItemStack potionStack = AMEffectRegistry.createPotion((Potion)obj);
                    items.add(potionStack);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            for (Field f : AMEnchantmentRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Enchantment ) {
                    Enchantment enchant = (Enchantment)obj;
                    if(/*enchant.isAllowedOnBooks()*/ true){
                        items.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchant, enchant.getMaxLevel())));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
