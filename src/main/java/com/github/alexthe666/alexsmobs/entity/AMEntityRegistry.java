package com.github.alexthe666.alexsmobs.entity;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.google.common.base.Predicates;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
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
import xyz.bluspring.enumextension.extensions.SpawnPlacementsTypeExtension;

import java.util.function.Predicate;

public class AMEntityRegistry {

    public static final LazyRegistrar<EntityType<?>> DEF_REG = LazyRegistrar.create(Registry.ENTITY_TYPE, AlexsMobs.MODID);
    public static final RegistryObject<EntityType<EntityGrizzlyBear>> GRIZZLY_BEAR = DEF_REG.register("grizzly_bear", () -> registerEntity(EntityType.Builder.of(EntityGrizzlyBear::new, MobCategory.CREATURE).sized(1.6F, 1.8F), "grizzly_bear"));
    public static final RegistryObject<EntityType<EntityRoadrunner>> ROADRUNNER = DEF_REG.register("roadrunner", () -> registerEntity(EntityType.Builder.of(EntityRoadrunner::new, MobCategory.CREATURE).sized(0.45F, 0.75F), "roadrunner"));
    public static final RegistryObject<EntityType<EntityBoneSerpent>> BONE_SERPENT = DEF_REG.register("bone_serpent", () -> registerEntity(EntityType.Builder.of(EntityBoneSerpent::new, MobCategory.MONSTER).sized(1.2F, 1.15F).fireImmune(), "bone_serpent"));
    public static final RegistryObject<EntityType<EntityBoneSerpentPart>> BONE_SERPENT_PART = DEF_REG.register("bone_serpent_part", () -> registerEntity(EntityType.Builder.of(EntityBoneSerpentPart::new, MobCategory.MONSTER).sized(1F, 1F).fireImmune(), "bone_serpent_part"));
    public static final RegistryObject<EntityType<EntityGazelle>> GAZELLE = DEF_REG.register("gazelle", () -> registerEntity(EntityType.Builder.of(EntityGazelle::new, MobCategory.CREATURE).sized(0.85F, 1.25F), "gazelle"));
    public static final RegistryObject<EntityType<EntityCrocodile>> CROCODILE = DEF_REG.register("crocodile", () -> registerEntity(EntityType.Builder.of(EntityCrocodile::new, MobCategory.WATER_CREATURE).sized(2.15F, 0.75F), "crocodile"));
    public static final RegistryObject<EntityType<EntityFly>> FLY = DEF_REG.register("fly", () -> registerEntity(EntityType.Builder.of(EntityFly::new, MobCategory.AMBIENT).sized(0.35F, 0.35F), "fly"));
    public static final RegistryObject<EntityType<EntityHummingbird>> HUMMINGBIRD = DEF_REG.register("hummingbird", () -> registerEntity(EntityType.Builder.of(EntityHummingbird::new, MobCategory.CREATURE).sized(0.45F, 0.45F), "hummingbird"));
    public static final RegistryObject<EntityType<EntityOrca>> ORCA = DEF_REG.register("orca", () -> registerEntity(EntityType.Builder.of(EntityOrca::new, MobCategory.WATER_CREATURE).sized(3.75F, 1.75F), "orca"));
    public static final RegistryObject<EntityType<EntitySunbird>> SUNBIRD = DEF_REG.register("sunbird", () -> registerEntity(EntityType.Builder.of(EntitySunbird::new, MobCategory.CREATURE).sized(2.75F, 1.5F).fireImmune().clientTrackingRange(10).updateInterval(1), "sunbird"));
    public static final RegistryObject<EntityType<EntityGorilla>> GORILLA = DEF_REG.register("gorilla", () -> registerEntity(EntityType.Builder.of(EntityGorilla::new, MobCategory.CREATURE).sized(1.15F, 1.35F), "gorilla"));
    public static final RegistryObject<EntityType<EntityCrimsonMosquito>> CRIMSON_MOSQUITO = DEF_REG.register("crimson_mosquito", () -> registerEntity(EntityType.Builder.of(EntityCrimsonMosquito::new, MobCategory.MONSTER).sized(1.25F, 1.15F).fireImmune(), "crimson_mosquito"));
    public static final RegistryObject<EntityType<EntityMosquitoSpit>> MOSQUITO_SPIT = DEF_REG.register("mosquito_spit", () -> registerEntity(EntityType.Builder.of(EntityMosquitoSpit::new, MobCategory.MISC).sized(0.5F, 0.5F)/**/.fireImmune(), "mosquito_spit"));
    public static final RegistryObject<EntityType<EntityRattlesnake>> RATTLESNAKE = DEF_REG.register("rattlesnake", () -> registerEntity(EntityType.Builder.of(EntityRattlesnake::new, MobCategory.CREATURE).sized(0.95F, 0.35F), "rattlesnake"));
    public static final RegistryObject<EntityType<EntityEndergrade>> ENDERGRADE = DEF_REG.register("endergrade", () -> registerEntity(EntityType.Builder.of(EntityEndergrade::new, MobCategory.CREATURE).sized(0.95F, 0.85F), "endergrade"));
    public static final RegistryObject<EntityType<EntityHammerheadShark>> HAMMERHEAD_SHARK = DEF_REG.register("hammerhead_shark", () -> registerEntity(EntityType.Builder.of(EntityHammerheadShark::new, MobCategory.WATER_CREATURE).sized(2.4F, 1.25F), "hammerhead_shark"));
    public static final RegistryObject<EntityType<EntitySharkToothArrow>> SHARK_TOOTH_ARROW = DEF_REG.register("shark_tooth_arrow", () -> registerEntity(EntityType.Builder.of(EntitySharkToothArrow::new, MobCategory.MISC).sized(0.5F, 0.5F)/**/, "shark_tooth_arrow"));
    public static final RegistryObject<EntityType<EntityLobster>> LOBSTER = DEF_REG.register("lobster", () -> registerEntity(EntityType.Builder.of(EntityLobster::new, MobCategory.WATER_AMBIENT).sized(0.7F, 0.4F), "lobster"));
    public static final RegistryObject<EntityType<EntityKomodoDragon>> KOMODO_DRAGON = DEF_REG.register("komodo_dragon", () -> registerEntity(EntityType.Builder.of(EntityKomodoDragon::new, MobCategory.CREATURE).sized(1.9F, 0.9F), "komodo_dragon"));
    public static final RegistryObject<EntityType<EntityCapuchinMonkey>> CAPUCHIN_MONKEY = DEF_REG.register("capuchin_monkey", () -> registerEntity(EntityType.Builder.of(EntityCapuchinMonkey::new, MobCategory.CREATURE).sized(0.65F, 0.75F), "capuchin_monkey"));
    public static final RegistryObject<EntityType<EntityTossedItem>> TOSSED_ITEM = DEF_REG.register("tossed_item", () -> registerEntity(EntityType.Builder.of(EntityTossedItem::new, MobCategory.MISC).sized(0.5F, 0.5F)/**/.fireImmune(), "tossed_item"));
    public static final RegistryObject<EntityType<EntityCentipedeHead>> CENTIPEDE_HEAD = DEF_REG.register("centipede_head", () -> registerEntity(EntityType.Builder.of(EntityCentipedeHead::new, MobCategory.MONSTER).sized(0.9F, 0.9F), "centipede_head"));
    public static final RegistryObject<EntityType<EntityCentipedeBody>> CENTIPEDE_BODY = DEF_REG.register("centipede_body", () -> registerEntity(EntityType.Builder.of(EntityCentipedeBody::new, MobCategory.MISC).sized(0.9F, 0.9F).fireImmune().updateInterval(1), "centipede_body"));
    public static final RegistryObject<EntityType<EntityCentipedeTail>> CENTIPEDE_TAIL = DEF_REG.register("centipede_tail", () -> registerEntity(EntityType.Builder.of(EntityCentipedeTail::new, MobCategory.MISC).sized(0.9F, 0.9F).fireImmune().updateInterval(1), "centipede_tail"));
    public static final RegistryObject<EntityType<EntityWarpedToad>> WARPED_TOAD = DEF_REG.register("warped_toad", () -> registerEntity(EntityType.Builder.of(EntityWarpedToad::new, MobCategory.CREATURE).sized(0.9F, 1.4F).fireImmune().updateInterval(1), "warped_toad"));
    public static final RegistryObject<EntityType<EntityMoose>> MOOSE = DEF_REG.register("moose", () -> registerEntity(EntityType.Builder.of(EntityMoose::new, MobCategory.CREATURE).sized(1.7F, 2.4F), "moose"));
    public static final RegistryObject<EntityType<EntityMimicube>> MIMICUBE = DEF_REG.register("mimicube", () -> registerEntity(EntityType.Builder.of(EntityMimicube::new, MobCategory.MONSTER).sized(0.9F, 0.9F), "mimicube"));
    public static final RegistryObject<EntityType<EntityRaccoon>> RACCOON = DEF_REG.register("raccoon", () -> registerEntity(EntityType.Builder.of(EntityRaccoon::new, MobCategory.CREATURE).sized(0.8F, 0.9F), "raccoon"));
    public static final RegistryObject<EntityType<EntityBlobfish>> BLOBFISH = DEF_REG.register("blobfish", () -> registerEntity(EntityType.Builder.of(EntityBlobfish::new, MobCategory.WATER_AMBIENT).sized(0.7F, 0.45F), "blobfish"));
    public static final RegistryObject<EntityType<EntitySeal>> SEAL = DEF_REG.register("seal", () -> registerEntity(EntityType.Builder.of(EntitySeal::new, MobCategory.CREATURE).sized(1.8F, 0.9F), "seal"));
    public static final RegistryObject<EntityType<EntityCockroach>> COCKROACH = DEF_REG.register("cockroach", () -> registerEntity(EntityType.Builder.of(EntityCockroach::new, MobCategory.AMBIENT).sized(0.7F, 0.3F), "cockroach"));
    public static final RegistryObject<EntityType<EntityCockroachEgg>> COCKROACH_EGG = DEF_REG.register("cockroach_egg", () -> registerEntity(EntityType.Builder.of(EntityCockroachEgg::new, MobCategory.MISC).sized(0.5F, 0.5F)/**/.fireImmune(), "cockroach_egg"));
    public static final RegistryObject<EntityType<EntityShoebill>> SHOEBILL = DEF_REG.register("shoebill", () -> registerEntity(EntityType.Builder.of(EntityShoebill::new, MobCategory.CREATURE).sized(0.8F, 1.5F).updateInterval(1), "shoebill"));
    public static final RegistryObject<EntityType<EntityElephant>> ELEPHANT = DEF_REG.register("elephant", () -> registerEntity(EntityType.Builder.of(EntityElephant::new, MobCategory.CREATURE).sized(3.1F, 3.5F).updateInterval(1), "elephant"));
    public static final RegistryObject<EntityType<EntitySoulVulture>> SOUL_VULTURE = DEF_REG.register("soul_vulture", () -> registerEntity(EntityType.Builder.of(EntitySoulVulture::new, MobCategory.MONSTER).sized(0.9F, 1.3F).updateInterval(1).fireImmune(), "soul_vulture"));
    public static final RegistryObject<EntityType<EntitySnowLeopard>> SNOW_LEOPARD = DEF_REG.register("snow_leopard", () -> registerEntity(EntityType.Builder.of(EntitySnowLeopard::new, MobCategory.CREATURE).sized(1.2F, 1.3F).immuneTo(Blocks.POWDER_SNOW), "snow_leopard"));
    public static final RegistryObject<EntityType<EntitySpectre>> SPECTRE = DEF_REG.register("spectre", () -> registerEntity(EntityType.Builder.of(EntitySpectre::new, MobCategory.CREATURE).sized(3.15F, 0.8F).fireImmune().clientTrackingRange(10).updateInterval(1), "spectre"));
    public static final RegistryObject<EntityType<EntityCrow>> CROW = DEF_REG.register("crow", () -> registerEntity(EntityType.Builder.of(EntityCrow::new, MobCategory.CREATURE).sized(0.45F, 0.45F), "crow"));
    public static final RegistryObject<EntityType<EntityAlligatorSnappingTurtle>> ALLIGATOR_SNAPPING_TURTLE = DEF_REG.register("alligator_snapping_turtle", () -> registerEntity(EntityType.Builder.of(EntityAlligatorSnappingTurtle::new, MobCategory.CREATURE).sized(1.25F, 0.65F), "alligator_snapping_turtle"));
    public static final RegistryObject<EntityType<EntityMungus>> MUNGUS = DEF_REG.register("mungus", () -> registerEntity(EntityType.Builder.of(EntityMungus::new, MobCategory.CREATURE).sized(0.75F, 1.45F), "mungus"));
    public static final RegistryObject<EntityType<EntityMantisShrimp>> MANTIS_SHRIMP = DEF_REG.register("mantis_shrimp", () -> registerEntity(EntityType.Builder.of(EntityMantisShrimp::new, MobCategory.WATER_CREATURE).sized(1.25F, 1.2F), "mantis_shrimp"));
    public static final RegistryObject<EntityType<EntityGuster>> GUSTER = DEF_REG.register("guster", () -> registerEntity(EntityType.Builder.of(EntityGuster::new, MobCategory.MONSTER).sized(1.42F, 2.35F).fireImmune(), "guster"));
    public static final RegistryObject<EntityType<EntitySandShot>> SAND_SHOT = DEF_REG.register("sand_shot", () -> registerEntity(EntityType.Builder.of(EntitySandShot::new, MobCategory.MISC).sized(0.95F, 0.65F)/**/.fireImmune(), "sand_shot"));
    public static final RegistryObject<EntityType<EntityGust>> GUST = DEF_REG.register("gust", () -> registerEntity(EntityType.Builder.of(EntityGust::new, MobCategory.MISC).sized(0.8F, 0.8F)/**/.fireImmune(), "gust"));
    public static final RegistryObject<EntityType<EntityWarpedMosco>> WARPED_MOSCO = DEF_REG.register("warped_mosco", () -> registerEntity(EntityType.Builder.of(EntityWarpedMosco::new, MobCategory.MONSTER).sized(1.99F, 3.25F).fireImmune(), "warped_mosco"));
    public static final RegistryObject<EntityType<EntityHemolymph>> HEMOLYMPH = DEF_REG.register("hemolymph", () -> registerEntity(EntityType.Builder.of(EntityHemolymph::new, MobCategory.MISC).sized(0.5F, 0.5F)/**/.fireImmune(), "hemolymph"));
    public static final RegistryObject<EntityType<EntityStraddler>> STRADDLER = DEF_REG.register("straddler", () -> registerEntity(EntityType.Builder.of(EntityStraddler::new, MobCategory.MONSTER).sized(1.65F, 3F).fireImmune(), "straddler"));
    public static final RegistryObject<EntityType<EntityStradpole>> STRADPOLE = DEF_REG.register("stradpole", () -> registerEntity(EntityType.Builder.of(EntityStradpole::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.5F).fireImmune(), "stradpole"));
    public static final RegistryObject<EntityType<EntityStraddleboard>> STRADDLEBOARD = DEF_REG.register("straddleboard", () -> registerEntity(EntityType.Builder.of(EntityStraddleboard::new, MobCategory.MISC).sized(1.5F, 0.35F).fireImmune().updateInterval(1).clientTrackingRange(10), "straddleboard"));
    public static final RegistryObject<EntityType<EntityEmu>> EMU = DEF_REG.register("emu", () -> registerEntity(EntityType.Builder.of(EntityEmu::new, MobCategory.CREATURE).sized(1.1F, 1.8F), "emu"));
    public static final RegistryObject<EntityType<EntityEmuEgg>> EMU_EGG = DEF_REG.register("emu_egg", () -> registerEntity(EntityType.Builder.of(EntityEmuEgg::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "emu_egg"));
    public static final RegistryObject<EntityType<EntityPlatypus>> PLATYPUS = DEF_REG.register("platypus", () -> registerEntity(EntityType.Builder.of(EntityPlatypus::new, MobCategory.CREATURE).sized(0.8F, 0.5F), "platypus"));
    public static final RegistryObject<EntityType<EntityDropBear>> DROPBEAR = DEF_REG.register("dropbear", () -> registerEntity(EntityType.Builder.of(EntityDropBear::new, MobCategory.MONSTER).sized(1.65F, 1.5F).fireImmune(), "dropbear"));
    public static final RegistryObject<EntityType<EntityTasmanianDevil>> TASMANIAN_DEVIL = DEF_REG.register("tasmanian_devil", () -> registerEntity(EntityType.Builder.of(EntityTasmanianDevil::new, MobCategory.CREATURE).sized(0.7F, 0.8F), "tasmanian_devil"));
    public static final RegistryObject<EntityType<EntityKangaroo>> KANGAROO = DEF_REG.register("kangaroo", () -> registerEntity(EntityType.Builder.of(EntityKangaroo::new, MobCategory.CREATURE).sized(1.65F, 1.5F), "kangaroo"));
    public static final RegistryObject<EntityType<EntityCachalotWhale>> CACHALOT_WHALE = DEF_REG.register("cachalot_whale", () -> registerEntity(EntityType.Builder.of(EntityCachalotWhale::new, MobCategory.WATER_CREATURE).sized(9F, 4.0F), "cachalot_whale"));
    public static final RegistryObject<EntityType<EntityCachalotEcho>> CACHALOT_ECHO = DEF_REG.register("cachalot_echo", () -> registerEntity(EntityType.Builder.of(EntityCachalotEcho::new, MobCategory.MISC).sized(2F, 2F).fireImmune(), "cachalot_echo"));
    public static final RegistryObject<EntityType<EntityLeafcutterAnt>> LEAFCUTTER_ANT = DEF_REG.register("leafcutter_ant", () -> registerEntity(EntityType.Builder.of(EntityLeafcutterAnt::new, MobCategory.CREATURE).sized(0.8F, 0.5F), "leafcutter_ant"));
    public static final RegistryObject<EntityType<EntityEnderiophage>> ENDERIOPHAGE = DEF_REG.register("enderiophage", () -> registerEntity(EntityType.Builder.of(EntityEnderiophage::new, MobCategory.CREATURE).sized(0.85F, 1.95F).updateInterval(1), "enderiophage"));
    public static final RegistryObject<EntityType<EntityEnderiophageRocket>> ENDERIOPHAGE_ROCKET = DEF_REG.register("enderiophage_rocket", () -> registerEntity(EntityType.Builder.of(EntityEnderiophageRocket::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "enderiophage_rocket"));
    public static final RegistryObject<EntityType<EntityBaldEagle>> BALD_EAGLE = DEF_REG.register("bald_eagle", () -> registerEntity(EntityType.Builder.of(EntityBaldEagle::new, MobCategory.CREATURE).sized(0.5F, 0.95F).updateInterval(1).clientTrackingRange(14), "bald_eagle"));
    public static final RegistryObject<EntityType<EntityTiger>> TIGER = DEF_REG.register("tiger", () -> registerEntity(EntityType.Builder.of(EntityTiger::new, MobCategory.CREATURE).sized(1.45F, 1.2F), "tiger"));
    public static final RegistryObject<EntityType<EntityTarantulaHawk>> TARANTULA_HAWK = DEF_REG.register("tarantula_hawk", () -> registerEntity(EntityType.Builder.of(EntityTarantulaHawk::new, MobCategory.CREATURE).sized(1.2F, 0.9F), "tarantula_hawk"));
    public static final RegistryObject<EntityType<EntityVoidWorm>> VOID_WORM = DEF_REG.register("void_worm", () -> registerEntity(EntityType.Builder.of(EntityVoidWorm::new, MobCategory.MONSTER).sized(3.4F, 3F).fireImmune().clientTrackingRange(20).updateInterval(1), "void_worm"));
    public static final RegistryObject<EntityType<EntityVoidWormPart>> VOID_WORM_PART = DEF_REG.register("void_worm_part", () -> registerEntity(EntityType.Builder.of(EntityVoidWormPart::new, MobCategory.MONSTER).sized(1.2F, 1.35F).fireImmune().clientTrackingRange(20).updateInterval(1), "void_worm_part"));
    public static final RegistryObject<EntityType<EntityVoidWormShot>> VOID_WORM_SHOT = DEF_REG.register("void_worm_shot", () -> registerEntity(EntityType.Builder.of(EntityVoidWormShot::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "void_worm_shot"));
    public static final RegistryObject<EntityType<EntityVoidPortal>> VOID_PORTAL = DEF_REG.register("void_portal", () -> registerEntity(EntityType.Builder.of(EntityVoidPortal::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "void_portal"));
    public static final RegistryObject<EntityType<EntityFrilledShark>> FRILLED_SHARK = DEF_REG.register("frilled_shark", () -> registerEntity(EntityType.Builder.of(EntityFrilledShark::new, MobCategory.WATER_CREATURE).sized(1.3F, 0.4F), "frilled_shark"));
    public static final RegistryObject<EntityType<EntityMimicOctopus>> MIMIC_OCTOPUS = DEF_REG.register("mimic_octopus", () -> registerEntity(EntityType.Builder.of(EntityMimicOctopus::new, MobCategory.WATER_CREATURE).sized(0.9F, 0.6F), "mimic_octopus"));
    public static final RegistryObject<EntityType<EntitySeagull>> SEAGULL = DEF_REG.register("seagull", () -> registerEntity(EntityType.Builder.of(EntitySeagull::new, MobCategory.CREATURE).sized(0.45F, 0.45F), "seagull"));
    public static final RegistryObject<EntityType<EntityFroststalker>> FROSTSTALKER = DEF_REG.register("froststalker", () -> registerEntity(EntityType.Builder.of(EntityFroststalker::new, MobCategory.CREATURE).sized(0.95F, 1.15F).immuneTo(Blocks.POWDER_SNOW), "froststalker"));
    public static final RegistryObject<EntityType<EntityIceShard>> ICE_SHARD = DEF_REG.register("ice_shard", () -> registerEntity(EntityType.Builder.of(EntityIceShard::new, MobCategory.MISC).sized(0.45F, 0.45F).fireImmune(), "ice_shard"));
    public static final RegistryObject<EntityType<EntityTusklin>> TUSKLIN = DEF_REG.register("tusklin", () -> registerEntity(EntityType.Builder.of(EntityTusklin::new, MobCategory.CREATURE).sized(2.2F, 1.9F).immuneTo(Blocks.POWDER_SNOW), "tusklin"));
    public static final RegistryObject<EntityType<EntityLaviathan>> LAVIATHAN = DEF_REG.register("laviathan", () -> registerEntity(EntityType.Builder.of(EntityLaviathan::new, MobCategory.CREATURE).sized(3.3F, 2.4F).fireImmune().updateInterval(1), "laviathan"));
    public static final RegistryObject<EntityType<EntityCosmaw>> COSMAW = DEF_REG.register("cosmaw", () -> registerEntity(EntityType.Builder.of(EntityCosmaw::new, MobCategory.CREATURE).sized(1.95F, 1.8F), "cosmaw"));
    public static final RegistryObject<EntityType<EntityToucan>> TOUCAN = DEF_REG.register("toucan", () -> registerEntity(EntityType.Builder.of(EntityToucan::new, MobCategory.CREATURE).sized(0.45F, 0.45F), "toucan"));
    public static final RegistryObject<EntityType<EntityManedWolf>> MANED_WOLF = DEF_REG.register("maned_wolf", () -> registerEntity(EntityType.Builder.of(EntityManedWolf::new, MobCategory.CREATURE).sized(0.9F, 1.26F), "maned_wolf"));
    public static final RegistryObject<EntityType<EntityAnaconda>> ANACONDA = DEF_REG.register("anaconda", () -> registerEntity(EntityType.Builder.of(EntityAnaconda::new, MobCategory.CREATURE).sized(0.8F, 0.8F), "anaconda"));
    public static final RegistryObject<EntityType<EntityAnacondaPart>> ANACONDA_PART = DEF_REG.register("anaconda_part", () -> registerEntity(EntityType.Builder.of(EntityAnacondaPart::new, MobCategory.MISC).sized(0.8F, 0.8F).updateInterval(1), "anaconda_part"));
    public static final RegistryObject<EntityType<EntityVineLasso>> VINE_LASSO = DEF_REG.register("vine_lasso", () -> registerEntity(EntityType.Builder.of(EntityVineLasso::new, MobCategory.MISC).sized(0.85F, 0.2F).fireImmune(), "vine_lasso"));
    public static final RegistryObject<EntityType<EntityAnteater>> ANTEATER = DEF_REG.register("anteater", () -> registerEntity(EntityType.Builder.of(EntityAnteater::new, MobCategory.CREATURE).sized(1.3F, 1.1F), "anteater"));
    public static final RegistryObject<EntityType<EntityRockyRoller>> ROCKY_ROLLER = DEF_REG.register("rocky_roller", () -> registerEntity(EntityType.Builder.of(EntityRockyRoller::new, MobCategory.MONSTER).sized(1.2F, 1.45F), "rocky_roller"));
    public static final RegistryObject<EntityType<EntityFlutter>> FLUTTER = DEF_REG.register("flutter", () -> registerEntity(EntityType.Builder.of(EntityFlutter::new, MobCategory.AMBIENT).sized(0.5F, 0.7F), "flutter"));
    public static final RegistryObject<EntityType<EntityPollenBall>> POLLEN_BALL = DEF_REG.register("pollen_ball", () -> registerEntity(EntityType.Builder.of(EntityPollenBall::new, MobCategory.MISC).sized(0.35F, 0.35F).fireImmune(), "pollen_ball"));
    public static final RegistryObject<EntityType<EntityGeladaMonkey>> GELADA_MONKEY = DEF_REG.register("gelada_monkey", () -> registerEntity(EntityType.Builder.of(EntityGeladaMonkey::new, MobCategory.CREATURE).sized(1.2F, 1.2F), "gelada_monkey"));
    public static final RegistryObject<EntityType<EntityJerboa>> JERBOA = DEF_REG.register("jerboa", () -> registerEntity(EntityType.Builder.of(EntityJerboa::new, MobCategory.AMBIENT).sized(0.5F, 0.5F), "jerboa"));
    public static final RegistryObject<EntityType<EntityTerrapin>> TERRAPIN = DEF_REG.register("terrapin", () -> registerEntity(EntityType.Builder.of(EntityTerrapin::new, MobCategory.WATER_AMBIENT).sized(0.75F, 0.45F), "terrapin"));
    public static final RegistryObject<EntityType<EntityCombJelly>> COMB_JELLY = DEF_REG.register("comb_jelly", () -> registerEntity(EntityType.Builder.of(EntityCombJelly::new, MobCategory.WATER_AMBIENT).sized(0.65F, 0.8F), "comb_jelly"));
    public static final RegistryObject<EntityType<EntityCosmicCod>> COSMIC_COD = DEF_REG.register("cosmic_cod", () -> registerEntity(EntityType.Builder.of(EntityCosmicCod::new, MobCategory.AMBIENT).sized(0.85F, 0.4F), "cosmic_cod"));
    public static final RegistryObject<EntityType<EntityBunfungus>> BUNFUNGUS = DEF_REG.register("bunfungus", () -> registerEntity(EntityType.Builder.of(EntityBunfungus::new, MobCategory.CREATURE).sized(1.85F, 2.1F), "bunfungus"));
    public static final RegistryObject<EntityType<EntityBison>> BISON = DEF_REG.register("bison", () -> registerEntity(EntityType.Builder.of(EntityBison::new, MobCategory.CREATURE).sized(2.4F, 2.1F), "bison"));
    public static final RegistryObject<EntityType<EntityGiantSquid>> GIANT_SQUID = DEF_REG.register("giant_squid", () -> registerEntity(EntityType.Builder.of(EntityGiantSquid::new, MobCategory.WATER_CREATURE).sized(0.9F, 1.2F), "giant_squid"));
    public static final RegistryObject<EntityType<EntitySquidGrapple>> SQUID_GRAPPLE = DEF_REG.register("squid_grapple", () -> registerEntity(EntityType.Builder.of(EntitySquidGrapple::new, MobCategory.MISC).sized(0.5F, 0.5F).fireImmune(), "squid_grapple"));
    public static final RegistryObject<EntityType<EntitySeaBear>> SEA_BEAR = DEF_REG.register("sea_bear", () -> registerEntity(EntityType.Builder.of(EntitySeaBear::new, MobCategory.WATER_CREATURE).sized(2.4F, 1.99F), "sea_bear"));
    public static final RegistryObject<EntityType<EntityDevilsHolePupfish>> DEVILS_HOLE_PUPFISH = DEF_REG.register("devils_hole_pupfish", () -> registerEntity(EntityType.Builder.of(EntityDevilsHolePupfish::new, MobCategory.WATER_AMBIENT).sized(0.6F, 0.4F), "devils_hole_pupfish"));
    public static final RegistryObject<EntityType<EntityCatfish>> CATFISH = DEF_REG.register("catfish", () -> registerEntity(EntityType.Builder.of(EntityCatfish::new, MobCategory.WATER_AMBIENT).sized(0.9F, 0.6F), "catfish"));
    public static final RegistryObject<EntityType<EntityFlyingFish>> FLYING_FISH = DEF_REG.register("flying_fish", () -> registerEntity(EntityType.Builder.of(EntityFlyingFish::new, MobCategory.WATER_AMBIENT).sized(0.6F, 0.4F), "flying_fish"));
    public static final RegistryObject<EntityType<EntitySkelewag>> SKELEWAG = DEF_REG.register("skelewag", () -> registerEntity(EntityType.Builder.of(EntitySkelewag::new, MobCategory.MONSTER).sized(2F, 1.2F).updateInterval(1), "skelewag"));
    public static final RegistryObject<EntityType<EntityRainFrog>> RAIN_FROG = DEF_REG.register("rain_frog", () -> registerEntity(EntityType.Builder.of(EntityRainFrog::new, MobCategory.AMBIENT).sized(0.55F, 0.5F), "rain_frog"));
    public static final RegistryObject<EntityType<EntityPotoo>> POTOO = DEF_REG.register("potoo", () -> registerEntity(EntityType.Builder.of(EntityPotoo::new, MobCategory.CREATURE).sized(0.6F, 0.8F), "potoo"));
    public static final RegistryObject<EntityType<EntityMudskipper>> MUDSKIPPER = DEF_REG.register("mudskipper", () -> registerEntity(EntityType.Builder.of(EntityMudskipper::new, MobCategory.CREATURE).sized(0.7F, 0.44F), "mudskipper"));
    public static final RegistryObject<EntityType<EntityMudBall>> MUD_BALL = DEF_REG.register("mud_ball", () -> registerEntity(EntityType.Builder.of(EntityMudBall::new, MobCategory.MISC).sized(0.35F, 0.35F).fireImmune(), "mud_ball"));
    public static final RegistryObject<EntityType<EntityRhinoceros>> RHINOCEROS = DEF_REG.register("rhinoceros", () -> registerEntity(EntityType.Builder.of(EntityRhinoceros::new, MobCategory.CREATURE).sized(2.3F, 2.4F), "rhinoceros"));
    public static final RegistryObject<EntityType<EntitySugarGlider>> SUGAR_GLIDER = DEF_REG.register("sugar_glider", () -> registerEntity(EntityType.Builder.of(EntitySugarGlider::new, MobCategory.CREATURE).sized(0.8F, 0.45F), "sugar_glider"));
    public static final RegistryObject<EntityType<EntityFarseer>> FARSEER = DEF_REG.register("farseer", () -> registerEntity(EntityType.Builder.of(EntityFarseer::new, MobCategory.MONSTER).sized(0.99F, 1.5F).updateInterval(1).fireImmune(), "farseer"));
    public static final RegistryObject<EntityType<EntitySkreecher>> SKREECHER = DEF_REG.register("skreecher", () -> registerEntity(EntityType.Builder.of(EntitySkreecher::new, MobCategory.MONSTER).sized(0.99F, 0.95F).updateInterval(1), "skreecher"));
    public static final RegistryObject<EntityType<EntityUnderminer>> UNDERMINER = DEF_REG.register("underminer", () -> registerEntity(EntityType.Builder.of(EntityUnderminer::new, MobCategory.AMBIENT).sized(0.8F, 1.8F), "underminer"));
    public static final RegistryObject<EntityType<EntityMurmur>> MURMUR = DEF_REG.register("murmur", () -> registerEntity(EntityType.Builder.of(EntityMurmur::new, MobCategory.MONSTER).sized(0.7F, 1.45F), "murmur"));
    public static final RegistryObject<EntityType<EntityMurmurHead>> MURMUR_HEAD = DEF_REG.register("murmur_head", () -> registerEntity(EntityType.Builder.of(EntityMurmurHead::new, MobCategory.MONSTER).sized(0.55F, 0.55F), "murmur_head"));
    public static final RegistryObject<EntityType<EntityTendonSegment>> TENDON_SEGMENT = DEF_REG.register("tendon_segment", () -> registerEntity(EntityType.Builder.of(EntityTendonSegment::new, MobCategory.MISC).sized(0.1F, 0.1F).fireImmune(), "tendon_segment"));
    public static final RegistryObject<EntityType<EntitySkunk>> SKUNK = DEF_REG.register("skunk", () -> registerEntity(EntityType.Builder.of(EntitySkunk::new, MobCategory.CREATURE).sized(0.85F, 0.65F), "skunk"));

    public static final RegistryObject<EntityType<EntityFart>> FART = DEF_REG.register("fart", () -> registerEntity(EntityType.Builder.of(EntityFart::new, MobCategory.MISC).sized(0.7F, 0.3F).fireImmune(), "fart"));
    public static final RegistryObject<EntityType<EntityBananaSlug>> BANANA_SLUG = DEF_REG.register("banana_slug", () -> registerEntity(EntityType.Builder.of(EntityBananaSlug::new, MobCategory.CREATURE).sized(0.8F, 0.4F), "banana_slug"));

    public static final RegistryObject<EntityType<EntityBlueJay>> BLUE_JAY = DEF_REG.register("blue_jay", () -> registerEntity(EntityType.Builder.of(EntityBlueJay::new, MobCategory.CREATURE).sized(0.5F, 0.6F), "blue_jay"));

    private static final EntityType registerEntity(EntityType.Builder builder, String entityName) {
        return (EntityType) builder.build(entityName);
    }

    public static void initializeAttributes() {
        SpawnPlacements.Type spawnsOnLeaves = SpawnPlacementsTypeExtension.create("am_leaves", AMEntityRegistry::createLeavesSpawnPlacement);
        SpawnPlacements.register(GRIZZLY_BEAR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ROADRUNNER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityRoadrunner::canRoadrunnerSpawn);
        SpawnPlacements.register(BONE_SERPENT.get(), SpawnPlacements.Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityBoneSerpent::canBoneSerpentSpawn);
        SpawnPlacements.register(GAZELLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(CROCODILE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCrocodile::canCrocodileSpawn);
        SpawnPlacements.register(FLY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityFly::canFlySpawn);
        SpawnPlacements.register(HUMMINGBIRD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, EntityHummingbird::canHummingbirdSpawn);
        SpawnPlacements.register(ORCA.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityOrca::canOrcaSpawn);
        SpawnPlacements.register(SUNBIRD.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySunbird::canSunbirdSpawn);
        SpawnPlacements.register(GORILLA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, EntityGorilla::canGorillaSpawn);
        SpawnPlacements.register(CRIMSON_MOSQUITO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCrimsonMosquito::canMosquitoSpawn);
        SpawnPlacements.register(RATTLESNAKE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityRattlesnake::canRattlesnakeSpawn);
        SpawnPlacements.register(ENDERGRADE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityEndergrade::canEndergradeSpawn);
        SpawnPlacements.register(HAMMERHEAD_SHARK.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityHammerheadShark::canHammerheadSharkSpawn);
        SpawnPlacements.register(LOBSTER.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityLobster::canLobsterSpawn);
        SpawnPlacements.register(KOMODO_DRAGON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityKomodoDragon::canKomodoDragonSpawn);
        SpawnPlacements.register(CAPUCHIN_MONKEY.get(), spawnsOnLeaves, Heightmap.Types.MOTION_BLOCKING, EntityCapuchinMonkey::canCapuchinSpawn);
        SpawnPlacements.register(CENTIPEDE_HEAD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCentipedeHead::canCentipedeSpawn);
        SpawnPlacements.register(WARPED_TOAD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, EntityWarpedToad::canWarpedToadSpawn);
        SpawnPlacements.register(MOOSE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityMoose::canMooseSpawn);
        SpawnPlacements.register(MIMICUBE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(RACCOON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(BLOBFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityBlobfish::canBlobfishSpawn);
        SpawnPlacements.register(SEAL.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySeal::canSealSpawn);
        SpawnPlacements.register(COCKROACH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCockroach::canCockroachSpawn);
        SpawnPlacements.register(SHOEBILL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ELEPHANT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(SOUL_VULTURE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySoulVulture::canVultureSpawn);
        SpawnPlacements.register(SNOW_LEOPARD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySnowLeopard::canSnowLeopardSpawn);
        SpawnPlacements.register(ALLIGATOR_SNAPPING_TURTLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityAlligatorSnappingTurtle::canTurtleSpawn);
        SpawnPlacements.register(MUNGUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityMungus::canMungusSpawn);
        SpawnPlacements.register(MANTIS_SHRIMP.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityMantisShrimp::canMantisShrimpSpawn);
        SpawnPlacements.register(GUSTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityGuster::canGusterSpawn);
        SpawnPlacements.register(WARPED_MOSCO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
        SpawnPlacements.register(STRADDLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityStraddler::canStraddlerSpawn);
        SpawnPlacements.register(STRADPOLE.get(), SpawnPlacements.Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityStradpole::canStradpoleSpawn);
        SpawnPlacements.register(EMU.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityEmu::canEmuSpawn);
        SpawnPlacements.register(PLATYPUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityPlatypus::canPlatypusSpawn);
        SpawnPlacements.register(DROPBEAR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkAnyLightMonsterSpawnRules);
        SpawnPlacements.register(TASMANIAN_DEVIL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(KANGAROO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityKangaroo::canKangarooSpawn);
        SpawnPlacements.register(CACHALOT_WHALE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCachalotWhale::canCachalotWhaleSpawn);
        SpawnPlacements.register(LEAFCUTTER_ANT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(ENDERIOPHAGE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityEnderiophage::canEnderiophageSpawn);
        SpawnPlacements.register(BALD_EAGLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, EntityBaldEagle::canEagleSpawn);
        SpawnPlacements.register(TIGER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityTiger::canTigerSpawn);
        SpawnPlacements.register(TARANTULA_HAWK.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityTarantulaHawk::canTarantulaHawkSpawn);
        SpawnPlacements.register(VOID_WORM.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityVoidWorm::canVoidWormSpawn);
        SpawnPlacements.register(FRILLED_SHARK.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityFrilledShark::canFrilledSharkSpawn);
        SpawnPlacements.register(MIMIC_OCTOPUS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityMimicOctopus::canMimicOctopusSpawn);
        SpawnPlacements.register(SEAGULL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySeagull::canSeagullSpawn);
        SpawnPlacements.register(FROSTSTALKER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityFroststalker::canFroststalkerSpawn);
        SpawnPlacements.register(TUSKLIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityTusklin::canTusklinSpawn);
        SpawnPlacements.register(LAVIATHAN.get(), SpawnPlacements.Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityLaviathan::canLaviathanSpawn);
        SpawnPlacements.register(COSMAW.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCosmaw::canCosmawSpawn);
        SpawnPlacements.register(TOUCAN.get(), spawnsOnLeaves, Heightmap.Types.MOTION_BLOCKING, EntityToucan::canToucanSpawn);
        SpawnPlacements.register(MANED_WOLF.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityManedWolf::checkAnimalSpawnRules);
        SpawnPlacements.register(ANACONDA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityAnaconda::canAnacondaSpawn);
        SpawnPlacements.register(ANTEATER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityAnteater::canAnteaterSpawn);
        SpawnPlacements.register(ROCKY_ROLLER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityRockyRoller::checkRockyRollerSpawnRules);
        SpawnPlacements.register(FLUTTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityFlutter::canFlutterSpawn);
        SpawnPlacements.register(GELADA_MONKEY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityGeladaMonkey::checkAnimalSpawnRules);
        SpawnPlacements.register(JERBOA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityJerboa::canJerboaSpawn);
        SpawnPlacements.register(TERRAPIN.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityTerrapin::canTerrapinSpawn);
        SpawnPlacements.register(COMB_JELLY.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCombJelly::canCombJellySpawn);
        SpawnPlacements.register(BUNFUNGUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityBunfungus::canBunfungusSpawn);
        SpawnPlacements.register(BISON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityBison::checkAnimalSpawnRules);
        SpawnPlacements.register(GIANT_SQUID.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityGiantSquid::canGiantSquidSpawn);
        SpawnPlacements.register(DEVILS_HOLE_PUPFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityDevilsHolePupfish::canPupfishSpawn);
        SpawnPlacements.register(CATFISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityCatfish::canCatfishSpawn);
        SpawnPlacements.register(FLYING_FISH.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules);
        SpawnPlacements.register(SKELEWAG.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySkelewag::canSkelewagSpawn);
        SpawnPlacements.register(RAIN_FROG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityRainFrog::canRainFrogSpawn);
        SpawnPlacements.register(POTOO.get(), spawnsOnLeaves, Heightmap.Types.MOTION_BLOCKING, EntityPotoo::canPotooSpawn);
        SpawnPlacements.register(MUDSKIPPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityMudskipper::canMudskipperSpawn);
        SpawnPlacements.register(RHINOCEROS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityRhinoceros::checkAnimalSpawnRules);
        SpawnPlacements.register(SUGAR_GLIDER.get(), spawnsOnLeaves, Heightmap.Types.MOTION_BLOCKING, EntitySugarGlider::canSugarGliderSpawn);
        SpawnPlacements.register(FARSEER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityFarseer::checkFarseerSpawnRules);
        SpawnPlacements.register(SKREECHER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySkreecher::checkSkreecherSpawnRules);
        SpawnPlacements.register(UNDERMINER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityUnderminer::checkUnderminerSpawnRules);
        SpawnPlacements.register(MURMUR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityMurmur::checkMurmurSpawnRules);
        SpawnPlacements.register(SKUNK.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySkunk::checkAnimalSpawnRules);
        SpawnPlacements.register(BANANA_SLUG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntityBananaSlug::checkBananaSlugSpawnRules);
        SpawnPlacements.register(BLUE_JAY.get(), spawnsOnLeaves, Heightmap.Types.MOTION_BLOCKING, EntityBlueJay::checkBlueJaySpawnRules);
        EntityAttributeRegistry.register(GRIZZLY_BEAR, EntityGrizzlyBear::bakeAttributes);
        EntityAttributeRegistry.register(ROADRUNNER, EntityRoadrunner::bakeAttributes);
        EntityAttributeRegistry.register(BONE_SERPENT, EntityBoneSerpent::bakeAttributes);
        EntityAttributeRegistry.register(BONE_SERPENT_PART, EntityBoneSerpentPart::bakeAttributes);
        EntityAttributeRegistry.register(GAZELLE, EntityGazelle::bakeAttributes);
        EntityAttributeRegistry.register(CROCODILE, EntityCrocodile::bakeAttributes);
        EntityAttributeRegistry.register(FLY, EntityFly::bakeAttributes);
        EntityAttributeRegistry.register(HUMMINGBIRD, EntityHummingbird::bakeAttributes);
        EntityAttributeRegistry.register(ORCA, EntityOrca::bakeAttributes);
        EntityAttributeRegistry.register(SUNBIRD, EntitySunbird::bakeAttributes);
        EntityAttributeRegistry.register(GORILLA, EntityGorilla::bakeAttributes);
        EntityAttributeRegistry.register(CRIMSON_MOSQUITO, EntityCrimsonMosquito::bakeAttributes);
        EntityAttributeRegistry.register(RATTLESNAKE, EntityRattlesnake::bakeAttributes);
        EntityAttributeRegistry.register(ENDERGRADE, EntityEndergrade::bakeAttributes);
        EntityAttributeRegistry.register(HAMMERHEAD_SHARK, EntityHammerheadShark::bakeAttributes);
        EntityAttributeRegistry.register(LOBSTER, EntityLobster::bakeAttributes);
        EntityAttributeRegistry.register(KOMODO_DRAGON, EntityKomodoDragon::bakeAttributes);
        EntityAttributeRegistry.register(CAPUCHIN_MONKEY, EntityCapuchinMonkey::bakeAttributes);
        EntityAttributeRegistry.register(CENTIPEDE_HEAD, EntityCentipedeHead::bakeAttributes);
        EntityAttributeRegistry.register(CENTIPEDE_BODY, EntityCentipedeBody::bakeAttributes);
        EntityAttributeRegistry.register(CENTIPEDE_TAIL, EntityCentipedeTail::bakeAttributes);
        EntityAttributeRegistry.register(WARPED_TOAD, EntityWarpedToad::bakeAttributes);
        EntityAttributeRegistry.register(MOOSE, EntityMoose::bakeAttributes);
        EntityAttributeRegistry.register(MIMICUBE, EntityMimicube::bakeAttributes);
        EntityAttributeRegistry.register(RACCOON, EntityRaccoon::bakeAttributes);
        EntityAttributeRegistry.register(BLOBFISH, EntityBlobfish::bakeAttributes);
        EntityAttributeRegistry.register(SEAL, EntitySeal::bakeAttributes);
        EntityAttributeRegistry.register(COCKROACH, EntityCockroach::bakeAttributes);
        EntityAttributeRegistry.register(SHOEBILL, EntityShoebill::bakeAttributes);
        EntityAttributeRegistry.register(ELEPHANT, EntityElephant::bakeAttributes);
        EntityAttributeRegistry.register(SOUL_VULTURE, EntitySoulVulture::bakeAttributes);
        EntityAttributeRegistry.register(SNOW_LEOPARD, EntitySnowLeopard::bakeAttributes);
        EntityAttributeRegistry.register(SPECTRE, EntitySpectre::bakeAttributes);
        EntityAttributeRegistry.register(CROW, EntityCrow::bakeAttributes);
        EntityAttributeRegistry.register(ALLIGATOR_SNAPPING_TURTLE, EntityAlligatorSnappingTurtle::bakeAttributes);
        EntityAttributeRegistry.register(MUNGUS, EntityMungus::bakeAttributes);
        EntityAttributeRegistry.register(MANTIS_SHRIMP, EntityMantisShrimp::bakeAttributes);
        EntityAttributeRegistry.register(GUSTER, EntityGuster::bakeAttributes);
        EntityAttributeRegistry.register(WARPED_MOSCO, EntityWarpedMosco::bakeAttributes);
        EntityAttributeRegistry.register(STRADDLER, EntityStraddler::bakeAttributes);
        EntityAttributeRegistry.register(STRADPOLE, EntityStradpole::bakeAttributes);
        EntityAttributeRegistry.register(EMU, EntityEmu::bakeAttributes);
        EntityAttributeRegistry.register(PLATYPUS, EntityPlatypus::bakeAttributes);
        EntityAttributeRegistry.register(DROPBEAR, EntityDropBear::bakeAttributes);
        EntityAttributeRegistry.register(TASMANIAN_DEVIL, EntityTasmanianDevil::bakeAttributes);
        EntityAttributeRegistry.register(KANGAROO, EntityKangaroo::bakeAttributes);
        EntityAttributeRegistry.register(CACHALOT_WHALE, EntityCachalotWhale::bakeAttributes);
        EntityAttributeRegistry.register(LEAFCUTTER_ANT, EntityLeafcutterAnt::bakeAttributes);
        EntityAttributeRegistry.register(ENDERIOPHAGE, EntityEnderiophage::bakeAttributes);
        EntityAttributeRegistry.register(BALD_EAGLE, EntityBaldEagle::bakeAttributes);
        EntityAttributeRegistry.register(TIGER, EntityTiger::bakeAttributes);
        EntityAttributeRegistry.register(TARANTULA_HAWK, EntityTarantulaHawk::bakeAttributes);
        EntityAttributeRegistry.register(VOID_WORM, EntityVoidWorm::bakeAttributes);
        EntityAttributeRegistry.register(VOID_WORM_PART, EntityVoidWormPart::bakeAttributes);
        EntityAttributeRegistry.register(FRILLED_SHARK, EntityFrilledShark::bakeAttributes);
        EntityAttributeRegistry.register(MIMIC_OCTOPUS, EntityMimicOctopus::bakeAttributes);
        EntityAttributeRegistry.register(SEAGULL, EntitySeagull::bakeAttributes);
        EntityAttributeRegistry.register(FROSTSTALKER, EntityFroststalker::bakeAttributes);
        EntityAttributeRegistry.register(TUSKLIN, EntityTusklin::bakeAttributes);
        EntityAttributeRegistry.register(LAVIATHAN, EntityLaviathan::bakeAttributes);
        EntityAttributeRegistry.register(COSMAW, EntityCosmaw::bakeAttributes);
        EntityAttributeRegistry.register(TOUCAN, EntityToucan::bakeAttributes);
        EntityAttributeRegistry.register(MANED_WOLF, EntityManedWolf::bakeAttributes);
        EntityAttributeRegistry.register(ANACONDA, EntityAnaconda::bakeAttributes);
        EntityAttributeRegistry.register(ANACONDA_PART, EntityAnacondaPart::bakeAttributes);
        EntityAttributeRegistry.register(ANTEATER, EntityAnteater::bakeAttributes);
        EntityAttributeRegistry.register(ROCKY_ROLLER, EntityRockyRoller::bakeAttributes);
        EntityAttributeRegistry.register(FLUTTER, EntityFlutter::bakeAttributes);
        EntityAttributeRegistry.register(GELADA_MONKEY, EntityGeladaMonkey::bakeAttributes);
        EntityAttributeRegistry.register(JERBOA, EntityJerboa::bakeAttributes);
        EntityAttributeRegistry.register(TERRAPIN, EntityTerrapin::bakeAttributes);
        EntityAttributeRegistry.register(COMB_JELLY, EntityCombJelly::bakeAttributes);
        EntityAttributeRegistry.register(COSMIC_COD, EntityCosmicCod::bakeAttributes);
        EntityAttributeRegistry.register(BUNFUNGUS, EntityBunfungus::bakeAttributes);
        EntityAttributeRegistry.register(BISON, EntityBison::bakeAttributes);
        EntityAttributeRegistry.register(GIANT_SQUID, EntityGiantSquid::bakeAttributes);
        EntityAttributeRegistry.register(SEA_BEAR, EntitySeaBear::bakeAttributes);
        EntityAttributeRegistry.register(DEVILS_HOLE_PUPFISH, EntityDevilsHolePupfish::bakeAttributes);
        EntityAttributeRegistry.register(CATFISH, EntityCatfish::bakeAttributes);
        EntityAttributeRegistry.register(FLYING_FISH, EntityFlyingFish::bakeAttributes);
        EntityAttributeRegistry.register(SKELEWAG, EntitySkelewag::bakeAttributes);
        EntityAttributeRegistry.register(RAIN_FROG, EntityRainFrog::bakeAttributes);
        EntityAttributeRegistry.register(POTOO, EntityPotoo::bakeAttributes);
        EntityAttributeRegistry.register(MUDSKIPPER, EntityMudskipper::bakeAttributes);
        EntityAttributeRegistry.register(RHINOCEROS, EntityRhinoceros::bakeAttributes);
        EntityAttributeRegistry.register(SUGAR_GLIDER, EntitySugarGlider::bakeAttributes);
        EntityAttributeRegistry.register(FARSEER, EntityFarseer::bakeAttributes);
        EntityAttributeRegistry.register(SKREECHER, EntitySkreecher::bakeAttributes);
        EntityAttributeRegistry.register(UNDERMINER, EntityUnderminer::bakeAttributes);
        EntityAttributeRegistry.register(MURMUR, EntityMurmur::bakeAttributes);
        EntityAttributeRegistry.register(MURMUR_HEAD, EntityMurmurHead::bakeAttributes);
        EntityAttributeRegistry.register(SKUNK, EntitySkunk::bakeAttributes);
        EntityAttributeRegistry.register(BANANA_SLUG, EntityBananaSlug::bakeAttributes);
        EntityAttributeRegistry.register(BLUE_JAY, EntityBlueJay::bakeAttributes);
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
        if (!NaturalSpawner.isSpawnPositionOk(SpawnPlacements.Type.ON_GROUND, level, blockpos1, type) && !blockstate1.is(BlockTags.LEAVES)) {
            return false;
        } else {
            return NaturalSpawner.isValidEmptySpawnBlock(level, pos, blockstate, fluidstate, type) && NaturalSpawner.isValidEmptySpawnBlock(level, blockpos, level.getBlockState(blockpos), level.getFluidState(blockpos), type);
        }
    }

}
