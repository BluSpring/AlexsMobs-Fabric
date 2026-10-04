package com.github.alexthe666.alexsmobs;

import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import com.github.alexthe666.alexsmobs.client.model.layered.AMModelLayers;
import com.github.alexthe666.alexsmobs.client.particle.AMParticleRegistry;
import com.github.alexthe666.alexsmobs.component.AMDataComponentRegistry;
import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.config.BiomeConfig;
import com.github.alexthe666.alexsmobs.config.ConfigHolder;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import com.github.alexthe666.alexsmobs.entity.AMEntityRegistry;
import com.github.alexthe666.alexsmobs.event.ServerEvents;
import com.github.alexthe666.alexsmobs.inventory.AMMenuRegistry;
import com.github.alexthe666.alexsmobs.item.AMArmorMaterial;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.message.*;
import com.github.alexthe666.alexsmobs.misc.*;
import com.github.alexthe666.alexsmobs.tileentity.AMTileEntityRegistry;
import com.github.alexthe666.alexsmobs.world.AMFeatureRegistry;
import com.github.alexthe666.alexsmobs.world.AMLeafcutterAntBiomeModifier;
import com.github.alexthe666.alexsmobs.world.AMMobSpawnBiomeModifier;
import com.github.alexthe666.alexsmobs.world.AMMobSpawnStructureModifier;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import io.github.fabricators_of_create.porting_lib.config.ConfigRegistry;
import io.github.fabricators_of_create.porting_lib.config.ModConfig;
import io.github.fabricators_of_create.porting_lib.config.ModConfigEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Date;
import java.util.function.Supplier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.fabricmc.loader.api.FabricLoader;

