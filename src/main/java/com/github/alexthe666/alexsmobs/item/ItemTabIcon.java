package com.github.alexthe666.alexsmobs.item;

import java.util.function.Consumer;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class ItemTabIcon extends ItemInventoryOnly {
    public ItemTabIcon(Item.Properties properties) {
        super(properties);
    }

    public static boolean hasCustomEntityDisplay(ItemStack stack) {
        return stack.has(DataComponents.CUSTOM_DATA)
                && stack.get(DataComponents.CUSTOM_DATA).contains("DisplayEntityType");
    }

    public static String getCustomDisplayEntityString(ItemStack stack) {
        return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag()
                .getString("DisplayEntityType");
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept((IClientItemExtensions) AlexsMobs.PROXY.getISTERProperties());
    }

    @Nullable
    public static EntityType getEntityType(@Nullable CompoundTag tag) {
        if (tag != null && tag.contains("DisplayEntityType")) {
            String entityType = tag.getString("DisplayEntityType");
            return BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.tryParse(entityType));
        }
        return null;
    }
}
