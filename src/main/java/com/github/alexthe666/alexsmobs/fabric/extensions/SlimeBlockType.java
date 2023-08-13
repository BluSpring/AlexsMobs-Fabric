package com.github.alexthe666.alexsmobs.fabric.extensions;

import net.minecraft.world.level.block.state.BlockState;

public interface SlimeBlockType {
    default boolean isSlimeBlock(BlockState state) {
        return false;
    }
}
