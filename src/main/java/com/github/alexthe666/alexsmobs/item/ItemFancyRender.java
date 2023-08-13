package com.github.alexthe666.alexsmobs.item;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.citadel.forge.extensions.IClientItemExtensions;
import com.github.alexthe666.citadel.forge.extensions.ItemRenderExtension;
import net.minecraft.world.item.Item;

public class ItemFancyRender extends Item implements ItemRenderExtension {

    public ItemFancyRender(Properties props) {
        super(props);
    }

    @Override
    public void initializeClient(java.util.function.Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) AlexsMobs.PROXY.getISTERProperties());
    }
}
