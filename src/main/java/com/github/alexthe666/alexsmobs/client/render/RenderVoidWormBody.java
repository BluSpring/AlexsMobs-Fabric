package com.github.alexthe666.alexsmobs.client.render;

import com.github.alexthe666.alexsmobs.client.model.ModelVoidWormBody;
import com.github.alexthe666.alexsmobs.client.model.ModelVoidWormTail;
import com.github.alexthe666.alexsmobs.client.render.layer.LayerVoidWormGlow;
import com.github.alexthe666.alexsmobs.entity.EntityVoidWormPart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

public class RenderVoidWormBody extends LivingEntityRenderer<EntityVoidWormPart, EntityModel<EntityVoidWormPart>> {
    private static final Identifier TEXTURE_BODY = Identifier.parse("alexsmobs:textures/entity/void_worm/void_worm_body.png");
    private static final Identifier TEXTURE_BODY_HURT = Identifier.parse("alexsmobs:textures/entity/void_worm/void_worm_body_hurt.png");
    private static final Identifier TEXTURE_BODY_GLOW = Identifier.parse("alexsmobs:textures/entity/void_worm/void_worm_body_glow.png");
    private static final Identifier TEXTURE_TAIL = Identifier.parse("alexsmobs:textures/entity/void_worm/void_worm_tail.png");
    private static final Identifier TEXTURE_TAIL_HURT = Identifier.parse("alexsmobs:textures/entity/void_worm/void_worm_tail_hurt.png");
    private static final Identifier TEXTURE_TAIL_GLOW = Identifier.parse("alexsmobs:textures/entity/void_worm/void_worm_tail_glow.png");
    private final ModelVoidWormBody bodyModel = new ModelVoidWormBody(0.0F);
    private final ModelVoidWormTail tailModel = new ModelVoidWormTail(0.0F);

    public RenderVoidWormBody(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ModelVoidWormBody(0.0F), 1F);
        this.addLayer(new LayerVoidWormGlow(this, renderManagerIn.getResourceManager(), new ModelVoidWormBody(0.0F)){
            @Override
            public Identifier getGlowTexture(LivingEntity worm){
                return ((EntityVoidWormPart)worm).isTail() ? TEXTURE_TAIL_GLOW : TEXTURE_BODY_GLOW;
            }
            @Override
            public boolean isGlowing(LivingEntity worm){
                return !((EntityVoidWormPart)worm).isHurt();
            }
            @Override
            public float getAlpha(LivingEntity livingEntity){
                EntityVoidWormPart worm = (EntityVoidWormPart) livingEntity;
                return (float) Mth.clamp((worm.getHealth() - worm.getHealthThreshold()) / (worm.getMaxHealth() - worm.getHealthThreshold()), 0, 1F);
            }
        });
    }

    @Override
    public boolean shouldRender(EntityVoidWormPart worm, Frustum camera, double camX, double camY, double camZ) {
        return worm.getPortalTicks() <= 0 && super.shouldRender(worm, camera, camX, camY, camZ);
    }

    @Override
    public Identifier getTextureLocation(EntityVoidWormPart entity) {
        if (entity.isHurt()) {
            return entity.isTail() ? TEXTURE_TAIL_HURT : TEXTURE_BODY_HURT;
        } else {
            return entity.isTail() ? TEXTURE_TAIL : TEXTURE_BODY;
        }
    }

    @Override
    protected void setupRotations(EntityVoidWormPart entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks, float tickDelta) {
        Pose pose = entityLiving.getPose();
        if (pose != Pose.SLEEPING) {
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F - entityLiving.getWormYaw(partialTicks)));
        }
        if (entityLiving.deathTime > 0) {
            float f = ((float) entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
            f = Mth.sqrt(f);
            if (f > 1.0F) {
                f = 1.0F;
            }
            matrixStackIn.mulPose(Axis.ZP.rotationDegrees(f * this.getFlipDegrees(entityLiving)));
        }

    }

    @Override
    protected void scale(EntityVoidWormPart entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
        this.model = entitylivingbaseIn.isTail() ? tailModel : bodyModel;
        matrixStackIn.scale(entitylivingbaseIn.getWormScale(), entitylivingbaseIn.getWormScale(), entitylivingbaseIn.getWormScale());
    }

    @Override
    protected boolean shouldShowName(EntityVoidWormPart entity) {
        return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
    }
}
