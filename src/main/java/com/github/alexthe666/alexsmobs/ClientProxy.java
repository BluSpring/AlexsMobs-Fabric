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
import com.github.alexthe666.alexsmobs.client.render.tile.RenderTransmutationTable;
import com.github.alexthe666.alexsmobs.client.render.tile.RenderVoidWormBeak;
import com.github.alexthe666.alexsmobs.client.sound.SoundBearMusicBox;
import com.github.alexthe666.alexsmobs.client.sound.SoundLaCucaracha;
import com.github.alexthe666.alexsmobs.client.sound.SoundWormBoss;
import com.github.alexthe666.alexsmobs.entity.*;
import com.github.alexthe666.alexsmobs.entity.util.RainbowUtil;
import com.github.alexthe666.alexsmobs.inventory.AMMenuRegistry;
import com.github.alexthe666.alexsmobs.item.*;
import com.github.alexthe666.alexsmobs.mixin.RenderBuffersAccessor;
import com.github.alexthe666.alexsmobs.tileentity.AMTileEntityRegistry;
import com.mojang.blaze3d.vertex.BufferBuilder;
import dev.architectury.registry.client.particle.ParticleProviderRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import io.github.fabricators_of_create.porting_lib.event.client.ModelsBakedCallback;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.concurrent.Callable;

@Environment(EnvType.CLIENT)
public class ClientProxy extends CommonProxy {

    public static final Map<Integer, SoundBearMusicBox> BEAR_MUSIC_BOX_SOUND_MAP = new HashMap<>();
    public static final Map<Integer, SoundLaCucaracha> COCKROACH_SOUND_MAP = new HashMap<>();
    public static final Map<Integer, SoundWormBoss> WORMBOSS_SOUND_MAP = new HashMap<>();
    public static List<UUID> currentUnrenderedEntities = new ArrayList<UUID>();
    public static int voidPortalCreationTime = 0;
    public CameraType prevPOV = CameraType.FIRST_PERSON;
    public boolean initializedRainbowBuffers = false;
    private int pupfishChunkX = 0;
    private int pupfishChunkZ = 0;
    private int singingBlueJayId = -1;
    private ItemStack[] transmuteStacks = new ItemStack[3];

