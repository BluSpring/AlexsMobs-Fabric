package com.github.alexthe666.alexsmobs.datagen;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = AlexsMobs.MODID)
public class AMDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeServer(), new AMRecipeProvider(output, lookupProvider));
        AMBlockTagProvider blockTagGenerator = generator.addProvider(event.includeServer(), new AMBlockTagProvider(output, lookupProvider, AlexsMobs.MODID, existingFileHelper));
        generator.addProvider(event.includeServer(), new AMItemTagProvider(output, lookupProvider, blockTagGenerator.contentsGetter()));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, Set.of(), List.of(
            new LootTableProvider.SubProviderEntry(provider -> new AMBlockLootTableProvider(Set.of(), FeatureFlags.DEFAULT_FLAGS, provider), LootContextParamSets.BLOCK),
            new LootTableProvider.SubProviderEntry(provider -> new AMEntityLootTableProvider(FeatureFlags.DEFAULT_FLAGS, provider), LootContextParamSets.ENTITY),
            new LootTableProvider.SubProviderEntry(AMBarteringLootTableProvider::new, LootContextParamSets.PIGLIN_BARTER),
            new LootTableProvider.SubProviderEntry(AMGameplayLootTableProvider::new, LootContextParamSets.EMPTY)
        ), lookupProvider));
        generator.addProvider(event.includeServer(), new AdvancementProvider(output, lookupProvider, List.of(
            new AMAdvancementProvider()
        )));
    }
}
