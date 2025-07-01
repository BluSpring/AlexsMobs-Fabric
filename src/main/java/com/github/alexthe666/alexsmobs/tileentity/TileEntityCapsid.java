package com.github.alexthe666.alexsmobs.tileentity;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.block.BlockCapsid;
import com.github.alexthe666.alexsmobs.entity.AMEntityRegistry;
import com.github.alexthe666.alexsmobs.entity.EntityEnderiophage;
import com.github.alexthe666.alexsmobs.fabric.transfer.ForgeItemHandler;
import com.github.alexthe666.alexsmobs.fabric.transfer.ForgeItemHandlerHelper;
import com.github.alexthe666.alexsmobs.fabric.transfer.SidedInvWrapper;
import com.github.alexthe666.alexsmobs.message.MessageUpdateCapsid;
import com.github.alexthe666.alexsmobs.misc.AMSoundRegistry;
import com.github.alexthe666.alexsmobs.misc.CapsidRecipe;
import io.github.fabricators_of_create.porting_lib.block.CustomDataPacketHandlingBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemHandlerHelper;
import io.github.fabricators_of_create.porting_lib.transfer.item.SlotExposedStorage;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleItemStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndRodBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class TileEntityCapsid extends BaseContainerBlockEntity implements WorldlyContainer, CustomDataPacketHandlingBlockEntity {
    private static final int[] slotsTop = new int[]{0};
    public int ticksExisted;
    public float prevFloatUpProgress;
    public float floatUpProgress;
    public float prevYawSwitchProgress;
    public float yawSwitchProgress;
    public boolean vibratingThisTick = false;
    private final CapsidTransferWrapper transferWrapper = new CapsidTransferWrapper();
    private float yawTarget = 0;
    private int transformTime = 0;
    private boolean fnaf = false;
    private CapsidRecipe lastRecipe = null;
    private NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);

    public TileEntityCapsid(BlockPos pos, BlockState state) {
        super(AMTileEntityRegistry.CAPSID.get(), pos, state);
    }

    public static void commonTick(Level level, BlockPos pos, BlockState state, TileEntityCapsid entity) {
        entity.tick();
    }

    public Storage<ItemVariant> getSidedStorage(Direction direction) {
        if (direction == Direction.UP || direction == Direction.DOWN) {
            return transferWrapper;
        }

        return null;
    }

    public void tick() {
        prevFloatUpProgress = floatUpProgress;
        prevYawSwitchProgress = yawSwitchProgress;
        ticksExisted++;
        vibratingThisTick = false;
        if (!this.getItem(0).isEmpty()) {
            BlockEntity up = level.getBlockEntity(this.worldPosition.above());
            if (up instanceof Container) {
                if (floatUpProgress >= 1) {
                    var handler = ItemStorage.SIDED.find(level, this.worldPosition.above(), Direction.UP);
                    if (handler != null) {
                        try (Transaction transaction = Transaction.openOuter()) {
                            var stack = this.getItem(0);
                            var originalCount = stack.getCount();
                            var inserted = handler.simulateInsert(ItemVariant.of(stack), originalCount, transaction);
                            if (inserted >= originalCount) {
                                handler.insert(ItemVariant.of(stack), originalCount, transaction);
                                transaction.commit();
                                this.setItem(0, ItemStack.EMPTY);
                            }
                        }
                    }
                    yawTarget = 0F;
                    floatUpProgress = 0F;
                    yawSwitchProgress = 0F;
                } else {
                    if (up instanceof TileEntityCapsid) {
                        yawTarget = Mth.wrapDegrees(((TileEntityCapsid) up).getBlockAngle() - this.getBlockAngle());
                    }else{
                        yawTarget = 0F;
                    }
                    if(yawTarget < yawSwitchProgress){
                        yawSwitchProgress += yawTarget * 0.1F;
                    }else if(yawTarget > yawSwitchProgress){
                        yawSwitchProgress += yawTarget * 0.1F;
                    }
                    floatUpProgress += 0.05F;
                }
            } else {
                floatUpProgress = 0F;
            }
            if(this.getItem(0).getItem() == Items.ENDER_EYE && level.getBlockState(this.getBlockPos().below()).getBlock() == Blocks.END_ROD && level.getBlockState(this.getBlockPos().below()).getValue(EndRodBlock.FACING).getAxis() == Direction.Axis.Y){
                vibratingThisTick = true;
                if(transformTime > 20){
                    this.setItem(0, ItemStack.EMPTY);
                    this.level.destroyBlock(this.getBlockPos(), false);
                    this.level.destroyBlock(this.getBlockPos().below(), false);
                    EntityEnderiophage phage = AMEntityRegistry.ENDERIOPHAGE.get().create(level);
                    phage.setPos(this.getBlockPos().getX() + 0.5F, this.getBlockPos().getY() - 1.0F, this.getBlockPos().getZ() + 0.5F);
                    phage.setVariant(0);
                    if(!level.isClientSide){
                        level.addFreshEntity(phage);
                    }
                }
            }else if(!this.getItem(0).isEmpty() && level.getBlockState(this.getBlockPos().above()).getBlock() != this.getBlockState().getBlock()){
                if(lastRecipe != null && lastRecipe.matches(this.getItem(0))){
                    floatUpProgress = 0.0F;
                    vibratingThisTick = true;
                    if(transformTime == 1 && (AlexsMobs.isAprilFools() || new Random().nextInt(100) == 0)){
                        fnaf = true;
                        level.playSound(null, this.getBlockPos(), AMSoundRegistry.MOSQUITO_CAPSID_CONVERT.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                    if(transformTime > (fnaf ? Math.max(160, lastRecipe.getTime()) : lastRecipe.getTime())) {
                        ItemStack current = this.getItem(0).copy();
                        current.shrink(1);
                        fnaf = false;
                        if(!current.isEmpty()){
                            ItemEntity itemEntity = new ItemEntity(this.level, this.getBlockPos().getX() + 0.5F, this.getBlockPos().getY() + 0.5F, this.getBlockPos().getZ() + 0.5F, current);
                            if(!level.isClientSide){
                                level.addFreshEntity(itemEntity);
                            }
                        }
                        this.setItem(0, lastRecipe.getResult().copy());
                    }
                }
            }
        }
        if(!vibratingThisTick){
            transformTime = 0;
        }else{
            transformTime++;
        }
    }

    @Environment(EnvType.CLIENT)
    public net.minecraft.world.phys.AABB getRenderBoundingBox() {
        return new net.minecraft.world.phys.AABB(worldPosition, worldPosition.offset(1, 2, 1));
    }

    @Override
    public int getContainerSize() {
        return this.stacks.size();
    }

    @Override
    public ItemStack getItem(int index) {
        return this.stacks.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        if (!this.stacks.get(index).isEmpty()) {
            ItemStack itemstack;

            if (this.stacks.get(index).getCount() <= count) {
                itemstack = this.stacks.get(index);
                this.stacks.set(index, ItemStack.EMPTY);
                return itemstack;
            } else {
                itemstack = this.stacks.get(index).split(count);

                if (this.stacks.get(index).isEmpty()) {
                    this.stacks.set(index, ItemStack.EMPTY);
                }

                return itemstack;
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    public ItemStack getStackInSlotOnClosing(int index) {
        if (!this.stacks.get(index).isEmpty()) {
            ItemStack itemstack = this.stacks.get(index);
            this.stacks.set(index, itemstack);
            return itemstack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        boolean flag = !stack.isEmpty() && stack.sameItem(this.stacks.get(index)) && ItemStack.tagMatches(stack, this.stacks.get(index));
        this.stacks.set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        lastRecipe = AlexsMobs.PROXY.getCapsidRecipeManager().getRecipeFor(stack);
        this.saveAdditional(this.getUpdateTag());
        if (!level.isClientSide) {
            AlexsMobs.sendMSGToAll(new MessageUpdateCapsid(this.getBlockPos().asLong(), stacks.get(0)));
        }
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, this.stacks);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        ContainerHelper.saveAllItems(compound, this.stacks);
    }

    @Override
    public void startOpen(Player player) {
    }

    @Override
    public void stopOpen(Player player) {
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.stacks.clear();
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return slotsTop;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return false;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
        if (packet != null && packet.getTag() != null) {
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
            ContainerHelper.loadAllItems(packet.getTag(), this.stacks);
        }
    }

    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack lvt_2_1_ = this.stacks.get(index);
        if (lvt_2_1_.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.stacks.set(index, ItemStack.EMPTY);
            return lvt_2_1_;
        }
    }

    @Override
    public Component getDisplayName() {
        return getDefaultName();
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.alexsmobs.capsid");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.getContainerSize(); i++) {
            if (!this.getItem(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public float getBlockAngle() {
        if (this.getBlockState().getBlock() instanceof BlockCapsid) {
            Direction dir = this.getBlockState().getValue(BlockCapsid.HORIZONTAL_FACING);
            return dir.toYRot();
        }
        return 0.0F;
    }

    private class CapsidTransferWrapper implements Storage<ItemVariant> {
        @Override
        public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            var snapshot = new ForgeItemHandler.ItemSnapshot((int) maxAmount, value -> {
                var currentStack = TileEntityCapsid.this.getItem(0);
                if (resource.matches(currentStack)) {
                    currentStack.setCount(currentStack.getCount() + value);
                    TileEntityCapsid.this.setItem(0, currentStack);
                } else {
                    TileEntityCapsid.this.setItem(0, resource.toStack(value));
                }
            });
            snapshot.updateSnapshots(transaction);

            var item = TileEntityCapsid.this.getItem(0);
            if (item.isEmpty()) {
                var total = (int) Math.min(64L, maxAmount);
                snapshot.setCurrent(total);
                snapshot.updateSnapshots(transaction);
                return total;
            } else if (resource.matches(item)) {
                var count = item.getCount();
                var capacity = item.getMaxStackSize();
                var total = (int) Math.min(capacity - count, maxAmount);
                snapshot.setCurrent(total);
                snapshot.updateSnapshots(transaction);

                return total;
            }

            return 0;
        }

        @Override
        public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            var snapshot = new ForgeItemHandler.ItemSnapshot(0, value -> TileEntityCapsid.this.setItem(0, ItemStack.EMPTY));
            snapshot.updateSnapshots(transaction);

            var item = TileEntityCapsid.this.getItem(0);

            if (item.isEmpty() || resource.matches(item)) {
                if (maxAmount >= item.getCount()) {
                    return Math.min(maxAmount, item.getCount());
                }
            }

            return 0;
        }

        @Override
        public Iterator<StorageView<ItemVariant>> iterator() {
            var list = new ArrayList<StorageView<ItemVariant>>();
            for (ItemStack stack : TileEntityCapsid.this.stacks) {
                list.add(new SingleItemStorage() {
                    @Override
                    public long getCapacity() {
                        return 64;
                    }

                    @Override
                    protected long getCapacity(ItemVariant variant) {
                        if (variant.isBlank())
                            return 64;

                        return variant.getItem().getMaxStackSize();
                    }

                    @Override
                    public ItemVariant getResource() {
                        return ItemVariant.of(stack);
                    }

                    @Override
                    public long getAmount() {
                        return stack.getCount();
                    }
                });
            }

            return list.iterator();
        }
    }
}
