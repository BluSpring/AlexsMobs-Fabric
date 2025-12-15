package com.github.alexthe666.alexsmobs;

import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import com.github.alexthe666.alexsmobs.client.model.layered.AMModelLayers;
import com.github.alexthe666.alexsmobs.client.particle.AMParticleRegistry;
import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.config.BiomeConfig;
import com.github.alexthe666.alexsmobs.config.ConfigHolder;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import com.github.alexthe666.alexsmobs.enchantment.AMEnchantmentRegistry;
import com.github.alexthe666.alexsmobs.entity.AMEntityRegistry;
import com.github.alexthe666.alexsmobs.event.ServerEvents;
import com.github.alexthe666.alexsmobs.inventory.AMMenuRegistry;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.message.*;
import com.github.alexthe666.alexsmobs.misc.*;
import com.github.alexthe666.alexsmobs.tileentity.AMTileEntityRegistry;
import com.github.alexthe666.alexsmobs.world.AMFeatureRegistry;
import com.github.alexthe666.alexsmobs.world.AMLeafcutterAntBiomeModifier;
import com.github.alexthe666.alexsmobs.world.AMMobSpawnBiomeModifier;
import com.github.alexthe666.alexsmobs.world.AMMobSpawnStructureModifier;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.NetworkRegistry;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

@Mod(AlexsMobs.MODID)
@EventBusSubscriber(modid = AlexsMobs.MODID)
public class AlexsMobs {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "alexsmobs";
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static final CommonProxy PROXY = FMLLoader.getDist() == Dist.CLIENT ? new ClientProxy() : new CommonProxy();
    private static boolean isAprilFools = false;
    private static boolean isHalloween = false;

