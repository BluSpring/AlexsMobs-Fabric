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
import com.github.alexthe666.alexsmobs.entity.util.RockyChestplateUtil;
import com.github.alexthe666.alexsmobs.entity.util.VineLassoUtil;
import com.github.alexthe666.alexsmobs.fabric.event.ComputeCameraAnglesCallback;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.item.ItemDimensionalCarver;
import com.github.alexthe666.alexsmobs.message.MessageUpdateEagleControls;
import com.github.alexthe666.alexsmobs.misc.AMTagRegistry;
import com.github.alexthe666.alexsmobs.mixin.*;
import com.github.alexthe666.citadel.client.event.EventGetFluidRenderType;
import com.github.alexthe666.citadel.client.event.EventGetOutlineColor;
import com.github.alexthe666.citadel.client.event.EventGetStarBrightness;
import com.github.alexthe666.citadel.client.event.EventPosePlayerHand;
import com.google.common.base.MoreObjects;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Vector3f;
import dev.architectury.event.EventResult;
import io.github.fabricators_of_create.porting_lib.event.client.FogEvents;
import io.github.fabricators_of_create.porting_lib.event.client.LivingEntityRenderEvents;
import io.github.fabricators_of_create.porting_lib.event.client.OverlayRenderCallback;
import io.github.fabricators_of_create.porting_lib.event.client.RenderHandCallback;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.EntityHitResult;

public class ClientEvents {

    private static final ResourceLocation ROCKY_CHESTPLATE_TEXTURE = new ResourceLocation("alexsmobs:textures/armor/rocky_chestplate.png");
    private static final ModelRockyChestplateRolling ROCKY_CHESTPLATE_MODEL = new ModelRockyChestplateRolling();

    private boolean previousLavaVision = false;
    private LiquidBlockRenderer previousFluidRenderer;
    public long lastStaticTick = -1;
    public static int renderStaticScreenFor = 0;

    public ClientEvents() {
        EventGetOutlineColor.EVENT.register(this::onOutlineEntityColor);
        EventGetStarBrightness.EVENT.register(this::onGetStarBrightness);
        FogEvents.SET_COLOR.register((data, partialTicks) -> {
            onFogColor(data);
        });
        FogEvents.RENDER_FOG.register(((mode, type, camera, partialTick, renderDistance, nearDistance, farDistance, shape, fogData) -> {
            onFogDensity(camera, fogData);
            return false;
        }));
        LivingEntityRenderEvents.PRE.register(((entity, renderer, partialRenderTick, matrixStack, buffers, light) -> {
            return onPreRenderEntity(entity, matrixStack, partialRenderTick, renderer, buffers, light);
        }));
        LivingEntityRenderEvents.POST.register(((entity, renderer, partialRenderTick, matrixStack, buffers, light) -> {
            onPostRenderEntity(entity, renderer, partialRenderTick, matrixStack, buffers, light);
        }));

        EventPosePlayerHand.EVENT.register(this::onPoseHand);
        RenderHandCallback.EVENT.register((event) -> {
            onRenderHand(event);
        });
        WorldRenderEvents.LAST.register((context) -> {
            onRenderWorldLastEvent(context);
        });

        EventGetFluidRenderType.EVENT.register(this::onGetFluidRenderType);

        ClientTickEvents.START_CLIENT_TICK.register(this::clientTick);

        ComputeCameraAnglesCallback.EVENT.register(this::onCameraSetup);

        OverlayRenderCallback.EVENT.register((stack, partialTicks, window, type) -> {
            onPostGameOverlay(stack, partialTicks, window, type);

            return false;
        });
    }

