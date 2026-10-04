package com.github.alexthe666.alexsmobs.entity;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.fabric.DeferredRegister;
import com.google.common.base.Predicates;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;

import java.util.function.Predicate;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class AMEntityRegistry {

    public static final DeferredRegister<EntityType<?>> DEF_REG = DeferredRegister.create(Registries.ENTITY_TYPE, AlexsMobs.MODID);
    public static final EntityType<EntityGrizzlyBear> GRIZZLY_BEAR = DEF_REG.register("grizzly_bear", () -> registerEntity(EntityType.Builder.of(EntityGrizzlyBear::new, MobCategory.CREATURE).sized(1.6F, 1.8F).clientTrackingRange(10), "grizzly_bear"));
    public static final EntityType<EntityRoadrunner> ROADRUNNER = DEF_REG.register("roadrunner", () -> registerEntity(EntityType.Builder.of(EntityRoadrunner::new, MobCategory.CREATURE).sized(0.45F, 0.75F).clientTrackingRange(10), "roadrunner"));
    public static final EntityType<EntityBoneSerpent> BONE_SERPENT = DEF_REG.register("bone_serpent", () -> registerEntity(EntityType.Builder.of(EntityBoneSerpent::new, MobCategory.MONSTER).sized(1.2F, 1.15F).fireImmune().clientTrackingRange(10), "bone_serpent"));
    public static final EntityType<EntityBoneSerpentPart> BONE_SERPENT_PART = DEF_REG.register("bone_serpent_part", () -> registerEntity(EntityType.Builder.<EntityBoneSerpentPart>of(EntityBoneSerpentPart::new, MobCategory.MONSTER).sized(1F, 1F).fireImmune().clientTrackingRange(10), "bone_serpent_part"));
    public static final EntityType<EntityGazelle> GAZELLE = DEF_REG.register("gazelle", () -> registerEntity(EntityType.Builder.of(EntityGazelle::new, MobCategory.CREATURE).sized(0.85F, 1.25F).clientTrackingRange(10), "gazelle"));
    public static final EntityType<EntityCrocodile> CROCODILE = DEF_REG.register("crocodile", () -> registerEntity(EntityType.Builder.of(EntityCrocodile::new, MobCategory.CREATURE).sized(2.15F, 0.75F).clientTrackingRange(10), "crocodile"));
    public static final EntityType<EntityFly> FLY = DEF_REG.register("fly", () -> registerEntity(EntityType.Builder.of(EntityFly::new, MobCategory.AMBIENT).sized(0.35F, 0.35F).clientTrackingRange(4), "fly"));
    public static final EntityType<EntityHummingbird> HUMMINGBIRD = DEF_REG.register("hummingbird", () -> registerEntity(EntityType.Builder.of(EntityHummingbird::new, MobCategory.CREATURE).sized(0.45F, 0.45F).clientTrackingRange(5), "hummingbird"));
    public static final EntityType<EntityOrca> ORCA = DEF_REG.register("orca", () -> registerEntity(EntityType.Builder.of(EntityOrca::new, MobCategory.WATER_CREATURE).sized(3.75F, 1.75F).clientTrackingRange(10), "orca"));
    public static final EntityType<EntitySunbird> SUNBIRD = DEF_REG.register("sunbird", () -> registerEntity(EntityType.Builder.of(EntitySunbird::new, MobCategory.CREATURE).sized(2.75F, 1.5F).fireImmune().clientTrackingRange(12).alwaysUpdateVelocity(true).updateInterval(1), "sunbird"));
    public static final EntityType<EntityGorilla> GORILLA = DEF_REG.register("gorilla", () -> registerEntity(EntityType.Builder.of(EntityGorilla::new, MobCategory.CREATURE).sized(1.15F, 1.35F).clientTrackingRange(10), "gorilla"));
    public static final EntityType<EntityCrimsonMosquito> CRIMSON_MOSQUITO = DEF_REG.register("crimson_mosquito", () -> registerEntity(EntityType.Builder.of(EntityCrimsonMosquito::new, MobCategory.MONSTER).sized(1.25F, 1.15F).fireImmune().clientTrackingRange(8), "crimson_mosquito"));
    public static final EntityType<EntityMosquitoSpit> MOSQUITO_SPIT = DEF_REG.register("mosquito_spit", () -> registerEntity(EntityType.Builder.<EntityMosquitoSpit>of(EntityMosquitoSpit::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "mosquito_spit"));
    public static final EntityType<EntityRattlesnake> RATTLESNAKE = DEF_REG.register("rattlesnake", () -> registerEntity(EntityType.Builder.of(EntityRattlesnake::new, MobCategory.CREATURE).sized(0.95F, 0.35F).clientTrackingRange(10), "rattlesnake"));
    public static final EntityType<EntityEndergrade> ENDERGRADE = DEF_REG.register("endergrade", () -> registerEntity(EntityType.Builder.of(EntityEndergrade::new, MobCategory.CREATURE).sized(0.95F, 0.85F).clientTrackingRange(10), "endergrade"));
    public static final EntityType<EntityHammerheadShark> HAMMERHEAD_SHARK = DEF_REG.register("hammerhead_shark", () -> registerEntity(EntityType.Builder.of(EntityHammerheadShark::new, MobCategory.WATER_CREATURE).sized(2.4F, 1.25F).clientTrackingRange(10), "hammerhead_shark"));
    public static final EntityType<EntitySharkToothArrow> SHARK_TOOTH_ARROW = DEF_REG.register("shark_tooth_arrow", () -> registerEntity(EntityType.Builder.<EntitySharkToothArrow>of(EntitySharkToothArrow::new, MobCategory.MISC).sized(0.5F, 0.5F), "shark_tooth_arrow"));
    public static final EntityType<EntityLobster> LOBSTER = DEF_REG.register("lobster", () -> registerEntity(EntityType.Builder.of(EntityLobster::new, MobCategory.WATER_AMBIENT).sized(0.7F, 0.4F).clientTrackingRange(5), "lobster"));
    public static final EntityType<EntityKomodoDragon> KOMODO_DRAGON = DEF_REG.register("komodo_dragon", () -> registerEntity(EntityType.Builder.of(EntityKomodoDragon::new, MobCategory.CREATURE).sized(1.9F, 0.9F).clientTrackingRange(10), "komodo_dragon"));
    public static final EntityType<EntityCapuchinMonkey> CAPUCHIN_MONKEY = DEF_REG.register("capuchin_monkey", () -> registerEntity(EntityType.Builder.of(EntityCapuchinMonkey::new, MobCategory.CREATURE).sized(0.65F, 0.75F).clientTrackingRange(10), "capuchin_monkey"));
    public static final EntityType<EntityTossedItem> TOSSED_ITEM = DEF_REG.register("tossed_item", () -> registerEntity(EntityType.Builder.<EntityTossedItem>of(EntityTossedItem::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "tossed_item"));
    public static final EntityType<EntityCentipedeHead> CENTIPEDE_HEAD = DEF_REG.register("centipede_head", () -> registerEntity(EntityType.Builder.of(EntityCentipedeHead::new, MobCategory.MONSTER).sized(0.9F, 0.9F).clientTrackingRange(8), "centipede_head"));
    public static final EntityType<EntityCentipedeBody> CENTIPEDE_BODY = DEF_REG.register("centipede_body", () -> registerEntity(EntityType.Builder.<EntityCentipedeBody>of(EntityCentipedeBody::new, MobCategory.MISC).sized(0.9F, 0.9F).fireImmune().alwaysUpdateVelocity(true).updateInterval(1).clientTrackingRange(8), "centipede_body"));
    public static final EntityType<EntityCentipedeTail> CENTIPEDE_TAIL = DEF_REG.register("centipede_tail", () -> registerEntity(EntityType.Builder.of(EntityCentipedeTail::new, MobCategory.MISC).sized(0.9F, 0.9F).fireImmune().alwaysUpdateVelocity(true).updateInterval(1).clientTrackingRange(8), "centipede_tail"));
    public static final EntityType<EntityWarpedToad> WARPED_TOAD = DEF_REG.register("warped_toad", () -> registerEntity(EntityType.Builder.of(EntityWarpedToad::new, MobCategory.CREATURE).sized(0.9F, 1.4F).fireImmune().alwaysUpdateVelocity(true).updateInterval(1).clientTrackingRange(10), "warped_toad"));
    public static final EntityType<EntityMoose> MOOSE = DEF_REG.register("moose", () -> registerEntity(EntityType.Builder.of(EntityMoose::new, MobCategory.CREATURE).sized(1.7F, 2.4F).clientTrackingRange(10), "moose"));
    public static final EntityType<EntityMimicube> MIMICUBE = DEF_REG.register("mimicube", () -> registerEntity(EntityType.Builder.of(EntityMimicube::new, MobCategory.MONSTER).sized(0.9F, 0.9F).clientTrackingRange(8), "mimicube"));
    public static final EntityType<EntityRaccoon> RACCOON = DEF_REG.register("raccoon", () -> registerEntity(EntityType.Builder.of(EntityRaccoon::new, MobCategory.CREATURE).sized(0.8F, 0.9F).clientTrackingRange(10), "raccoon"));
    public static final EntityType<EntityBlobfish> BLOBFISH = DEF_REG.register("blobfish", () -> registerEntity(EntityType.Builder.of(EntityBlobfish::new, MobCategory.WATER_AMBIENT).sized(0.7F, 0.45F).clientTrackingRange(5), "blobfish"));
    public static final EntityType<EntitySeal> SEAL = DEF_REG.register("seal", () -> registerEntity(EntityType.Builder.of(EntitySeal::new, MobCategory.CREATURE).sized(1.45F, 0.9F).clientTrackingRange(10), "seal"));
    public static final EntityType<EntityCockroach> COCKROACH = DEF_REG.register("cockroach", () -> registerEntity(EntityType.Builder.of(EntityCockroach::new, MobCategory.AMBIENT).sized(0.7F, 0.3F).clientTrackingRange(5), "cockroach"));
    public static final EntityType<EntityCockroachEgg> COCKROACH_EGG = DEF_REG.register("cockroach_egg", () -> registerEntity(EntityType.Builder.<EntityCockroachEgg>of(EntityCockroachEgg::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "cockroach_egg"));
    public static final EntityType<EntityShoebill> SHOEBILL = DEF_REG.register("shoebill", () -> registerEntity(EntityType.Builder.of(EntityShoebill::new, MobCategory.CREATURE).sized(0.8F, 1.5F).updateInterval(1).clientTrackingRange(10), "shoebill"));
    public static final EntityType<EntityElephant> ELEPHANT = DEF_REG.register("elephant", () -> registerEntity(EntityType.Builder.of(EntityElephant::new, MobCategory.CREATURE).sized(3.1F, 3.5F).updateInterval(1).clientTrackingRange(10), "elephant"));
    public static final EntityType<EntitySoulVulture> SOUL_VULTURE = DEF_REG.register("soul_vulture", () -> registerEntity(EntityType.Builder.of(EntitySoulVulture::new, MobCategory.MONSTER).sized(0.9F, 1.3F).updateInterval(1).fireImmune().clientTrackingRange(8), "soul_vulture"));
    public static final EntityType<EntitySnowLeopard> SNOW_LEOPARD = DEF_REG.register("snow_leopard", () -> registerEntity(EntityType.Builder.of(EntitySnowLeopard::new, MobCategory.CREATURE).sized(1.2F, 1.3F).immuneTo(Blocks.POWDER_SNOW).clientTrackingRange(10), "snow_leopard"));
    public static final EntityType<EntitySpectre> SPECTRE = DEF_REG.register("spectre", () -> registerEntity(EntityType.Builder.of(EntitySpectre::new, MobCategory.CREATURE).sized(3.15F, 0.8F).fireImmune().clientTrackingRange(10).alwaysUpdateVelocity(true).updateInterval(1), "spectre"));
    public static final EntityType<EntityCrow> CROW = DEF_REG.register("crow", () -> registerEntity(EntityType.Builder.of(EntityCrow::new, MobCategory.CREATURE).sized(0.45F, 0.45F).clientTrackingRange(10), "crow"));
    public static final EntityType<EntityAlligatorSnappingTurtle> ALLIGATOR_SNAPPING_TURTLE = DEF_REG.register("alligator_snapping_turtle", () -> registerEntity(EntityType.Builder.of(EntityAlligatorSnappingTurtle::new, MobCategory.CREATURE).sized(1.25F, 0.65F).clientTrackingRange(10), "alligator_snapping_turtle"));
    public static final EntityType<EntityMungus> MUNGUS = DEF_REG.register("mungus", () -> registerEntity(EntityType.Builder.of(EntityMungus::new, MobCategory.CREATURE).sized(0.75F, 1.45F).clientTrackingRange(10), "mungus"));
    public static final EntityType<EntityMantisShrimp> MANTIS_SHRIMP = DEF_REG.register("mantis_shrimp", () -> registerEntity(EntityType.Builder.of(EntityMantisShrimp::new, MobCategory.WATER_CREATURE).sized(1.25F, 1.2F).clientTrackingRange(10), "mantis_shrimp"));
    public static final EntityType<EntityGuster> GUSTER = DEF_REG.register("guster", () -> registerEntity(EntityType.Builder.of(EntityGuster::new, MobCategory.MONSTER).sized(1.42F, 2.35F).fireImmune().clientTrackingRange(8), "guster"));
    public static final EntityType<EntitySandShot> SAND_SHOT = DEF_REG.register("sand_shot", () -> registerEntity(EntityType.Builder.<EntitySandShot>of(EntitySandShot::new, MobCategory.MISC).sized(0.95F, 0.65F).fireImmune(), "sand_shot"));
    public static final EntityType<EntityGust> GUST = DEF_REG.register("gust", () -> registerEntity(EntityType.Builder.<EntityGust>of(EntityGust::new, MobCategory.MISC).sized(0.8F, 0.8F).fireImmune(), "gust"));
    public static final EntityType<EntityWarpedMosco> WARPED_MOSCO = DEF_REG.register("warped_mosco", () -> registerEntity(EntityType.Builder.of(EntityWarpedMosco::new, MobCategory.MONSTER).sized(1.99F, 3.25F).fireImmune().clientTrackingRange(10), "warped_mosco"));
    public static final EntityType<EntityHemolymph> HEMOLYMPH = DEF_REG.register("hemolymph", () -> registerEntity(EntityType.Builder.<EntityHemolymph>of(EntityHemolymph::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "hemolymph"));
    public static final EntityType<EntityStraddler> STRADDLER = DEF_REG.register("straddler", () -> registerEntity(EntityType.Builder.of(EntityStraddler::new, MobCategory.MONSTER).sized(1.65F, 3F).fireImmune().clientTrackingRange(8), "straddler"));
    public static final EntityType<EntityStradpole> STRADPOLE = DEF_REG.register("stradpole", () -> registerEntity(EntityType.Builder.of(EntityStradpole::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.5F).fireImmune().clientTrackingRange(4), "stradpole"));
    public static final EntityType<EntityStraddleboard> STRADDLEBOARD = DEF_REG.register("straddleboard", () -> registerEntity(EntityType.Builder.<EntityStraddleboard>of(EntityStraddleboard::new, MobCategory.MISC).sized(1.5F, 0.35F).fireImmune().updateInterval(1).clientTrackingRange(10).alwaysUpdateVelocity(true), "straddleboard"));
    public static final EntityType<EntityEmu> EMU = DEF_REG.register("emu", () -> registerEntity(EntityType.Builder.of(EntityEmu::new, MobCategory.CREATURE).sized(1.1F, 1.8F).clientTrackingRange(10), "emu"));
    public static final EntityType<EntityEmuEgg> EMU_EGG = DEF_REG.register("emu_egg", () -> registerEntity(EntityType.Builder.<EntityEmuEgg>of(EntityEmuEgg::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "emu_egg"));
    public static final EntityType<EntityPlatypus> PLATYPUS = DEF_REG.register("platypus", () -> registerEntity(EntityType.Builder.of(EntityPlatypus::new, MobCategory.CREATURE).sized(0.8F, 0.5F).clientTrackingRange(10), "platypus"));
    public static final EntityType<EntityDropBear> DROPBEAR = DEF_REG.register("dropbear", () -> registerEntity(EntityType.Builder.of(EntityDropBear::new, MobCategory.MONSTER).sized(1.65F, 1.5F).fireImmune().clientTrackingRange(8), "dropbear"));
    public static final EntityType<EntityTasmanianDevil> TASMANIAN_DEVIL = DEF_REG.register("tasmanian_devil", () -> registerEntity(EntityType.Builder.of(EntityTasmanianDevil::new, MobCategory.CREATURE).sized(0.7F, 0.8F).clientTrackingRange(10), "tasmanian_devil"));
    public static final EntityType<EntityKangaroo> KANGAROO = DEF_REG.register("kangaroo", () -> registerEntity(EntityType.Builder.of(EntityKangaroo::new, MobCategory.CREATURE).sized(1.65F, 1.5F).clientTrackingRange(10), "kangaroo"));
    public static final EntityType<EntityCachalotWhale> CACHALOT_WHALE = DEF_REG.register("cachalot_whale", () -> registerEntity(EntityType.Builder.of(EntityCachalotWhale::new, MobCategory.WATER_CREATURE).sized(9F, 4.0F).clientTrackingRange(10), "cachalot_whale"));
    public static final EntityType<EntityCachalotEcho> CACHALOT_ECHO = DEF_REG.register("cachalot_echo", () -> registerEntity(EntityType.Builder.<EntityCachalotEcho>of(EntityCachalotEcho::new, MobCategory.MISC).sized(2F, 2F).fireImmune(), "cachalot_echo"));
    public static final EntityType<EntityLeafcutterAnt> LEAFCUTTER_ANT = DEF_REG.register("leafcutter_ant", () -> registerEntity(EntityType.Builder.of(EntityLeafcutterAnt::new, MobCategory.CREATURE).sized(0.8F, 0.5F).clientTrackingRange(5), "leafcutter_ant"));
    public static final EntityType<EntityEnderiophage> ENDERIOPHAGE = DEF_REG.register("enderiophage", () -> registerEntity(EntityType.Builder.of(EntityEnderiophage::new, MobCategory.CREATURE).sized(0.85F, 1.95F).updateInterval(1).clientTrackingRange(8), "enderiophage"));
    public static final EntityType<EntityEnderiophageRocket> ENDERIOPHAGE_ROCKET = DEF_REG.register("enderiophage_rocket", () -> registerEntity(EntityType.Builder.<EntityEnderiophageRocket>of(EntityEnderiophageRocket::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "enderiophage_rocket"));
    public static final EntityType<EntityBaldEagle> BALD_EAGLE = DEF_REG.register("bald_eagle", () -> registerEntity(EntityType.Builder.of(EntityBaldEagle::new, MobCategory.CREATURE).sized(0.5F, 0.95F).updateInterval(1).clientTrackingRange(14), "bald_eagle"));
    public static final EntityType<EntityTiger> TIGER = DEF_REG.register("tiger", () -> registerEntity(EntityType.Builder.of(EntityTiger::new, MobCategory.CREATURE).sized(1.45F, 1.2F).clientTrackingRange(10), "tiger"));
    public static final EntityType<EntityTarantulaHawk> TARANTULA_HAWK = DEF_REG.register("tarantula_hawk", () -> registerEntity(EntityType.Builder.of(EntityTarantulaHawk::new, MobCategory.CREATURE).sized(1.2F, 0.9F).clientTrackingRange(10), "tarantula_hawk"));
    public static final EntityType<EntityVoidWorm> VOID_WORM = DEF_REG.register("void_worm", () -> registerEntity(EntityType.Builder.of(EntityVoidWorm::new, MobCategory.MONSTER).sized(3.4F, 3F).fireImmune().clientTrackingRange(20).alwaysUpdateVelocity(true).updateInterval(1), "void_worm"));
    public static final EntityType<EntityVoidWormPart> VOID_WORM_PART = DEF_REG.register("void_worm_part", () -> registerEntity(EntityType.Builder.<EntityVoidWormPart>of(EntityVoidWormPart::new, MobCategory.MONSTER).sized(1.2F, 1.35F).fireImmune().clientTrackingRange(20).alwaysUpdateVelocity(true).updateInterval(1), "void_worm_part"));
    public static final EntityType<EntityVoidWormShot> VOID_WORM_SHOT = DEF_REG.register("void_worm_shot", () -> registerEntity(EntityType.Builder.<EntityVoidWormShot>of(EntityVoidWormShot::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "void_worm_shot"));
    public static final EntityType<EntityVoidPortal> VOID_PORTAL = DEF_REG.register("void_portal", () -> registerEntity(EntityType.Builder.<EntityVoidPortal>of(EntityVoidPortal::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "void_portal"));
    public static final EntityType<EntityFrilledShark> FRILLED_SHARK = DEF_REG.register("frilled_shark", () -> registerEntity(EntityType.Builder.of(EntityFrilledShark::new, MobCategory.WATER_CREATURE).sized(1.3F, 0.4F).clientTrackingRange(8), "frilled_shark"));
    public static final EntityType<EntityMimicOctopus> MIMIC_OCTOPUS = DEF_REG.register("mimic_octopus", () -> registerEntity(EntityType.Builder.of(EntityMimicOctopus::new, MobCategory.WATER_CREATURE).sized(0.9F, 0.6F).clientTrackingRange(8), "mimic_octopus"));
    public static final EntityType<EntitySeagull> SEAGULL = DEF_REG.register("seagull", () -> registerEntity(EntityType.Builder.of(EntitySeagull::new, MobCategory.CREATURE).sized(0.45F, 0.45F).clientTrackingRange(10), "seagull"));
    public static final EntityType<EntityFroststalker> FROSTSTALKER = DEF_REG.register("froststalker", () -> registerEntity(EntityType.Builder.of(EntityFroststalker::new, MobCategory.CREATURE).sized(0.95F, 1.15F).immuneTo(Blocks.POWDER_SNOW), "froststalker"));
    public static final EntityType<EntityIceShard> ICE_SHARD = DEF_REG.register("ice_shard", () -> registerEntity(EntityType.Builder.<EntityIceShard>of(EntityIceShard::new, MobCategory.MISC).sized(0.45F, 0.45F).fireImmune(), "ice_shard"));
    public static final EntityType<EntityTusklin> TUSKLIN = DEF_REG.register("tusklin", () -> registerEntity(EntityType.Builder.of(EntityTusklin::new, MobCategory.CREATURE).sized(2.2F, 1.9F).immuneTo(Blocks.POWDER_SNOW).clientTrackingRange(10), "tusklin"));
    public static final EntityType<EntityLaviathan> LAVIATHAN = DEF_REG.register("laviathan", () -> registerEntity(EntityType.Builder.of(EntityLaviathan::new, MobCategory.CREATURE).sized(3.3F, 2.4F).fireImmune().alwaysUpdateVelocity(true).updateInterval(1).clientTrackingRange(10), "laviathan"));
    public static final EntityType<EntityCosmaw> COSMAW = DEF_REG.register("cosmaw", () -> registerEntity(EntityType.Builder.of(EntityCosmaw::new, MobCategory.CREATURE).sized(1.95F, 1.8F).clientTrackingRange(10), "cosmaw"));
    public static final EntityType<EntityToucan> TOUCAN = DEF_REG.register("toucan", () -> registerEntity(EntityType.Builder.of(EntityToucan::new, MobCategory.CREATURE).sized(0.45F, 0.45F).clientTrackingRange(10), "toucan"));
    public static final EntityType<EntityManedWolf> MANED_WOLF = DEF_REG.register("maned_wolf", () -> registerEntity(EntityType.Builder.of(EntityManedWolf::new, MobCategory.CREATURE).sized(0.9F, 1.26F).clientTrackingRange(10), "maned_wolf"));
    public static final EntityType<EntityAnaconda> ANACONDA = DEF_REG.register("anaconda", () -> registerEntity(EntityType.Builder.of(EntityAnaconda::new, MobCategory.CREATURE).sized(0.8F, 0.8F).clientTrackingRange(10), "anaconda"));
    public static final EntityType<EntityAnacondaPart> ANACONDA_PART = DEF_REG.register("anaconda_part", () -> registerEntity(EntityType.Builder.<EntityAnacondaPart>of(EntityAnacondaPart::new, MobCategory.MISC).sized(0.8F, 0.8F).alwaysUpdateVelocity(true).updateInterval(1).clientTrackingRange(10), "anaconda_part"));
    public static final EntityType<EntityVineLasso> VINE_LASSO = DEF_REG.register("vine_lasso", () -> registerEntity(EntityType.Builder.<EntityVineLasso>of(EntityVineLasso::new, MobCategory.MISC).sized(0.85F, 0.2F).fireImmune(), "vine_lasso"));
    public static final EntityType<EntityAnteater> ANTEATER = DEF_REG.register("anteater", () -> registerEntity(EntityType.Builder.of(EntityAnteater::new, MobCategory.CREATURE).sized(1.3F, 1.1F).clientTrackingRange(10), "anteater"));
    public static final EntityType<EntityRockyRoller> ROCKY_ROLLER = DEF_REG.register("rocky_roller", () -> registerEntity(EntityType.Builder.of(EntityRockyRoller::new, MobCategory.MONSTER).sized(1.2F, 1.45F).clientTrackingRange(8), "rocky_roller"));
    public static final EntityType<EntityFlutter> FLUTTER = DEF_REG.register("flutter", () -> registerEntity(EntityType.Builder.of(EntityFlutter::new, MobCategory.AMBIENT).sized(0.5F, 0.7F).clientTrackingRange(6), "flutter"));
    public static final EntityType<EntityPollenBall> POLLEN_BALL = DEF_REG.register("pollen_ball", () -> registerEntity(EntityType.Builder.<EntityPollenBall>of(EntityPollenBall::new, MobCategory.MISC).sized(0.35F, 0.35F).fireImmune(), "pollen_ball"));
    public static final EntityType<EntityGeladaMonkey> GELADA_MONKEY = DEF_REG.register("gelada_monkey", () -> registerEntity(EntityType.Builder.of(EntityGeladaMonkey::new, MobCategory.CREATURE).sized(1.2F, 1.2F).clientTrackingRange(10), "gelada_monkey"));
    public static final EntityType<EntityJerboa> JERBOA = DEF_REG.register("jerboa", () -> registerEntity(EntityType.Builder.of(EntityJerboa::new, MobCategory.AMBIENT).sized(0.5F, 0.5F).clientTrackingRange(5), "jerboa"));
    public static final EntityType<EntityTerrapin> TERRAPIN = DEF_REG.register("terrapin", () -> registerEntity(EntityType.Builder.of(EntityTerrapin::new, MobCategory.WATER_AMBIENT).sized(0.75F, 0.45F).clientTrackingRange(5), "terrapin"));
    public static final EntityType<EntityCombJelly> COMB_JELLY = DEF_REG.register("comb_jelly", () -> registerEntity(EntityType.Builder.of(EntityCombJelly::new, MobCategory.WATER_AMBIENT).sized(0.65F, 0.8F).clientTrackingRange(5), "comb_jelly"));
    public static final EntityType<EntityCosmicCod> COSMIC_COD = DEF_REG.register("cosmic_cod", () -> registerEntity(EntityType.Builder.of(EntityCosmicCod::new, MobCategory.AMBIENT).sized(0.85F, 0.4F).clientTrackingRange(5), "cosmic_cod"));
    public static final EntityType<EntityBunfungus> BUNFUNGUS = DEF_REG.register("bunfungus", () -> registerEntity(EntityType.Builder.of(EntityBunfungus::new, MobCategory.CREATURE).sized(1.85F, 2.1F).clientTrackingRange(10), "bunfungus"));
    public static final EntityType<EntityBison> BISON = DEF_REG.register("bison", () -> registerEntity(EntityType.Builder.of(EntityBison::new, MobCategory.CREATURE).sized(2.4F, 2.1F).clientTrackingRange(10), "bison"));
    public static final EntityType<EntityGiantSquid> GIANT_SQUID = DEF_REG.register("giant_squid", () -> registerEntity(EntityType.Builder.of(EntityGiantSquid::new, MobCategory.WATER_CREATURE).sized(0.9F, 1.2F).clientTrackingRange(10), "giant_squid"));
    public static final EntityType<EntitySquidGrapple> SQUID_GRAPPLE = DEF_REG.register("squid_grapple", () -> registerEntity(EntityType.Builder.<EntitySquidGrapple>of(EntitySquidGrapple::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "squid_grapple"));
    public static final EntityType<EntitySeaBear> SEA_BEAR = DEF_REG.register("sea_bear", () -> registerEntity(EntityType.Builder.of(EntitySeaBear::new, MobCategory.WATER_CREATURE).sized(2.4F, 1.99F).clientTrackingRange(10), "sea_bear"));
    public static final EntityType<EntityDevilsHolePupfish> DEVILS_HOLE_PUPFISH = DEF_REG.register("devils_hole_pupfish", () -> registerEntity(EntityType.Builder.of(EntityDevilsHolePupfish::new, MobCategory.WATER_AMBIENT).sized(0.6F, 0.4F).clientTrackingRange(4), "devils_hole_pupfish"));
    public static final EntityType<EntityCatfish> CATFISH = DEF_REG.register("catfish", () -> registerEntity(EntityType.Builder.of(EntityCatfish::new, MobCategory.WATER_AMBIENT).sized(0.9F, 0.6F).clientTrackingRange(5), "catfish"));
    public static final EntityType<EntityFlyingFish> FLYING_FISH = DEF_REG.register("flying_fish", () -> registerEntity(EntityType.Builder.of(EntityFlyingFish::new, MobCategory.WATER_AMBIENT).sized(0.6F, 0.4F).clientTrackingRange(5), "flying_fish"));
    public static final EntityType<EntitySkelewag> SKELEWAG = DEF_REG.register("skelewag", () -> registerEntity(EntityType.Builder.of(EntitySkelewag::new, MobCategory.MONSTER).sized(2F, 1.2F).alwaysUpdateVelocity(true).updateInterval(1).clientTrackingRange(8), "skelewag"));
    public static final EntityType<EntityRainFrog> RAIN_FROG = DEF_REG.register("rain_frog", () -> registerEntity(EntityType.Builder.of(EntityRainFrog::new, MobCategory.AMBIENT).sized(0.55F, 0.5F).clientTrackingRange(5), "rain_frog"));
    public static final EntityType<EntityPotoo> POTOO = DEF_REG.register("potoo", () -> registerEntity(EntityType.Builder.of(EntityPotoo::new, MobCategory.CREATURE).sized(0.6F, 0.8F).clientTrackingRange(10), "potoo"));
    public static final EntityType<EntityMudskipper> MUDSKIPPER = DEF_REG.register("mudskipper", () -> registerEntity(EntityType.Builder.of(EntityMudskipper::new, MobCategory.CREATURE).sized(0.7F, 0.44F).clientTrackingRange(10), "mudskipper"));
    public static final EntityType<EntityMudBall> MUD_BALL = DEF_REG.register("mud_ball", () -> registerEntity(EntityType.Builder.<EntityMudBall>of(EntityMudBall::new, MobCategory.MISC).sized(0.35F, 0.35F).fireImmune(), "mud_ball"));
    public static final EntityType<EntityRhinoceros> RHINOCEROS = DEF_REG.register("rhinoceros", () -> registerEntity(EntityType.Builder.of(EntityRhinoceros::new, MobCategory.CREATURE).sized(2.3F, 2.4F).clientTrackingRange(10), "rhinoceros"));
    public static final EntityType<EntitySugarGlider> SUGAR_GLIDER = DEF_REG.register("sugar_glider", () -> registerEntity(EntityType.Builder.of(EntitySugarGlider::new, MobCategory.CREATURE).sized(0.8F, 0.45F).clientTrackingRange(10), "sugar_glider"));
    public static final EntityType<EntityFarseer> FARSEER = DEF_REG.register("farseer", () -> registerEntity(EntityType.Builder.of(EntityFarseer::new, MobCategory.MONSTER).sized(0.99F, 1.5F).alwaysUpdateVelocity(true).updateInterval(1).fireImmune().clientTrackingRange(8), "farseer"));
    public static final EntityType<EntitySkreecher> SKREECHER = DEF_REG.register("skreecher", () -> registerEntity(EntityType.Builder.of(EntitySkreecher::new, MobCategory.CREATURE).sized(0.99F, 0.95F).alwaysUpdateVelocity(true).updateInterval(1).clientTrackingRange(8), "skreecher"));
    public static final EntityType<EntityUnderminer> UNDERMINER = DEF_REG.register("underminer", () -> registerEntity(EntityType.Builder.of(EntityUnderminer::new, MobCategory.AMBIENT).sized(0.8F, 1.8F).clientTrackingRange(8), "underminer"));
    public static final EntityType<EntityMurmur> MURMUR = DEF_REG.register("murmur", () -> registerEntity(EntityType.Builder.of(EntityMurmur::new, MobCategory.MONSTER).sized(0.7F, 1.45F).clientTrackingRange(8), "murmur"));
    public static final EntityType<EntityMurmurHead> MURMUR_HEAD = DEF_REG.register("murmur_head", () -> registerEntity(EntityType.Builder.<EntityMurmurHead>of(EntityMurmurHead::new, MobCategory.MONSTER).sized(0.55F, 0.55F).clientTrackingRange(8), "murmur_head"));
    public static final EntityType<EntityTendonSegment> TENDON_SEGMENT = DEF_REG.register("tendon_segment", () -> registerEntity(EntityType.Builder.of(EntityTendonSegment::new, MobCategory.MISC).sized(0.1F, 0.1F).fireImmune(), "tendon_segment"));
    public static final EntityType<EntitySkunk> SKUNK = DEF_REG.register("skunk", () -> registerEntity(EntityType.Builder.of(EntitySkunk::new, MobCategory.CREATURE).sized(0.85F, 0.65F).clientTrackingRange(10), "skunk"));
    public static final EntityType<EntityFart> FART = DEF_REG.register("fart", () -> registerEntity(EntityType.Builder.<EntityFart>of(EntityFart::new, MobCategory.MISC).sized(0.7F, 0.3F).fireImmune(), "fart"));
    public static final EntityType<EntityBananaSlug> BANANA_SLUG = DEF_REG.register("banana_slug", () -> registerEntity(EntityType.Builder.of(EntityBananaSlug::new, MobCategory.CREATURE).sized(0.8F, 0.4F).clientTrackingRange(10), "banana_slug"));
    public static final EntityType<EntityBlueJay> BLUE_JAY = DEF_REG.register("blue_jay", () -> registerEntity(EntityType.Builder.of(EntityBlueJay::new, MobCategory.CREATURE).sized(0.5F, 0.6F).clientTrackingRange(10), "blue_jay"));
    public static final EntityType<EntityCaiman> CAIMAN = DEF_REG.register("caiman", () -> registerEntity(EntityType.Builder.of(EntityCaiman::new, MobCategory.CREATURE).sized(1.3F, 0.6F).clientTrackingRange(10), "caiman"));
    public static final EntityType<EntityTriops> TRIOPS = DEF_REG.register("triops", () -> registerEntity(EntityType.Builder.of(EntityTriops::new, MobCategory.WATER_AMBIENT).sized(0.7F, 0.25F).clientTrackingRange(5), "triops"));

    private static <T extends Entity> EntityType<T> registerEntity(EntityType.Builder<T> builder, String entityName) {
        return builder.build(entityName);
    }

    public static void registerSpawnPlacements() {
        // Custom spawn placement type for leaves - using ON_GROUND as base with custom spawn rules
        SpawnPlacementType spawnsOnLeaves = SpawnPlacementTypes.ON_GROUND;
        SpawnPlacements.register(GRIZZLY_BEAR, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ROADRUNNER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityRoadrunner::canRoadrunnerSpawn);
        SpawnPlacements.register(BONE_SERPENT, SpawnPlacementTypes.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityBoneSerpent::canBoneSerpentSpawn);
        SpawnPlacements.register(GAZELLE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(CROCODILE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCrocodile::canCrocodileSpawn);
        SpawnPlacements.register(FLY, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityFly::canFlySpawn);
        SpawnPlacements.register(HUMMINGBIRD, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, EntityHummingbird::canHummingbirdSpawn);
        SpawnPlacements.register(ORCA, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityOrca::canOrcaSpawn);
        SpawnPlacements.register(SUNBIRD, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySunbird::canSunbirdSpawn);
        SpawnPlacements.register(GORILLA, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, EntityGorilla::canGorillaSpawn);
        SpawnPlacements.register(CRIMSON_MOSQUITO, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCrimsonMosquito::canMosquitoSpawn);
        SpawnPlacements.register(RATTLESNAKE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityRattlesnake::canRattlesnakeSpawn);
        SpawnPlacements.register(ENDERGRADE, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityEndergrade::canEndergradeSpawn);
        SpawnPlacements.register(HAMMERHEAD_SHARK, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityHammerheadShark::canHammerheadSharkSpawn);
        SpawnPlacements.register(LOBSTER, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityLobster::canLobsterSpawn);
        SpawnPlacements.register(KOMODO_DRAGON, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityKomodoDragon::canKomodoDragonSpawn);
        SpawnPlacements.register(CAPUCHIN_MONKEY, spawnsOnLeaves, Heightmap.Types.MOTION_BLOCKING, EntityCapuchinMonkey::canCapuchinSpawn);
        SpawnPlacements.register(CENTIPEDE_HEAD, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCentipedeHead::canCentipedeSpawn);
        SpawnPlacements.register(WARPED_TOAD, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, EntityWarpedToad::canWarpedToadSpawn);
        SpawnPlacements.register(MOOSE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityMoose::canMooseSpawn);
        SpawnPlacements.register(MIMICUBE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(RACCOON, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(BLOBFISH, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityBlobfish::canBlobfishSpawn);
        SpawnPlacements.register(SEAL, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySeal::canSealSpawn);
        SpawnPlacements.register(COCKROACH, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCockroach::canCockroachSpawn);
        SpawnPlacements.register(SHOEBILL, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ELEPHANT, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(SOUL_VULTURE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySoulVulture::canVultureSpawn);
        SpawnPlacements.register(SNOW_LEOPARD, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySnowLeopard::canSnowLeopardSpawn);
        SpawnPlacements.register(CROW, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, EntityCrow::canCrowSpawn);
        SpawnPlacements.register(ALLIGATOR_SNAPPING_TURTLE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityAlligatorSnappingTurtle::canTurtleSpawn);
        SpawnPlacements.register(MUNGUS, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityMungus::canMungusSpawn);
        SpawnPlacements.register(MANTIS_SHRIMP, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityMantisShrimp::canMantisShrimpSpawn);
        SpawnPlacements.register(GUSTER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityGuster::canGusterSpawn);
        SpawnPlacements.register(WARPED_MOSCO, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
        SpawnPlacements.register(STRADDLER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityStraddler::canStraddlerSpawn);
        SpawnPlacements.register(STRADPOLE, SpawnPlacementTypes.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityStradpole::canStradpoleSpawn);
        SpawnPlacements.register(EMU, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityEmu::canEmuSpawn);
        SpawnPlacements.register(PLATYPUS, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityPlatypus::canPlatypusSpawn);
        SpawnPlacements.register(DROPBEAR, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
        SpawnPlacements.register(TASMANIAN_DEVIL, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(KANGAROO, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityKangaroo::canKangarooSpawn);
        SpawnPlacements.register(CACHALOT_WHALE, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCachalotWhale::canCachalotWhaleSpawn);
        SpawnPlacements.register(LEAFCUTTER_ANT, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ENDERIOPHAGE, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityEnderiophage::canEnderiophageSpawn);
        SpawnPlacements.register(BALD_EAGLE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, EntityBaldEagle::canEagleSpawn);
        SpawnPlacements.register(TIGER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityTiger::canTigerSpawn);
        SpawnPlacements.register(TARANTULA_HAWK, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityTarantulaHawk::canTarantulaHawkSpawn);
        SpawnPlacements.register(VOID_WORM, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityVoidWorm::canVoidWormSpawn);
        SpawnPlacements.register(FRILLED_SHARK, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityFrilledShark::canFrilledSharkSpawn);
        SpawnPlacements.register(MIMIC_OCTOPUS, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityMimicOctopus::canMimicOctopusSpawn);
        SpawnPlacements.register(SEAGULL, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySeagull::canSeagullSpawn);
        SpawnPlacements.register(FROSTSTALKER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityFroststalker::canFroststalkerSpawn);
        SpawnPlacements.register(TUSKLIN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityTusklin::canTusklinSpawn);
        SpawnPlacements.register(LAVIATHAN, SpawnPlacementTypes.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityLaviathan::canLaviathanSpawn);
        SpawnPlacements.register(COSMAW, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCosmaw::canCosmawSpawn);
        SpawnPlacements.register(TOUCAN, spawnsOnLeaves, Heightmap.Types.MOTION_BLOCKING, EntityToucan::canToucanSpawn);
        SpawnPlacements.register(MANED_WOLF, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityManedWolf::checkAnimalSpawnRules);
        SpawnPlacements.register(ANACONDA, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityAnaconda::canAnacondaSpawn);
        SpawnPlacements.register(ANTEATER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityAnteater::canAnteaterSpawn);
        SpawnPlacements.register(ROCKY_ROLLER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityRockyRoller::checkRockyRollerSpawnRules);
        SpawnPlacements.register(FLUTTER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityFlutter::canFlutterSpawn);
        SpawnPlacements.register(GELADA_MONKEY, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityGeladaMonkey::checkAnimalSpawnRules);
        SpawnPlacements.register(JERBOA, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityJerboa::canJerboaSpawn);
        SpawnPlacements.register(TERRAPIN, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityTerrapin::canTerrapinSpawn);
        SpawnPlacements.register(COMB_JELLY, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCombJelly::canCombJellySpawn);
        SpawnPlacements.register(BUNFUNGUS, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityBunfungus::canBunfungusSpawn);
        SpawnPlacements.register(BISON, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityBison::checkAnimalSpawnRules);
        SpawnPlacements.register(GIANT_SQUID, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityGiantSquid::canGiantSquidSpawn);
        SpawnPlacements.register(DEVILS_HOLE_PUPFISH, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityDevilsHolePupfish::canPupfishSpawn);
        SpawnPlacements.register(CATFISH, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCatfish::canCatfishSpawn);
        SpawnPlacements.register(FLYING_FISH, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(SKELEWAG, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySkelewag::canSkelewagSpawn);
        SpawnPlacements.register(RAIN_FROG, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityRainFrog::canRainFrogSpawn);
        SpawnPlacements.register(POTOO, spawnsOnLeaves, Heightmap.Types.MOTION_BLOCKING, EntityPotoo::canPotooSpawn);
        SpawnPlacements.register(MUDSKIPPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityMudskipper::canMudskipperSpawn);
        SpawnPlacements.register(RHINOCEROS, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityRhinoceros::checkAnimalSpawnRules);
        SpawnPlacements.register(SUGAR_GLIDER, spawnsOnLeaves, Heightmap.Types.MOTION_BLOCKING, EntitySugarGlider::canSugarGliderSpawn);
        SpawnPlacements.register(FARSEER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityFarseer::checkFarseerSpawnRules);
        SpawnPlacements.register(SKREECHER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySkreecher::checkSkreecherSpawnRules);
        SpawnPlacements.register(UNDERMINER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityUnderminer::checkUnderminerSpawnRules);
        SpawnPlacements.register(MURMUR, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityMurmur::checkMurmurSpawnRules);
        SpawnPlacements.register(SKUNK, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySkunk::checkAnimalSpawnRules);
        SpawnPlacements.register(BANANA_SLUG, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityBananaSlug::checkBananaSlugSpawnRules);
        SpawnPlacements.register(BLUE_JAY, spawnsOnLeaves, Heightmap.Types.MOTION_BLOCKING, EntityBlueJay::checkBlueJaySpawnRules);
        SpawnPlacements.register(CAIMAN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCaiman::canCaimanSpawn);
        SpawnPlacements.register(TRIOPS, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
    }

    public static void initializeAttributes() {
        FabricDefaultAttributeRegistry.register(GRIZZLY_BEAR, EntityGrizzlyBear.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(ROADRUNNER, EntityRoadrunner.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(BONE_SERPENT, EntityBoneSerpent.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(BONE_SERPENT_PART, EntityBoneSerpentPart.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(GAZELLE, EntityGazelle.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(CROCODILE, EntityCrocodile.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(FLY, EntityFly.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(HUMMINGBIRD, EntityHummingbird.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(ORCA, EntityOrca.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(SUNBIRD, EntitySunbird.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(GORILLA, EntityGorilla.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(CRIMSON_MOSQUITO, EntityCrimsonMosquito.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(RATTLESNAKE, EntityRattlesnake.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(ENDERGRADE, EntityEndergrade.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(HAMMERHEAD_SHARK, EntityHammerheadShark.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(LOBSTER, EntityLobster.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(KOMODO_DRAGON, EntityKomodoDragon.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(CAPUCHIN_MONKEY, EntityCapuchinMonkey.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(CENTIPEDE_HEAD, EntityCentipedeHead.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(CENTIPEDE_BODY, EntityCentipedeBody.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(CENTIPEDE_TAIL, EntityCentipedeTail.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(WARPED_TOAD, EntityWarpedToad.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MOOSE, EntityMoose.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MIMICUBE, EntityMimicube.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(RACCOON, EntityRaccoon.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(BLOBFISH, EntityBlobfish.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(SEAL, EntitySeal.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(COCKROACH, EntityCockroach.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(SHOEBILL, EntityShoebill.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(ELEPHANT, EntityElephant.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(SOUL_VULTURE, EntitySoulVulture.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(SNOW_LEOPARD, EntitySnowLeopard.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(SPECTRE, EntitySpectre.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(CROW, EntityCrow.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(ALLIGATOR_SNAPPING_TURTLE, EntityAlligatorSnappingTurtle.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MUNGUS, EntityMungus.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MANTIS_SHRIMP, EntityMantisShrimp.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(GUSTER, EntityGuster.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(WARPED_MOSCO, EntityWarpedMosco.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(STRADDLER, EntityStraddler.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(STRADPOLE, EntityStradpole.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(EMU, EntityEmu.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(PLATYPUS, EntityPlatypus.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(DROPBEAR, EntityDropBear.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(TASMANIAN_DEVIL, EntityTasmanianDevil.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(KANGAROO, EntityKangaroo.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(CACHALOT_WHALE, EntityCachalotWhale.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(LEAFCUTTER_ANT, EntityLeafcutterAnt.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(ENDERIOPHAGE, EntityEnderiophage.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(BALD_EAGLE, EntityBaldEagle.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(TIGER, EntityTiger.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(TARANTULA_HAWK, EntityTarantulaHawk.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(VOID_WORM, EntityVoidWorm.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(VOID_WORM_PART, EntityVoidWormPart.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(FRILLED_SHARK, EntityFrilledShark.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MIMIC_OCTOPUS, EntityMimicOctopus.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(SEAGULL, EntitySeagull.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(FROSTSTALKER, EntityFroststalker.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(TUSKLIN, EntityTusklin.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(LAVIATHAN, EntityLaviathan.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(COSMAW, EntityCosmaw.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(TOUCAN, EntityToucan.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MANED_WOLF, EntityManedWolf.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(ANACONDA, EntityAnaconda.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(ANACONDA_PART, EntityAnacondaPart.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(ANTEATER, EntityAnteater.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(ROCKY_ROLLER, EntityRockyRoller.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(FLUTTER, EntityFlutter.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(GELADA_MONKEY, EntityGeladaMonkey.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(JERBOA, EntityJerboa.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(TERRAPIN, EntityTerrapin.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(COMB_JELLY, EntityCombJelly.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(COSMIC_COD, EntityCosmicCod.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(BUNFUNGUS, EntityBunfungus.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(BISON, EntityBison.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(GIANT_SQUID, EntityGiantSquid.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(SEA_BEAR, EntitySeaBear.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(DEVILS_HOLE_PUPFISH, EntityDevilsHolePupfish.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(CATFISH, EntityCatfish.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(FLYING_FISH, EntityFlyingFish.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(SKELEWAG, EntitySkelewag.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(RAIN_FROG, EntityRainFrog.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(POTOO, EntityPotoo.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MUDSKIPPER, EntityMudskipper.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(RHINOCEROS, EntityRhinoceros.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(SUGAR_GLIDER, EntitySugarGlider.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(FARSEER, EntityFarseer.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(SKREECHER, EntitySkreecher.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(UNDERMINER, EntityUnderminer.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MURMUR, EntityMurmur.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(MURMUR_HEAD, EntityMurmurHead.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(SKUNK, EntitySkunk.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(BANANA_SLUG, EntityBananaSlug.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(BLUE_JAY, EntityBlueJay.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(CAIMAN, EntityCaiman.bakeAttributes().build());
        FabricDefaultAttributeRegistry.register(TRIOPS, EntityTriops.bakeAttributes().build());
    }

    public static Predicate<LivingEntity> buildPredicateFromTag(TagKey<EntityType<?>> entityTag){
        if(entityTag == null){
            return Predicates.alwaysFalse();
        }else{
            return (com.google.common.base.Predicate<LivingEntity>) e -> e.isAlive() && e.getType().is(entityTag);
        }
    }

    public static Predicate<LivingEntity> buildPredicateFromTagTameable(TagKey<EntityType<?>> entityTag, LivingEntity owner){
        if(entityTag == null){
            return Predicates.alwaysFalse();
        }else{
            return (com.google.common.base.Predicate<LivingEntity>) e -> e.isAlive() && e.getType().is(entityTag) && !owner.isAlliedTo(e);
        }
    }

    public static boolean rollSpawn(int rolls, RandomSource random, MobSpawnType reason){
        if(reason == MobSpawnType.SPAWNER){
            return true;
        }else{
            return rolls <= 0 || random.nextInt(rolls) == 0;
        }
    }

    public static boolean createLeavesSpawnPlacement(LevelReader level, BlockPos pos, EntityType<?> type){
        BlockPos blockpos = pos.above();
        BlockPos blockpos1 = pos.below();
        FluidState fluidstate = level.getFluidState(pos);
        BlockState blockstate = level.getBlockState(pos);
        BlockState blockstate1 = level.getBlockState(blockpos1);
        if (!blockstate1.isValidSpawn(level, blockpos1, type) && !blockstate1.is(BlockTags.LEAVES)) {
            return false;
        } else {
            return NaturalSpawner.isValidEmptySpawnBlock(level, pos, blockstate, fluidstate, type) && NaturalSpawner.isValidEmptySpawnBlock(level, blockpos, level.getBlockState(blockpos), level.getFluidState(blockpos), type);
        }
    }

}