    @Environment(EnvType.CLIENT)
    public static void onItemColors() {
        AlexsMobs.LOGGER.info("loaded in item colorizer");
        if(AMItemRegistry.STRADDLEBOARD.isPresent()){
            ColorProviderRegistry.ITEM.register((stack, colorIn) -> colorIn < 1 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack), AMItemRegistry.STRADDLEBOARD.get());
        }else{
            AlexsMobs.LOGGER.warn("Could not add straddleboard item to colorizer...");
        }
    }

    @Environment(EnvType.CLIENT)
    public static void onBlockColors() {
        AlexsMobs.LOGGER.info("loaded in block colorizer");
        ColorProviderRegistry.BLOCK.register((state, tintGetter, pos, tint) -> {
            return tintGetter != null && pos != null ? RainbowUtil.calculateGlassColor(pos) : -1;
        }, AMBlockRegistry.RAINBOW_GLASS.get());
    }

    @Environment(EnvType.CLIENT)
    public static Callable<BlockEntityWithoutLevelRenderer> getTEISR() {
        return AMItemstackRenderer::new;
    }

    public void init() {
        onItemColors();
        onBlockColors();
        ClientLayerRegistry.init();
        setupParticles();
        onBakingCompleted();
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientProxy::setupParticles);
        //FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientProxy::onBakingCompleted);
    }

    public void clientInit() {
        initRainbowBuffers();
        ItemRenderer itemRendererIn = Minecraft.getInstance().getItemRenderer();
        EntityRendererRegistry.register(AMEntityRegistry.GRIZZLY_BEAR.get(), RenderGrizzlyBear::new);
        EntityRendererRegistry.register(AMEntityRegistry.ROADRUNNER.get(), RenderRoadrunner::new);
        EntityRendererRegistry.register(AMEntityRegistry.BONE_SERPENT.get(), RenderBoneSerpent::new);
        EntityRendererRegistry.register(AMEntityRegistry.BONE_SERPENT_PART.get(), RenderBoneSerpentPart::new);
        EntityRendererRegistry.register(AMEntityRegistry.GAZELLE.get(), RenderGazelle::new);
        EntityRendererRegistry.register(AMEntityRegistry.CROCODILE.get(), RenderCrocodile::new);
        EntityRendererRegistry.register(AMEntityRegistry.FLY.get(), RenderFly::new);
        EntityRendererRegistry.register(AMEntityRegistry.HUMMINGBIRD.get(), RenderHummingbird::new);
        EntityRendererRegistry.register(AMEntityRegistry.ORCA.get(), RenderOrca::new);
        EntityRendererRegistry.register(AMEntityRegistry.SUNBIRD.get(), RenderSunbird::new);
        EntityRendererRegistry.register(AMEntityRegistry.GORILLA.get(), RenderGorilla::new);
        EntityRendererRegistry.register(AMEntityRegistry.CRIMSON_MOSQUITO.get(), RenderCrimsonMosquito::new);
        EntityRendererRegistry.register(AMEntityRegistry.MOSQUITO_SPIT.get(), RenderMosquitoSpit::new);
        EntityRendererRegistry.register(AMEntityRegistry.RATTLESNAKE.get(), RenderRattlesnake::new);
        EntityRendererRegistry.register(AMEntityRegistry.ENDERGRADE.get(), RenderEndergrade::new);
        EntityRendererRegistry.register(AMEntityRegistry.HAMMERHEAD_SHARK.get(), RenderHammerheadShark::new);
        EntityRendererRegistry.register(AMEntityRegistry.SHARK_TOOTH_ARROW.get(), RenderSharkToothArrow::new);
        EntityRendererRegistry.register(AMEntityRegistry.LOBSTER.get(), RenderLobster::new);
        EntityRendererRegistry.register(AMEntityRegistry.KOMODO_DRAGON.get(), RenderKomodoDragon::new);
        EntityRendererRegistry.register(AMEntityRegistry.CAPUCHIN_MONKEY.get(), RenderCapuchinMonkey::new);
        EntityRendererRegistry.register(AMEntityRegistry.TOSSED_ITEM.get(), RenderTossedItem::new);
        EntityRendererRegistry.register(AMEntityRegistry.CENTIPEDE_HEAD.get(), RenderCentipedeHead::new);
        EntityRendererRegistry.register(AMEntityRegistry.CENTIPEDE_BODY.get(), RenderCentipedeBody::new);
        EntityRendererRegistry.register(AMEntityRegistry.CENTIPEDE_TAIL.get(), RenderCentipedeTail::new);
        EntityRendererRegistry.register(AMEntityRegistry.WARPED_TOAD.get(), RenderWarpedToad::new);
        EntityRendererRegistry.register(AMEntityRegistry.MOOSE.get(), RenderMoose::new);
        EntityRendererRegistry.register(AMEntityRegistry.MIMICUBE.get(), RenderMimicube::new);
        EntityRendererRegistry.register(AMEntityRegistry.RACCOON.get(), RenderRaccoon::new);
        EntityRendererRegistry.register(AMEntityRegistry.BLOBFISH.get(), RenderBlobfish::new);
        EntityRendererRegistry.register(AMEntityRegistry.SEAL.get(), RenderSeal::new);
        EntityRendererRegistry.register(AMEntityRegistry.COCKROACH.get(), RenderCockroach::new);
        EntityRendererRegistry.register(AMEntityRegistry.COCKROACH_EGG.get(), (render) -> {
            return new ThrownItemRenderer<>(render, 0.75F, true);
        });
        EntityRendererRegistry.register(AMEntityRegistry.SHOEBILL.get(), RenderShoebill::new);
        EntityRendererRegistry.register(AMEntityRegistry.ELEPHANT.get(), RenderElephant::new);
        EntityRendererRegistry.register(AMEntityRegistry.SOUL_VULTURE.get(), RenderSoulVulture::new);
        EntityRendererRegistry.register(AMEntityRegistry.SNOW_LEOPARD.get(), RenderSnowLeopard::new);
        EntityRendererRegistry.register(AMEntityRegistry.SPECTRE.get(), RenderSpectre::new);
        EntityRendererRegistry.register(AMEntityRegistry.CROW.get(), RenderCrow::new);
        EntityRendererRegistry.register(AMEntityRegistry.ALLIGATOR_SNAPPING_TURTLE.get(), RenderAlligatorSnappingTurtle::new);
        EntityRendererRegistry.register(AMEntityRegistry.MUNGUS.get(), RenderMungus::new);
        EntityRendererRegistry.register(AMEntityRegistry.MANTIS_SHRIMP.get(), RenderMantisShrimp::new);
        EntityRendererRegistry.register(AMEntityRegistry.GUSTER.get(), RenderGuster::new);
        EntityRendererRegistry.register(AMEntityRegistry.SAND_SHOT.get(), RenderSandShot::new);
        EntityRendererRegistry.register(AMEntityRegistry.GUST.get(), RenderGust::new);
        EntityRendererRegistry.register(AMEntityRegistry.WARPED_MOSCO.get(), RenderWarpedMosco::new);
        EntityRendererRegistry.register(AMEntityRegistry.HEMOLYMPH.get(), RenderHemolymph::new);
        EntityRendererRegistry.register(AMEntityRegistry.STRADDLER.get(), RenderStraddler::new);
        EntityRendererRegistry.register(AMEntityRegistry.STRADPOLE.get(), RenderStradpole::new);
        EntityRendererRegistry.register(AMEntityRegistry.STRADDLEBOARD.get(), RenderStraddleboard::new);
        EntityRendererRegistry.register(AMEntityRegistry.EMU.get(), RenderEmu::new);
        EntityRendererRegistry.register(AMEntityRegistry.EMU_EGG.get(), (render) -> {
            return new ThrownItemRenderer<>(render, 0.75F, true);
        });
        EntityRendererRegistry.register(AMEntityRegistry.PLATYPUS.get(), RenderPlatypus::new);
        EntityRendererRegistry.register(AMEntityRegistry.DROPBEAR.get(), RenderDropBear::new);
        EntityRendererRegistry.register(AMEntityRegistry.TASMANIAN_DEVIL.get(), RenderTasmanianDevil::new);
        EntityRendererRegistry.register(AMEntityRegistry.KANGAROO.get(), RenderKangaroo::new);
        EntityRendererRegistry.register(AMEntityRegistry.CACHALOT_WHALE.get(), RenderCachalotWhale::new);
        EntityRendererRegistry.register(AMEntityRegistry.CACHALOT_ECHO.get(), RenderCachalotEcho::new);
        EntityRendererRegistry.register(AMEntityRegistry.LEAFCUTTER_ANT.get(), RenderLeafcutterAnt::new);
        EntityRendererRegistry.register(AMEntityRegistry.ENDERIOPHAGE.get(), RenderEnderiophage::new);
        EntityRendererRegistry.register(AMEntityRegistry.ENDERIOPHAGE_ROCKET.get(), (render) -> {
            return new ThrownItemRenderer<>(render, 0.75F, true);
        });
        EntityRendererRegistry.register(AMEntityRegistry.BALD_EAGLE.get(), RenderBaldEagle::new);
        EntityRendererRegistry.register(AMEntityRegistry.TIGER.get(), RenderTiger::new);
        EntityRendererRegistry.register(AMEntityRegistry.TARANTULA_HAWK.get(), RenderTarantulaHawk::new);
        EntityRendererRegistry.register(AMEntityRegistry.VOID_WORM.get(), RenderVoidWormHead::new);
        EntityRendererRegistry.register(AMEntityRegistry.VOID_WORM_PART.get(), RenderVoidWormBody::new);
        EntityRendererRegistry.register(AMEntityRegistry.VOID_WORM_SHOT.get(), RenderVoidWormShot::new);
        EntityRendererRegistry.register(AMEntityRegistry.VOID_PORTAL.get(), RenderVoidPortal::new);
        EntityRendererRegistry.register(AMEntityRegistry.FRILLED_SHARK.get(), RenderFrilledShark::new);
        EntityRendererRegistry.register(AMEntityRegistry.MIMIC_OCTOPUS.get(), RenderMimicOctopus::new);
        EntityRendererRegistry.register(AMEntityRegistry.SEAGULL.get(), RenderSeagull::new);
        EntityRendererRegistry.register(AMEntityRegistry.FROSTSTALKER.get(), RenderFroststalker::new);
        EntityRendererRegistry.register(AMEntityRegistry.ICE_SHARD.get(), RenderIceShard::new);
        EntityRendererRegistry.register(AMEntityRegistry.TUSKLIN.get(), RenderTusklin::new);
        EntityRendererRegistry.register(AMEntityRegistry.LAVIATHAN.get(), RenderLaviathan::new);
        EntityRendererRegistry.register(AMEntityRegistry.COSMAW.get(), RenderCosmaw::new);
        EntityRendererRegistry.register(AMEntityRegistry.TOUCAN.get(), RenderToucan::new);
        EntityRendererRegistry.register(AMEntityRegistry.MANED_WOLF.get(), RenderManedWolf::new);
        EntityRendererRegistry.register(AMEntityRegistry.ANACONDA.get(), RenderAnaconda::new);
        EntityRendererRegistry.register(AMEntityRegistry.ANACONDA_PART.get(), RenderAnacondaPart::new);
        EntityRendererRegistry.register(AMEntityRegistry.VINE_LASSO.get(), RenderVineLasso::new);
        EntityRendererRegistry.register(AMEntityRegistry.ANTEATER.get(), RenderAnteater::new);
        EntityRendererRegistry.register(AMEntityRegistry.ROCKY_ROLLER.get(), RenderRockyRoller::new);
        EntityRendererRegistry.register(AMEntityRegistry.FLUTTER.get(), RenderFlutter::new);
        EntityRendererRegistry.register(AMEntityRegistry.POLLEN_BALL.get(), RenderPollenBall::new);
        EntityRendererRegistry.register(AMEntityRegistry.GELADA_MONKEY.get(), RenderGeladaMonkey::new);
        EntityRendererRegistry.register(AMEntityRegistry.JERBOA.get(), RenderJerboa::new);
        EntityRendererRegistry.register(AMEntityRegistry.TERRAPIN.get(), RenderTerrapin::new);
        EntityRendererRegistry.register(AMEntityRegistry.COMB_JELLY.get(), RenderCombJelly::new);
        EntityRendererRegistry.register(AMEntityRegistry.COSMIC_COD.get(), RenderCosmicCod::new);
        EntityRendererRegistry.register(AMEntityRegistry.BUNFUNGUS.get(), RenderBunfungus::new);
        EntityRendererRegistry.register(AMEntityRegistry.BISON.get(), RenderBison::new);
        EntityRendererRegistry.register(AMEntityRegistry.GIANT_SQUID.get(), RenderGiantSquid::new);
        EntityRendererRegistry.register(AMEntityRegistry.SQUID_GRAPPLE.get(), RenderSquidGrapple::new);
        EntityRendererRegistry.register(AMEntityRegistry.SEA_BEAR.get(), RenderSeaBear::new);
        EntityRendererRegistry.register(AMEntityRegistry.DEVILS_HOLE_PUPFISH.get(), RenderDevilsHolePupfish::new);
        EntityRendererRegistry.register(AMEntityRegistry.CATFISH.get(), RenderCatfish::new);
        EntityRendererRegistry.register(AMEntityRegistry.FLYING_FISH.get(), RenderFlyingFish::new);
        EntityRendererRegistry.register(AMEntityRegistry.SKELEWAG.get(), RenderSkelewag::new);
        EntityRendererRegistry.register(AMEntityRegistry.RAIN_FROG.get(), RenderRainFrog::new);
        EntityRendererRegistry.register(AMEntityRegistry.POTOO.get(), RenderPotoo::new);
        EntityRendererRegistry.register(AMEntityRegistry.MUDSKIPPER.get(), RenderMudskipper::new);
        EntityRendererRegistry.register(AMEntityRegistry.MUD_BALL.get(), RenderMudBall::new);
        EntityRendererRegistry.register(AMEntityRegistry.RHINOCEROS.get(), RenderRhinoceros::new);
        EntityRendererRegistry.register(AMEntityRegistry.SUGAR_GLIDER.get(), RenderSugarGlider::new);
        EntityRendererRegistry.register(AMEntityRegistry.FARSEER.get(), RenderFarseer::new);
        EntityRendererRegistry.register(AMEntityRegistry.SKREECHER.get(), RenderSkreecher::new);
        EntityRendererRegistry.register(AMEntityRegistry.UNDERMINER.get(), RenderUnderminer::new);
        EntityRendererRegistry.register(AMEntityRegistry.MURMUR.get(), RenderMurmurBody::new);
        EntityRendererRegistry.register(AMEntityRegistry.MURMUR_HEAD.get(), RenderMurmurHead::new);
        EntityRendererRegistry.register(AMEntityRegistry.TENDON_SEGMENT.get(), RenderTendonSegment::new);
        EntityRendererRegistry.register(AMEntityRegistry.SKUNK.get(), RenderSkunk::new);
        EntityRendererRegistry.register(AMEntityRegistry.FART.get(), RenderFart::new);
        EntityRendererRegistry.register(AMEntityRegistry.BANANA_SLUG.get(), RenderBananaSlug::new);
        EntityRendererRegistry.register(AMEntityRegistry.BLUE_JAY.get(), RenderBlueJay::new);
        new ClientEvents();
        try {
            ItemProperties.register(AMItemRegistry.BLOOD_SPRAYER.get(), new ResourceLocation("empty"), (stack, p_239428_1_, p_239428_2_, j) -> {
                return !ItemBloodSprayer.isUsable(stack) || p_239428_2_ instanceof Player && ((Player) p_239428_2_).getCooldowns().isOnCooldown(AMItemRegistry.BLOOD_SPRAYER.get()) ? 1.0F : 0.0F;
            });
            ItemProperties.register(AMItemRegistry.HEMOLYMPH_BLASTER.get(), new ResourceLocation("empty"), (stack, p_239428_1_, p_239428_2_, j) -> {
                return !ItemHemolymphBlaster.isUsable(stack) || p_239428_2_ instanceof Player && ((Player) p_239428_2_).getCooldowns().isOnCooldown(AMItemRegistry.HEMOLYMPH_BLASTER.get()) ? 1.0F : 0.0F;
            });
            ItemProperties.register(AMItemRegistry.TARANTULA_HAWK_ELYTRA.get(), new ResourceLocation("broken"), (stack, p_239428_1_, p_239428_2_, j) -> {
                return ItemTarantulaHawkElytra.isUsable(stack) ? 0.0F : 1.0F;
            });
            ItemProperties.register(AMItemRegistry.SHIELD_OF_THE_DEEP.get(), new ResourceLocation("blocking"), (stack, p_239421_1_, p_239421_2_, j) -> {
                return p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F;
            });
            ItemProperties.register(AMItemRegistry.SOMBRERO.get(), new ResourceLocation("silly"), (stack, p_239421_1_, p_239421_2_, j) -> {
                return AlexsMobs.isAprilFools() ? 1.0F : 0.0F;
            });
            ItemProperties.register(AMItemRegistry.TENDON_WHIP.get(), new ResourceLocation("active"), (stack, p_239421_1_, holder, j) -> {
                return ItemTendonWhip.isActive(stack, holder) ? 1.0F : 0.0F;
            });
            ItemProperties.register(AMItemRegistry.PUPFISH_LOCATOR.get(), new ResourceLocation("in_chunk"), (stack, world, entity, j) -> {
                int x = pupfishChunkX * 16;
                int z = pupfishChunkZ * 16;
                if (entity != null && entity.getX() >= x && entity.getX() <= x + 16 && entity.getZ() >= z && entity.getZ() <= z + 16) {
                    return 1.0F;
                }
                return 0.0F;
            });
            ItemProperties.register(AMItemRegistry.SKELEWAG_SWORD.get(), new ResourceLocation("blocking"), (stack, p_239421_1_, p_239421_2_, j) -> {
                return p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F;
            });
        } catch (Exception e) {
            AlexsMobs.LOGGER.warn("Could not load item models for weapons");
        }
        BlockEntityRendererRegistry.register(AMTileEntityRegistry.CAPSID.get(), RenderCapsid::new);
        BlockEntityRendererRegistry.register(AMTileEntityRegistry.VOID_WORM_BEAK.get(), RenderVoidWormBeak::new);
        BlockEntityRendererRegistry.register(AMTileEntityRegistry.TRANSMUTATION_TABLE.get(), RenderTransmutationTable::new);
        MenuScreens.register(AMMenuRegistry.TRANSMUTATION_TABLE.get(), GUITransmutationTable::new);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.cutout(),
                AMBlockRegistry.BANANA_PEEL.get(),
                AMBlockRegistry.HUMMINGBIRD_FEEDER.get(),
                AMBlockRegistry.BISON_CARPET.get(),
                AMBlockRegistry.BISON_FUR_BLOCK.get(),
                AMBlockRegistry.VOID_WORM_BEAK.get()
        );

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderType.translucent(),
                AMBlockRegistry.BANANA_SLUG_SLIME_BLOCK.get(),
                AMBlockRegistry.CRYSTALIZED_BANANA_SLUG_MUCUS.get(),
                AMBlockRegistry.CAPSID.get(),
                AMBlockRegistry.ENDER_RESIDUE.get(),
                AMBlockRegistry.RAINBOW_GLASS.get(),
                AMBlockRegistry.SKUNK_SPRAY.get()
        );
    }

    private void initRainbowBuffers() {
        var renderBuffers = (RenderBuffersAccessor) Minecraft.getInstance().renderBuffers();
        renderBuffers.getFixedBuffers().put(AMRenderTypes.COMBJELLY_RAINBOW_GLINT, new BufferBuilder(AMRenderTypes.COMBJELLY_RAINBOW_GLINT.bufferSize()));
        renderBuffers.getFixedBuffers().put(AMRenderTypes.VOID_WORM_PORTAL_OVERLAY, new BufferBuilder(AMRenderTypes.VOID_WORM_PORTAL_OVERLAY.bufferSize()));
        renderBuffers.getFixedBuffers().put(AMRenderTypes.STATIC_PORTAL, new BufferBuilder(AMRenderTypes.STATIC_PORTAL.bufferSize()));
        renderBuffers.getFixedBuffers().put(AMRenderTypes.STATIC_PARTICLE, new BufferBuilder(AMRenderTypes.STATIC_PARTICLE.bufferSize()));
        renderBuffers.getFixedBuffers().put(AMRenderTypes.STATIC_ENTITY, new BufferBuilder(AMRenderTypes.STATIC_ENTITY.bufferSize()));
        initializedRainbowBuffers = true;
    }

    private static void onBakingCompleted() {
        ModelsBakedCallback.EVENT.register(((manager, models, loader) -> {
            String ghostlyPickaxe = "alexsmobs:ghostly_pickaxe";
            for (ResourceLocation id : models.keySet()) {
                if (id.toString().contains(ghostlyPickaxe)) {
                    models.put(id, new GhostlyPickaxeBakedModel(models.get(id)));
                }
            }
        }));
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
            case 0:
                return ROADRUNNER_BOOTS_MODEL;
            case 1:
                return MOOSE_HEADGEAR_MODEL;
            case 2:
                return FRONTIER_CAP_MODEL.withAnimations(entity);
            case 3:
                return SOMBRERO_MODEL;
            case 4:
                return SPIKED_TURTLE_SHELL_MODEL;
            case 5:
                return FEDORA_MODEL;
            case 6:
                return ELYTRA_MODEL.withAnimations(entity);

             */
            default:
                return null;
        }
    }

    @Environment(EnvType.CLIENT)
    public void onEntityStatus(Entity entity, byte updateKind) {
        if (entity instanceof EntityCockroach && entity.isAlive() && updateKind == 67) {
            SoundLaCucaracha sound;
            if (COCKROACH_SOUND_MAP.get(entity.getId()) == null) {
                sound = new SoundLaCucaracha((EntityCockroach) entity);
                COCKROACH_SOUND_MAP.put(entity.getId(), sound);
            } else {
                sound = COCKROACH_SOUND_MAP.get(entity.getId());
            }
            if (!Minecraft.getInstance().getSoundManager().isActive(sound) && sound.canPlaySound() && sound.isOnlyCockroach()) {
                Minecraft.getInstance().getSoundManager().play(sound);
            }
        }
        if (entity instanceof EntityVoidWorm && entity.isAlive() && updateKind == 67) {
            float f2 = Minecraft.getInstance().options.getSoundSourceVolume(SoundSource.MUSIC);
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
        }
        if (entity instanceof EntityGrizzlyBear && entity.isAlive() && updateKind == 67) {
            SoundBearMusicBox sound;
            if (BEAR_MUSIC_BOX_SOUND_MAP.get(entity.getId()) == null) {
                sound = new SoundBearMusicBox((EntityGrizzlyBear) entity);
                BEAR_MUSIC_BOX_SOUND_MAP.put(entity.getId(), sound);
            } else {
                sound = BEAR_MUSIC_BOX_SOUND_MAP.get(entity.getId());
            }
            if (!Minecraft.getInstance().getSoundManager().isActive(sound) && sound.canPlaySound() && sound.isOnlyMusicBox()) {
                Minecraft.getInstance().getSoundManager().play(sound);
            }
        }
        if (entity instanceof EntityBlueJay && entity.isAlive() && updateKind == 67) {
            singingBlueJayId = entity.getId();
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
        ParticleProviderRegistry.register(AMParticleRegistry.GUSTER_SAND_SPIN.get(), ParticleGusterSandSpin.Factory::new);
        ParticleProviderRegistry.register(AMParticleRegistry.GUSTER_SAND_SHOT.get(), ParticleGusterSandShot.Factory::new);
        ParticleProviderRegistry.register(AMParticleRegistry.GUSTER_SAND_SPIN_RED.get(), ParticleGusterSandSpin.FactoryRed::new);
        ParticleProviderRegistry.register(AMParticleRegistry.GUSTER_SAND_SHOT_RED.get(), ParticleGusterSandShot.FactoryRed::new);
        ParticleProviderRegistry.register(AMParticleRegistry.GUSTER_SAND_SPIN_SOUL.get(), ParticleGusterSandSpin.FactorySoul::new);
        ParticleProviderRegistry.register(AMParticleRegistry.GUSTER_SAND_SHOT_SOUL.get(), ParticleGusterSandShot.FactorySoul::new);
        ParticleProviderRegistry.register(AMParticleRegistry.HEMOLYMPH.get(), ParticleHemolymph.Factory::new);
        ParticleProviderRegistry.register(AMParticleRegistry.PLATYPUS_SENSE.get(), ParticlePlatypus.Factory::new);
        ParticleProviderRegistry.register(AMParticleRegistry.WHALE_SPLASH.get(), ParticleWhaleSplash.Factory::new);
        ParticleProviderRegistry.register(AMParticleRegistry.DNA.get(), ParticleDna.Factory::new);
        ParticleProviderRegistry.register(AMParticleRegistry.SHOCKED.get(), ParticleSimpleHeart.Factory::new);
        ParticleProviderRegistry.register(AMParticleRegistry.WORM_PORTAL.get(), ParticleWormPortal.Factory::new);
        ParticleProviderRegistry.register(AMParticleRegistry.INVERT_DIG.get(), ParticleInvertDig.Factory::new);
        ParticleProviderRegistry.register(AMParticleRegistry.TEETH_GLINT.get(), ParticleTeethGlint.Factory::new);
        ParticleProviderRegistry.register(AMParticleRegistry.SMELLY.get(), ParticleSmelly.Factory::new);
        ParticleProviderRegistry.register(AMParticleRegistry.BUNFUNGUS_TRANSFORMATION.get(), ParticleBunfungusTransformation.Factory::new);
        ParticleProviderRegistry.register(AMParticleRegistry.FUNGUS_BUBBLE.get(), ParticleFungusBubble.Factory::new);
        ParticleProviderRegistry.register(AMParticleRegistry.BEAR_FREDDY.get(), new ParticleBearFreddy.Factory());
        ParticleProviderRegistry.register(AMParticleRegistry.SUNBIRD_FEATHER.get(), ParticleSunbirdFeather.Factory::new);
        ParticleProviderRegistry.register(AMParticleRegistry.STATIC_SPARK.get(), new ParticleStaticSpark.Factory());
        ParticleProviderRegistry.register(AMParticleRegistry.SKULK_BOOM.get(), new ParticleSkulkBoom.Factory());
        ParticleProviderRegistry.register(AMParticleRegistry.BIRD_SONG.get(), ParticleBirdSong.Factory::new);
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

    @Environment(EnvType.CLIENT)
    public void onRegisterEntityRenders() {
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
            Minecraft.getInstance().level.addParticle(AMParticleRegistry.BEAR_FREDDY.get(), Minecraft.getInstance().player.getX(), Minecraft.getInstance().player.getY(), Minecraft.getInstance().player.getZ(), 0, 0, 0);
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

    public void setDisplayTransmuteResult(int slot, ItemStack stack){
        transmuteStacks[Mth.clamp(slot, 0, 2)] = stack;
    }

    public ItemStack getDisplayTransmuteResult(int slot){
        ItemStack stack = transmuteStacks[Mth.clamp(slot, 0, 2)];
        return stack == null ? ItemStack.EMPTY : stack;
    }

    public int getSingingBlueJayId() {
        return singingBlueJayId;
    }

}