    public EventResult onOutlineEntityColor(EventGetOutlineColor event) {
        var result = EventResult.pass();

        if(event.getEntityIn() instanceof Enemy && AlexsMobs.PROXY.getSingingBlueJayId() != -1){
            Entity entity = event.getEntityIn().level.getEntity(AlexsMobs.PROXY.getSingingBlueJayId());
            if(entity instanceof EntityBlueJay jay && jay.isAlive() && jay.isMakingMonstersBlue()){
                event.setColor(0X4B95FE);
                result = EventResult.interruptTrue();
            }
        }
        if (event.getEntityIn() instanceof ItemEntity && ((ItemEntity) event.getEntityIn()).getItem().is(AMTagRegistry.VOID_WORM_DROPS)){
            int fromColor = 0;
            int toColor = 0X21E5FF;
            float startR = (float) (fromColor >> 16 & 255) / 255.0F;
            float startG = (float) (fromColor >> 8 & 255) / 255.0F;
            float startB = (float) (fromColor & 255) / 255.0F;
            float endR = (float) (toColor >> 16 & 255) / 255.0F;
            float endG = (float) (toColor >> 8 & 255) / 255.0F;
            float endB = (float) (toColor & 255) / 255.0F;
            float f = (float) (Math.cos(0.4F * (event.getEntityIn().tickCount + Minecraft.getInstance().getFrameTime())) + 1.0F) * 0.5F;
            float r = (endR - startR) * f + startR;
            float g = (endG - startG) * f + startG;
            float b = (endB - startB) * f + startB;
            int j = ((((int) (r * 255)) & 0xFF) << 16) |
                    ((((int) (g * 255)) & 0xFF) << 8) |
                    ((((int) (b * 255)) & 0xFF) << 0);
            event.setColor(j);
            result = EventResult.interruptTrue();
        }

        return result;
    }

    @Environment(EnvType.CLIENT)
    public EventResult onGetStarBrightness(EventGetStarBrightness event) {
        if (Minecraft.getInstance().player.hasEffect(AMEffectRegistry.POWER_DOWN.get())) {
            if (Minecraft.getInstance().player.getEffect(AMEffectRegistry.POWER_DOWN.get()) != null) {
                MobEffectInstance instance = Minecraft.getInstance().player.getEffect(AMEffectRegistry.POWER_DOWN.get());
                EffectPowerDown powerDown = (EffectPowerDown) instance.getEffect();
                int duration = instance.getDuration();
                float partialTicks = Minecraft.getInstance().getFrameTime();
                float f = (Math.min(powerDown.getActiveTime(), duration) + partialTicks) * 0.1F;
                event.setBrightness(0);
                return EventResult.interruptTrue();
            }

        }

        return EventResult.pass();
    }

    @Environment(EnvType.CLIENT)
    public void onFogColor(FogEvents.ColorData event) {
        if (Minecraft.getInstance().player.hasEffect(AMEffectRegistry.POWER_DOWN.get())) {
            if (Minecraft.getInstance().player.getEffect(AMEffectRegistry.POWER_DOWN.get()) != null) {
                event.setBlue(0);
                event.setRed(0);
                event.setGreen(0);
            }

        }
    }

    @Environment(EnvType.CLIENT)
    public void onFogDensity(Camera camera, FogEvents.FogData data) {
        FogType fogType = camera.getFluidInCamera();
        if (Minecraft.getInstance().player.hasEffect(AMEffectRegistry.LAVA_VISION.get()) && fogType == FogType.LAVA) {
            RenderSystem.setShaderFogStart(-8.0F);
            RenderSystem.setShaderFogEnd(50.0F);
        }
        if (Minecraft.getInstance().player.hasEffect(AMEffectRegistry.POWER_DOWN.get()) && fogType == FogType.NONE) {
            if (Minecraft.getInstance().player.getEffect(AMEffectRegistry.POWER_DOWN.get()) != null) {
                float initEnd = data.getFarPlaneDistance();
                MobEffectInstance instance = Minecraft.getInstance().player.getEffect(AMEffectRegistry.POWER_DOWN.get());
                EffectPowerDown powerDown = (EffectPowerDown) instance.getEffect();
                int duration = instance.getDuration();
                float partialTicks = Minecraft.getInstance().getFrameTime();
                float f = Math.min(20, (Math.min(powerDown.getActiveTime() + partialTicks, duration + partialTicks))) * 0.05F;
                RenderSystem.setShaderFogStart(-8.0F);
                float f1 = 8.0F + (1 - f) * Math.max(0, initEnd - 8.0F);
                RenderSystem.setShaderFogEnd(f1);
            }

        }
    }

