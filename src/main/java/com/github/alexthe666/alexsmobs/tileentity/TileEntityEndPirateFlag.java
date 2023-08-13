package com.github.alexthe666.alexsmobs.tileentity;

import io.github.fabricators_of_create.porting_lib.block.CustomRenderBoundingBoxBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileEntityEndPirateFlag  extends BlockEntity implements CustomRenderBoundingBoxBlockEntity {

    public int ticksExisted;

    public TileEntityEndPirateFlag(BlockPos pos, BlockState state) {
        super(AMTileEntityRegistry.END_PIRATE_FLAG.get(), pos, state);
    }

    public static void commonTick(Level level, BlockPos pos, BlockState state, TileEntityEndPirateFlag entity) {
        entity.tick();
    }

    @Environment(EnvType.CLIENT)
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition.offset(-2, -2, -2), worldPosition.offset(2, 2, 2));
    }

    public void tick() {
        ticksExisted++;
    }
}

