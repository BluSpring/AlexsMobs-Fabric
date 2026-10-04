package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.mojang.serialization.MapCodec;
import io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier;
import io.github.fabricators_of_create.porting_lib.loot.PortingLibLoot;
import io.github.fabricators_of_create.porting_lib.registry.DeferredHolder;
import io.github.fabricators_of_create.porting_lib.registry.DeferredRegister;

public class AMLootRegistry {

    public static void register() {
        AncientDartLootModifier.apply();
        BananaLootModifier.apply();
        BlossomLootModifier.apply();
        PigshoesLootModifier.apply();
    }
}
