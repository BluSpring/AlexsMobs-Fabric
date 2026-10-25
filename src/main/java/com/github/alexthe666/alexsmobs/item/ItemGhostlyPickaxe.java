package com.github.alexthe666.alexsmobs.item;

import java.util.function.Consumer;

import org.jspecify.annotations.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ItemGhostlyPickaxe extends Item {

    public ItemGhostlyPickaxe(Properties props) {
        super(props
            .pickaxe(ToolMaterial.IRON, 1f, -2.8f)
            .repairable(Items.PHANTOM_MEMBRANE)
        );
    }

    public static boolean shouldStoreInGhost(LivingEntity player, ItemStack stack){
        return player instanceof Player && ((Player)player).getInventory().getFreeSlot() == -1 ;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState blockState) {
        return blockState.is(BlockTags.MINEABLE_WITH_PICKAXE) ? 20.0F : 1.0F;
    }

    public static void putItemInGhostInventoryOrDrop(LivingEntity user, ItemStack pickaxe, ItemStack item) {
        ItemContainerContents contents = pickaxe.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        SimpleContainer container = new SimpleContainer(9);
        contents.copyInto(container.getItems());

        if(user instanceof Player player){
            if(player.getInventory().add(item)){
                return;
            }else if(container.canAddItem(item)){
                ItemStack leftover = container.addItem(item);
                pickaxe.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(container.getItems()));
                item = leftover;

            }
        }
        if(!item.isEmpty() && user.level() instanceof ServerLevel level){
            user.spawnAtLocation(level, item);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity owner, @Nullable EquipmentSlot equipmentSlot) {
        super.inventoryTick(stack, level, owner, equipmentSlot);
        if(owner instanceof Player player){
            if(player.tickCount % 3 == 0){
                ItemContainerContents contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);

                if (contents == ItemContainerContents.EMPTY)
                    return;

                SimpleContainer container = new SimpleContainer(9);
                boolean flag = false;
                contents.copyInto(container.getItems());
                for(int slot = 0; slot < container.getContainerSize(); slot++) {
                    ItemStack stackAt = container.getItem(slot);
                    if(!stackAt.isEmpty() && player.addItem(stackAt)){
                        container.removeItem(slot, stackAt.getCount());
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(container.getItems()));
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, display, builder, tooltipFlag);
        if (stack.has(DataComponents.CONTAINER)) {
            ItemContainerContents contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
            int i = 0;
            int j = 0;

            for(int slot = 0; slot < contents.getSlots(); slot++) {
                ItemStack itemstack = contents.getStackInSlot(slot);
                if (!itemstack.isEmpty()) {
                    ++j;
                    if (i <= 4) {
                        ++i;
                        MutableComponent mutablecomponent = itemstack.getHoverName().copy();
                        mutablecomponent.append(" x").append(String.valueOf(itemstack.getCount()));
                        builder.accept(mutablecomponent.withStyle(ChatFormatting.DARK_AQUA));
                    }
                }
            }

            if (j - i > 0) {
                builder.accept(Component.translatable("container.shulkerBox.more", j - i).withStyle(ChatFormatting.DARK_AQUA, ChatFormatting.ITALIC));
            }
        }
    }

    private void dropAllContents(Level level, Vec3 vec3, ItemStack pickaxe){
        if (pickaxe.has(DataComponents.CONTAINER)) {
            ItemContainerContents contents = pickaxe.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
            SimpleContainer container = new SimpleContainer(9);
            contents.copyInto(container.getItems());
            for (int slot = 0; slot < container.getContainerSize(); slot++) {
                ItemStack itemstack = container.getItem(slot);
                if (!itemstack.isEmpty()) {
                    ItemEntity itemEntity = new ItemEntity(level, vec3.x, vec3.y, vec3.z, itemstack.copy());
                    if(level.addFreshEntity(itemEntity)){
                        container.removeItem(slot, itemstack.getCount());
                    }
                }
            }
            pickaxe.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(container.getItems()));
        }
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        dropAllContents(itemEntity.level(), itemEntity.position(), itemEntity.getItem());
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<Item> onBroken) {
        int i = super.damageItem(stack, amount, entity, onBroken);
        if(i + stack.getDamageValue() >= stack.getMaxDamage() && entity != null){
            dropAllContents(entity.level(), entity.position(), stack);
        }
        return i;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 700;
    }
}
