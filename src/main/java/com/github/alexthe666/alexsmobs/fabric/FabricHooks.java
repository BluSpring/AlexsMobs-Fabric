package com.github.alexthe666.alexsmobs.fabric;

import io.github.fabricators_of_create.porting_lib.blocks.extensions.CustomFrictionBlock;
import io.github.fabricators_of_create.porting_lib.blocks.extensions.EntityDestroyBlock;
import io.github.fabricators_of_create.porting_lib.blocks.extensions.StickyBlock;
import io.github.fabricators_of_create.porting_lib.event.common.ItemAttributeModifierEvent;
import io.github.fabricators_of_create.porting_lib.item.extensions.EquipmentItem;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class FabricHooks {
    public static boolean canEquip(ItemStack stack, EquipmentSlot slot, LivingEntity entity) {
        if (stack.getItem() instanceof EquipmentItem equipmentItem)
            return equipmentItem.canEquip(stack, slot, entity);

        return entity.getEquipmentSlotForItem(stack) == slot;
    }

    public static boolean canEntityDestroy(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (state.getBlock() instanceof EntityDestroyBlock destroyBlock) {
            return destroyBlock.canEntityDestroy(state, level, pos, entity);
        }

        if (entity instanceof EnderDragon) {
            return !state.is(BlockTags.DRAGON_IMMUNE);
        } else if ((entity instanceof WitherBoss) || (entity instanceof WitherSkull)) {
            return state.isAir() || WitherBoss.canDestroy(state);
        }

        return true;
    }

    public static ItemAttributeModifiers getAttributeModifiers(ItemStack stack) {
        ItemAttributeModifiers defaultModifiers = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);

        if (defaultModifiers.modifiers().isEmpty()) {
            defaultModifiers = stack.getItem().getDefaultAttributeModifiers();
        }

        ItemAttributeModifierEvent event = new ItemAttributeModifierEvent(stack, defaultModifiers);
        event.sendEvent();
        return event.build();
    }

    public static float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        if (state.getBlock() instanceof CustomFrictionBlock frictionBlock)
            return frictionBlock.getFriction(state, level, pos, entity);

        return state.getBlock().getFriction();
    }

    public static boolean isStickyBlock(BlockState state) {
        if (state.getBlock() instanceof StickyBlock stickyBlock) {
            return stickyBlock.isStickyBlock(state);
        }

        return state.getBlock() == Blocks.SLIME_BLOCK || state.getBlock() == Blocks.HONEY_BLOCK;
    }
}
