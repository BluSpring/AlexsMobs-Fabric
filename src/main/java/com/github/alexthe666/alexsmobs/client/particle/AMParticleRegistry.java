package com.github.alexthe666.alexsmobs.client.particle;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.fabric.DeferredRegister;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;

public class AMParticleRegistry {

    public static final DeferredRegister<ParticleType<?>> DEF_REG = DeferredRegister.create(Registries.PARTICLE_TYPE, AlexsMobs.MODID);
    
    public static final SimpleParticleType GUSTER_SAND_SPIN = DEF_REG.register("guster_sand_spin", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType GUSTER_SAND_SHOT = DEF_REG.register("guster_sand_shot", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType GUSTER_SAND_SPIN_RED = DEF_REG.register("guster_sand_spin_red", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType GUSTER_SAND_SHOT_RED = DEF_REG.register("guster_sand_shot_red", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType GUSTER_SAND_SPIN_SOUL = DEF_REG.register("guster_sand_spin_soul", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType GUSTER_SAND_SHOT_SOUL = DEF_REG.register("guster_sand_shot_soul", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType HEMOLYMPH = DEF_REG.register("hemolymph", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType PLATYPUS_SENSE = DEF_REG.register("platypus_sense", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType WHALE_SPLASH = DEF_REG.register("whale_splash", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType DNA = DEF_REG.register("dna", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType SHOCKED = DEF_REG.register("shocked", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType WORM_PORTAL = DEF_REG.register("worm_portal", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType INVERT_DIG = DEF_REG.register("invert_dig", ()-> FabricParticleTypes.simple(true));
    public static final SimpleParticleType TEETH_GLINT = DEF_REG.register("teeth_glint", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType SMELLY = DEF_REG.register("smelly", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType BUNFUNGUS_TRANSFORMATION = DEF_REG.register("bunfungus_transformation", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType FUNGUS_BUBBLE = DEF_REG.register("fungus_bubble", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType BEAR_FREDDY = DEF_REG.register("bear_freddy", ()-> FabricParticleTypes.simple(true));
    public static final SimpleParticleType SUNBIRD_FEATHER = DEF_REG.register("sunbird_feather", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType STATIC_SPARK = DEF_REG.register("static_spark", ()-> FabricParticleTypes.simple(false));
    public static final SimpleParticleType SKULK_BOOM = DEF_REG.register("skulk_boom", ()-> FabricParticleTypes.simple(false));

    public static final SimpleParticleType BIRD_SONG = DEF_REG.register("bird_song", ()-> FabricParticleTypes.simple(false));
}
