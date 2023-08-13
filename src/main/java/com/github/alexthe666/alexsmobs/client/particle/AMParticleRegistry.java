package com.github.alexthe666.alexsmobs.client.particle;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class AMParticleRegistry {

    public static final LazyRegistrar<ParticleType<?>> DEF_REG = LazyRegistrar.create(Registry.PARTICLE_TYPE, AlexsMobs.MODID);
    
    public static final RegistryObject<SimpleParticleType> GUSTER_SAND_SPIN = DEF_REG.register("guster_sand_spin", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> GUSTER_SAND_SHOT = DEF_REG.register("guster_sand_shot", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> GUSTER_SAND_SPIN_RED = DEF_REG.register("guster_sand_spin_red", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> GUSTER_SAND_SHOT_RED = DEF_REG.register("guster_sand_shot_red", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> GUSTER_SAND_SPIN_SOUL = DEF_REG.register("guster_sand_spin_soul", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> GUSTER_SAND_SHOT_SOUL = DEF_REG.register("guster_sand_shot_soul", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> HEMOLYMPH = DEF_REG.register("hemolymph", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> PLATYPUS_SENSE = DEF_REG.register("platypus_sense", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> WHALE_SPLASH = DEF_REG.register("whale_splash", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> DNA = DEF_REG.register("dna", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> SHOCKED = DEF_REG.register("shocked", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> WORM_PORTAL = DEF_REG.register("worm_portal", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> INVERT_DIG = DEF_REG.register("invert_dig", ()-> FabricParticleTypes.simple(true));
    public static final RegistryObject<SimpleParticleType> TEETH_GLINT = DEF_REG.register("teeth_glint", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> SMELLY = DEF_REG.register("smelly", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> BUNFUNGUS_TRANSFORMATION = DEF_REG.register("bunfungus_transformation", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> FUNGUS_BUBBLE = DEF_REG.register("fungus_bubble", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> BEAR_FREDDY = DEF_REG.register("bear_freddy", ()-> FabricParticleTypes.simple(true));
    public static final RegistryObject<SimpleParticleType> SUNBIRD_FEATHER = DEF_REG.register("sunbird_feather", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> STATIC_SPARK = DEF_REG.register("static_spark", ()-> FabricParticleTypes.simple(false));
    public static final RegistryObject<SimpleParticleType> SKULK_BOOM = DEF_REG.register("skulk_boom", ()-> FabricParticleTypes.simple(false));

    public static final RegistryObject<SimpleParticleType> BIRD_SONG = DEF_REG.register("bird_song", ()-> FabricParticleTypes.simple(false));
}
