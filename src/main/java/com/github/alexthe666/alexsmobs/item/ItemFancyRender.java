package com.github.alexthe666.alexsmobs.item;

import java.util.function.Consumer;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import net.minecraft.world.item.Item;

public class ItemFancyRender extends Item {

    public ItemFancyRender(Properties props) {
        super(props);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) AlexsMobs.PROXY.getISTERProperties());
    }
}
