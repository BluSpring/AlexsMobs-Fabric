package com.github.alexthe666.alexsmobs.world;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.mojang.serialization.Codec;
import dev.architectury.mixin.fabric.BiomeAccessor;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import xyz.bluspring.forgebiomemodifiers.worldgen.BiomeModifier;
import xyz.bluspring.forgebiomemodifiers.worldgen.BiomeModifiers;
import xyz.bluspring.forgebiomemodifiers.worldgen.ModifiableBiomeInfo;

import java.util.List;

public class AMMobSpawnBiomeModifier implements BiomeModifier {
    private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER = new RegistryObject(new ResourceLocation(AlexsMobs.MODID, "am_mob_spawns"), BiomeModifiers.BIOME_MODIFIER_SERIALIZER_KEY);

    public AMMobSpawnBiomeModifier() {
    }

    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {
            AMWorldRegistry.addBiomeSpawns(biome, builder);
        }
    }

    public Codec<? extends BiomeModifier> codec() {
        return (Codec)SERIALIZER.get();
    }

    public static void init() {
        var modifier = new AMMobSpawnBiomeModifier();

        BiomeModifications.create(new ResourceLocation("alexsmobs", "am_spawner"))
                .add(ModificationPhase.ADDITIONS, BiomeSelectors.all(), (selection, modification) -> {
                    var biomeHolder = selection.getBiomeRegistryEntry();
                    var biome = selection.getBiome();
                    var builder = ModifiableBiomeInfo.BiomeInfo.Builder.copyOf(new ModifiableBiomeInfo.BiomeInfo(((BiomeAccessor) (Object) biome).getClimateSettings(), biome.getSpecialEffects(), biome.getGenerationSettings(), biome.getMobSettings()));

                    modifier.modify(biomeHolder, Phase.ADD, builder);

                    var info = builder.build();
                    var originalMobSettings = biome.getMobSettings();

                    for (MobCategory mobCategory : MobCategory.values()) {
                        var original = originalMobSettings.getMobs(mobCategory).isEmpty() ? List.of() : originalMobSettings.getMobs(mobCategory).unwrap();

                        var mobs = info.mobSpawnSettings().getMobs(mobCategory);

                        if (mobs.isEmpty())
                            continue;

                        var mobsList = mobs.unwrap().stream().filter(m -> !original.contains(m)).toList();

                        if (mobsList.isEmpty())
                            continue;

                        for (MobSpawnSettings.SpawnerData spawnerData : mobsList) {
                            modification.getSpawnSettings().addSpawn(mobCategory, spawnerData);
                        }
                    }
                });
    }

    public static Codec<AMMobSpawnBiomeModifier> makeCodec() {
        return Codec.unit(AMMobSpawnBiomeModifier::new);
    }
}
