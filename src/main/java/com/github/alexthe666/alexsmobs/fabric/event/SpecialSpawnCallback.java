package com.github.alexthe666.alexsmobs.fabric.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;

public interface SpecialSpawnCallback {
    Event<SpecialSpawnCallback> EVENT = EventFactory.createArrayBacked(SpecialSpawnCallback.class, callbacks -> (entity, level, x, y, z, spawner, spawnReason) -> {
        var result = true;

        for (SpecialSpawnCallback callback : callbacks) {
            if (result)
                result = callback.doSpecialSpawn(entity, level, x, y, z, spawner, spawnReason);
        }

        return result;
    });

    boolean doSpecialSpawn(Mob entity, LevelAccessor level, double x, double y, double z, @Nullable BaseSpawner spawner, MobSpawnType spawnReason);
}
