package com.github.alexthe666.alexsmobs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainerRO;

@Mixin(LevelChunkSection.class)
public interface LevelChunkSectionAccessor {
    @Accessor
    PalettedContainerRO<Holder<Biome>> getBiomes();

    @Accessor
    void setBiomes(PalettedContainerRO<Holder<Biome>> biomes);
}
