package com.github.alexthe666.alexsmobs.client;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import com.github.alexthe666.alexsmobs.client.model.layered.AMModelLayers;
import com.github.alexthe666.alexsmobs.client.render.AMItemstackRenderer;
import com.github.alexthe666.alexsmobs.client.render.item.CustomArmorRenderProperties;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.message.MessageCrowDismount;
import com.github.alexthe666.alexsmobs.message.MessageCrowMountPlayer;
import com.github.alexthe666.alexsmobs.message.MessageHurtMultipart;
import com.github.alexthe666.alexsmobs.message.MessageInteractMultipart;
import com.github.alexthe666.alexsmobs.message.MessageKangarooEat;
import com.github.alexthe666.alexsmobs.message.MessageKangarooInventorySync;
import com.github.alexthe666.alexsmobs.message.MessageMosquitoDismount;
import com.github.alexthe666.alexsmobs.message.MessageMosquitoMountPlayer;
import com.github.alexthe666.alexsmobs.message.MessageMungusBiomeChange;
import com.github.alexthe666.alexsmobs.message.MessageSendVisualFlagFromServer;
import com.github.alexthe666.alexsmobs.message.MessageSetPupfishChunkOnClient;
import com.github.alexthe666.alexsmobs.message.MessageSyncEntityPos;
import com.github.alexthe666.alexsmobs.message.MessageTarantulaHawkSting;
import com.github.alexthe666.alexsmobs.message.MessageUpdateCapsid;
import com.github.alexthe666.alexsmobs.message.MessageUpdateTransmutablesToDisplay;
import io.github.fabricators_of_create.porting_lib.item.client.ItemClientHooks;
import io.github.fabricators_of_create.porting_lib.item.extensions.ArmorTextureItem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;

public class AlexsMobsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AlexsMobs.PROXY.clientInit();
        AMModelLayers.register();

        ArmorRenderer.register((matrices, vertexConsumers, stack, entity, slot, light, contextModel) -> {
            var model = CustomArmorRenderProperties.getHumanoidArmorModel(entity, stack, slot, contextModel);
            if (stack.getItem() instanceof ArmorItem armorItem) {
                var material = armorItem.getMaterial().value();
                for (ArmorMaterial.Layer layer : material.layers()) {
                    boolean inner = slot == EquipmentSlot.LEGS;
                    ResourceLocation texture = ItemClientHooks.getArmorTexture(entity, stack, layer, inner, slot);
                    ArmorRenderer.renderPart(matrices, vertexConsumers, light, stack, model, texture);
                }
            }
        }, AMItemRegistry.TARANTULA_HAWK_ELYTRA, AMItemRegistry.ROADDRUNNER_BOOTS, AMItemRegistry.MOOSE_HEADGEAR, AMItemRegistry.FRONTIER_CAP, AMItemRegistry.FEDORA, AMItemRegistry.SPIKED_TURTLE_SHELL,
            AMItemRegistry.SOMBRERO, AMItemRegistry.FROSTSTALKER_HELMET, AMItemRegistry.ROCKY_CHESTPLATE, AMItemRegistry.FLYING_FISH_BOOTS, AMItemRegistry.NOVELTY_HAT, AMItemRegistry.UNSETTLING_KIMONO);

        BuiltinItemRendererRegistry.INSTANCE.register(AMItemRegistry.SHIELD_OF_THE_DEEP, AMItemstackRenderer.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(AMItemRegistry.MYSTERIOUS_WORM, AMItemstackRenderer.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(AMItemRegistry.FALCONRY_GLOVE, AMItemstackRenderer.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(AMItemRegistry.VINE_LASSO, AMItemstackRenderer.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(AMItemRegistry.SKELEWAG_SWORD, AMItemstackRenderer.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(AMBlockRegistry.TRANSMUTATION_TABLE, AMItemstackRenderer.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(AMItemRegistry.SHATTERED_DIMENSIONAL_CARVER, AMItemstackRenderer.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(AMItemRegistry.STINK_RAY, AMItemstackRenderer.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(AMBlockRegistry.END_PIRATE_ANCHOR, AMItemstackRenderer.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(AMBlockRegistry.END_PIRATE_ANCHOR_WINCH, AMItemstackRenderer.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(AMBlockRegistry.END_PIRATE_SHIP_WHEEL, AMItemstackRenderer.INSTANCE);
        BuiltinItemRendererRegistry.INSTANCE.register(AMItemRegistry.TAB_ICON, AMItemstackRenderer.INSTANCE);

        registerPayloads();
    }

    private void registerPayloads() {
        ClientPlayNetworking.registerGlobalReceiver(MessageHurtMultipart.TYPE, MessageHurtMultipart::handle);
        ClientPlayNetworking.registerGlobalReceiver(MessageInteractMultipart.TYPE, MessageInteractMultipart::handle);
        ClientPlayNetworking.registerGlobalReceiver(MessageCrowDismount.TYPE, MessageCrowDismount::handle);
        ClientPlayNetworking.registerGlobalReceiver(MessageCrowMountPlayer.TYPE, MessageCrowMountPlayer::handle);
        ClientPlayNetworking.registerGlobalReceiver(MessageMosquitoDismount.TYPE, MessageMosquitoDismount::handle);
        ClientPlayNetworking.registerGlobalReceiver(MessageMosquitoMountPlayer.TYPE, MessageMosquitoMountPlayer::handle);
        ClientPlayNetworking.registerGlobalReceiver(MessageKangarooEat.TYPE, MessageKangarooEat::handle);
        ClientPlayNetworking.registerGlobalReceiver(MessageKangarooInventorySync.TYPE, MessageKangarooInventorySync::handle);
        ClientPlayNetworking.registerGlobalReceiver(MessageSyncEntityPos.TYPE, MessageSyncEntityPos::handle);
        ClientPlayNetworking.registerGlobalReceiver(MessageSendVisualFlagFromServer.TYPE, MessageSendVisualFlagFromServer::handle);
        ClientPlayNetworking.registerGlobalReceiver(MessageSetPupfishChunkOnClient.TYPE, MessageSetPupfishChunkOnClient::handle);
        ClientPlayNetworking.registerGlobalReceiver(MessageTarantulaHawkSting.TYPE, MessageTarantulaHawkSting::handle);
        ClientPlayNetworking.registerGlobalReceiver(MessageMungusBiomeChange.TYPE, MessageMungusBiomeChange::handle);
        ClientPlayNetworking.registerGlobalReceiver(MessageUpdateCapsid.TYPE, MessageUpdateCapsid::handle);
        ClientPlayNetworking.registerGlobalReceiver(MessageUpdateTransmutablesToDisplay.TYPE, MessageUpdateTransmutablesToDisplay::handle);
    }
}
