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
import com.github.alexthe666.alexsmobs.world.AMMobSpawnBiomeModifier;
import com.github.alexthe666.alexsmobs.world.AMMobSpawnStructureModifier;
import com.github.alexthe666.alexsmobs.world.AMWorldRegistry;
import com.mojang.serialization.Codec;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.ServerLifecycleHooks;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.api.fml.event.config.ModConfigEvents;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.TriConsumer;
import xyz.bluspring.forgebiomemodifiers.structures.StructureModifier;
import xyz.bluspring.forgebiomemodifiers.structures.StructureModifiers;
import xyz.bluspring.forgebiomemodifiers.worldgen.BiomeModifier;
import xyz.bluspring.forgebiomemodifiers.worldgen.BiomeModifiers;

import java.util.Calendar;
import java.util.Date;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AlexsMobs implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "alexsmobs";
    public static final SimpleChannel NETWORK_WRAPPER;
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static CommonProxy PROXY;
    private static int packetsRegistered;
    private static boolean isAprilFools = false;
    private static boolean isHalloween = false;

    static {
        NETWORK_WRAPPER = new SimpleChannel(new ResourceLocation("alexsmobs", "main_channel"));

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
            PROXY = new ClientProxy();
        else
            PROXY = new CommonProxy();
    }

    @Override
    public void onInitialize() {
        this.setup();
        this.setupClient();

        AMBlockRegistry.DEF_REG.register();
        AMEntityRegistry.DEF_REG.register();

        AMAdvancementTriggerRegistry.init();

        AMEffectRegistry.EFFECT_DEF_REG.register();
        AMEffectRegistry.POTION_DEF_REG.register();

        AMSoundRegistry.DEF_REG.register();
        AMEnchantmentRegistry.DEF_REG.register();

        AMItemRegistry.DEF_REG.register();
        AMItemRegistry.init();
        AMItemRegistry.initDispenser();
        AMEffectRegistry.init();

        AMRecipeRegistry.init();
        AMTileEntityRegistry.DEF_REG.register();
        AMPointOfInterestRegistry.DEF_REG.register();
        AMFeatureRegistry.DEF_REG.register();
        AMFeatureRegistry.AMConfiguredFeatureRegistry.DEF_REG.register();
        AMFeatureRegistry.AMPlacedFeatureRegistry.DEF_REG.register();
        AMParticleRegistry.DEF_REG.register();
        AMPaintingRegistry.DEF_REG.register();
        AMMenuRegistry.DEF_REG.register();
        AMRecipeRegistry.DEF_REG.register();
        AMLootRegistry.DEF_REG.register();
        AMBannerRegistry.DEF_REG.register();
        final LazyRegistrar<Codec<? extends BiomeModifier>> biomeModifiers = LazyRegistrar.create(BiomeModifiers.BIOME_MODIFIER_SERIALIZER_KEY, AlexsMobs.MODID);
        biomeModifiers.register();
        biomeModifiers.register("am_mob_spawns", AMMobSpawnBiomeModifier::makeCodec);
        final LazyRegistrar<Codec<? extends StructureModifier>> structureModifiers = LazyRegistrar.create(StructureModifiers.STRUCTURE_MODIFIER_SERIALIZER_KEY, AlexsMobs.MODID);
        structureModifiers.register();
        structureModifiers.register("am_structure_spawns", AMMobSpawnStructureModifier::makeCodec);
        ModLoadingContext.registerConfig(MODID, ModConfig.Type.COMMON, ConfigHolder.COMMON_SPEC, "alexsmobs.toml");
        PROXY.init();
        //MinecraftForge.EVENT_BUS.register(this);
        //MinecraftForge.EVENT_BUS.register(new ServerEvents());
        new ServerEvents();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        isAprilFools = calendar.get(Calendar.MONTH) + 1 == 4 && calendar.get(Calendar.DATE) == 1;
        isHalloween = calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) >= 29 && calendar.get(Calendar.DATE) <= 31;

        ModConfigEvents.loading(MODID).register(this::onModConfigEvent);
        ModConfigEvents.reloading(MODID).register(this::onModConfigEvent);

        AMWorldRegistry.init();

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            this.setupEntityModelLayers();
        }

        AMEntityRegistry.initializeAttributes();
        NETWORK_WRAPPER.initServerListener();
    }

    public static boolean isAprilFools() {
        return isAprilFools || AMConfig.superSecretSettings;
    }

    public static boolean isHalloween() {
        return isHalloween || AMConfig.superSecretSettings;
    }

    private void setupEntityModelLayers() {
        AMModelLayers.register();
    }

    public void onModConfigEvent(ModConfig config) {
        // Rebake the configs when they change
        if (config.getSpec() == ConfigHolder.COMMON_SPEC) {
            AMConfig.bake(config);
        }
        BiomeConfig.init();
    }

    public static <MSG extends C2SPacket> void sendMSGToServer(MSG message) {
        NETWORK_WRAPPER.sendToServer(message);
    }

    public static <MSG extends S2CPacket> void sendMSGToAll(MSG message) {
        for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            sendNonLocal(message, player);
        }
    }

    public static <MSG extends S2CPacket> void sendNonLocal(MSG msg, ServerPlayer player) {
        NETWORK_WRAPPER.sendToClient(msg, player);
    }

    private <T extends S2CPacket & C2SPacket> void registerMessage(int id, int id2, Class<T> packet, BiConsumer<T, FriendlyByteBuf> writer, Function<FriendlyByteBuf, T> decoder, TriConsumer<T, BlockableEventLoop<?>, Player> handler) {
        NETWORK_WRAPPER.registerS2CPacket(packet, id, decoder);
        NETWORK_WRAPPER.registerC2SPacket(packet, id2, decoder);
    }

    private void setup() {
        registerMessage(packetsRegistered++, packetsRegistered++, MessageMosquitoMountPlayer.class, MessageMosquitoMountPlayer::write, MessageMosquitoMountPlayer::read, MessageMosquitoMountPlayer.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageMosquitoDismount.class, MessageMosquitoDismount::write, MessageMosquitoDismount::read, MessageMosquitoDismount.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageHurtMultipart.class, MessageHurtMultipart::write, MessageHurtMultipart::read, MessageHurtMultipart.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageCrowMountPlayer.class, MessageCrowMountPlayer::write, MessageCrowMountPlayer::read, MessageCrowMountPlayer.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageCrowDismount.class, MessageCrowDismount::write, MessageCrowDismount::read, MessageCrowDismount.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageMungusBiomeChange.class, MessageMungusBiomeChange::write, MessageMungusBiomeChange::read, MessageMungusBiomeChange.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageKangarooInventorySync.class, MessageKangarooInventorySync::write, MessageKangarooInventorySync::read, MessageKangarooInventorySync.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageKangarooEat.class, MessageKangarooEat::write, MessageKangarooEat::read, MessageKangarooEat.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageUpdateCapsid.class, MessageUpdateCapsid::write, MessageUpdateCapsid::read, MessageUpdateCapsid.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageSwingArm.class, MessageSwingArm::write, MessageSwingArm::read, MessageSwingArm.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageUpdateEagleControls.class, MessageUpdateEagleControls::write, MessageUpdateEagleControls::read, MessageUpdateEagleControls.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageSyncEntityPos.class, MessageSyncEntityPos::write, MessageSyncEntityPos::read, MessageSyncEntityPos.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageTarantulaHawkSting.class, MessageTarantulaHawkSting::write, MessageTarantulaHawkSting::read, MessageTarantulaHawkSting.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageStartDancing.class, MessageStartDancing::write, MessageStartDancing::read, MessageStartDancing.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageInteractMultipart.class, MessageInteractMultipart::write, MessageInteractMultipart::read, MessageInteractMultipart.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageSendVisualFlagFromServer.class, MessageSendVisualFlagFromServer::write, MessageSendVisualFlagFromServer::read, MessageSendVisualFlagFromServer.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageSetPupfishChunkOnClient.class, MessageSetPupfishChunkOnClient::write, MessageSetPupfishChunkOnClient::read, MessageSetPupfishChunkOnClient.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageUpdateTransmutablesToDisplay.class, MessageUpdateTransmutablesToDisplay::write, MessageUpdateTransmutablesToDisplay::read, MessageUpdateTransmutablesToDisplay.Handler::handle);
        registerMessage(packetsRegistered++, packetsRegistered++, MessageTransmuteFromMenu.class, MessageTransmuteFromMenu::write, MessageTransmuteFromMenu::read, MessageTransmuteFromMenu.Handler::handle);
    }

    private void setupClient() {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
                NETWORK_WRAPPER.initClientListener();
                PROXY.clientInit();
            });
        }
    }

}
