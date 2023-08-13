package com.github.alexthe666.alexsmobs.fabric.extensions;

import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public interface CanStickBlock {
    boolean canStickTo(BlockState state, @NotNull BlockState other);
}
