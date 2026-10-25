package com.github.alexthe666.alexsmobs.datagen;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.datagen.client.AMItemModelProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

@EventBusSubscriber(modid = AlexsMobs.MODID, bus = EventBusSubscriber.Bus.MOD)
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
        generator.addProvider(event.includeServer(), new AMAdvancementProvider(output, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeClient(), new AMItemModelProvider(output, existingFileHelper));
    }
}