    @Environment(EnvType.CLIENT)
    public boolean onPreRenderEntity(LivingEntity entity, PoseStack poseStack, float partialTick, LivingEntityRenderer<?, ?> renderer, MultiBufferSource bufferSource, int packedLight) {
        var isCancelled = false;
        if (RockyChestplateUtil.isRockyRolling(entity)) {
            isCancelled = true;
            poseStack.pushPose();
            float limbSwing = entity.animationPosition - entity.animationSpeed * (1.0F - partialTick);
            float limbSwingAmount = Mth.lerp(partialTick, entity.animationSpeedOld, entity.animationSpeed);
            float yRot = entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTick;
            float roll = entity.walkDistO + (entity.walkDist - entity.walkDistO) * partialTick;
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(bufferSource, RenderType.armorCutoutNoCull(ROCKY_CHESTPLATE_TEXTURE), false, entity.getItemBySlot(EquipmentSlot.CHEST).hasFoil());
            poseStack.translate(0.0D, entity.getBbHeight() - entity.getBbHeight() * 0.5F, 0.0D);
            poseStack.mulPose(Vector3f.YN.rotationDegrees(180F + yRot));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(100F * roll));
            ROCKY_CHESTPLATE_MODEL.setupAnim(entity, limbSwing, limbSwingAmount, entity.tickCount + partialTick, 0, 0);
            ROCKY_CHESTPLATE_MODEL.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
            LivingEntityRenderEvents.POST.invoker().afterRender(entity, renderer, partialTick, poseStack, bufferSource, packedLight);
            return isCancelled;
        }
        if (entity instanceof WanderingTrader && entity.getType() == EntityType.WANDERING_TRADER) {
            if (entity.getVehicle() instanceof EntityElephant) {
                if (!(renderer.getModel() instanceof ModelWanderingVillagerRider)) {
                    ((LivingEntityRendererAccessor) renderer).setModel(new ModelWanderingVillagerRider(Minecraft.getInstance().getEntityModels().bakeLayer(AMModelLayers.SITTING_WANDERING_VILLAGER)));
                }
            }
        }
        if (entity.hasEffect(AMEffectRegistry.CLINGING.get()) && entity.getEyeHeight() < entity.getBbHeight() * 0.45F || entity.hasEffect(AMEffectRegistry.DEBILITATING_STING.get()) && entity.getMobType() == MobType.ARTHROPOD && entity.getBbWidth() > entity.getBbHeight()) {
            poseStack.pushPose();
            poseStack.translate(0.0D, entity.getBbHeight() + 0.1F, 0.0D);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            entity.yBodyRotO = -entity.yBodyRotO;
            entity.yBodyRot = -entity.yBodyRot;
            entity.yHeadRotO = -entity.yHeadRotO;
            entity.yHeadRot = -entity.yHeadRot;
        }
        if (entity.hasEffect(AMEffectRegistry.ENDER_FLU.get())) {
            poseStack.pushPose();
            poseStack.mulPose(Vector3f.YP.rotationDegrees((float) (Math.cos((double) entity.tickCount * 7F) * Math.PI * (double) 1.2F)));
            float vibrate = 0.05F;
            poseStack.translate((entity.getRandom().nextFloat() - 0.5F) * vibrate, (entity.getRandom().nextFloat() - 0.5F) * vibrate, (entity.getRandom().nextFloat() - 0.5F) * vibrate);
        }
        
