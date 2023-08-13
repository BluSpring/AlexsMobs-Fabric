package com.github.alexthe666.alexsmobs.item;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.citadel.forge.extensions.IClientItemExtensions;
import com.github.alexthe666.citadel.forge.extensions.ItemRenderExtension;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.world.level.block.Block;

public class BlockItemAMRender extends AMBlockItem implements ItemRenderExtension {

    public BlockItemAMRender(RegistryObject<Block> blockSupplier, Properties props) {
        super(blockSupplier, props);
    }

    @Override
    public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) AlexsMobs.PROXY.getISTERProperties());
    }
}
