package com.github.alexthe666.alexsmobs;

import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import com.github.alexthe666.alexsmobs.client.ClientLayerRegistry;
import com.github.alexthe666.alexsmobs.client.event.ClientEvents;
import com.github.alexthe666.alexsmobs.client.gui.GUIAnimalDictionary;
import com.github.alexthe666.alexsmobs.client.gui.GUITransmutationTable;
import com.github.alexthe666.alexsmobs.client.particle.*;
import com.github.alexthe666.alexsmobs.client.render.*;
import com.github.alexthe666.alexsmobs.client.render.item.AMItemRenderProperties;
import com.github.alexthe666.alexsmobs.client.render.item.CustomArmorRenderProperties;
import com.github.alexthe666.alexsmobs.client.render.item.GhostlyPickaxeBakedModel;
import com.github.alexthe666.alexsmobs.client.render.tile.RenderCapsid;
import com.github.alexthe666.alexsmobs.client.render.tile.RenderEndPirateAnchor;
import com.github.alexthe666.alexsmobs.client.render.tile.RenderEndPirateAnchorWinch;
import com.github.alexthe666.alexsmobs.client.render.tile.RenderEndPirateDoor;
import com.github.alexthe666.alexsmobs.client.render.tile.RenderEndPirateFlag;
import com.github.alexthe666.alexsmobs.client.render.tile.RenderEndPirateShipWheel;
import com.github.alexthe666.alexsmobs.client.render.tile.RenderTransmutationTable;
import com.github.alexthe666.alexsmobs.client.render.tile.RenderVoidWormBeak;
import com.github.alexthe666.alexsmobs.client.sound.SoundBearMusicBox;
import com.github.alexthe666.alexsmobs.client.sound.SoundLaCucaracha;
import com.github.alexthe666.alexsmobs.client.sound.SoundWormBoss;
import com.github.alexthe666.alexsmobs.entity.*;
import com.github.alexthe666.alexsmobs.entity.util.RainbowUtil;
import com.github.alexthe666.alexsmobs.inventory.AMMenuRegistry;
import com.github.alexthe666.alexsmobs.item.*;
import com.github.alexthe666.alexsmobs.tileentity.AMTileEntityRegistry;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.material.Fluids;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class ClientProxy extends CommonProxy {

    public static final Int2ObjectMap<SoundBearMusicBox> BEAR_MUSIC_BOX_SOUND_MAP = new Int2ObjectOpenHashMap<>();
    public static final Int2ObjectMap<SoundLaCucaracha> COCKROACH_SOUND_MAP = new Int2ObjectOpenHashMap<>();
    public static final Int2ObjectMap<SoundWormBoss> WORMBOSS_SOUND_MAP = new Int2ObjectOpenHashMap<>();
    public static final List<UUID> currentUnrenderedEntities = new ArrayList<>();
    public static int voidPortalCreationTime = 0;
    public CameraType prevPOV = CameraType.FIRST_PERSON;
    public boolean initializedRainbowBuffers = false;
    private int pupfishChunkX = 0;
    private int pupfishChunkZ = 0;
    private int singingBlueJayId = -1;
    private final ItemStack[] transmuteStacks = new ItemStack[3];

    @Environment(EnvType.CLIENT)
    public static void onItemColors() {

        AlexsMobs.LOGGER.info("loaded in item colorizer");
        ColorProviderRegistry.ITEM.register((stack, colorIn) -> colorIn < 1 ? -1 : ((ItemStraddleboard) stack.getItem()).getColor(stack),
            AMItemRegistry.STRADDLEBOARD);
    }

    @Environment(EnvType.CLIENT)
    public static void onBlockColors() {
        AlexsMobs.LOGGER.info("loaded in block colorizer");
        ColorProviderRegistry.BLOCK.register((state, tintGetter, pos, tint) -> {
            return tintGetter != null && pos != null ? RainbowUtil.calculateGlassColor(pos) : -1;
        }, AMBlockRegistry.RAINBOW_GLASS);
    }

    @Environment(EnvType.CLIENT)
    public static void onRegisterMenuScreens() {
        MenuScreens.register(AMMenuRegistry.TRANSMUTATION_TABLE, GUITransmutationTable::new);
    }

    public void init() {
        ClientProxy.onBakingCompleted();
        ClientProxy.onItemColors();
        ClientProxy.onBlockColors();
        ClientLayerRegistry.onAddLayers();
        ClientProxy.setupParticles();
        ClientProxy.onRegisterMenuScreens();
    }

    public void clientInit() {
        new ClientEvents();
        // Set lava to translucent render layer for lava vision effect
        BlockRenderLayerMap.INSTANCE.putFluid(Fluids.LAVA, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putFluid(Fluids.FLOWING_LAVA, RenderType.translucent());
        initializedRainbowBuffers = true;
        ItemRenderer itemRendererIn = Minecraft.getInstance().getItemRenderer();
        EntityRendererRegistry.register(AMEntityRegistry.GRIZZLY_BEAR, RenderGrizzlyBear::new);
        EntityRendererRegistry.register(AMEntityRegistry.ROADRUNNER, RenderRoadrunner::new);
        EntityRendererRegistry.register(AMEntityRegistry.BONE_SERPENT, RenderBoneSerpent::new);
        EntityRendererRegistry.register(AMEntityRegistry.BONE_SERPENT_PART, RenderBoneSerpentPart::new);
        EntityRendererRegistry.register(AMEntityRegistry.GAZELLE, RenderGazelle::new);
        EntityRendererRegistry.register(AMEntityRegistry.CROCODILE, RenderCrocodile::new);
        EntityRendererRegistry.register(AMEntityRegistry.FLY, RenderFly::new);
        EntityRendererRegistry.register(AMEntityRegistry.HUMMINGBIRD, RenderHummingbird::new);
        EntityRendererRegistry.register(AMEntityRegistry.ORCA, RenderOrca::new);
        EntityRendererRegistry.register(AMEntityRegistry.SUNBIRD, RenderSunbird::new);
        EntityRendererRegistry.register(AMEntityRegistry.GORILLA, RenderGorilla::new);
        EntityRendererRegistry.register(AMEntityRegistry.CRIMSON_MOSQUITO, RenderCrimsonMosquito::new);
        EntityRendererRegistry.register(AMEntityRegistry.MOSQUITO_SPIT, RenderMosquitoSpit::new);
        EntityRendererRegistry.register(AMEntityRegistry.RATTLESNAKE, RenderRattlesnake::new);
        EntityRendererRegistry.register(AMEntityRegistry.ENDERGRADE, RenderEndergrade::new);
        EntityRendererRegistry.register(AMEntityRegistry.HAMMERHEAD_SHARK, RenderHammerheadShark::new);
        EntityRendererRegistry.register(AMEntityRegistry.SHARK_TOOTH_ARROW, RenderSharkToothArrow::new);
        EntityRendererRegistry.register(AMEntityRegistry.LOBSTER, RenderLobster::new);
        EntityRendererRegistry.register(AMEntityRegistry.KOMODO_DRAGON, RenderKomodoDragon::new);
        EntityRendererRegistry.register(AMEntityRegistry.CAPUCHIN_MONKEY, RenderCapuchinMonkey::new);
        EntityRendererRegistry.register(AMEntityRegistry.TOSSED_ITEM, RenderTossedItem::new);
        EntityRendererRegistry.register(AMEntityRegistry.CENTIPEDE_HEAD, RenderCentipedeHead::new);
        EntityRendererRegistry.register(AMEntityRegistry.CENTIPEDE_BODY, RenderCentipedeBody::new);
        EntityRendererRegistry.register(AMEntityRegistry.CENTIPEDE_TAIL, RenderCentipedeTail::new);
        EntityRendererRegistry.register(AMEntityRegistry.WARPED_TOAD, RenderWarpedToad::new);
        EntityRendererRegistry.register(AMEntityRegistry.MOOSE, RenderMoose::new);
        EntityRendererRegistry.register(AMEntityRegistry.MIMICUBE, RenderMimicube::new);
        EntityRendererRegistry.register(AMEntityRegistry.RACCOON, RenderRaccoon::new);
        EntityRendererRegistry.register(AMEntityRegistry.BLOBFISH, RenderBlobfish::new);
        EntityRendererRegistry.register(AMEntityRegistry.SEAL, RenderSeal::new);
        EntityRendererRegistry.register(AMEntityRegistry.COCKROACH, RenderCockroach::new);
        EntityRendererRegistry.register(AMEntityRegistry.COCKROACH_EGG, (render) -> {
            return new ThrownItemRenderer<>(render, 0.75F, true);
        });
        EntityRendererRegistry.register(AMEntityRegistry.SHOEBILL, RenderShoebill::new);
        EntityRendererRegistry.register(AMEntityRegistry.ELEPHANT, RenderElephant::new);
        EntityRendererRegistry.register(AMEntityRegistry.SOUL_VULTURE, RenderSoulVulture::new);
        EntityRendererRegistry.register(AMEntityRegistry.SNOW_LEOPARD, RenderSnowLeopard::new);
        EntityRendererRegistry.register(AMEntityRegistry.SPECTRE, RenderSpectre::new);
        EntityRendererRegistry.register(AMEntityRegistry.CROW, RenderCrow::new);
        EntityRendererRegistry.register(AMEntityRegistry.ALLIGATOR_SNAPPING_TURTLE, RenderAlligatorSnappingTurtle::new);
        EntityRendererRegistry.register(AMEntityRegistry.MUNGUS, RenderMungus::new);
        EntityRendererRegistry.register(AMEntityRegistry.MANTIS_SHRIMP, RenderMantisShrimp::new);
        EntityRendererRegistry.register(AMEntityRegistry.GUSTER, RenderGuster::new);
        EntityRendererRegistry.register(AMEntityRegistry.SAND_SHOT, RenderSandShot::new);
        EntityRendererRegistry.register(AMEntityRegistry.GUST, RenderGust::new);
        EntityRendererRegistry.register(AMEntityRegistry.WARPED_MOSCO, RenderWarpedMosco::new);
        EntityRendererRegistry.register(AMEntityRegistry.HEMOLYMPH, RenderHemolymph::new);
        EntityRendererRegistry.register(AMEntityRegistry.STRADDLER, RenderStraddler::new);
        EntityRendererRegistry.register(AMEntityRegistry.STRADPOLE, RenderStradpole::new);
        EntityRendererRegistry.register(AMEntityRegistry.STRADDLEBOARD, RenderStraddleboard::new);
        EntityRendererRegistry.register(AMEntityRegistry.EMU, RenderEmu::new);
        EntityRendererRegistry.register(AMEntityRegistry.EMU_EGG, (render) -> {
            return new ThrownItemRenderer<>(render, 0.75F, true);
        });
        EntityRendererRegistry.register(AMEntityRegistry.PLATYPUS, RenderPlatypus::new);
        EntityRendererRegistry.register(AMEntityRegistry.DROPBEAR, RenderDropBear::new);
        EntityRendererRegistry.register(AMEntityRegistry.TASMANIAN_DEVIL, RenderTasmanianDevil::new);
        EntityRendererRegistry.register(AMEntityRegistry.KANGAROO, RenderKangaroo::new);
        EntityRendererRegistry.register(AMEntityRegistry.CACHALOT_WHALE, RenderCachalotWhale::new);
        EntityRendererRegistry.register(AMEntityRegistry.CACHALOT_ECHO, RenderCachalotEcho::new);
        EntityRendererRegistry.register(AMEntityRegistry.LEAFCUTTER_ANT, RenderLeafcutterAnt::new);
        EntityRendererRegistry.register(AMEntityRegistry.ENDERIOPHAGE, RenderEnderiophage::new);
        EntityRendererRegistry.register(AMEntityRegistry.ENDERIOPHAGE_ROCKET, (render) -> {
            return new ThrownItemRenderer<>(render, 0.75F, true);
        });
        EntityRendererRegistry.register(AMEntityRegistry.BALD_EAGLE, RenderBaldEagle::new);
        EntityRendererRegistry.register(AMEntityRegistry.TIGER, RenderTiger::new);
        EntityRendererRegistry.register(AMEntityRegistry.TARANTULA_HAWK, RenderTarantulaHawk::new);
        EntityRendererRegistry.register(AMEntityRegistry.VOID_WORM, RenderVoidWormHead::new);
        EntityRendererRegistry.register(AMEntityRegistry.VOID_WORM_PART, RenderVoidWormBody::new);
        EntityRendererRegistry.register(AMEntityRegistry.VOID_WORM_SHOT, RenderVoidWormShot::new);
        EntityRendererRegistry.register(AMEntityRegistry.VOID_PORTAL, RenderVoidPortal::new);
        EntityRendererRegistry.register(AMEntityRegistry.FRILLED_SHARK, RenderFrilledShark::new);
        EntityRendererRegistry.register(AMEntityRegistry.MIMIC_OCTOPUS, RenderMimicOctopus::new);
        EntityRendererRegistry.register(AMEntityRegistry.SEAGULL, RenderSeagull::new);
        EntityRendererRegistry.register(AMEntityRegistry.FROSTSTALKER, RenderFroststalker::new);
        EntityRendererRegistry.register(AMEntityRegistry.ICE_SHARD, RenderIceShard::new);
        EntityRendererRegistry.register(AMEntityRegistry.TUSKLIN, RenderTusklin::new);
        EntityRendererRegistry.register(AMEntityRegistry.LAVIATHAN, RenderLaviathan::new);
        EntityRendererRegistry.register(AMEntityRegistry.COSMAW, RenderCosmaw::new);
        EntityRendererRegistry.register(AMEntityRegistry.TOUCAN, RenderToucan::new);
        EntityRendererRegistry.register(AMEntityRegistry.MANED_WOLF, RenderManedWolf::new);
        EntityRendererRegistry.register(AMEntityRegistry.ANACONDA, RenderAnaconda::new);
        EntityRendererRegistry.register(AMEntityRegistry.ANACONDA_PART, RenderAnacondaPart::new);
        EntityRendererRegistry.register(AMEntityRegistry.VINE_LASSO, RenderVineLasso::new);
        EntityRendererRegistry.register(AMEntityRegistry.ANTEATER, RenderAnteater::new);
        EntityRendererRegistry.register(AMEntityRegistry.ROCKY_ROLLER, RenderRockyRoller::new);
        EntityRendererRegistry.register(AMEntityRegistry.FLUTTER, RenderFlutter::new);
        EntityRendererRegistry.register(AMEntityRegistry.POLLEN_BALL, RenderPollenBall::new);
        EntityRendererRegistry.register(AMEntityRegistry.GELADA_MONKEY, RenderGeladaMonkey::new);
        EntityRendererRegistry.register(AMEntityRegistry.JERBOA, RenderJerboa::new);
        EntityRendererRegistry.register(AMEntityRegistry.TERRAPIN, RenderTerrapin::new);
        EntityRendererRegistry.register(AMEntityRegistry.COMB_JELLY, RenderCombJelly::new);
        EntityRendererRegistry.register(AMEntityRegistry.COSMIC_COD, RenderCosmicCod::new);
        EntityRendererRegistry.register(AMEntityRegistry.BUNFUNGUS, RenderBunfungus::new);
        EntityRendererRegistry.register(AMEntityRegistry.BISON, RenderBison::new);
        EntityRendererRegistry.register(AMEntityRegistry.GIANT_SQUID, RenderGiantSquid::new);
        EntityRendererRegistry.register(AMEntityRegistry.SQUID_GRAPPLE, RenderSquidGrapple::new);
        EntityRendererRegistry.register(AMEntityRegistry.SEA_BEAR, RenderSeaBear::new);
        EntityRendererRegistry.register(AMEntityRegistry.DEVILS_HOLE_PUPFISH, RenderDevilsHolePupfish::new);
        EntityRendererRegistry.register(AMEntityRegistry.CATFISH, RenderCatfish::new);
        EntityRendererRegistry.register(AMEntityRegistry.FLYING_FISH, RenderFlyingFish::new);
        EntityRendererRegistry.register(AMEntityRegistry.SKELEWAG, RenderSkelewag::new);
        EntityRendererRegistry.register(AMEntityRegistry.RAIN_FROG, RenderRainFrog::new);
        EntityRendererRegistry.register(AMEntityRegistry.POTOO, RenderPotoo::new);
        EntityRendererRegistry.register(AMEntityRegistry.MUDSKIPPER, RenderMudskipper::new);
        EntityRendererRegistry.register(AMEntityRegistry.MUD_BALL, RenderMudBall::new);
        EntityRendererRegistry.register(AMEntityRegistry.RHINOCEROS, RenderRhinoceros::new);
        EntityRendererRegistry.register(AMEntityRegistry.SUGAR_GLIDER, RenderSugarGlider::new);
        EntityRendererRegistry.register(AMEntityRegistry.FARSEER, RenderFarseer::new);
        EntityRendererRegistry.register(AMEntityRegistry.SKREECHER, RenderSkreecher::new);
        EntityRendererRegistry.register(AMEntityRegistry.UNDERMINER, RenderUnderminer::new);
        EntityRendererRegistry.register(AMEntityRegistry.MURMUR, RenderMurmurBody::new);
        EntityRendererRegistry.register(AMEntityRegistry.MURMUR_HEAD, RenderMurmurHead::new);
        EntityRendererRegistry.register(AMEntityRegistry.TENDON_SEGMENT, RenderTendonSegment::new);
        EntityRendererRegistry.register(AMEntityRegistry.SKUNK, RenderSkunk::new);
        EntityRendererRegistry.register(AMEntityRegistry.FART, RenderFart::new);
        EntityRendererRegistry.register(AMEntityRegistry.BANANA_SLUG, RenderBananaSlug::new);
        EntityRendererRegistry.register(AMEntityRegistry.BLUE_JAY, RenderBlueJay::new);
        EntityRendererRegistry.register(AMEntityRegistry.CAIMAN, RenderCaiman::new);
        EntityRendererRegistry.register(AMEntityRegistry.TRIOPS, RenderTriops::new);
        try {
            ItemProperties.register(AMItemRegistry.BLOOD_SPRAYER, ResourceLocation.withDefaultNamespace("empty"),
                    (stack, p_239428_1_, p_239428_2_, j) -> {
                        return !ItemBloodSprayer.isUsable(stack)
                                || p_239428_2_ instanceof Player && ((Player) p_239428_2_).getCooldowns()
                                .isOnCooldown(AMItemRegistry.BLOOD_SPRAYER) ? 1.0F : 0.0F;
                    });
            ItemProperties.register(AMItemRegistry.HEMOLYMPH_BLASTER,
                    ResourceLocation.withDefaultNamespace("empty"), (stack, p_239428_1_, p_239428_2_, j) -> {
                        return !ItemHemolymphBlaster.isUsable(stack)
                                || p_239428_2_ instanceof Player && ((Player) p_239428_2_).getCooldowns()
                                .isOnCooldown(AMItemRegistry.HEMOLYMPH_BLASTER) ? 1.0F : 0.0F;
                    });
            ItemProperties.register(AMItemRegistry.TARANTULA_HAWK_ELYTRA,
                    ResourceLocation.withDefaultNamespace("broken"), (stack, p_239428_1_, p_239428_2_, j) -> {
                        return ItemTarantulaHawkElytra.isUsable(stack) ? 0.0F : 1.0F;
                    });
            ItemProperties.register(AMItemRegistry.SHIELD_OF_THE_DEEP,
                    ResourceLocation.withDefaultNamespace("blocking"), (stack, p_239421_1_, p_239421_2_, j) -> {
                        return p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack
                                ? 1.0F
                                : 0.0F;
                    });
            ItemProperties.register(AMItemRegistry.SOMBRERO, ResourceLocation.withDefaultNamespace("silly"),
                    (stack, p_239421_1_, p_239421_2_, j) -> {
                        return AlexsMobs.isAprilFools() ? 1.0F : 0.0F;
                    });
            ItemProperties.register(AMItemRegistry.TENDON_WHIP, ResourceLocation.withDefaultNamespace("active"),
                    (stack, p_239421_1_, holder, j) -> {
                        return ItemTendonWhip.isActive(stack, holder) ? 1.0F : 0.0F;
                    });
            ItemProperties.register(AMItemRegistry.PUPFISH_LOCATOR,
                    ResourceLocation.withDefaultNamespace("in_chunk"), (stack, world, entity, j) -> {
                        int x = pupfishChunkX * 16;
                        int z = pupfishChunkZ * 16;
                        if (entity != null && entity.getX() >= x && entity.getX() <= x + 16 && entity.getZ() >= z
                                && entity.getZ() <= z + 16) {
                            return 1.0F;
                        }
                        return 0.0F;
                    });
            ItemProperties.register(AMItemRegistry.SKELEWAG_SWORD,
                    ResourceLocation.withDefaultNamespace("blocking"), (stack, p_239421_1_, p_239421_2_, j) -> {
                        return p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack
                                ? 1.0F
                                : 0.0F;
                    });
        } catch (Exception e) {
            AlexsMobs.LOGGER.warn("Could not load item models for weapons");
        }
        BlockEntityRenderers.register(AMTileEntityRegistry.CAPSID, RenderCapsid::new);
        BlockEntityRenderers.register(AMTileEntityRegistry.VOID_WORM_BEAK, RenderVoidWormBeak::new);
        BlockEntityRenderers.register(AMTileEntityRegistry.TRANSMUTATION_TABLE, RenderTransmutationTable::new);
        // End Pirate TileEntity renderers
        BlockEntityRenderers.register(AMTileEntityRegistry.END_PIRATE_DOOR, RenderEndPirateDoor::new);
        BlockEntityRenderers.register(AMTileEntityRegistry.END_PIRATE_ANCHOR, RenderEndPirateAnchor::new);
        BlockEntityRenderers.register(AMTileEntityRegistry.END_PIRATE_ANCHOR_WINCH, RenderEndPirateAnchorWinch::new);
        BlockEntityRenderers.register(AMTileEntityRegistry.END_PIRATE_SHIP_WHEEL, RenderEndPirateShipWheel::new);
        BlockEntityRenderers.register(AMTileEntityRegistry.END_PIRATE_FLAG, RenderEndPirateFlag::new);
        // MenuScreens.register handled via RegisterMenuScreensEvent
    }

    public static void onRegisterRenderBuffers(final Consumer<RenderType> registrar) {
        // Register custom render buffers for special visual effects
        registrar.accept(AMRenderTypes.COMBJELLY_RAINBOW_GLINT);
        registrar.accept(AMRenderTypes.VOID_WORM_PORTAL_OVERLAY);
        registrar.accept(AMRenderTypes.STATIC_PORTAL);
        registrar.accept(AMRenderTypes.STATIC_PARTICLE);
        registrar.accept(AMRenderTypes.STATIC_ENTITY);
    }

    private static void onBakingCompleted() {
        String ghostlyPickaxe = "alexsmobs:ghostly_pickaxe";

        ModelLoadingPlugin.register(context -> {
            context.modifyModelAfterBake().register((model, context1) -> {
                if ((context1.resourceId() != null && context1.resourceId().toString().contains(ghostlyPickaxe)) || (context1.topLevelId() != null && context1.topLevelId().toString().contains(ghostlyPickaxe))) {
                    return new GhostlyPickaxeBakedModel(model);
                }

                return model;
            });
        });
    }

    public void openBookGUI(ItemStack itemStackIn) {
        Minecraft.getInstance().setScreen(new GUIAnimalDictionary(itemStackIn));
    }

    public void openBookGUI(ItemStack itemStackIn, String page) {
        Minecraft.getInstance().setScreen(new GUIAnimalDictionary(itemStackIn, page));
    }

    public Player getClientSidePlayer() {
        return Minecraft.getInstance().player;
    }

    @Environment(EnvType.CLIENT)
    public Object getArmorModel(int armorId, LivingEntity entity) {
        switch (armorId) {
            /*
             * case 0:
             * return ROADRUNNER_BOOTS_MODEL;
             * case 1:
             * return MOOSE_HEADGEAR_MODEL;
             * case 2:
             * return FRONTIER_CAP_MODEL.withAnimations(entity);
             * case 3:
             * return SOMBRERO_MODEL;
             * case 4:
             * return SPIKED_TURTLE_SHELL_MODEL;
             * case 5:
             * return FEDORA_MODEL;
             * case 6:
             * return ELYTRA_MODEL.withAnimations(entity);
             *
             */
            default:
                return null;
        }
    }

    @Environment(EnvType.CLIENT)
    public void onEntityStatus(Entity entity, byte updateKind) {
        if (updateKind == 67) {
            if (entity instanceof EntityCockroach && entity.isAlive()) {
                SoundLaCucaracha sound;
                if (COCKROACH_SOUND_MAP.get(entity.getId()) == null) {
                    sound = new SoundLaCucaracha((EntityCockroach) entity);
                    COCKROACH_SOUND_MAP.put(entity.getId(), sound);
                } else {
                    sound = COCKROACH_SOUND_MAP.get(entity.getId());
                }
                if (!Minecraft.getInstance().getSoundManager().isActive(sound) && sound.canPlaySound()
                        && sound.isOnlyCockroach()) {
                    Minecraft.getInstance().getSoundManager().play(sound);
                }
            } else if (entity instanceof EntityVoidWorm && entity.isAlive()) {
                final float f2 = Minecraft.getInstance().options.getSoundSourceVolume(SoundSource.MUSIC);
                if (f2 <= 0) {
                    WORMBOSS_SOUND_MAP.clear();
                } else {
                    SoundWormBoss sound;
                    if (WORMBOSS_SOUND_MAP.get(entity.getId()) == null) {
                        sound = new SoundWormBoss((EntityVoidWorm) entity);
                        WORMBOSS_SOUND_MAP.put(entity.getId(), sound);
                    } else {
                        sound = WORMBOSS_SOUND_MAP.get(entity.getId());
                    }
                    if (!Minecraft.getInstance().getSoundManager().isActive(sound) && sound.isNearest()) {
                        Minecraft.getInstance().getSoundManager().play(sound);
                    }
                }
            } else if (entity instanceof EntityGrizzlyBear && entity.isAlive()) {
                SoundBearMusicBox sound;
                if (BEAR_MUSIC_BOX_SOUND_MAP.get(entity.getId()) == null) {
                    sound = new SoundBearMusicBox((EntityGrizzlyBear) entity);
                    BEAR_MUSIC_BOX_SOUND_MAP.put(entity.getId(), sound);
                } else {
                    sound = BEAR_MUSIC_BOX_SOUND_MAP.get(entity.getId());
                }
                if (!Minecraft.getInstance().getSoundManager().isActive(sound) && sound.canPlaySound()
                        && sound.isOnlyMusicBox()) {
                    Minecraft.getInstance().getSoundManager().play(sound);
                }
            } else if (entity instanceof EntityBlueJay && entity.isAlive()) {
                singingBlueJayId = entity.getId();
            }
        }
        if (entity instanceof EntityBlueJay && entity.isAlive() && updateKind == 68) {
            singingBlueJayId = -1;
        }
    }

    public void updateBiomeVisuals(int x, int z) {
        Minecraft.getInstance().levelRenderer.setBlocksDirty(x - 32, 0, x - 32, z + 32, 255, z + 32);
    }

    public static void setupParticles() {
        AlexsMobs.LOGGER.debug("Registered particle factories");
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.GUSTER_SAND_SPIN, ParticleGusterSandSpin.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.GUSTER_SAND_SHOT, ParticleGusterSandShot.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.GUSTER_SAND_SPIN_RED,
                ParticleGusterSandSpin.FactoryRed::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.GUSTER_SAND_SHOT_RED,
                ParticleGusterSandShot.FactoryRed::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.GUSTER_SAND_SPIN_SOUL,
                ParticleGusterSandSpin.FactorySoul::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.GUSTER_SAND_SHOT_SOUL,
                ParticleGusterSandShot.FactorySoul::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.HEMOLYMPH, ParticleHemolymph.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.PLATYPUS_SENSE, ParticlePlatypus.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.WHALE_SPLASH, ParticleWhaleSplash.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.DNA, ParticleDna.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.SHOCKED, ParticleSimpleHeart.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.WORM_PORTAL, ParticleWormPortal.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.INVERT_DIG, ParticleInvertDig.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.TEETH_GLINT, ParticleTeethGlint.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.SMELLY, ParticleSmelly.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.BUNFUNGUS_TRANSFORMATION,
                ParticleBunfungusTransformation.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.FUNGUS_BUBBLE, ParticleFungusBubble.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.BEAR_FREDDY, new ParticleBearFreddy.Factory());
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.SUNBIRD_FEATHER, ParticleSunbirdFeather.Factory::new);
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.STATIC_SPARK, new ParticleStaticSpark.Factory());
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.SKULK_BOOM, new ParticleSkulkBoom.Factory());
        ParticleFactoryRegistry.getInstance().register(AMParticleRegistry.BIRD_SONG, ParticleBirdSong.Factory::new);
    }

    public void setRenderViewEntity(Entity entity) {
        prevPOV = Minecraft.getInstance().options.getCameraType();
        Minecraft.getInstance().setCameraEntity(entity);
        Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
    }

    public void resetRenderViewEntity() {
        Minecraft.getInstance().setCameraEntity(Minecraft.getInstance().player);
    }

    public int getPreviousPOV() {
        return prevPOV.ordinal();
    }

    public boolean isFarFromCamera(double x, double y, double z) {
        Minecraft lvt_1_1_ = Minecraft.getInstance();
        return lvt_1_1_.gameRenderer.getMainCamera().getPosition().distanceToSqr(x, y, z) >= 256.0D;
    }

    public void resetVoidPortalCreation(Player player) {

    }

    @Override
    public Object getISTERProperties() {
        return new AMItemRenderProperties();
    }

    @Override
    public Object getArmorRenderProperties() {
        return new CustomArmorRenderProperties();
    }

    public void spawnSpecialParticle(int type) {
        if (type == 0) {
            Minecraft.getInstance().level.addParticle(AMParticleRegistry.BEAR_FREDDY,
                    Minecraft.getInstance().player.getX(), Minecraft.getInstance().player.getY(),
                    Minecraft.getInstance().player.getZ(), 0, 0, 0);
        }
    }

    public void processVisualFlag(Entity entity, int flag) {
        if (entity == Minecraft.getInstance().player && flag == 87) {
            ClientEvents.renderStaticScreenFor = 60;
        }
    }

    public void setPupfishChunkForItem(int chunkX, int chunkZ) {
        this.pupfishChunkX = chunkX;
        this.pupfishChunkZ = chunkZ;
    }

    public void setDisplayTransmuteResult(int slot, ItemStack stack) {
        transmuteStacks[Mth.clamp(slot, 0, 2)] = stack;
    }

    public ItemStack getDisplayTransmuteResult(int slot) {
        ItemStack stack = transmuteStacks[Mth.clamp(slot, 0, 2)];
        return stack == null ? ItemStack.EMPTY : stack;
    }

    public int getSingingBlueJayId() {
        return singingBlueJayId;
    }

}
