package com.github.alexthe666.alexsmobs.datagen;

import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class AMItemTagProvider extends ItemTagsProvider {
    public AMItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags) {
        super(output, lookupProvider, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.MEAT)
            .add(AMItemRegistry.RAW_CATFISH.get(), AMItemRegistry.COOKED_CATFISH.get(), AMItemRegistry.FLYING_FISH.get())
            .add(AMItemRegistry.KANGAROO_BURGER.get(), AMItemRegistry.KANGAROO_MEAT.get(), AMItemRegistry.COOKED_KANGAROO_MEAT.get())
            .add(AMItemRegistry.BOILED_EMU_EGG.get())
            .add(AMItemRegistry.BLOBFISH.get())
            .add(AMItemRegistry.MOOSE_RIBS.get(), AMItemRegistry.COOKED_MOOSE_RIBS.get())
            .add(AMItemRegistry.SOPA_DE_MACACO.get())
            .add(AMItemRegistry.LOBSTER_TAIL.get(), AMItemRegistry.COOKED_LOBSTER_TAIL.get());
    }
}
