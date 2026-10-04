package com.github.alexthe666.alexsmobs.mixin.fabric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.alexthe666.alexsmobs.fabric.SpawnModifiableStructure;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;

@Mixin(Structure.class)
public abstract class StructureMixin implements SpawnModifiableStructure {
    @Shadow
    public abstract Map<MobCategory, StructureSpawnOverride> spawnOverrides();

    @Unique private Map<MobCategory, List<MobSpawnSettings.SpawnerData>> alexsmobs$addedSpawnOverrides;
    @Unique private Map<MobCategory, StructureSpawnOverride> alexsmobs$backingOverride;
    @Unique private Map<MobCategory, StructureSpawnOverride> alexsmobs$cachedOverride;

    @Override
    public void alexsmobs$addSpawnOverride(MobCategory category, MobSpawnSettings.SpawnerData data) {
        if (alexsmobs$addedSpawnOverrides == null) {
            alexsmobs$addedSpawnOverrides = new HashMap<>();
        }

        var existing = alexsmobs$addedSpawnOverrides.computeIfAbsent(category, $ -> new ArrayList<>());
        existing.add(data);
    }

    // Safe and compatible mob spawn overrides for structures, why not.
    @ModifyReturnValue(method = "spawnOverrides", at = @At("RETURN"))
    private Map<MobCategory, StructureSpawnOverride> alexsmobs$modifyWithBackingOverride(Map<MobCategory, StructureSpawnOverride> original) {
        if (alexsmobs$addedSpawnOverrides != null) {
            if (original != alexsmobs$backingOverride) {
                var copy = new HashMap<>(original);
                alexsmobs$backingOverride = original;

                for (Map.Entry<MobCategory, List<MobSpawnSettings.SpawnerData>> entry : alexsmobs$addedSpawnOverrides.entrySet()) {
                    var category = entry.getKey();
                    var list = entry.getValue();

                    if (copy.containsKey(category)) {
                        var originalData = copy.get(category);
                        var listCopy = new ArrayList<>(originalData.spawns().unwrap());
                        listCopy.addAll(list);

                        copy.put(category, new StructureSpawnOverride(originalData.boundingBox(), WeightedRandomList.create(listCopy)));
                    } else {
                        copy.put(category, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create(list)));
                    }
                }

                alexsmobs$cachedOverride = copy;
            }

            return alexsmobs$cachedOverride;
        }

        return original;
    }
}
