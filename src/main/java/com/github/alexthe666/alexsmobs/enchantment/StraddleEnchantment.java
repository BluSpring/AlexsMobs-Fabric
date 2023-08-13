package com.github.alexthe666.alexsmobs.enchantment;

import com.github.alexthe666.alexsmobs.config.AMConfig;
import io.github.fabricators_of_create.porting_lib.enchant.CustomEnchantingTableBehaviorEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class StraddleEnchantment extends Enchantment implements CustomEnchantingTableBehaviorEnchantment {

    protected StraddleEnchantment(Rarity r, EnchantmentCategory type, EquipmentSlot... types) {
        super(r, type, types);
    }

    public int getMinCost(int i) {
        return 6 + (i + 1) * 6;
    }

    public int getMaxCost(int i) {
        return super.getMinCost(i) + 10;
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean isTradeable() {
        return super.isTradeable() && AMConfig.straddleboardEnchants;
    }

    public boolean isDiscoverable() {
        return super.isDiscoverable() && AMConfig.straddleboardEnchants;
    }

    public boolean isAllowedOnBooks() {
        return/* super.isAllowedOnBooks() &&*/ AMConfig.straddleboardEnchants;
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return CustomEnchantingTableBehaviorEnchantment.super.canApplyAtEnchantingTable(stack) && AMConfig.straddleboardEnchants;
    }

}
