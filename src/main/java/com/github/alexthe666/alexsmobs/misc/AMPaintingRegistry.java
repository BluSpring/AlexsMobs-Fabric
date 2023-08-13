package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.decoration.PaintingVariant;


public class AMPaintingRegistry {
    public static final LazyRegistrar<PaintingVariant> DEF_REG = LazyRegistrar.create(Registry.PAINTING_VARIANT, AlexsMobs.MODID);

    public static final RegistryObject<PaintingVariant> NFT = DEF_REG.register("nft", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> DOG_POKER = DEF_REG.register("dog_poker", () -> new PaintingVariant(32, 16));
}
