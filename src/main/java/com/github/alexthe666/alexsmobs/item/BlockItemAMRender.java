package com.github.alexthe666.alexsmobs.item;

import java.util.function.Consumer;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BlockItemAMRender extends AMBlockItem {

    public BlockItemAMRender(DeferredHolder<Block, Block> blockSupplier, Properties props) {
        super(blockSupplier, props);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) AlexsMobs.PROXY.getISTERProperties());
    }
}
