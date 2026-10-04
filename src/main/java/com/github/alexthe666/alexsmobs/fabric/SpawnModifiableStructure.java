package com.github.alexthe666.alexsmobs.fabric;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

public interface SpawnModifiableStructure {
    void alexsmobs$addSpawnOverride(MobCategory category, MobSpawnSettings.SpawnerData data);
}
