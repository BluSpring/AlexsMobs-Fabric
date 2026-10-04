package com.github.alexthe666.alexsmobs.fabric;

import io.github.fabricators_of_create.porting_lib.blocks.extensions.EntityDestroyBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EventHooks {
    public static boolean canEntityGrief(Level level, Entity entity) {
        return level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
    }

    public static boolean canEntityDestroy(Level level, BlockPos pos, LivingEntity entity) {
        if (!level.isLoaded(pos))
            return false;
        BlockState state = level.getBlockState(pos);
        return EventHooks.canEntityGrief(level, entity) && FabricHooks.canEntityDestroy(state, level, pos, entity) && EventHooks.onEntityDestroyBlock(entity, pos, state);
    }

    public static boolean onEntityDestroyBlock(LivingEntity entity, BlockPos pos, BlockState state) {
//        return !NeoForge.EVENT_BUS.post(new LivingDestroyBlockEvent(entity, pos, state)).isCanceled();
        return true;
    }
}
