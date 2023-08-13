package com.github.alexthe666.alexsmobs.mixin.event;

import com.github.alexthe666.alexsmobs.event.ServerEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "hasCorrectToolForDrops", at = @At("RETURN"), cancellable = true)
    public void am$harvestCheck(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!ServerEvents.onHarvestCheck((Player) (Object) this)) {
            cir.setReturnValue(false);
        }
    }
}
