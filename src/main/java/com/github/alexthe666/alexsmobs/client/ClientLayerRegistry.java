package com.github.alexthe666.alexsmobs.client;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.client.render.layer.LayerRainbow;
import com.github.alexthe666.alexsmobs.mixin.LivingEntityRendererAccessor;
import com.google.common.collect.ImmutableList;
import io.github.fabricators_of_create.porting_lib.event.client.EntityAddedLayerCallback;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class ClientLayerRegistry {
    static {
        EntityAddedLayerCallback.EVENT.register((renderers, skinMap) -> {
            onAddLayers(renderers, skinMap);
        });
    }

    public static void init() {}

    @Environment(EnvType.CLIENT)
    public static void onAddLayers(Map<EntityType<?>, EntityRenderer<?>> renderers, Map<String, EntityRenderer<? extends Player>> skinMap) {
        List<EntityType<? extends LivingEntity>> entityTypes = ImmutableList.copyOf(
                Registry.ENTITY_TYPE.stream()
                        .filter(DefaultAttributes::hasSupplier)
                        .map(entityType -> (EntityType<? extends LivingEntity>) entityType)
                        .collect(Collectors.toList()));
        entityTypes.forEach((entityType -> {
            addLayerIfApplicable(entityType, renderers);
        }));
        for (String skinType : skinMap.keySet()){
            ((LivingEntityRendererAccessor) skinMap.get(skinType)).callAddLayer(new LayerRainbow((RenderLayerParent) skinMap.get(skinType)));
        }
    }

    private static void addLayerIfApplicable(EntityType<? extends LivingEntity> entityType, Map<EntityType<?>, EntityRenderer<?>> renderers) {
        EntityRenderer renderer = null;
        if(entityType != EntityType.ENDER_DRAGON){
            try{
                renderer = renderers.get(entityType);
            }catch (Exception e){
                AlexsMobs.LOGGER.warn("Could not apply rainbow color layer to " + Registry.ENTITY_TYPE.getKey(entityType) + ", has custom renderer that is not LivingEntityRenderer.");
            }
            if(renderer != null){
                ((LivingEntityRendererAccessor) renderer).callAddLayer(new LayerRainbow((RenderLayerParent) renderer));
            }
        }
    }
}
