package com.github.alexthe666.alexsmobs.item;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import io.github.fabricators_of_create.porting_lib.item.extensions.ArmorTextureItem;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;

public class ItemTarantulaHawkElytra extends ArmorItem implements ArmorTextureItem, FabricElytraItem {

    public ItemTarantulaHawkElytra(Item.Properties props, Holder<ArmorMaterial> mat) {
        super(mat, Type.CHESTPLATE, props);
    }

    public static boolean isUsable(ItemStack stack) {
        return stack.getDamageValue() < stack.getMaxDamage() - 1;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        return super.use(worldIn, playerIn, handIn);
    }

    @Override
    public boolean useCustomElytra(LivingEntity entity, ItemStack stack, boolean tickElytra) {
        return ElytraItem.isFlyEnabled(stack);
    }

    @Override
    public void doVanillaElytraTick(LivingEntity entity, ItemStack stack) {
        int flightTicks = entity.getFallFlyingTicks();
        if (!entity.level().isClientSide && (flightTicks + 1) % 20 == 0) {
            stack.hurtAndBreak(1, entity, EquipmentSlot.CHEST);
        }
    }

    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == AMItemRegistry.TARANTULA_HAWK_WING_FRAGMENT;
    }

    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.CHEST;
    }

    @Override
    @Nullable
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return AlexsMobs.prefix("textures/armor/tarantula_hawk_elytra.png");
    }
}
