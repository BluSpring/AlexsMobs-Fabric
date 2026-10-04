package com.github.alexthe666.alexsmobs.client.event;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.ClientProxy;
import com.github.alexthe666.alexsmobs.client.model.ModelRockyChestplateRolling;
import com.github.alexthe666.alexsmobs.client.model.ModelWanderingVillagerRider;
import com.github.alexthe666.alexsmobs.client.model.layered.AMModelLayers;
import com.github.alexthe666.alexsmobs.client.render.AMItemstackRenderer;
import com.github.alexthe666.alexsmobs.client.render.AMRenderTypes;
import com.github.alexthe666.alexsmobs.client.render.LavaVisionFluidRenderer;
import com.github.alexthe666.alexsmobs.client.render.RenderVineLasso;
import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import com.github.alexthe666.alexsmobs.effect.EffectPowerDown;
import com.github.alexthe666.alexsmobs.entity.EntityBaldEagle;
import com.github.alexthe666.alexsmobs.entity.EntityBlueJay;
import com.github.alexthe666.alexsmobs.entity.EntityElephant;
import com.github.alexthe666.alexsmobs.entity.IFalconry;
import com.github.alexthe666.alexsmobs.entity.util.Maths;
import com.github.alexthe666.alexsmobs.entity.util.RockyChestplateUtil;
import com.github.alexthe666.alexsmobs.entity.util.VineLassoUtil;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.item.ItemDimensionalCarver;
import com.github.alexthe666.alexsmobs.message.MessageUpdateEagleControls;
import com.github.alexthe666.alexsmobs.misc.AMTagRegistry;
import com.github.alexthe666.citadel.client.event.EventGetOutlineColor;
import com.github.alexthe666.citadel.client.event.EventGetStarBrightness;
import com.github.alexthe666.citadel.client.event.EventPosePlayerHand;
import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import io.github.fabricators_of_create.porting_lib.client_events.event.client.RenderHandEvent;
import io.github.fabricators_of_create.porting_lib.client_events.event.client.ViewportEvent;
import io.github.fabricators_of_create.porting_lib.event.client.LivingEntityRenderEvents;
import io.github.fabricators_of_create.porting_lib.gui.events.RenderGuiLayerCallback;
import io.github.fabricators_of_create.porting_lib.gui.layered.VanillaGuiLayers;

import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.CameraType;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.EntityHitResult;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.util.TriState;

@Environment(EnvType.CLIENT)
public class ClientEvents {

    private static final ResourceLocation ROCKY_CHESTPLATE_TEXTURE = ResourceLocation.fromNamespaceAndPath("alexsmobs",
            "textures/armor/rocky_chestplate.png");
    private static final ModelRockyChestplateRolling ROCKY_CHESTPLATE_MODEL = new ModelRockyChestplateRolling();

    private boolean previousLavaVision = false;
    private LiquidBlockRenderer previousFluidRenderer;
    public long lastStaticTick = -1;
    public static int renderStaticScreenFor = 0;

