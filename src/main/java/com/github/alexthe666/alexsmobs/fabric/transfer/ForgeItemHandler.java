package com.github.alexthe666.alexsmobs.fabric.transfer;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class ForgeItemHandler extends ItemStackHandler {
    @Override
    public long insertSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
        var snapshot = new ItemSnapshot((int) maxAmount, value -> insertItem(slot, resource.toStack(value), false));
        snapshot.updateSnapshots(transaction);

        return insertItem(slot, resource.toStack((int) maxAmount), true).getCount();
    }

    @NotNull
    public abstract ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate);

    @Override
    public long extractSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
        var snapshot = new ItemSnapshot((int) maxAmount, value -> extractItem(slot, value, false));
        snapshot.updateSnapshots(transaction);

        var extracted = extractItem(slot, (int) maxAmount, true);
        if (resource.matches(extracted)) {
            return extracted.getCount();
        }

        return 0L;
    }

    @NotNull
    public abstract ItemStack extractItem(int slot, int amount, boolean simulate);

    @Override
    public boolean isItemValid(int slot, ItemVariant resource, long amount) {
        return isItemValid(slot, resource.toStack((int) amount));
    }

    public abstract boolean isItemValid(int slot, @NotNull ItemStack stack);

    public static class ItemSnapshot extends SnapshotParticipant<Integer> {
        private int current;
        private Consumer<Integer> callback;

        public void setCurrent(int value) {
            this.current = value;
        }

        public ItemSnapshot(int current, Consumer<Integer> callback) {
            this.current = current;
            this.callback = callback;
        }

        @Override
        protected Integer createSnapshot() {
            return current;
        }

        @Override
        protected void readSnapshot(Integer snapshot) {
            this.current = snapshot;
        }

        @Override
        protected void onFinalCommit() {
            this.callback.accept(this.current);
        }
    }
}
