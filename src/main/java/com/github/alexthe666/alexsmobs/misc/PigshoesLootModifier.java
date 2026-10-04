package com.github.alexthe666.alexsmobs.misc;

import static io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier.LOOT_CONDITIONS_CODEC;

import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;

public class PigshoesLootModifier {
    public static void apply() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (key == BuiltInLootTables.PIGLIN_BARTERING) {
                if (AMConfig.addLootToChests) {
                    tableBuilder.withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(0f, (float) AMConfig.tusklinShoesBarteringChance))
                        .add(
                            LootItem.lootTableItem(AMItemRegistry.PIGSHOES)
                        )
                    );
                }
            }
        });
    }
}