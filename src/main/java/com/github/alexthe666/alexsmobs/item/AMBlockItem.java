package com.github.alexthe666.alexsmobs.item;

import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.phys.BlockHitResult;

public class AMBlockItem extends BlockItem implements CustomTabBehavior {
    public AMBlockItem(Block blockSupplier, Item.Properties props) {
        super(blockSupplier, props);
    }

    @Override
    public boolean canFitInsideCraftingRemainingItems() {
        return !(this.getBlock() instanceof ShulkerBoxBlock);
    }

    public void onDestroyed(ItemEntity p_150700_) {
        if (this.getBlock() instanceof ShulkerBoxBlock) {
            ItemStack itemstack = p_150700_.getItem();
            CustomData customData = itemstack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
            CompoundTag compoundtag = customData.copyTag();
            if (compoundtag != null && compoundtag.contains("Items", 9)) {
                ListTag listtag = compoundtag.getList("Items", 10);
                ItemUtils.onContainerDestroyed(p_150700_, listtag.stream().map(CompoundTag.class::cast)
                        .map(tag -> ItemStack.parse(p_150700_.registryAccess(), tag).orElse(ItemStack.EMPTY)).toList());
            }
        }
    }

    @Override
    public boolean canBeHurtBy(ItemStack stack, DamageSource damage) {
        return super.canBeHurtBy(stack, damage) && (this != AMBlockRegistry.TRANSMUTATION_TABLE.asItem()
                || !damage.is(DamageTypeTags.IS_EXPLOSION));
    }

    @Override
    public void fillItemCategory(CreativeModeTab.Output contents) {
        if (this.getBlock().equals(AMBlockRegistry.SAND_CIRCLE)
                || this.getBlock().equals(AMBlockRegistry.RED_SAND_CIRCLE)) {

        } else {
            contents.accept(this);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return this.getBlock().equals(AMBlockRegistry.TRIOPS_EGGS) ? InteractionResult.PASS : super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (this.getBlock().equals(AMBlockRegistry.TRIOPS_EGGS)) {
            BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
            BlockHitResult blockhitresult1 = blockhitresult.withPosition(blockhitresult.getBlockPos().above());
            InteractionResult interactionresult = super.useOn(new UseOnContext(player, hand, blockhitresult1));
            return new InteractionResultHolder<>(interactionresult, player.getItemInHand(hand));
        } else {
            return super.use(level, player, hand);
        }
    }
}
