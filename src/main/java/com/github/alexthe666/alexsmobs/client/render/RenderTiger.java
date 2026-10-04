package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelTiger;
import com.github.alexthe666.alexsmobs.client.render.layer.LayerTigerEyes;
import com.github.alexthe666.alexsmobs.entity.EntityTiger;
import com.github.alexthe666.alexsmobs.misc.AMBlockPos;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

import io.github.fabricators_of_create.porting_lib.event.client.LivingEntityRenderEvents;
import org.joml.Matrix4f;

import org.jetbrains.annotations.Nullable;

public class RenderTiger extends MobRenderer<EntityTiger, ModelTiger> {
    private static final ResourceLocation TEXTURE = ResourceLocation.parse("alexsmobs:textures/entity/tiger/tiger.png");
    private static final ResourceLocation TEXTURE_ANGRY = ResourceLocation
            .parse("alexsmobs:textures/entity/tiger/tiger_angry.png");
    private static final ResourceLocation TEXTURE_SLEEPING = ResourceLocation
            .parse("alexsmobs:textures/entity/tiger/tiger_sleeping.png");
    private static final ResourceLocation TEXTURE_WHITE = ResourceLocation
            .parse("alexsmobs:textures/entity/tiger/tiger_white.png");
    private static final ResourceLocation TEXTURE_ANGRY_WHITE = ResourceLocation
            .parse("alexsmobs:textures/entity/tiger/tiger_white_angry.png");
    private static final ResourceLocation TEXTURE_SLEEPING_WHITE = ResourceLocation
            .parse("alexsmobs:textures/entity/tiger/tiger_white_sleeping.png");

    public RenderTiger(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelTiger(), 0.6F);
        this.addLayer(new LayerTigerEyes(this));
    }

    @Override
    protected void scale(EntityTiger entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
    }

    // TODO Fabric: PR the rendering improvements upstream

    @Nullable
    @Override
    protected RenderType getRenderType(EntityTiger tiger, boolean b0, boolean b1, boolean b2) {
        if (tiger.isStealth()) {
            ResourceLocation resourcelocation = this.getTextureLocation(tiger);
            return RenderType.itemEntityTranslucentCull(resourcelocation);
        } else {
            return super.getRenderType(tiger, b0, b1, b2);
        }
    }

    public ResourceLocation getTextureLocation(EntityTiger entity) {
        if (entity.isSleeping()) {
            return entity.isWhite() ? TEXTURE_SLEEPING_WHITE : TEXTURE_SLEEPING;
        } else if (entity.getRemainingPersistentAngerTime() > 0) {
            return entity.isWhite() ? TEXTURE_ANGRY_WHITE : TEXTURE_ANGRY;
        } else {
            return entity.isWhite() ? TEXTURE_WHITE : TEXTURE;
        }
    }
}