        return isCancelled;
    }

    @Environment(EnvType.CLIENT)
    public void onPostRenderEntity(LivingEntity entity, LivingEntityRenderer<?, ?> renderer, float partialTick, PoseStack poseStack, MultiBufferSource buffers, int light) {
        if (RockyChestplateUtil.isRockyRolling(entity)) {
            return;
        }
        if (entity.hasEffect(AMEffectRegistry.ENDER_FLU.get())) {
            poseStack.popPose();
        }
        if (entity.hasEffect(AMEffectRegistry.CLINGING.get()) && entity.getEyeHeight() < entity.getBbHeight() * 0.45F || entity.hasEffect(AMEffectRegistry.DEBILITATING_STING.get()) && entity.getMobType() == MobType.ARTHROPOD && entity.getBbWidth() > entity.getBbHeight()) {
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
                RenderVineLasso.renderVine(entity, partialTick, poseStack, buffers, (LivingEntity) lassoedOwner, ((LivingEntity) lassoedOwner).getMainArm() == HumanoidArm.LEFT, 0.1F);
                poseStack.popPose();
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public EventResult onPoseHand(EventPosePlayerHand event) {
        LivingEntity player = (LivingEntity) event.getEntityIn();
        float f = Minecraft.getInstance().getFrameTime();
        boolean leftHand = false;
        boolean usingLasso = player.isUsingItem() && player.getUseItem().is(AMItemRegistry.VINE_LASSO.get());
        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == AMItemRegistry.VINE_LASSO.get()) {
            leftHand = player.getMainArm() == HumanoidArm.LEFT;
        } else if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() == AMItemRegistry.VINE_LASSO.get()) {
            leftHand = player.getMainArm() != HumanoidArm.LEFT;
        }

        var result = EventResult.pass();
        if (leftHand && event.isLeftHand() && usingLasso) {
            float swing = (float) Math.sin(player.tickCount + f) * 0.5F;
            result = EventResult.interruptTrue();
            event.getModel().leftArm.xRot = (float) Math.toRadians(-120F) + (float) Math.sin(player.tickCount + f) * 0.5F;
            event.getModel().leftArm.yRot = (float) Math.toRadians(-20F) + (float) Math.cos(player.tickCount + f) * 0.5F;
        }
        if (!leftHand && !event.isLeftHand() && usingLasso) {
            result = EventResult.interruptTrue();
            event.getModel().rightArm.xRot = (float) Math.toRadians(-120F) + (float) Math.sin(player.tickCount + f) * 0.5F;
            event.getModel().rightArm.yRot = (float) Math.toRadians(20F) - (float) Math.cos(player.tickCount + f) * 0.5F;
        }

        return result;
    }

    @Environment(EnvType.CLIENT)
    public void onRenderHand(RenderHandCallback.RenderHandEvent event) {
        if (Minecraft.getInstance().getCameraEntity() instanceof IFalconry) {
            event.setCanceled(true);
        }
        if (!Minecraft.getInstance().player.getPassengers().isEmpty() && event.getHand() == InteractionHand.MAIN_HAND) {
            Player player = Minecraft.getInstance().player;
            boolean leftHand = false;
            if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == AMItemRegistry.FALCONRY_GLOVE.get()) {
                leftHand = player.getMainArm() == HumanoidArm.LEFT;
            } else if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() == AMItemRegistry.FALCONRY_GLOVE.get()) {
                leftHand = player.getMainArm() != HumanoidArm.LEFT;
            }
            for (Entity entity : player.getPassengers()) {
                if (entity instanceof IFalconry) {
                    IFalconry falconry = (IFalconry)entity;
                    float yaw = player.yBodyRotO + (player.yBodyRot - player.yBodyRotO) * event.getPartialTicks();
                    ClientProxy.currentUnrenderedEntities.remove(entity.getUUID());
                    PoseStack matrixStackIn = event.getPoseStack();
                    matrixStackIn.pushPose();
                    matrixStackIn.scale(0.5F, 0.5F, 0.5F);
                    matrixStackIn.translate(leftHand ? -falconry.getHandOffset() : falconry.getHandOffset(), -0.6F, -1F);
                    matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(yaw));
                    if (leftHand) {
                        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
                    } else {
                        matrixStackIn.mulPose(Vector3f.YN.rotationDegrees(90));
                    }
                    renderEntity(entity, 0, 0, 0, 0, event.getPartialTicks(), matrixStackIn, event.getMultiBufferSource(), event.getPackedLight());
                    matrixStackIn.popPose();
                    ClientProxy.currentUnrenderedEntities.add(entity.getUUID());
                }
            }
        }
        if (Minecraft.getInstance().player.getUseItem().getItem() instanceof ItemDimensionalCarver && event.getItemStack().getItem() instanceof ItemDimensionalCarver) {
            PoseStack matrixStackIn = event.getPoseStack();
            matrixStackIn.pushPose();
            ItemInHandRenderer renderer = Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer();
            InteractionHand hand = MoreObjects.firstNonNull(Minecraft.getInstance().player.swingingArm, InteractionHand.MAIN_HAND);
            float f = Minecraft.getInstance().player.getAttackAnim(event.getPartialTicks());
            float f1 = Mth.lerp(event.getPartialTicks(), Minecraft.getInstance().player.xRotO, Minecraft.getInstance().player.getXRot());
            float f5 = -0.4F * Mth.sin(Mth.sqrt(f) * (float) Math.PI);
            float f6 = 0.2F * Mth.sin(Mth.sqrt(f) * ((float) Math.PI * 2F));
            float f10 = -0.2F * Mth.sin(f * (float) Math.PI);
            HumanoidArm handside = hand == InteractionHand.MAIN_HAND ? Minecraft.getInstance().player.getMainArm() : Minecraft.getInstance().player.getMainArm().getOpposite();
            boolean flag3 = handside == HumanoidArm.RIGHT;
            int l = flag3 ? 1 : -1;
            matrixStackIn.translate((float) l * f5, f6, f10);
        }
    }

    public <E extends Entity> void renderEntity(E entityIn, double x, double y, double z, float yaw, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLight) {
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
    public void onRenderNameplate(/*RenderNameTagEvent event*/ Entity entity) {
        if (Minecraft.getInstance().getCameraEntity() instanceof EntityBaldEagle && entity == Minecraft.getInstance().player) {
            if (Minecraft.getInstance().hasSingleplayerServer()) {
                //event.setResult(Event.Result.DENY);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void onRenderWorldLastEvent(WorldRenderContext context) {
        if(true){
            if (!AMConfig.shadersCompat) {
                if (Minecraft.getInstance().player.hasEffect(AMEffectRegistry.LAVA_VISION.get())) {
                    if (!previousLavaVision) {
                        previousFluidRenderer = ((BlockRenderDispatcherAccessor) Minecraft.getInstance().getBlockRenderer()).getLiquidBlockRenderer();
                        ((BlockRenderDispatcherAccessor) Minecraft.getInstance().getBlockRenderer()).setLiquidBlockRenderer(new LavaVisionFluidRenderer());
                        updateAllChunks();
                    }
                } else {
                    if (previousLavaVision) {
                        if (previousFluidRenderer != null) {
                            ((BlockRenderDispatcherAccessor) Minecraft.getInstance().getBlockRenderer()).setLiquidBlockRenderer(previousFluidRenderer);
                        }
                        updateAllChunks();
                    }
                }
                previousLavaVision = Minecraft.getInstance().player.hasEffect(AMEffectRegistry.LAVA_VISION.get());
                if (AMConfig.clingingFlipEffect) {
                    if (Minecraft.getInstance().player.hasEffect(AMEffectRegistry.CLINGING.get()) && Minecraft.getInstance().player.getEyeHeight() < Minecraft.getInstance().player.getBbHeight() * 0.45F) {
                        ((GameRendererAccessor) Minecraft.getInstance().gameRenderer).callLoadEffect(new ResourceLocation("shaders/post/flip.json"));
                    } else if (Minecraft.getInstance().gameRenderer.currentEffect() != null && Minecraft.getInstance().gameRenderer.currentEffect().getName().equals("minecraft:shaders/post/flip.json")) {
                        Minecraft.getInstance().gameRenderer.shutdownEffect();
                    }
                }
            }
            if (Minecraft.getInstance().getCameraEntity() instanceof EntityBaldEagle) {
                EntityBaldEagle eagle = (EntityBaldEagle) Minecraft.getInstance().getCameraEntity();
                LocalPlayer playerEntity = Minecraft.getInstance().player;

                if (((EntityBaldEagle) Minecraft.getInstance().getCameraEntity()).shouldHoodedReturn() || eagle.isRemoved()) {
                    Minecraft.getInstance().setCameraEntity(playerEntity);
                    Minecraft.getInstance().options.setCameraType(CameraType.values()[AlexsMobs.PROXY.getPreviousPOV()]);
                } else {
                    float rotX = Mth.wrapDegrees(playerEntity.getYRot() + playerEntity.yHeadRot);
                    float rotY = playerEntity.getXRot();
                    Entity over = null;
                    if (Minecraft.getInstance().hitResult instanceof EntityHitResult) {
                        over = ((EntityHitResult) Minecraft.getInstance().hitResult).getEntity();
                    } else {
                        Minecraft.getInstance().hitResult = null;
                    }
                    boolean loadChunks = playerEntity.level.getDayTime() % 10 == 0;
                    ((EntityBaldEagle) Minecraft.getInstance().getCameraEntity()).directFromPlayer(rotX, rotY, false, over);
                    AlexsMobs.NETWORK_WRAPPER.sendToServer(new MessageUpdateEagleControls(Minecraft.getInstance().getCameraEntity().getId(), rotX, rotY, loadChunks, over == null ? -1 : over.getId()));
                }
            }
        }
    }

    private void updateAllChunks() {
        if (((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).getViewArea() != null) {
            int length = ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).getViewArea().chunks.length;
            for (int i = 0; i < length; i++) {
                ((RenderChunkAccessor) ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).getViewArea().chunks[i]).setDirty(true);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public EventResult onGetFluidRenderType(EventGetFluidRenderType event) {
        if (Minecraft.getInstance().player.hasEffect(AMEffectRegistry.LAVA_VISION.get()) && (event.getFluidState().is(Fluids.LAVA) || event.getFluidState().is(Fluids.FLOWING_LAVA))) {
            event.setRenderType(RenderType.translucent());
            return EventResult.interruptTrue();
        }

        return EventResult.pass();
    }

    public void clientTick(Minecraft client) {
        AMItemstackRenderer.incrementTick();
    }

    public void onCameraSetup(ComputeCameraAnglesCallback event) {
        if (Minecraft.getInstance().player.getEffect(AMEffectRegistry.EARTHQUAKE.get()) != null && !Minecraft.getInstance().isPaused()) {
            int duration = Minecraft.getInstance().player.getEffect(AMEffectRegistry.EARTHQUAKE.get()).getDuration();
            float f = (Math.min(10, duration) + Minecraft.getInstance().getFrameTime()) * 0.1F;
            double intensity = f * Minecraft.getInstance().options.screenEffectScale().get();
            RandomSource rng = Minecraft.getInstance().player.getRandom();
            ((CameraAccessor) event.getCamera()).callMove(rng.nextFloat() * 0.1F * intensity, rng.nextFloat() * 0.2F * intensity, rng.nextFloat() * 0.4F * intensity);
        }
    }

    public void onPostGameOverlay(PoseStack stack, float partialTick, Window window, OverlayRenderCallback.Types type) {
            if(renderStaticScreenFor > 0){
                if (Minecraft.getInstance().player.isAlive() && lastStaticTick != Minecraft.getInstance().level.getGameTime()) {
                    renderStaticScreenFor--;
                }
                float staticLevel = (renderStaticScreenFor / 60F);
                if (type == OverlayRenderCallback.Types.CROSSHAIRS) {
                    float screenWidth = window.getScreenWidth();
                    float screenHeight = window.getScreenHeight();
                    RenderSystem.disableDepthTest();
                    RenderSystem.depthMask(false);

                    float ageInTicks = Minecraft.getInstance().level.getGameTime() + partialTick;
                    float staticIndexX = (float) Math.sin(ageInTicks * 0.2F) * 2;
                    float staticIndexY = (float) Math.cos(ageInTicks * 0.2F + 3F) * 2;
                    RenderSystem.defaultBlendFunc();
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, staticLevel);
                    RenderSystem.setShaderTexture(0, AMRenderTypes.STATIC_TEXTURE);
                    Tesselator tesselator = Tesselator.getInstance();
                    BufferBuilder bufferbuilder = tesselator.getBuilder();
                    bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                    float minU = 10 * staticIndexX * 0.125F;
                    float maxU = 10 * (0.5F + staticIndexX * 0.125F);
                    float minV = 10 * staticIndexY * 0.125F;
                    float maxV = 10 * (0.125F + staticIndexY * 0.125F);
                    bufferbuilder.vertex(0.0D, screenHeight, -190.0D).uv(minU, maxV).endVertex();
                    bufferbuilder.vertex(screenWidth, screenHeight, -190.0D).uv(maxU, maxV).endVertex();
                    bufferbuilder.vertex(screenWidth, 0.0D, -190.0D).uv(maxU, minV).endVertex();
                    bufferbuilder.vertex(0.0D, 0.0D, -190.0D).uv(minU, minV).endVertex();
                    tesselator.end();
                    RenderSystem.depthMask(true);
                    RenderSystem.enableDepthTest();
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
                lastStaticTick = Minecraft.getInstance().level.getGameTime();
        }
    }
}
