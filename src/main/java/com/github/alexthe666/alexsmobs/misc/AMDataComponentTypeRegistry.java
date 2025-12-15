package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AMDataComponentTypeRegistry {
    public static final DeferredRegister.DataComponents DEF_REG = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, AlexsMobs.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Unit>> BISON_FUR = DEF_REG.registerComponentType("has_bison_fur", builder -> builder.persistent(Unit.CODEC));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceKey<EntityType<?>>>> DISPLAY_ENTITY_TYPE = DEF_REG.registerComponentType("display_entity_type", builder -> builder.persistent(ResourceKey.codec(Registries.ENTITY_TYPE)).networkSynchronized(ResourceKey.streamCodec(Registries.ENTITY_TYPE)));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> DISPLAY_MOB_FLAGS = DEF_REG.registerComponentType("display_mob_flags", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT));
}
