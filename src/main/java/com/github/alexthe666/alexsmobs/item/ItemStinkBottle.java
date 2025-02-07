package com.github.alexthe666.alexsmobs.item;

import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;

public class ItemStinkBottle extends AMBlockItem {

    public ItemStinkBottle(RegistryObject<Block> blockSupplier, Item.Properties props) {
        super(blockSupplier, props);
    }

    public InteractionResult place(BlockPlaceContext context) {
        InteractionResult result = super.place(context);
        if(result.consumesAction()){
            ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
            if(context.getPlayer() == null){
                context.getLevel().addFreshEntity(new ItemEntity(context.getLevel(),context.getClickedPos().getX() + 0.5F, context.getClickedPos().getY() + 0.5F, context.getClickedPos().getZ() + 0.5F, bottle));
            }else if(!context.getPlayer().addItem(bottle)){
                context.getPlayer().drop(bottle, false);
            }
        }
        return result;
    }
    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
}
