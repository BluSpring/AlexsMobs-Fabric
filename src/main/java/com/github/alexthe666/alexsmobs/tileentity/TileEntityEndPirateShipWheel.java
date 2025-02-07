package com.github.alexthe666.alexsmobs.tileentity;

import io.github.fabricators_of_create.porting_lib.block.CustomRenderBoundingBoxBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class TileEntityEndPirateShipWheel extends BlockEntity implements CustomRenderBoundingBoxBlockEntity {

    private float wheelRot;
    private float prevWheelRot;
    private float targetWheelRot;
    public int ticksExisted;


    public TileEntityEndPirateShipWheel(BlockPos pos, BlockState state) {
        super(AMTileEntityRegistry.END_PIRATE_SHIP_WHEEL.get(), pos, state);
    }

    public static void commonTick(Level level, BlockPos pos, BlockState state, TileEntityEndPirateShipWheel entity) {
        entity.tick();
    }

    @Environment(EnvType.CLIENT)
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition.offset(-2, -2, -2), worldPosition.offset(2, 2, 2));
    }

    public void tick() {
        prevWheelRot = wheelRot;
        float scale = Math.abs(Math.abs(targetWheelRot - wheelRot) / 180F);
        float progress = Mth.clamp(10F * scale, 1, 10);
        if(wheelRot < targetWheelRot){
            wheelRot = Math.min(targetWheelRot, wheelRot + progress);
        }
        if(wheelRot > targetWheelRot){
            wheelRot = Math.max(targetWheelRot, wheelRot - progress);
        }
        ticksExisted++;
    }

    public void rotate(boolean clockwise){
        if(Math.abs(wheelRot - targetWheelRot) < 90F){
            if(clockwise){
                targetWheelRot += 180;
            }else{
                targetWheelRot -= 180;

            }
        }
    }

    public float getWheelRot(float partialTick){
        return prevWheelRot + (wheelRot - prevWheelRot) * partialTick;
    }
}
