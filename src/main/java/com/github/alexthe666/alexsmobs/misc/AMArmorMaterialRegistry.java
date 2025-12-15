package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;

public class AMArmorMaterialRegistry {
    public static final DeferredRegister<ArmorMaterial> DEF_REG = DeferredRegister.create(Registries.ARMOR_MATERIAL, AlexsMobs.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ROADRUNNER_ARMOR_MATERIAL = DEF_REG.register("roadrunner", id -> new ArmorMaterial(Map.of(ArmorItem.Type.BOOTS, 3), 20, SoundEvents.ARMOR_EQUIP_TURTLE, () -> Ingredient.of(AMItemRegistry.ROADRUNNER_FEATHER), List.of(new ArmorMaterial.Layer(id)), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CROCODILE_ARMOR_MATERIAL = DEF_REG.register("crocodile", id -> new ArmorMaterial(Map.of(ArmorItem.Type.CHESTPLATE, 5), 25, SoundEvents.ARMOR_EQUIP_TURTLE, () -> Ingredient.of(AMItemRegistry.CROCODILE_SCUTE), List.of(new ArmorMaterial.Layer(id)), 1, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> CENTIPEDE_ARMOR_MATERIAL = DEF_REG.register("centipede", id -> new ArmorMaterial(Map.of(ArmorItem.Type.LEGGINGS, 6), 22, SoundEvents.ARMOR_EQUIP_TURTLE, () -> Ingredient.of(AMItemRegistry.CENTIPEDE_LEG), List.of(new ArmorMaterial.Layer(id)), 0.5F, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> MOOSE_ARMOR_MATERIAL = DEF_REG.register("moose", id -> new ArmorMaterial(Map.of(ArmorItem.Type.HELMET, 3), 21, SoundEvents.ARMOR_EQUIP_TURTLE, () -> Ingredient.of(AMItemRegistry.MOOSE_ANTLER), List.of(new ArmorMaterial.Layer(id)), 0.5F, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> RACCOON_ARMOR_MATERIAL = DEF_REG.register("raccoon", id -> new ArmorMaterial(Map.of(ArmorItem.Type.HELMET, 3), 21, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(AMItemRegistry.RACCOON_TAIL), List.of(new ArmorMaterial.Layer(id)), 2.5F, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SOMBRERO_ARMOR_MATERIAL = DEF_REG.register("sombrero", id -> new ArmorMaterial(Map.of(ArmorItem.Type.HELMET, 2), 30, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.HAY_BLOCK), List.of(new ArmorMaterial.Layer(id)), 0.5F, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SPIKED_TURTLE_SHELL_ARMOR_MATERIAL = DEF_REG.register("spiked_turtle_shell", id -> new ArmorMaterial(Map.of(ArmorItem.Type.HELMET, 3), 30, SoundEvents.ARMOR_EQUIP_TURTLE, () -> Ingredient.of(AMItemRegistry.SPIKED_SCUTE), List.of(new ArmorMaterial.Layer(id)), 1F, 0.2F));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> FEDORA_ARMOR_MATERIAL = DEF_REG.register("fedora", id -> new ArmorMaterial(Map.of(ArmorItem.Type.HELMET, 2), 30, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.FEATHER), List.of(new ArmorMaterial.Layer(id)), 0.5F, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> EMU_ARMOR_MATERIAL = DEF_REG.register("emu",id -> new ArmorMaterial(Map.of(ArmorItem.Type.LEGGINGS, 4), 20, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(AMItemRegistry.EMU_FEATHER), List.of(new ArmorMaterial.Layer(id)), 0.5F, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> TARANTULA_HAWK_ELYTRA_MATERIAL = DEF_REG.register("tarantula_hawk_elytra", id -> new ArmorMaterial(Map.of(ArmorItem.Type.CHESTPLATE, 3), 5, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.EMPTY, List.of(new ArmorMaterial.Layer(id)), 0, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> FROSTSTALKER_ARMOR_MATERIAL = DEF_REG.register("froststalker", id -> new ArmorMaterial(Map.of(ArmorItem.Type.HELMET, 3), 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.EMPTY, List.of(new ArmorMaterial.Layer(id)), 0.5F, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> ROCKY_ARMOR_MATERIAL = DEF_REG.register("rocky_roller", id -> new ArmorMaterial(Map.of(ArmorItem.Type.HELMET, 5), 10, SoundEvents.ARMOR_EQUIP_TURTLE, () -> Ingredient.of(AMItemRegistry.ROCKY_SHELL), List.of(new ArmorMaterial.Layer(id)), 0.5F, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> FLYING_FISH_MATERIAL = DEF_REG.register("flying_fish", id -> new ArmorMaterial(Map.of(ArmorItem.Type.BOOTS, 1), 8, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(AMItemRegistry.FLYING_FISH), List.of(new ArmorMaterial.Layer(id)), 0F, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> NOVELTY_HAT_MATERIAL = DEF_REG.register("novelty_hat", id -> new ArmorMaterial(Map.of(ArmorItem.Type.HELMET, 2), 30, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(Items.BONE), List.of(new ArmorMaterial.Layer(id)), 0F, 0));
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> KIMONO_MATERIAL = DEF_REG.register("kimono", id -> new ArmorMaterial(Map.of(ArmorItem.Type.CHESTPLATE, 3), 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(ItemTags.WOOL), List.of(new ArmorMaterial.Layer(id)), 0F, 0));
}
