package com.github.alexthe666.alexsmobs.fabric;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class DeferredRegister<T> {
    private final Registry<T> registry;
    private final String modId;

    private final List<T> entries = new ArrayList<>();

    public DeferredRegister(Registry<T> registry, String modId) {
        this.registry = registry;
        this.modId = modId;
    }

    public <U extends T> Holder<T> registerHolder(String id, Supplier<U> supplier) {
        var value = Registry.registerForHolder(this.registry, ResourceLocation.fromNamespaceAndPath(modId, id), supplier.get());
        this.entries.add(value.value());
        return value;
    }

    public <U extends T> U register(String id, Supplier<U> supplier) {
        var value = Registry.register(this.registry, ResourceLocation.fromNamespaceAndPath(modId, id), supplier.get());
        this.entries.add(value);
        return value;
    }

    public List<T> getEntries() {
        return entries;
    }

    public void register() {}

    public static <T> DeferredRegister<T> create(Registry<T> registry, String modId) {
        return new DeferredRegister<>(registry, modId);
    }

    public static <T> DeferredRegister<T> create(ResourceKey<Registry<T>> registryKey, String modId) {
        return create(BuiltInRegistries.REGISTRY.get((ResourceKey) registryKey), modId);
    }
}
