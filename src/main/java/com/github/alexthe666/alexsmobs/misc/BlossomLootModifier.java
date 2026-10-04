package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.fabricators_of_create.porting_lib.loot.IGlobalLootModifier;
import io.github.fabricators_of_create.porting_lib.tool.ItemAbilities;
import io.github.fabricators_of_create.porting_lib.tool.loot.CanItemPerformAbility;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicates;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.AllOfCondition;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;

public class BlossomLootModifier {
    public static void apply() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (key == Blocks.ACACIA_LEAVES.getLootTable()) {
                if (AMConfig.acaciaBlossomsDropFromLeaves) {
                    tableBuilder.withPool(LootPool.lootPool()
                        .when(AllOfCondition.allOf(
                            InvertedLootItemCondition.invert(
                                // none of these should match
                                AnyOfCondition.anyOf(
                                    MatchTool.toolMatches(
                                        ItemPredicate.Builder.item()
                                            .withSubPredicate(ItemSubPredicates.ENCHANTMENTS, ItemEnchantmentsPredicate.enchantments(
                                                List.of(new EnchantmentPredicate(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH), MinMaxBounds.Ints.atLeast(1)))
                                            ))
                                    ),
                                    MatchTool.toolMatches(
                                        ItemPredicate.Builder.item()
                                            .of(Items.SHEARS)
                                    ),
                                    InvertedLootItemCondition.invert(
                                        CanItemPerformAbility.canItemPerformAbility(ItemAbilities.SHEARS_HARVEST)
                                    )
                                )
                            )
                        ))
                        .setRolls(UniformGenerator.between(0f, AMConfig.acaciaBlossomChance - Mth.floor(AMConfig.acaciaBlossomChance * 0.1f)))
                        .add(
                            LootItem.lootTableItem(AMItemRegistry.ACACIA_BLOSSOM)
                        )
                    );
                }
            }
        });
    }
}