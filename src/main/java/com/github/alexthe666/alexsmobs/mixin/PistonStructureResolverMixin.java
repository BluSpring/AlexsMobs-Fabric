package com.github.alexthe666.alexsmobs.mixin;

import com.github.alexthe666.alexsmobs.fabric.extensions.CanStickBlock;
import io.github.fabricators_of_create.porting_lib.block.StickyBlock;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonStructureResolver.class)
public class PistonStructureResolverMixin {
    @Inject(method = "isSticky", at = @At("HEAD"), cancellable = true)
    private static void am$useCustomStickyCheck(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if ((state.getBlock() instanceof StickyBlock stickyBlock)) {
            cir.setReturnValue(stickyBlock.isStickyBlock(state));
        }
    }

    @Inject(method = "canStickToEachOther", at = @At("HEAD"), cancellable = true)
    private static void am$useCustomStickToEachOtherCheck(BlockState state1, BlockState state2, CallbackInfoReturnable<Boolean> cir) {
        if ((state1.getBlock() instanceof CanStickBlock canStickBlock)) {
            cir.setReturnValue(canStickBlock.canStickTo(state1, state2));
        } else if ((state2.getBlock() instanceof CanStickBlock canStickBlock)) {
            cir.setReturnValue(canStickBlock.canStickTo(state2, state1));
        }
    }
}
