package com.github.alexthe666.alexsmobs.fabric.transfer;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class ForgeItemHandler extends ItemStackHandler {
    @Override
    public long insertSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
        return insertItem(slot, resource.toStack((int) maxAmount), false).getCount();
    }

    @NotNull
    public abstract ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate);

    @Override
    public long extractSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
        var extract = extractItem(slot, (int) maxAmount, false);
        return extract.getCount();
    }

    @NotNull
    public abstract ItemStack extractItem(int slot, int amount, boolean simulate);

    @Override
    public boolean isItemValid(int slot, ItemVariant resource, long amount) {
        return isItemValid(slot, resource.toStack((int) amount));
    }

    public abstract boolean isItemValid(int slot, @NotNull ItemStack stack);
}
