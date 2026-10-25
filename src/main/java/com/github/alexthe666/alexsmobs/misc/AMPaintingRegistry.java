package com.github.alexthe666.alexsmobs.misc;

import java.util.Optional;

import com.github.alexthe666.alexsmobs.AlexsMobs;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;

public class AMPaintingRegistry {
    public static final ResourceKey<PaintingVariant> NFT = create("nft");
    public static final ResourceKey<PaintingVariant> DOG_POKER = create("dog_poker");

    private static ResourceKey<PaintingVariant> create(String name) {
        return ResourceKey.create(Registries.PAINTING_VARIANT, Identifier.fromNamespaceAndPath(AlexsMobs.MODID, name));
    }

    public static void bootstrap(BootstrapContext<PaintingVariant> context) {
        context.register(NFT, new PaintingVariant(2, 2, AlexsMobs.prefix("nft"), Optional.empty(), Optional.empty()));
        context.register(DOG_POKER, new PaintingVariant(2, 1, AlexsMobs.prefix("dog_poker"), Optional.empty(), Optional.empty()));
    }
}
