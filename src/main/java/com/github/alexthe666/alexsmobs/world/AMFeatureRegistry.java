package com.github.alexthe666.alexsmobs.world;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.google.common.collect.ImmutableList;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AMFeatureRegistry {
    public static final LazyRegistrar<Feature<?>> DEF_REG = LazyRegistrar.create(Registry.FEATURE, AlexsMobs.MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ANTHILL = DEF_REG.register("leafcutter_anthill", () -> new FeatureLeafcutterAnthill(NoneFeatureConfiguration.CODEC));

    public static final class AMConfiguredFeatureRegistry {
        public static final LazyRegistrar<ConfiguredFeature<?, ?>> DEF_REG = LazyRegistrar.create(Registry.CONFIGURED_FEATURE_REGISTRY, AlexsMobs.MODID);

        public static final RegistryObject<ConfiguredFeature<?, ?>> ANTHILL_CF = DEF_REG.register("leafcutter_anthill", () -> new ConfiguredFeature<>(AMFeatureRegistry.ANTHILL.get(), NoneFeatureConfiguration.INSTANCE));

    }

    public static final class AMPlacedFeatureRegistry{
        public static final LazyRegistrar<PlacedFeature> DEF_REG = LazyRegistrar.create(Registry.PLACED_FEATURE_REGISTRY, AlexsMobs.MODID);
        public static final RegistryObject<PlacedFeature> ANTHILL = DEF_REG.register("leafcutter_anthill", () -> new PlacedFeature(AMConfiguredFeatureRegistry.ANTHILL_CF.getHolder().get(), ImmutableList.of()));

    }
}
