package com.github.alexthe666.alexsmobs.fabric.transfer;

import com.github.alexthe666.alexsmobs.mixin.HopperBlockEntityAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.jetbrains.annotations.NotNull;

public class VanillaHopperItemHandler extends InvWrapper
{
    private final HopperBlockEntity hopper;

    public VanillaHopperItemHandler(HopperBlockEntity hopper)
    {
        super(hopper);
        this.hopper = hopper;
    }

    @Override
    @NotNull
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate)
    {
        if (simulate)
        {
            return super.insertItem(slot, stack, simulate);
        }
        else
        {
            boolean wasEmpty = getInv().isEmpty();

            int originalStackSize = stack.getCount();
            stack = super.insertItem(slot, stack, simulate);

            if (wasEmpty && originalStackSize > stack.getCount())
            {
                if (!((HopperBlockEntityAccessor) hopper).callIsOnCustomCooldown())
                {
                    // This cooldown is always set to 8 in vanilla with one exception:
                    // Hopper -> Hopper transfer sets this cooldown to 7 when this hopper
                    // has not been updated as recently as the one pushing items into it.
                    // This vanilla behavior is preserved by VanillaInventoryCodeHooks#insertStack,
                    // the cooldown is set properly by the hopper that is pushing items into this one.
                    ((HopperBlockEntityAccessor) hopper).callSetCooldown(8);
                }
            }

            return stack;
        }
    }
}
