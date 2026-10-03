package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.fabric.DeferredRegister;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class AMSoundRegistry {

    public static final DeferredRegister<SoundEvent> DEF_REG = DeferredRegister.create(Registries.SOUND_EVENT, AlexsMobs.MODID);

    public static final SoundEvent GRIZZLY_BEAR_IDLE = createSoundEvent("grizzly_bear_idle");

    public static final SoundEvent GRIZZLY_BEAR_HURT = createSoundEvent("grizzly_bear_hurt");

    public static final SoundEvent GRIZZLY_BEAR_DIE = createSoundEvent("grizzly_bear_die");

    public static final SoundEvent BEAR_DUST = createSoundEvent("bear_dust");

    public static final SoundEvent ROADRUNNER_IDLE = createSoundEvent("roadrunner_idle");

    public static final SoundEvent ROADRUNNER_HURT = createSoundEvent("roadrunner_hurt");

    public static final SoundEvent ROADRUNNER_MEEP = createSoundEvent("roadrunner_meep");

    public static final SoundEvent GAZELLE_HURT = createSoundEvent("gazelle_hurt");

    public static final SoundEvent CROCODILE_IDLE = createSoundEvent("crocodile_idle");

    public static final SoundEvent CROCODILE_HURT = createSoundEvent("crocodile_hurt");

    public static final SoundEvent CROCODILE_BITE = createSoundEvent("crocodile_bite");

    public static final SoundEvent CROCODILE_BABY = createSoundEvent("crocodile_baby");

    public static final SoundEvent FLY_IDLE = createSoundEvent("fly_idle");

    public static final SoundEvent FLY_HURT = createSoundEvent("fly_hurt");

    public static final SoundEvent HUMMINGBIRD_IDLE = createSoundEvent("hummingbird_idle");

    public static final SoundEvent HUMMINGBIRD_HURT = createSoundEvent("hummingbird_hurt");

    public static final SoundEvent HUMMINGBIRD_LOOP = createSoundEvent("hummingbird_loop");

    public static final SoundEvent ORCA_IDLE = createSoundEvent("orca_idle");

    public static final SoundEvent ORCA_HURT = createSoundEvent("orca_hurt");

    public static final SoundEvent ORCA_DIE = createSoundEvent("orca_die");

    public static final SoundEvent GORILLA_IDLE = createSoundEvent("gorilla_idle");

    public static final SoundEvent GORILLA_HURT = createSoundEvent("gorilla_hurt");

    public static final SoundEvent MOSQUITO_LOOP = createSoundEvent("mosquito_loop");

    public static final SoundEvent MOSQUITO_HURT = createSoundEvent("mosquito_hurt");

    public static final SoundEvent MOSQUITO_DIE = createSoundEvent("mosquito_die");

    public static final SoundEvent RATTLESNAKE_LOOP = createSoundEvent("rattlesnake_loop");

    public static final SoundEvent RATTLESNAKE_HURT = createSoundEvent("rattlesnake_hurt");

    public static final SoundEvent RATTLESNAKE_ATTACK = createSoundEvent("rattlesnake_attack");

    public static final SoundEvent ENDERGRADE_IDLE = createSoundEvent("endergrade_idle");

    public static final SoundEvent ENDERGRADE_HURT = createSoundEvent("endergrade_hurt");

    public static final SoundEvent LOBSTER_HURT = createSoundEvent("lobster_hurt");

    public static final SoundEvent LOBSTER_ATTACK = createSoundEvent("lobster_attack");

    public static final SoundEvent KOMODO_DRAGON_IDLE = createSoundEvent("komodo_dragon_idle");

    public static final SoundEvent KOMODO_DRAGON_HURT = createSoundEvent("komodo_dragon_hurt");

    public static final SoundEvent SUNBIRD_IDLE = createSoundEvent("sunbird_idle");

    public static final SoundEvent SUNBIRD_HURT = createSoundEvent("sunbird_hurt");

    public static final SoundEvent CAPUCHIN_MONKEY_IDLE = createSoundEvent("capuchin_monkey_idle");

    public static final SoundEvent CAPUCHIN_MONKEY_HURT = createSoundEvent("capuchin_monkey_hurt");

    public static final SoundEvent BONE_SERPENT_IDLE = createSoundEvent("bone_serpent_idle");

    public static final SoundEvent BONE_SERPENT_HURT = createSoundEvent("bone_serpent_hurt");

    public static final SoundEvent CENTIPEDE_WALK = createSoundEvent("centipede_walk");

    public static final SoundEvent CENTIPEDE_HURT = createSoundEvent("centipede_hurt");

    public static final SoundEvent CENTIPEDE_ATTACK = createSoundEvent("centipede_attack");

    public static final SoundEvent WARPED_TOAD_IDLE = createSoundEvent("warped_toad_idle");

    public static final SoundEvent WARPED_TOAD_HURT = createSoundEvent("warped_toad_hurt");

    public static final SoundEvent MOOSE_IDLE = createSoundEvent("moose_idle");

    public static final SoundEvent MOOSE_HURT = createSoundEvent("moose_hurt");

    public static final SoundEvent MOOSE_JOSTLE = createSoundEvent("moose_jostle");

    public static final SoundEvent MIMICUBE_JUMP = createSoundEvent("mimicube_jump");

    public static final SoundEvent MIMICUBE_HURT = createSoundEvent("mimicube_hurt");

    public static final SoundEvent RACCOON_IDLE = createSoundEvent("raccoon_idle");

    public static final SoundEvent RACCOON_HURT = createSoundEvent("raccoon_hurt");

    public static final SoundEvent MARACA = createSoundEvent("maraca");

    public static final SoundEvent LA_CUCARACHA = createSoundEvent("la_cucaracha");

    public static final SoundEvent SEAL_IDLE = createSoundEvent("seal_idle");

    public static final SoundEvent SEAL_HURT = createSoundEvent("seal_hurt");

    public static final SoundEvent COCKROACH_HURT = createSoundEvent("cockroach_hurt");

    public static final SoundEvent SHOEBILL_HURT = createSoundEvent("shoebill_hurt");

    public static final SoundEvent ELEPHANT_IDLE = createSoundEvent("elephant_idle");

    public static final SoundEvent ELEPHANT_HURT = createSoundEvent("elephant_hurt");

    public static final SoundEvent ELEPHANT_DIE = createSoundEvent("elephant_die");

    public static final SoundEvent ELEPHANT_TRUMPET = createSoundEvent("elephant_trumpet");

    public static final SoundEvent ELEPHANT_WALK = createSoundEvent("elephant_walk");

    public static final SoundEvent SOUL_VULTURE_IDLE = createSoundEvent("soul_vulture_idle");

    public static final SoundEvent SOUL_VULTURE_HURT = createSoundEvent("soul_vulture_hurt");

    public static final SoundEvent SNOW_LEOPARD_IDLE = createSoundEvent("snow_leopard_idle");

    public static final SoundEvent SNOW_LEOPARD_HURT = createSoundEvent("snow_leopard_hurt");

    public static final SoundEvent SPECTRE_IDLE = createSoundEvent("spectre_idle");

    public static final SoundEvent SPECTRE_HURT = createSoundEvent("spectre_hurt");

    public static final SoundEvent CROW_IDLE = createSoundEvent("crow_idle");

    public static final SoundEvent CROW_HURT = createSoundEvent("crow_hurt");

    public static final SoundEvent ALLIGATOR_SNAPPING_TURTLE_IDLE = createSoundEvent("alligator_snapping_turtle_idle");

    public static final SoundEvent ALLIGATOR_SNAPPING_TURTLE_HURT = createSoundEvent("alligator_snapping_turtle_hurt");

    public static final SoundEvent MUNGUS_IDLE = createSoundEvent("mungus_idle");

    public static final SoundEvent MUNGUS_HURT = createSoundEvent("mungus_hurt");

    public static final SoundEvent MUNGUS_LASER_END = createSoundEvent("mungus_laser_end");

    public static final SoundEvent MUNGUS_LASER_LOOP = createSoundEvent("mungus_laser_loop");

    public static final SoundEvent MUNGUS_LASER_GROW = createSoundEvent("mungus_laser_grow");

    public static final SoundEvent MANTIS_SHRIMP_SNAP = createSoundEvent("mantis_shrimp_snap");

    public static final SoundEvent MANTIS_SHRIMP_HURT = createSoundEvent("mantis_shrimp_hurt");

    public static final SoundEvent GUSTER_IDLE = createSoundEvent("guster_idle");

    public static final SoundEvent GUSTER_HURT = createSoundEvent("guster_hurt");
    
    public static final SoundEvent WARPED_MOSCO_IDLE = createSoundEvent("warped_mosco_idle");

    public static final SoundEvent WARPED_MOSCO_HURT = createSoundEvent("warped_mosco_hurt");

    public static final SoundEvent STRADDLER_IDLE = createSoundEvent("straddler_idle");

    public static final SoundEvent STRADDLER_HURT = createSoundEvent("straddler_hurt");

    public static final SoundEvent EMU_IDLE = createSoundEvent("emu_idle");

    public static final SoundEvent EMU_HURT = createSoundEvent("emu_hurt");

    public static final SoundEvent PLATYPUS_IDLE = createSoundEvent("platypus_idle");

    public static final SoundEvent PLATYPUS_HURT = createSoundEvent("platypus_hurt");

    public static final SoundEvent DROPBEAR_IDLE = createSoundEvent("dropbear_idle");

    public static final SoundEvent DROPBEAR_HURT = createSoundEvent("dropbear_hurt");

    public static final SoundEvent TASMANIAN_DEVIL_IDLE = createSoundEvent("tasmanian_devil_idle");

    public static final SoundEvent TASMANIAN_DEVIL_HURT = createSoundEvent("tasmanian_devil_hurt");

    public static final SoundEvent TASMANIAN_DEVIL_ROAR = createSoundEvent("tasmanian_devil_roar");

    public static final SoundEvent KANGAROO_IDLE = createSoundEvent("kangaroo_idle");

    public static final SoundEvent KANGAROO_HURT = createSoundEvent("kangaroo_hurt");

    public static final SoundEvent CACHALOT_WHALE_IDLE = createSoundEvent("cachalot_whale_idle");

    public static final SoundEvent CACHALOT_WHALE_HURT = createSoundEvent("cachalot_whale_hurt");

    public static final SoundEvent CACHALOT_WHALE_CLICK = createSoundEvent("cachalot_whale_click");

    public static final SoundEvent LEAFCUTTER_ANT_HURT = createSoundEvent("leafcutter_ant_hurt");

    public static final SoundEvent LEAFCUTTER_ANT_QUEEN_HURT = createSoundEvent("leafcutter_ant_queen_hurt");

    public static final SoundEvent ENDERIOPHAGE_HURT = createSoundEvent("enderiophage_hurt");

    public static final SoundEvent ENDERIOPHAGE_SQUISH = createSoundEvent("enderiophage_squish");

    public static final SoundEvent ENDERIOPHAGE_WALK = createSoundEvent("enderiophage_walk");

    public static final SoundEvent MUSIC_DISC_THIME = createSoundEvent("music_disc_thime");

    public static final SoundEvent BALD_EAGLE_IDLE = createSoundEvent("bald_eagle_idle");

    public static final SoundEvent BALD_EAGLE_HURT = createSoundEvent("bald_eagle_hurt");

    public static final SoundEvent TIGER_IDLE = createSoundEvent("tiger_idle");

    public static final SoundEvent TIGER_HURT = createSoundEvent("tiger_hurt");

    public static final SoundEvent TIGER_ANGRY = createSoundEvent("tiger_angry");

    public static final SoundEvent TARANTULA_HAWK_WING = createSoundEvent("tarantula_hawk_wing");

    public static final SoundEvent TARANTULA_HAWK_HURT = createSoundEvent("tarantula_hawk_hurt");

    public static final SoundEvent MUSIC_WORMBOSS = createSoundEvent("music_wormboss");

    public static final SoundEvent VOID_WORM_IDLE = createSoundEvent("void_worm_idle");

    public static final SoundEvent VOID_WORM_HURT = createSoundEvent("void_worm_hurt");

    public static final SoundEvent VOID_PORTAL_CLOSE = createSoundEvent("void_portal_close");

    public static final SoundEvent VOID_PORTAL_OPEN = createSoundEvent("void_portal_open");

    public static final SoundEvent MIMIC_OCTOPUS_IDLE = createSoundEvent("mimic_octopus_idle");

    public static final SoundEvent MIMIC_OCTOPUS_HURT = createSoundEvent("mimic_octopus_hurt");

    public static final SoundEvent MUSIC_DISC_DAZE = createSoundEvent("music_disc_daze");

    public static final SoundEvent SEAGULL_IDLE = createSoundEvent("seagull_idle");

    public static final SoundEvent SEAGULL_HURT = createSoundEvent("seagull_hurt");

    public static final SoundEvent FROSTSTALKER_IDLE = createSoundEvent("froststalker_idle");

    public static final SoundEvent FROSTSTALKER_HURT = createSoundEvent("froststalker_hurt");
   
    public static final SoundEvent TUSKLIN_IDLE = createSoundEvent("tusklin_idle");

    public static final SoundEvent TUSKLIN_HURT = createSoundEvent("tusklin_hurt");

    public static final SoundEvent LAVIATHAN_IDLE = createSoundEvent("laviathan_idle");

    public static final SoundEvent LAVIATHAN_HURT = createSoundEvent("laviathan_hurt");

    public static final SoundEvent COSMAW_IDLE = createSoundEvent("cosmaw_idle");

    public static final SoundEvent COSMAW_HURT = createSoundEvent("cosmaw_hurt");

    public static final SoundEvent TOUCAN_IDLE = createSoundEvent("toucan_idle");

    public static final SoundEvent TOUCAN_HURT = createSoundEvent("toucan_hurt");

    public static final SoundEvent MANED_WOLF_IDLE = createSoundEvent("maned_wolf_idle");

    public static final SoundEvent MANED_WOLF_HURT = createSoundEvent("maned_wolf_hurt");

    public static final SoundEvent ANACONDA_SLITHER = createSoundEvent("anaconda_slither");

    public static final SoundEvent ANACONDA_HURT = createSoundEvent("anaconda_hurt");

    public static final SoundEvent ANACONDA_ATTACK = createSoundEvent("anaconda_attack");

    public static final SoundEvent VINE_LASSO = createSoundEvent("vine_lasso");

    public static final SoundEvent ANTEATER_HURT = createSoundEvent("anteater_hurt");

    public static final SoundEvent ROCKY_ROLLER_IDLE = createSoundEvent("rocky_roller_idle");

    public static final SoundEvent ROCKY_ROLLER_HURT = createSoundEvent("rocky_roller_hurt");

    public static final SoundEvent ROCKY_ROLLER_EARTHQUAKE = createSoundEvent("rocky_roller_earthquake");

    public static final SoundEvent GELADA_MONKEY_IDLE = createSoundEvent("gelada_monkey_idle");

    public static final SoundEvent GELADA_MONKEY_HURT = createSoundEvent("gelada_monkey_hurt");

    public static final SoundEvent FLUTTER_IDLE = createSoundEvent("flutter_idle");

    public static final SoundEvent FLUTTER_HURT = createSoundEvent("flutter_hurt");

    public static final SoundEvent FLUTTER_FLAP = createSoundEvent("flutter_flap");

    public static final SoundEvent FLUTTER_NO = createSoundEvent("flutter_no");

    public static final SoundEvent FLUTTER_YES = createSoundEvent("flutter_yes");

    public static final SoundEvent JERBOA_IDLE = createSoundEvent("jerboa_idle");

    public static final SoundEvent JERBOA_HURT = createSoundEvent("jerboa_hurt");

    public static final SoundEvent TERRAPIN_HURT = createSoundEvent("terrapin_hurt");

    public static final SoundEvent COMB_JELLY_HURT = createSoundEvent("comb_jelly_hurt");

    public static final SoundEvent COSMIC_COD_HURT = createSoundEvent("cosmic_cod_hurt");

    public static final SoundEvent MOSQUITO_CAPSID_CONVERT = createSoundEvent("mosquito_capsid_convert");

    public static final SoundEvent GIANT_SQUID_GAMES = createSoundEvent("giant_squid_games");

    public static final SoundEvent APRIL_FOOLS_SCREAM = createSoundEvent("april_fools_scream");

    public static final SoundEvent APRIL_FOOLS_POWER_OUTAGE = createSoundEvent("april_fools_power_outage");

    public static final SoundEvent APRIL_FOOLS_MUSIC_BOX = createSoundEvent("april_fools_music_box");

    public static final SoundEvent BUNFUNGUS_IDLE = createSoundEvent("bunfungus_idle");

    public static final SoundEvent BUNFUNGUS_HURT = createSoundEvent("bunfungus_hurt");

    public static final SoundEvent BUNFUNGUS_ATTACK = createSoundEvent("bunfungus_attack");

    public static final SoundEvent BUNFUNGUS_JUMP = createSoundEvent("bunfungus_jump");

    public static final SoundEvent BISON_IDLE = createSoundEvent("bison_idle");

    public static final SoundEvent BISON_HURT = createSoundEvent("bison_hurt");

    public static final SoundEvent GIANT_SQUID_HURT = createSoundEvent("giant_squid_hurt");

    public static final SoundEvent GIANT_SQUID_TENTACLE = createSoundEvent("giant_squid_tentacle");

    public static final SoundEvent DEVILS_HOLE_PUPFISH_HURT = createSoundEvent("devils_hole_pupfish_hurt");

    public static final SoundEvent SKELEWAG_HURT = createSoundEvent("skelewag_hurt");

    public static final SoundEvent SKELEWAG_IDLE = createSoundEvent("skelewag_idle");

    public static final SoundEvent RAIN_FROG_HURT = createSoundEvent("rain_frog_hurt");

    public static final SoundEvent RAIN_FROG_IDLE = createSoundEvent("rain_frog_idle");

    public static final SoundEvent POTOO_HURT = createSoundEvent("potoo_hurt");

    public static final SoundEvent POTOO_CALL = createSoundEvent("potoo_call");

    public static final SoundEvent MUDSKIPPER_HURT = createSoundEvent("mudskipper_hurt");

    public static final SoundEvent MUDSKIPPER_WALK = createSoundEvent("mudskipper_walk");

    public static final SoundEvent MUDSKIPPER_SPIT = createSoundEvent("mudskipper_spit");

    public static final SoundEvent RHINOCEROS_IDLE = createSoundEvent("rhinoceros_idle");

    public static final SoundEvent RHINOCEROS_HURT = createSoundEvent("rhinoceros_hurt");

    public static final SoundEvent SUGAR_GLIDER_IDLE = createSoundEvent("sugar_glider_idle");

    public static final SoundEvent SUGAR_GLIDER_HURT = createSoundEvent("sugar_glider_hurt");

    public static final SoundEvent FARSEER_HURT = createSoundEvent("farseer_hurt");

    public static final SoundEvent FARSEER_IDLE = createSoundEvent("farseer_idle");

    public static final SoundEvent FARSEER_EMERGE = createSoundEvent("farseer_emerge");

    public static final SoundEvent FARSEER_BEAM = createSoundEvent("farseer_beam");

    public static final SoundEvent TRANSMUTE_ITEM = createSoundEvent("transmute_item");

    public static final SoundEvent SKREECHER_HURT = createSoundEvent("skreecher_hurt");

    public static final SoundEvent SKREECHER_CALL = createSoundEvent("skreecher_call");

    public static final SoundEvent SKREECHER_DETECT = createSoundEvent("skreecher_detect");

    public static final SoundEvent SKREECHER_CLAP = createSoundEvent("skreecher_clap");

    public static final SoundEvent SCULK_BOOMER = createSoundEvent("sculk_boomer");

    public static final SoundEvent SCULK_BOOMER_FART = createSoundEvent("sculk_boomer_fart");

    public static final SoundEvent UNDERMINER_IDLE = createSoundEvent("underminer_idle");

    public static final SoundEvent UNDERMINER_HURT = createSoundEvent("underminer_hurt");

    public static final SoundEvent UNDERMINER_STEP = createSoundEvent("underminer_step");

    public static final SoundEvent MURMUR_HURT = createSoundEvent("murmur_hurt");

    public static final SoundEvent MURMUR_IDLE = createSoundEvent("murmur_idle");

    public static final SoundEvent MURMUR_NECK = createSoundEvent("murmur_neck");

    public static final SoundEvent MURMUR_ATTACK = createSoundEvent("murmur_attack");

    public static final SoundEvent MURMUR_ANGER = createSoundEvent("murmur_anger");

    public static final SoundEvent TENDON_WHIP = createSoundEvent("tendon_whip");

    public static final SoundEvent SKUNK_HURT = createSoundEvent("skunk_hurt");

    public static final SoundEvent SKUNK_IDLE = createSoundEvent("skunk_idle");

    public static final SoundEvent SKUNK_SPRAY = createSoundEvent("skunk_spray");

    public static final SoundEvent STINK_RAY = createSoundEvent("stink_ray");

    public static final SoundEvent BANANA_SLUG_HURT = createSoundEvent("banana_slug_hurt");

    public static final SoundEvent BANANA_SLUG_SLIME_EXPAND = createSoundEvent("banana_slug_slime_expand");

    public static final SoundEvent BLUE_JAY_IDLE = createSoundEvent("blue_jay_idle");

    public static final SoundEvent BLUE_JAY_HURT = createSoundEvent("blue_jay_hurt");

    public static final SoundEvent BLUE_JAY_SONG = createSoundEvent("blue_jay_song");

    public static final SoundEvent CAIMAN_IDLE = createSoundEvent("caiman_idle");

    public static final SoundEvent CAIMAN_HURT = createSoundEvent("caiman_hurt");

    public static final SoundEvent CAIMAN_SPLASH = createSoundEvent("caiman_splash");
    public static final SoundEvent TRIOPS_HURT = createSoundEvent("triops_hurt");
    public static final SoundEvent END_PIRATE_DOOR = createSoundEvent("end_pirate_door");
    
    private static SoundEvent createSoundEvent(final String soundName) {
        return DEF_REG.register(soundName, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(AlexsMobs.MODID, soundName)));
    }
}
