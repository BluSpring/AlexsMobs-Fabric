package com.github.alexthe666.alexsmobs.datagen;

import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class AMBlockLootTableProvider extends BlockLootSubProvider {
    protected AMBlockLootTableProvider(Set<Item> explosionResistant, FeatureFlagSet enabledFeatures, HolderLookup.Provider registries) {
        super(explosionResistant, enabledFeatures, registries);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return AMBlockRegistry.DEF_REG.getEntries().stream().map(e -> (Block) e.get()).toList();
    }

    @Override
    protected void generate() {
        dropSelf(AMBlockRegistry.BANANA_PEEL.get());
        dropSelf(AMBlockRegistry.BANANA_SLUG_SLIME_BLOCK.get());
        dropSelf(AMBlockRegistry.BISON_CARPET.get());
        dropSelf(AMBlockRegistry.BISON_FUR_BLOCK.get());
        dropWhenSilkTouch(AMBlockRegistry.CAIMAN_EGG.get());
        dropSelf(AMBlockRegistry.CAPSID.get());
        dropWhenSilkTouch(AMBlockRegistry.CROCODILE_EGG.get());
        dropWhenSilkTouch(AMBlockRegistry.CRYSTALIZED_BANANA_SLUG_MUCUS.get());
        add(AMBlockRegistry.ENDER_RESIDUE.get(), noDrop());
        dropSelf(AMBlockRegistry.GUSTMAKER.get());
        dropSelf(AMBlockRegistry.HUMMINGBIRD_FEEDER.get());
        add(AMBlockRegistry.LEAFCUTTER_ANT_CHAMBER.get(), LootTable.lootTable()
            .withPool(
                LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1f))
                    .add(
                        AlternativesEntry.alternatives(
                            LootItem.lootTableItem(AMBlockRegistry.LEAFCUTTER_ANT_CHAMBER)
                                .when(hasSilkTouch()),

                            AlternativesEntry.alternatives(
                                LootItem.lootTableItem(AMItemRegistry.LEAFCUTTER_ANT_PUPA)
                                    .when(
                                        BonusLevelTableCondition.bonusLevelFlatChance(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE),
                                            0.1f, 0.14285715f, 0.25f, 1f
                                        )
                                    ),
                                LootItem.lootTableItem(AMBlockRegistry.LEAFCUTTER_ANT_CHAMBER)
                            )
                                .when(ExplosionCondition.survivesExplosion())
                        )
                    )
            )
        );

        add(AMBlockRegistry.LEAFCUTTER_ANTHILL.get(), LootTable.lootTable()
            .withPool(
                LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1f))
                    .add(
                        LootItem.lootTableItem(AMBlockRegistry.LEAFCUTTER_ANTHILL)
                            .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY))
                    )
                    .when(hasSilkTouch())
            )
        );

        dropWhenSilkTouch(AMBlockRegistry.PLATYPUS_EGG.get());
        dropWhenSilkTouch(AMBlockRegistry.RAINBOW_GLASS.get());
        dropSelf(AMBlockRegistry.RED_SAND_CIRCLE.get());
        dropSelf(AMBlockRegistry.SAND_CIRCLE.get());
        dropSelf(AMBlockRegistry.SCULK_BOOMER.get());
        add(AMBlockRegistry.SKUNK_SPRAY.get(), noDrop());
        dropSelf(AMBlockRegistry.STRADDLITE_BLOCK.get());
        add(AMBlockRegistry.TERRAPIN_EGG.get(), noDrop());

        add(AMBlockRegistry.TRANSMUTATION_TABLE.get(), LootTable.lootTable()
            .withPool(
                LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1f))
                    .setBonusRolls(ConstantValue.exactly(0f))
                    .add(
                        AlternativesEntry.alternatives(
                            LootItem.lootTableItem(AMBlockRegistry.TRANSMUTATION_TABLE)
                                .when(hasSilkTouch()),

                            LootItem.lootTableItem(Items.NETHER_STAR)
                        )
                    )
            )
        );

        dropSelf(AMBlockRegistry.TRIOPS_EGGS.get());
        dropSelf(AMBlockRegistry.VOID_WORM_BEAK.get());
        dropSelf(AMBlockRegistry.VOID_WORM_EFFIGY.get());
    }
}
