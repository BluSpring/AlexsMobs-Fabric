package com.github.alexthe666.alexsmobs.client;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.client.render.layer.LayerRainbow;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;

import java.util.List;
import java.util.stream.Collectors;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;

@Environment(EnvType.CLIENT)
public class ClientLayerRegistry {
    public static void onAddLayers() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            addLayerIfApplicable(entityType, entityRenderer, registrationHelper);
        });
        // Add rainbow layer to player renderers
        /*for (PlayerSkin.Model model : event.getSkins()){
            PlayerRenderer skin = event.getSkin(model);
            if (skin != null) {
                skin.addLayer(new LayerRainbow(skin));
            }
        }*/
    }

    @SuppressWarnings("unchecked")
    private static void addLayerIfApplicable(EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<?, ?> renderer, LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper helper) {
        if(entityType != EntityType.ENDER_DRAGON){
            helper.register(new LayerRainbow(renderer));
        }
    }
}