    public AlexsMobs(ModContainer container, IEventBus modBusEvent) {
        modBusEvent.addListener(this::setup);
        modBusEvent.addListener(this::setupClient);
        modBusEvent.addListener(this::onModConfigEvent);
        modBusEvent.addListener(this::setupEntityModelLayers);
        modBusEvent.addListener(this::registerPayloads);
        AMArmorMaterialRegistry.DEF_REG.register(modBusEvent);
        AMDataComponentTypeRegistry.DEF_REG.register(modBusEvent);
        AMAdvancementTriggerRegistry.DEF_REG.register(modBusEvent);
        AMBlockRegistry.DEF_REG.register(modBusEvent);
        AMEntityRegistry.DEF_REG.register(modBusEvent);
        AMItemRegistry.DEF_REG.register(modBusEvent);
        AMTileEntityRegistry.DEF_REG.register(modBusEvent);
        AMPointOfInterestRegistry.DEF_REG.register(modBusEvent);
        AMFeatureRegistry.DEF_REG.register(modBusEvent);
        AMSoundRegistry.DEF_REG.register(modBusEvent);
        AMParticleRegistry.DEF_REG.register(modBusEvent);
        AMPaintingRegistry.DEF_REG.register(modBusEvent);
        AMEffectRegistry.EFFECT_DEF_REG.register(modBusEvent);
        AMEffectRegistry.POTION_DEF_REG.register(modBusEvent);
        AMEnchantmentRegistry.DEF_REG.register(modBusEvent);
        AMMenuRegistry.DEF_REG.register(modBusEvent);
        AMRecipeRegistry.DEF_REG.register(modBusEvent);
        AMLootRegistry.DEF_REG.register(modBusEvent);
        AMBannerRegistry.DEF_REG.register(modBusEvent);
        AMCreativeTabRegistry.DEF_REG.register(modBusEvent);
        final DeferredRegister<MapCodec<? extends BiomeModifier>> biomeModifiers = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, AlexsMobs.MODID);
        biomeModifiers.register(modBusEvent);
        biomeModifiers.register("am_mob_spawns", () -> AMMobSpawnBiomeModifier.CODEC);
        biomeModifiers.register("am_leafcutter_ant_spawns", () -> AMLeafcutterAntBiomeModifier.CODEC);
        final DeferredRegister<MapCodec<? extends StructureModifier>> structureModifiers = DeferredRegister.create(NeoForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, AlexsMobs.MODID);
        structureModifiers.register(modBusEvent);
        structureModifiers.register("am_structure_spawns", () -> AMMobSpawnStructureModifier.CODEC);
        container.registerConfig(ModConfig.Type.COMMON, ConfigHolder.COMMON_SPEC, "alexsmobs.toml");
        PROXY.init(modBusEvent);
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(new ServerEvents());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        isAprilFools = calendar.get(Calendar.MONTH) + 1 == 4 && calendar.get(Calendar.DATE) == 1;
        isHalloween = calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) >= 29 && calendar.get(Calendar.DATE) <= 31;
    }

    public static boolean isAprilFools() {
        return isAprilFools || AMConfig.superSecretSettings;
    }

    public static boolean isHalloween() {
        return isHalloween || AMConfig.superSecretSettings;
    }

    private void setupEntityModelLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        AMModelLayers.register(event);
    }

    @SubscribeEvent
    public void onModConfigEvent(final ModConfigEvent event) {
        final ModConfig config = event.getConfig();
        // Rebake the configs when they change
        if (config.getSpec() == ConfigHolder.COMMON_SPEC) {
            AMConfig.bake(config);
        }
        BiomeConfig.init();
    }

    @OnlyIn(Dist.CLIENT)
    public static <MSG extends CustomPacketPayload> void sendMSGToServer(MSG message) {
        Minecraft.getInstance().getConnection().send(message);
    }

    public static <MSG extends CustomPacketPayload> void sendMSGToAll(MSG message) {
        for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            sendNonLocal(message, player);
        }
    }

    public static <MSG extends CustomPacketPayload> void sendNonLocal(MSG msg, ServerPlayer player) {
        player.connection.send(msg);
    }

    private void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);

        registrar.playToClient(MessageMosquitoMountPlayer.TYPE, MessageMosquitoMountPlayer.STREAM_CODEC, MessageMosquitoMountPlayer.Handler::handle);
        registrar.playBidirectional(MessageMosquitoDismount.TYPE, MessageMosquitoDismount.STREAM_CODEC, MessageMosquitoDismount.Handler::handle);
        registrar.playBidirectional(MessageHurtMultipart.TYPE, MessageHurtMultipart.STREAM_CODEC, MessageHurtMultipart.Handler::handle);
        registrar.playToClient(MessageCrowMountPlayer.TYPE, MessageCrowMountPlayer.STREAM_CODEC, MessageCrowMountPlayer.Handler::handle);
        registrar.playToClient(MessageCrowDismount.TYPE, MessageCrowDismount.STREAM_CODEC, MessageCrowDismount.Handler::handle);
        registrar.playToClient(MessageMungusBiomeChange.TYPE, MessageMungusBiomeChange.STREAM_CODEC, MessageMungusBiomeChange.Handler::handle);
        registrar.playToClient(MessageKangarooInventorySync.TYPE, MessageKangarooInventorySync.STREAM_CODEC, MessageKangarooInventorySync.Handler::handle);
        registrar.playToClient(MessageKangarooEat.TYPE, MessageKangarooEat.STREAM_CODEC, MessageKangarooEat.Handler::handle);
        registrar.playToClient(MessageUpdateCapsid.TYPE, MessageUpdateCapsid.STREAM_CODEC, MessageUpdateCapsid.Handler::handle);
        registrar.playToServer(MessageSwingArm.TYPE, MessageSwingArm.STREAM_CODEC, MessageSwingArm.Handler::handle);
        registrar.playToServer(MessageUpdateEagleControls.TYPE, MessageUpdateEagleControls.STREAM_CODEC, MessageUpdateEagleControls.Handler::handle);
        registrar.playBidirectional(MessageSyncEntityPos.TYPE, MessageSyncEntityPos.STREAM_CODEC, MessageSyncEntityPos.Handler::handle);
        registrar.playToClient(MessageTarantulaHawkSting.TYPE, MessageTarantulaHawkSting.STREAM_CODEC, MessageTarantulaHawkSting.Handler::handle);
        registrar.playToServer(MessageSetDancing.TYPE, MessageSetDancing.STREAM_CODEC, MessageSetDancing.Handler::handle);
        registrar.playToServer(MessageInteractMultipart.TYPE, MessageInteractMultipart.STREAM_CODEC, MessageInteractMultipart.Handler::handle);
        registrar.playToClient(MessageSendVisualFlagFromServer.TYPE, MessageSendVisualFlagFromServer.STREAM_CODEC, MessageSendVisualFlagFromServer.Handler::handle);
        registrar.playToClient(MessageSetPupfishChunkOnClient.TYPE, MessageSetPupfishChunkOnClient.STREAM_CODEC, MessageSetPupfishChunkOnClient.Handler::handle);
        registrar.playToClient(MessageUpdateTransmutablesToDisplay.TYPE, MessageUpdateTransmutablesToDisplay.STREAM_CODEC, MessageUpdateTransmutablesToDisplay.Handler::handle);
        registrar.playToServer(MessageTransmuteFromMenu.TYPE, MessageTransmuteFromMenu.STREAM_CODEC, MessageTransmuteFromMenu.Handler::handle);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(AMItemRegistry::init);
        event.enqueueWork(AMItemRegistry::initDispenser);
        AMRecipeRegistry.init();
        PROXY.initPathfinding();
    }

    private void setupClient(FMLClientSetupEvent event) {
        event.enqueueWork(PROXY::clientInit);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