public class AlexsMobs implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "alexsmobs";
    public static final String VERSION = "1.22.9";
    
    // Use supplier pattern to avoid loading ClientProxy class on dedicated server
    public static final CommonProxy PROXY = unsafeRunForDist(
        () -> ClientProxy::new,
        () -> CommonProxy::new
    );
    private static boolean isAprilFools = false;
    private static boolean isHalloween = false;

    private static MinecraftServer server;

    @Override
    public void onInitialize() {
        this.setup();
        new ServerEvents();
        ModConfigEvent.Loading.EVENT.register(this::onModConfigEvent);
        ModConfigEvent.Reloading.EVENT.register(this::onModConfigEvent);
        this.registerPayloads();
        FabricBrewingRecipeRegistryBuilder.BUILD.register(AMEffectRegistry::registerBrewingRecipes);
        
        // Register all deferred registers
        AMBlockRegistry.DEF_REG.register();
        AMEntityRegistry.DEF_REG.register();
        AMDataComponentRegistry.DEF_REG.register();
        AMItemRegistry.DEF_REG.register();
        AMArmorMaterial.ARMOR_MATERIALS.register();
        AMTileEntityRegistry.DEF_REG.register();
        AMPointOfInterestRegistry.DEF_REG.register();
        AMFeatureRegistry.DEF_REG.register();
        AMSoundRegistry.DEF_REG.register();
        AMParticleRegistry.DEF_REG.register();
        AMEffectRegistry.EFFECT_DEF_REG.register();
        AMEffectRegistry.POTION_DEF_REG.register();
        AMMenuRegistry.DEF_REG.register();
        AMRecipeRegistry.DEF_REG.register();
        AMLootRegistry.DEF_REG.register();
        AMBannerRegistry.DEF_REG.register();
        AMCreativeTabRegistry.DEF_REG.register();
        AMAdvancementTriggerRegistry.DEF_REG.register();

        ServerLifecycleEvents.SERVER_STARTING.register(s -> server = s);
        ServerLifecycleEvents.SERVER_STOPPING.register(s -> server = null);
        
        // Biome modifiers
        AMMobSpawnBiomeModifier.BIOME_MODIFIER_SERIALIZERS.register();
        AMLeafcutterAntBiomeModifier.BIOME_MODIFIER_SERIALIZERS.register();
        
        // Structure modifiers
        AMMobSpawnStructureModifier.STRUCTURE_MODIFIER_SERIALIZERS.register();
        
        // Register config
        ConfigRegistry.registerConfig(MODID, ModConfig.Type.COMMON, ConfigHolder.COMMON_SPEC, "alexsmobs.toml");
        
        PROXY.init();
        // ServerEvents is already registered via @EventBusSubscriber annotation
        
        // Check for special dates
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

    private void onModConfigEvent(final ModConfigEvent event) {
        final ModConfig config = event.getConfig();
        if (config.getSpec() == ConfigHolder.COMMON_SPEC) {
            AMConfig.bake(config);
        }
        BiomeConfig.init();
    }

    private void registerPayloads() {
        // Client to Server messages
        PayloadTypeRegistry.playC2S().register(MessageSwingArm.TYPE, MessageSwingArm.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MessageSwingArm.TYPE, MessageSwingArm::handle);
        PayloadTypeRegistry.playC2S().register(MessageUpdateEagleControls.TYPE, MessageUpdateEagleControls.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MessageUpdateEagleControls.TYPE, MessageUpdateEagleControls::handle);
        // Bidirectional - sent from client (when player attacks multipart) and from server (sendMSGToAll for sync)
        PayloadTypeRegistry.playC2S().register(MessageHurtMultipart.TYPE, MessageHurtMultipart.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MessageHurtMultipart.TYPE, MessageHurtMultipart::handle);
        PayloadTypeRegistry.playS2C().register(MessageHurtMultipart.TYPE, MessageHurtMultipart.CODEC);
        PayloadTypeRegistry.playC2S().register(MessageInteractMultipart.TYPE, MessageInteractMultipart.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MessageInteractMultipart.TYPE, MessageInteractMultipart::handle);
        PayloadTypeRegistry.playS2C().register(MessageInteractMultipart.TYPE, MessageInteractMultipart.CODEC);
        PayloadTypeRegistry.playC2S().register(MessageTransmuteFromMenu.TYPE, MessageTransmuteFromMenu.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MessageTransmuteFromMenu.TYPE, MessageTransmuteFromMenu::handle);
        
        // Server to Client messages
        PayloadTypeRegistry.playS2C().register(MessageCrowDismount.TYPE, MessageCrowDismount.CODEC);
        PayloadTypeRegistry.playS2C().register(MessageCrowMountPlayer.TYPE, MessageCrowMountPlayer.CODEC);
        // Bidirectional - sent from client (falconry glove launch) and from server (sendMSGToAll for sync)
        PayloadTypeRegistry.playS2C().register(MessageMosquitoDismount.TYPE, MessageMosquitoDismount.CODEC);
        PayloadTypeRegistry.playC2S().register(MessageMosquitoDismount.TYPE, MessageMosquitoDismount.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MessageMosquitoDismount.TYPE, MessageMosquitoDismount::handle);

        PayloadTypeRegistry.playS2C().register(MessageMosquitoMountPlayer.TYPE, MessageMosquitoMountPlayer.CODEC);
        PayloadTypeRegistry.playS2C().register(MessageKangarooEat.TYPE, MessageKangarooEat.CODEC);
        PayloadTypeRegistry.playS2C().register(MessageKangarooInventorySync.TYPE, MessageKangarooInventorySync.CODEC);
        // Client to Server - sent from client when jukebox plays near dancing mobs (e.g., rain frog rain dance)
        PayloadTypeRegistry.playC2S().register(MessageStartDancing.TYPE, MessageStartDancing.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MessageStartDancing.TYPE, MessageStartDancing::handle);
        // Bidirectional - sent from client (falconry glove launch) and from server (sendMSGToAll for sync)
        PayloadTypeRegistry.playC2S().register(MessageSyncEntityPos.TYPE, MessageSyncEntityPos.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(MessageSyncEntityPos.TYPE, MessageSyncEntityPos::handle);
        PayloadTypeRegistry.playS2C().register(MessageSyncEntityPos.TYPE, MessageSyncEntityPos.CODEC);
        PayloadTypeRegistry.playS2C().register(MessageSendVisualFlagFromServer.TYPE, MessageSendVisualFlagFromServer.CODEC);
        PayloadTypeRegistry.playS2C().register(MessageSetPupfishChunkOnClient.TYPE, MessageSetPupfishChunkOnClient.CODEC);
        PayloadTypeRegistry.playS2C().register(MessageTarantulaHawkSting.TYPE, MessageTarantulaHawkSting.CODEC);
        PayloadTypeRegistry.playS2C().register(MessageMungusBiomeChange.TYPE, MessageMungusBiomeChange.CODEC);
        PayloadTypeRegistry.playS2C().register(MessageUpdateCapsid.TYPE, MessageUpdateCapsid.CODEC);
        PayloadTypeRegistry.playS2C().register(MessageUpdateTransmutablesToDisplay.TYPE, MessageUpdateTransmutablesToDisplay.CODEC);
    }

    @Environment(EnvType.CLIENT)
    public static <MSG extends CustomPacketPayload> void sendMSGToServer(MSG message) {
        ClientPlayNetworking.send(message);
    }

    public static <MSG extends CustomPacketPayload> void sendMSGToAll(MSG message) {
        PlayerLookup.all(server).forEach(p -> ServerPlayNetworking.send(p, message));
    }

    public static <MSG extends CustomPacketPayload> void sendNonLocal(MSG msg, ServerPlayer player) {
        ServerPlayNetworking.send(player, msg);
    }

    private void setup() {
        AMItemRegistry.init();
        AMItemRegistry.initDispenser();
        AMAdvancementTriggerRegistry.init();
        AMEffectRegistry.init();
        AMRecipeRegistry.init();
        PROXY.initPathfinding();
    }
    
    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    // Safe dist proxy helper - uses Supplier to avoid loading client classes on dedicated server
    private static <T> T unsafeRunForDist(Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        return switch (FabricLoader.getInstance().getEnvironmentType()) {
            case CLIENT -> clientTarget.get().get();
            case SERVER -> serverTarget.get().get();
        };
    }
}