    public void onOutlineEntityColor(EventGetOutlineColor event) {
        if (event.getEntityIn() instanceof Enemy && AlexsMobs.PROXY.getSingingBlueJayId() != -1) {
            Entity entity = event.getEntityIn().level().getEntity(AlexsMobs.PROXY.getSingingBlueJayId());
            if (entity instanceof EntityBlueJay jay && jay.isAlive() && jay.isMakingMonstersBlue()) {
                event.setColor(0X4B95FE);
                // Event allowed by default;
            }
        }
        if (event.getEntityIn() instanceof ItemEntity
                && ((ItemEntity) event.getEntityIn()).getItem().is(AMTagRegistry.VOID_WORM_DROPS)) {
            int fromColor = 0;
            int toColor = 0X21E5FF;
            float startR = (float) (fromColor >> 16 & 255) / 255.0F;
            float startG = (float) (fromColor >> 8 & 255) / 255.0F;
            float startB = (float) (fromColor & 255) / 255.0F;
            float endR = (float) (toColor >> 16 & 255) / 255.0F;
            float endG = (float) (toColor >> 8 & 255) / 255.0F;
            float endB = (float) (toColor & 255) / 255.0F;
            float f = (float) (Math.cos(0.4F * (event.getEntityIn().tickCount
                    + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false))) + 1.0F) * 0.5F;
            float r = (endR - startR) * f + startR;
            float g = (endG - startG) * f + startG;
            float b = (endB - startB) * f + startB;
            int j = ((((int) (r * 255)) & 0xFF) << 16) |
                    ((((int) (g * 255)) & 0xFF) << 8) |
                    ((((int) (b * 255)) & 0xFF) << 0);
            event.setColor(j);
            // Event allowed by default;
        }
    }

    @Environment(EnvType.CLIENT)
    public void onGetStarBrightness(EventGetStarBrightness event) {
        if (Minecraft.getInstance().player.hasEffect(AMEffectRegistry.POWER_DOWN)) {
            if (Minecraft.getInstance().player.getEffect(AMEffectRegistry.POWER_DOWN) != null) {
                MobEffectInstance instance = Minecraft.getInstance().player.getEffect(AMEffectRegistry.POWER_DOWN);
                EffectPowerDown powerDown = (EffectPowerDown) instance.getEffect();
                int duration = instance.getDuration();
                float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
                float f = (Math.min(powerDown.getActiveTime(), duration) + partialTicks) * 0.1F;
                event.setBrightness(0);
                // Event allowed by default;
            }

        }
    }

    public ClientEvents() {
        ViewportEvent.ComputeFogColor.EVENT.register(this::onFogColor);
        ViewportEvent.RenderFog.EVENT.register(this::onFogDensity);
        LivingEntityRenderEvents.PRE.register(this::onPreRenderEntity);
        LivingEntityRenderEvents.POST.register(this::onPostRenderEntity);
        RenderHandEvent.EVENT.register(this::onRenderHand);
        onRenderWorldLastEvent();
        ClientTickEvents.START_CLIENT_TICK.register(client -> clientTick());
        ViewportEvent.ComputeCameraAngles.EVENT.register(this::onCameraSetup);
        RenderGuiLayerCallback.POST.register(this::onPostGameOverlay);
        EventGetStarBrightness.EVENT.register(this::onGetStarBrightness);
        EventGetOutlineColor.EVENT.register(this::onOutlineEntityColor);
        EventPosePlayerHand.EVENT.register(this::onPoseHand);
    }

    @Environment(EnvType.CLIENT)
    public void onFogColor(ViewportEvent.ComputeFogColor event) {
        if (Minecraft.getInstance().player.hasEffect(AMEffectRegistry.POWER_DOWN)) {
            if (Minecraft.getInstance().player.getEffect(AMEffectRegistry.POWER_DOWN) != null) {
                event.setBlue(0);
                event.setRed(0);
                event.setGreen(0);
            }

        }
    }

    @Environment(EnvType.CLIENT)
    public void onFogDensity(ViewportEvent.RenderFog event) {
        FogType fogType = event.getCamera().getFluidInCamera();
        if (Minecraft.getInstance().player.hasEffect(AMEffectRegistry.LAVA_VISION) && fogType == FogType.LAVA) {
            event.setNearPlaneDistance(-8.0F);
            event.setFarPlaneDistance(50.0F);
            event.setCanceled(true);
        }
        if (Minecraft.getInstance().player.hasEffect(AMEffectRegistry.POWER_DOWN) && fogType == FogType.NONE) {
            if (Minecraft.getInstance().player.getEffect(AMEffectRegistry.POWER_DOWN) != null) {
                float initEnd = event.getFarPlaneDistance();
                MobEffectInstance instance = Minecraft.getInstance().player.getEffect(AMEffectRegistry.POWER_DOWN);
                EffectPowerDown powerDown = (EffectPowerDown) instance.getEffect().value();
                int duration = instance.getDuration();
                float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
                float f = Math.min(20, (Math.min(powerDown.getActiveTime() + partialTicks, duration + partialTicks)))
                        * 0.05F;
                event.setNearPlaneDistance(-8.0F);
                float f1 = 8.0F + (1 - f) * Math.max(0, initEnd - 8.0F);
                event.setFarPlaneDistance(f1);
                event.setCanceled(true);
            }

        }
    }

    @Environment(EnvType.CLIENT)
    public boolean onPreRenderEntity(LivingEntity entity, LivingEntityRenderer livingRenderer, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light) {
        if (RockyChestplateUtil.isRockyRolling(entity)) {
            poseStack.pushPose();
            float limbSwing = entity.walkAnimation.position()
                    - entity.walkAnimation.speed() * (1.0F - partialTick);
            float limbSwingAmount = entity.walkAnimation.speed(light);
            float yRot = entity.yBodyRotO
                    + (entity.yBodyRot - entity.yBodyRotO) * partialTick;
            float roll = entity.walkDistO
                    + (entity.walkDist - entity.walkDistO) * partialTick;
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffers,
                    RenderType.armorCutoutNoCull(ROCKY_CHESTPLATE_TEXTURE),
                    entity.getItemBySlot(EquipmentSlot.CHEST).hasFoil());
            poseStack.translate(0.0D,
                    entity.getBbHeight() - entity.getBbHeight() * 0.5F, 0.0D);
            poseStack.mulPose(Axis.YN.rotationDegrees(180F + yRot));
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            poseStack.mulPose(Axis.XP.rotationDegrees(100F * roll));
            ROCKY_CHESTPLATE_MODEL.setupAnim(entity, limbSwing, limbSwingAmount,
                    entity.tickCount + partialTick, 0, 0);
            ROCKY_CHESTPLATE_MODEL.renderToBuffer(poseStack, vertexconsumer, light,
                    OverlayTexture.NO_OVERLAY, -1);
            poseStack.popPose();
            LivingEntityRenderEvents.POST.invoker().afterRender(entity, livingRenderer, partialTick, poseStack, buffers, light);
            return true;
        }
        if (entity instanceof WanderingTrader
                && entity.getType() == EntityType.WANDERING_TRADER) {
            if (entity.getVehicle() instanceof EntityElephant) {
                if (true) {
                    if (!(livingRenderer.model instanceof ModelWanderingVillagerRider)) {
                        livingRenderer.model = new ModelWanderingVillagerRider(
                            Minecraft.getInstance().getEntityModels().bakeLayer(AMModelLayers.SITTING_WANDERING_VILLAGER));
                    }
                }
            }
        }
        if (entity.hasEffect(AMEffectRegistry.CLINGING)
                && entity.getEyeHeight() < entity.getBbHeight() * 0.45F
                || entity.hasEffect(AMEffectRegistry.DEBILITATING_STING)
                        && entity.getType().is(net.minecraft.tags.EntityTypeTags.ARTHROPOD)
                        && entity.getBbWidth() > entity.getBbHeight()) {
            poseStack.pushPose();
            poseStack.translate(0.0D, entity.getBbHeight() + 0.1F, 0.0D);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            entity.yBodyRotO = -entity.yBodyRotO;
            entity.yBodyRot = -entity.yBodyRot;
            entity.yHeadRotO = -entity.yHeadRotO;
            entity.yHeadRot = -entity.yHeadRot;
        }
        if (entity.hasEffect(AMEffectRegistry.ENDER_FLU)) {
            poseStack.pushPose();
            poseStack.mulPose(Axis.YP.rotationDegrees(
                    (Mth.cos(entity.tickCount * 7F) * Mth.PI * 1.2F)));
            float vibrate = 0.05F;
            poseStack.translate((entity.getRandom().nextFloat() - 0.5F) * vibrate,
                    (entity.getRandom().nextFloat() - 0.5F) * vibrate,
                    (entity.getRandom().nextFloat() - 0.5F) * vibrate);
        }

        return false;
    }

    @Environment(EnvType.CLIENT)
    public void onPostRenderEntity(LivingEntity entity, LivingEntityRenderer<?, ?> livingRenderer, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light) {
        if (RockyChestplateUtil.isRockyRolling(entity)) {
            return;
        }
        if (entity.hasEffect(AMEffectRegistry.ENDER_FLU)) {
            poseStack.popPose();
        }
        if (entity.hasEffect(AMEffectRegistry.CLINGING)
                && entity.getEyeHeight() < entity.getBbHeight() * 0.45F
                || entity.hasEffect(AMEffectRegistry.DEBILITATING_STING)
                        && entity.getType().is(net.minecraft.tags.EntityTypeTags.ARTHROPOD)
                        && entity.getBbWidth() > entity.getBbHeight()) {
            poseStack.popPose();
            entity.yBodyRotO = -entity.yBodyRotO;
            entity.yBodyRot = -entity.yBodyRot;
            entity.yHeadRotO = -entity.yHeadRotO;
            entity.yHeadRot = -entity.yHeadRot;
        }
        if (VineLassoUtil.hasLassoData(entity) && !(entity instanceof Player)) {
            Entity lassoedOwner = VineLassoUtil.getLassoedTo(entity);
            if (lassoedOwner instanceof LivingEntity && lassoedOwner != entity) {
                double d0 = Mth.lerp(partialTick, entity.xOld, entity.getX());
                double d1 = Mth.lerp(partialTick, entity.yOld, entity.getY());
                double d2 = Mth.lerp(partialTick, entity.zOld, entity.getZ());
                poseStack.pushPose();
                poseStack.translate(-d0, -d1, -d2);
                RenderVineLasso.renderVine(entity, partialTick, poseStack,
                        buffers, (LivingEntity) lassoedOwner,
                        ((LivingEntity) lassoedOwner).getMainArm() == HumanoidArm.LEFT, 0.1F);
                poseStack.popPose();
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void onPoseHand(EventPosePlayerHand event) {
        LivingEntity player = (LivingEntity) event.getEntityIn();
        float f = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
        boolean leftHand = false;
        boolean usingLasso = player.isUsingItem() && player.getUseItem().is(AMItemRegistry.VINE_LASSO);
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == AMItemRegistry.VINE_LASSO) {
            leftHand = player.getMainArm() == HumanoidArm.LEFT;
        } else if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() == AMItemRegistry.VINE_LASSO) {
            leftHand = player.getMainArm() != HumanoidArm.LEFT;
        }
        if (leftHand && event.isLeftHand() && usingLasso) {
            // float swing = (float) Math.sin(player.tickCount + f) * 0.5F;
            // Event allowed by default;
            event.getModel().leftArm.xRot = Maths.rad(-120F) + Mth.sin(player.tickCount + f) * 0.5F;
            event.getModel().leftArm.yRot = Maths.rad(-20F) + Mth.cos(player.tickCount + f) * 0.5F;
        }
        if (!leftHand && !event.isLeftHand() && usingLasso) {
            // Event allowed by default;
            event.getModel().rightArm.xRot = Maths.rad(-120F) + Mth.sin(player.tickCount + f) * 0.5F;
            event.getModel().rightArm.yRot = Maths.rad(20F) - Mth.cos(player.tickCount + f) * 0.5F;
        }
    }

    @Environment(EnvType.CLIENT)
    public void onRenderHand(RenderHandEvent event) {
        if (Minecraft.getInstance().getCameraEntity() instanceof IFalconry) {
            event.setCanceled(true);
        }
        if (!Minecraft.getInstance().player.getPassengers().isEmpty() && event.getHand() == InteractionHand.MAIN_HAND) {
            Player player = Minecraft.getInstance().player;
            boolean leftHand = false;
            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == AMItemRegistry.FALCONRY_GLOVE) {
                leftHand = player.getMainArm() == HumanoidArm.LEFT;
            } else if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() == AMItemRegistry.FALCONRY_GLOVE) {
                leftHand = player.getMainArm() != HumanoidArm.LEFT;
            }
            for (Entity entity : player.getPassengers()) {
                if (entity instanceof IFalconry falconry) {
                    float yaw = player.yBodyRotO + (player.yBodyRot - player.yBodyRotO) * event.getPartialTick();
                    ClientProxy.currentUnrenderedEntities.remove(entity.getUUID());
                    PoseStack matrixStackIn = event.getPoseStack();
                    matrixStackIn.pushPose();
                    matrixStackIn.scale(0.5F, 0.5F, 0.5F);
                    matrixStackIn.translate(leftHand ? -falconry.getHandOffset() : falconry.getHandOffset(), -0.6F,
                            -1F);
                    matrixStackIn.mulPose(Axis.YP.rotationDegrees(yaw));
                    if (leftHand) {
                        matrixStackIn.mulPose(Axis.YP.rotationDegrees(90));
                    } else {
                        matrixStackIn.mulPose(Axis.YN.rotationDegrees(90));
                    }
                    renderEntity(entity, 0, 0, 0, 0, event.getPartialTick(), matrixStackIn,
                            event.getMultiBufferSource(), event.getPackedLight());
                    matrixStackIn.popPose();
                    ClientProxy.currentUnrenderedEntities.add(entity.getUUID());
                }
            }
        }
        if (Minecraft.getInstance().player.getUseItem().getItem() instanceof ItemDimensionalCarver
                && event.getItemStack().getItem() instanceof ItemDimensionalCarver) {
            PoseStack matrixStackIn = event.getPoseStack();
            matrixStackIn.pushPose();
            ItemInHandRenderer renderer = Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer();
            InteractionHand hand = MoreObjects.firstNonNull(Minecraft.getInstance().player.swingingArm,
                    InteractionHand.MAIN_HAND);
            float f = Minecraft.getInstance().player.getAttackAnim(event.getPartialTick());
            // float f1 = Mth.lerp(event.getPartialTick(),
            // Minecraft.getInstance().player.xRotO,
            // Minecraft.getInstance().player.getXRot());
            float f5 = -0.4F * Mth.sin(Mth.sqrt(f) * Mth.PI);
            float f6 = 0.2F * Mth.sin(Mth.sqrt(f) * Mth.TWO_PI);
            float f10 = -0.2F * Mth.sin(f * Mth.PI);
            HumanoidArm handside = hand == InteractionHand.MAIN_HAND ? Minecraft.getInstance().player.getMainArm()
                    : Minecraft.getInstance().player.getMainArm().getOpposite();
            boolean flag3 = handside == HumanoidArm.RIGHT;
            int l = flag3 ? 1 : -1;
            matrixStackIn.translate((float) l * f5, f6, f10);
        }
    }

    public <E extends Entity> void renderEntity(E entityIn, double x, double y, double z, float yaw, float partialTicks,
            PoseStack matrixStack, MultiBufferSource bufferIn, int packedLight) {
        EntityRenderer<? super E> render = null;
        EntityRenderDispatcher manager = Minecraft.getInstance().getEntityRenderDispatcher();
        try {
            render = manager.getRenderer(entityIn);

            if (render != null) {
                try {
                    render.render(entityIn, yaw, partialTicks, matrixStack, bufferIn, packedLight);
                } catch (Throwable throwable1) {
                    throw new ReportedException(CrashReport.forThrowable(throwable1, "Rendering entity in world"));
                }
            }
        } catch (Throwable throwable3) {
            CrashReport crashreport = CrashReport.forThrowable(throwable3, "Rendering entity in world");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Entity being rendered");
            entityIn.fillCrashReportCategory(crashreportcategory);
            CrashReportCategory crashreportcategory1 = crashreport.addCategory("Renderer details");
            crashreportcategory1.setDetail("Assigned renderer", render);
            crashreportcategory1.setDetail("Rotation", Float.valueOf(yaw));
            crashreportcategory1.setDetail("Delta", Float.valueOf(partialTicks));
            throw new ReportedException(crashreport);
        }
    }

    @Environment(EnvType.CLIENT)
    public static TriState onRenderNameplate(Entity entity) {
        if (Minecraft.getInstance().getCameraEntity() instanceof EntityBaldEagle
                && entity == Minecraft.getInstance().player) {
            if (Minecraft.getInstance().hasSingleplayerServer()) {
                return TriState.FALSE;
            }
        }

        return TriState.DEFAULT;
    }

    @Environment(EnvType.CLIENT)
    public void onRenderWorldLastEvent() {
        WorldRenderEvents.AFTER_SETUP.register(context -> {
            if (!AMConfig.shadersCompat) {
                // Lava vision custom fluid rendering
                if (Minecraft.getInstance().player.hasEffect(AMEffectRegistry.LAVA_VISION)) {
                    if (!previousLavaVision) {
                        previousFluidRenderer = Minecraft.getInstance().getBlockRenderer().liquidBlockRenderer;
                        Minecraft.getInstance().getBlockRenderer().liquidBlockRenderer = new LavaVisionFluidRenderer();
                        updateAllChunks();
                    }
                } else {
                    if (previousLavaVision) {
                        if (previousFluidRenderer != null) {
                            Minecraft.getInstance().getBlockRenderer().liquidBlockRenderer = previousFluidRenderer;
                        }
                        updateAllChunks();
                    }
                }
                previousLavaVision = Minecraft.getInstance().player.hasEffect(AMEffectRegistry.LAVA_VISION);
                if (AMConfig.clingingFlipEffect) {
                    if (Minecraft.getInstance().player.hasEffect(AMEffectRegistry.CLINGING)
                            && Minecraft.getInstance().player
                                    .getEyeHeight() < Minecraft.getInstance().player.getBbHeight() * 0.45F) {
                        Minecraft.getInstance().gameRenderer
                                .loadEffect(ResourceLocation.withDefaultNamespace("shaders/post/flip.json"));
                    } else if (Minecraft.getInstance().gameRenderer.currentEffect() != null
                            && Minecraft.getInstance().gameRenderer.currentEffect().getName()
                                    .equals("minecraft:shaders/post/flip.json")) {
                        Minecraft.getInstance().gameRenderer.shutdownEffect();
                    }
                }
            }
            if (Minecraft.getInstance().getCameraEntity() instanceof EntityBaldEagle) {
                EntityBaldEagle eagle = (EntityBaldEagle) Minecraft.getInstance().getCameraEntity();
                LocalPlayer playerEntity = Minecraft.getInstance().player;

                if (((EntityBaldEagle) Minecraft.getInstance().getCameraEntity()).shouldHoodedReturn()
                        || eagle.isRemoved()) {
                    Minecraft.getInstance().setCameraEntity(playerEntity);
                    Minecraft.getInstance().options
                            .setCameraType(CameraType.values()[AlexsMobs.PROXY.getPreviousPOV()]);
                } else {
                    float rotX = Mth.wrapDegrees(playerEntity.getYRot() + playerEntity.yHeadRot);
                    float rotY = playerEntity.getXRot();
                    Entity over = null;
                    if (Minecraft.getInstance().hitResult instanceof EntityHitResult) {
                        over = ((EntityHitResult) Minecraft.getInstance().hitResult).getEntity();
                    } else {
                        Minecraft.getInstance().hitResult = null;
                    }
                    boolean loadChunks = playerEntity.level().getDayTime() % 10 == 0;
                    ((EntityBaldEagle) Minecraft.getInstance().getCameraEntity()).directFromPlayer(rotX, rotY, false,
                            over);
                    AlexsMobs.sendMSGToServer(
                            new MessageUpdateEagleControls(Minecraft.getInstance().getCameraEntity().getId(), rotX,
                                    rotY, loadChunks, over == null ? -1 : over.getId()));
                }
            }
        });
    }

    private void updateAllChunks() {
        Minecraft.getInstance().levelRenderer.allChanged();
    }

    public void clientTick() {
        AMItemstackRenderer.incrementTick();
    }

    public void onCameraSetup(ViewportEvent.ComputeCameraAngles event) {
        if (Minecraft.getInstance().player.getEffect(AMEffectRegistry.EARTHQUAKE) != null
                && !Minecraft.getInstance().isPaused()) {
            int duration = Minecraft.getInstance().player.getEffect(AMEffectRegistry.EARTHQUAKE).getDuration();
            float f = (Math.min(10, duration) + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false))
                    * 0.1F;
            float intensity = (float) (f * Minecraft.getInstance().options.screenEffectScale().get());
            RandomSource rng = Minecraft.getInstance().player.getRandom();
            // Camera shake via rotation angles instead of protected move() method
            event.setYaw(event.getYaw() + (rng.nextFloat() - 0.5F) * 2.0F * intensity);
            event.setPitch(event.getPitch() + (rng.nextFloat() - 0.5F) * 4.0F * intensity);
            event.setRoll(event.getRoll() + (rng.nextFloat() - 0.5F) * 8.0F * intensity);
        }
    }

    public void onPostGameOverlay(GuiGraphics guiGraphics, DeltaTracker partialTick, ResourceLocation name, LayeredDraw.Layer layer) {
        if (renderStaticScreenFor > 0) {
            if (Minecraft.getInstance().player.isAlive()
                    && lastStaticTick != Minecraft.getInstance().level.getGameTime()) {
                renderStaticScreenFor--;
            }
            float staticLevel = (renderStaticScreenFor / 60F);
            if (name.equals(VanillaGuiLayers.CAMERA_OVERLAYS)) {
                float screenWidth = (float) Minecraft.getInstance().getWindow().getGuiScaledWidth();
                float screenHeight = (float) Minecraft.getInstance().getWindow().getGuiScaledHeight();
                RenderSystem.disableDepthTest();
                RenderSystem.depthMask(false);

                float ageInTicks = Minecraft.getInstance().level.getGameTime()
                        + partialTick.getGameTimeDeltaPartialTick(false);
                float staticIndexX = (float) Math.sin(ageInTicks * 0.2F) * 2;
                float staticIndexY = (float) Math.cos(ageInTicks * 0.2F + 3F) * 2;
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, staticLevel);
                RenderSystem.setShaderTexture(0, AMRenderTypes.STATIC_TEXTURE);
                Tesselator tesselator = Tesselator.getInstance();
                BufferBuilder bufferbuilder = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                float minU = 10 * staticIndexX * 0.125F;
                float maxU = 10 * (0.5F + staticIndexX * 0.125F);
                float minV = 10 * staticIndexY * 0.125F;
                float maxV = 10 * (0.125F + staticIndexY * 0.125F);
                bufferbuilder.addVertex(0.0F, screenHeight, -190.0F).setUv(minU, maxV);
                bufferbuilder.addVertex(screenWidth, screenHeight, -190.0F).setUv(maxU, maxV);
                bufferbuilder.addVertex(screenWidth, 0.0F, -190.0F).setUv(maxU, minV);
                bufferbuilder.addVertex(0.0F, 0.0F, -190.0F).setUv(minU, minV);
                BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
                RenderSystem.depthMask(true);
                RenderSystem.enableDepthTest();
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }
            lastStaticTick = Minecraft.getInstance().level.getGameTime();
        }
    }
}
