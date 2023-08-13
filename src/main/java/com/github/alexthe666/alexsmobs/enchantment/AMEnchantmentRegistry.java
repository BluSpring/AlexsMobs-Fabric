package com.github.alexthe666.alexsmobs.enchantment;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.item.ItemStraddleboard;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import xyz.bluspring.enumextension.extensions.EnchantmentCategoryExtension;

public class AMEnchantmentRegistry {

    public static final LazyRegistrar<Enchantment> DEF_REG = LazyRegistrar.create(Registry.ENCHANTMENT, AlexsMobs.MODID);
            public static final EnchantmentCategory STRADDLEBOARD = EnchantmentCategoryExtension.create("straddleboard", (item -> item instanceof ItemStraddleboard));

    public static final RegistryObject<Enchantment> STRADDLE_JUMP = DEF_REG.register("straddle_jump", () -> new StraddleJumpEnchantment(Enchantment.Rarity.COMMON, STRADDLEBOARD, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> STRADDLE_LAVAWAX = DEF_REG.register("lavawax", () -> new StraddleEnchantment(Enchantment.Rarity.UNCOMMON, STRADDLEBOARD, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> STRADDLE_SERPENTFRIEND = DEF_REG.register("serpentfriend", () -> new StraddleEnchantment(Enchantment.Rarity.RARE, STRADDLEBOARD, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> STRADDLE_BOARDRETURN = DEF_REG.register("board_return", () -> new StraddleEnchantment(Enchantment.Rarity.UNCOMMON, STRADDLEBOARD, EquipmentSlot.MAINHAND));
}
