package com.github.alexthe666.alexsmobs.mixin.transfer;

import com.github.alexthe666.alexsmobs.fabric.transfer.InvWrapper;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlotExposedStorage;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import xyz.bluspring.forgecapabilities.capabilities.Capability;
import xyz.bluspring.forgecapabilities.capabilities.ForgeCapabilities;

@Mixin(ChestBlockEntity.class)
public abstract class ChestBlockEntityMixin extends RandomizableContainerBlockEntity {
    protected ChestBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Unique
    private LazyOptional<SlotExposedStorage> chestHandler;

    @Override
    public void setBlockState(BlockState blockState) {
        super.setBlockState(blockState);

        if (this.chestHandler != null) {
            var old = this.chestHandler;
            this.chestHandler = null;
            old.invalidate();
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == ForgeCapabilities.ITEM_HANDLER) {
            if (this.chestHandler == null)
                this.chestHandler = LazyOptional.of(this::createHandler);
        }

        return super.getCapability(cap, side);
    }

    @Unique
    private SlotExposedStorage createHandler() {
        var state = this.getBlockState();

        if (!(state.getBlock() instanceof ChestBlock))
            return new InvWrapper(this);

        var inv = ChestBlock.getContainer((ChestBlock) state.getBlock(), state, this.getLevel(), this.getBlockPos(), true);
        return new InvWrapper(inv == null ? this : inv);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (chestHandler != null) {
            chestHandler.invalidate();
            chestHandler = null;
        }
    }
}
