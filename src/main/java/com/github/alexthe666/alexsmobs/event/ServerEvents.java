package com.github.alexthe666.alexsmobs.event;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import com.github.alexthe666.alexsmobs.client.particle.AMParticleRegistry;
import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import com.github.alexthe666.alexsmobs.entity.*;
import com.github.alexthe666.alexsmobs.entity.util.FlyingFishBootsUtil;
import com.github.alexthe666.alexsmobs.entity.util.RainbowUtil;
import com.github.alexthe666.alexsmobs.entity.util.RockyChestplateUtil;
import com.github.alexthe666.alexsmobs.entity.util.VineLassoUtil;
import com.github.alexthe666.alexsmobs.fabric.EntityItemHandlerHelper;
import com.github.alexthe666.alexsmobs.fabric.event.MobDespawnCallback;
import com.github.alexthe666.alexsmobs.fabric.event.SpecialSpawnCallback;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.item.ILeftClick;
import com.github.alexthe666.alexsmobs.item.ItemGhostlyPickaxe;
import com.github.alexthe666.alexsmobs.message.MessageSwingArm;
import com.github.alexthe666.alexsmobs.misc.AMAdvancementTriggerRegistry;
import com.github.alexthe666.alexsmobs.misc.AMTagRegistry;
import com.github.alexthe666.alexsmobs.misc.EmeraldsForItemsTrade;
import com.github.alexthe666.alexsmobs.misc.ItemsForEmeraldsTrade;
import com.github.alexthe666.alexsmobs.world.AMWorldData;
import com.github.alexthe666.alexsmobs.world.BeachedCachalotWhaleSpawner;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.*;
import dev.architectury.registry.level.entity.trade.TradeRegistry;
import io.github.fabricators_of_create.porting_lib.event.client.FieldOfViewEvents;
import io.github.fabricators_of_create.porting_lib.event.common.LivingEntityEvents;
import io.github.fabricators_of_create.porting_lib.event.common.MobEntitySetTargetCallback;
import io.github.fabricators_of_create.porting_lib.event.common.ProjectileImpactCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetExperiencePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.phys.*;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadLocalRandom;

public class ServerEvents {

    public static final UUID ALEX_UUID = UUID.fromString("71363abe-fd03-49c9-940d-aae8b8209b7c");
    public static final UUID CARRO_UUID = UUID.fromString("98905d4a-1cbc-41a4-9ded-2300404e2290");
    private static final UUID SAND_SPEED_MODIFIER = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF28E");
    private static final UUID SNEAK_SPEED_MODIFIER = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF28F");
    private static final AttributeModifier SAND_SPEED_BONUS = new AttributeModifier(SAND_SPEED_MODIFIER, "roadrunner speed bonus", 0.1F, AttributeModifier.Operation.ADDITION);
    private static final AttributeModifier SNEAK_SPEED_BONUS = new AttributeModifier(SNEAK_SPEED_MODIFIER, "frontier cap speed bonus", 0.1F, AttributeModifier.Operation.ADDITION);
    private static final Map<ServerLevel, BeachedCachalotWhaleSpawner> BEACHED_CACHALOT_WHALE_SPAWNER_MAP = new HashMap<>();
    public static List<Triple<ServerPlayer, ServerLevel, BlockPos>> teleportPlayers = new ArrayList<>();

    static {
        ServerTickEvents.END_WORLD_TICK.register(ServerEvents::onServerTick);

        UseItemCallback.EVENT.register(((player, world, hand) -> {
            var item = player.getItemInHand(hand);

            onItemUseLast(player, item);

            return InteractionResultHolder.pass(item);
        }));

        //ScaleTypes.EYE_HEIGHT.getScaleChangedEvent().register(ServerEvents::onEntityResize);

        ServerPlayConnectionEvents.JOIN.register(ServerEvents::onPlayerLoggedIn);

        InteractionEvent.CLIENT_LEFT_CLICK_AIR.register(ServerEvents::onPlayerLeftClick);

        LightningEvent.STRIKE.register(((bolt, level, pos, toStrike) -> {
            toStrike.removeIf(entity -> onStruckByLightning(bolt, level, pos, entity));
        }));

        ProjectileImpactCallback.EVENT.register(ServerEvents::onProjectileHit);

        MobDespawnCallback.EVENT.register(ServerEvents::onEntityDespawnAttempt);
    }

    public static void onServerTick(Level level) {
        if (!level.isClientSide && level instanceof ServerLevel serverWorld) {
            BEACHED_CACHALOT_WHALE_SPAWNER_MAP.computeIfAbsent(serverWorld,
                k -> new BeachedCachalotWhaleSpawner(serverWorld));
            BeachedCachalotWhaleSpawner spawner = BEACHED_CACHALOT_WHALE_SPAWNER_MAP.get(serverWorld);
            spawner.tick();

            for (final var triple : teleportPlayers) {
                ServerPlayer player = triple.getLeft();
                ServerLevel endpointWorld = triple.getMiddle();
                BlockPos endpoint = triple.getRight();
                int heightFromMap = endpointWorld.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, endpoint.getX(), endpoint.getZ());
                endpoint = new BlockPos(endpoint.getX(), Math.max(heightFromMap, endpoint.getY()), endpoint.getZ());
                player.teleportTo(endpointWorld, endpoint.getX() + 0.5D, endpoint.getY() + 0.5D, endpoint.getZ() + 0.5D, player.getYRot(), player.getXRot());
                ChunkPos chunkpos = new ChunkPos(endpoint);
                endpointWorld.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkpos, 1, player.getId());
                player.connection.send(new ClientboundSetExperiencePacket(player.experienceProgress, player.totalExperience, player.experienceLevel));

            }
            teleportPlayers.clear();
        }
        AMWorldData data = AMWorldData.get(level);
        if (data != null) {
            data.tickPupfish();
        }
    }

    protected static BlockHitResult rayTrace(Level worldIn, Player player, ClipContext.Fluid fluidMode) {
        final float x = player.getXRot();
        final float y = player.getYRot();
        Vec3 vector3d = player.getEyePosition(1.0F);
        final float f2 = Mth.cos(-y * ((float) Math.PI / 180F) - (float) Math.PI);
        final float f3 = Mth.sin(-y * ((float) Math.PI / 180F) - (float) Math.PI);
        final float f4 = -Mth.cos(-x * ((float) Math.PI / 180F));
        final float f5 = Mth.sin(-x * ((float) Math.PI / 180F));
        final float f6 = f3 * f4;
        final float f7 = f2 * f4;
        final double d0 = player.getAttribute(ReachEntityAttributes.REACH).getValue();
        Vec3 vector3d1 = vector3d.add(f6 * d0, f5 * d0, f7 * d0);
        return worldIn.clip(new ClipContext(vector3d, vector3d1, ClipContext.Block.OUTLINE, fluidMode, player));
    }


    private static final Random RAND = new Random();

    public static void onItemUseLast(Player entity, ItemStack item) {
        if (item.getItem() == Items.CHORUS_FRUIT && RAND.nextInt(3) == 0
            && entity.hasEffect(AMEffectRegistry.ENDER_FLU.get())) {
            entity.removeEffect(AMEffectRegistry.ENDER_FLU.get());
        }
    }

    /*public static void onEntityResize(ScaleData data) {
        if (data.getEntity() instanceof Player entity) {
            final var potions = entity.getActiveEffectsMap();
            if (data.getEntity().level != null && potions != null && !potions.isEmpty()
                && potions.containsKey(AMEffectRegistry.CLINGING)) {
                if (EffectClinging.isUpsideDown(entity)) {
                    float minus = data.getInitialScale() - data.getPrevScale();
                    event.setNewEyeHeight(minus);
                }
            }
        }

    }*/

    public static void onPlayerLoggedIn(ServerGamePacketListenerImpl handler, PacketSender sender, MinecraftServer server) {
        server.execute(() -> {
            var player = handler.getPlayer();

            if (AMConfig.giveBookOnStartup) {
                CompoundTag playerData = player.getExtraCustomData();
                CompoundTag data = playerData.getCompound("PlayerPersisted");
                if (data != null && !data.getBoolean("alexsmobs_has_book")) {
                    EntityItemHandlerHelper.giveItemToPlayer(player, new ItemStack(AMItemRegistry.ANIMAL_DICTIONARY.get()));
                    if (Objects.equals(player.getUUID(), ALEX_UUID)
                            || Objects.equals(player.getUUID(), CARRO_UUID)) {
                        EntityItemHandlerHelper.giveItemToPlayer(player, new ItemStack(AMItemRegistry.BEAR_DUST.get()));
                    }
                    if (Objects.equals(player, ALEX_UUID)) {
                        EntityItemHandlerHelper.giveItemToPlayer(player, new ItemStack(AMItemRegistry.NOVELTY_HAT.get()));
                    }
                    data.putBoolean("alexsmobs_has_book", true);
                    playerData.put("PlayerPersisted", data);
                }
            }
        });
    }

    public static void onPlayerLeftClick(Player entity, InteractionHand hand) {
        boolean flag = false;
        ItemStack leftItem = entity.getOffhandItem();
        ItemStack rightItem = entity.getMainHandItem();
        if(leftItem.getItem() instanceof ILeftClick){
            ((ILeftClick)leftItem.getItem()).onLeftClick(leftItem, entity);
            flag = true;
        }
        if(rightItem.getItem() instanceof ILeftClick){
            ((ILeftClick)rightItem.getItem()).onLeftClick(rightItem, entity);
            flag = true;
        }
        if (entity.level.isClientSide && flag) {
            AlexsMobs.sendMSGToServer(MessageSwingArm.INSTANCE);
        }
    }

    public static boolean onStruckByLightning(LightningBolt bolt, Level regularLevel, Vec3 pos, Entity entity) {
        if (!(regularLevel instanceof ServerLevel level))
            return false;

        if (entity.getType() == EntityType.SQUID && !level.isClientSide) {
            EntityGiantSquid squid = AMEntityRegistry.GIANT_SQUID.get().create(level);
            squid.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
            squid.finalizeSpawn(level, level.getCurrentDifficultyAt(squid.blockPosition()), MobSpawnType.CONVERSION, null, null);
            if (entity.hasCustomName()) {
                squid.setCustomName(entity.getCustomName());
                squid.setCustomNameVisible(entity.isCustomNameVisible());
            }
            squid.setBlue(true);
            squid.setPersistenceRequired();
            level.addFreshEntityWithPassengers(squid);
            entity.discard();

            return true;
        }

        return false;
    }

    public static boolean onProjectileHit(Projectile projectile, HitResult hit) {
        var cancelled = false;

        if (hit instanceof EntityHitResult hitResult
            && hitResult.getEntity() instanceof EntityEmu emu && !projectile.level.isClientSide) {
            if (projectile instanceof AbstractArrow arrow) {
                //fixes soft crash with vanilla
                arrow.setPierceLevel((byte) 0);
            }
            if ((emu.getAnimation() == EntityEmu.ANIMATION_DODGE_RIGHT || emu.getAnimation() == EntityEmu.ANIMATION_DODGE_LEFT) && emu.getAnimationTick() < 7) {
                cancelled = true;
            }
            if (emu.getAnimation() != EntityEmu.ANIMATION_DODGE_RIGHT && emu.getAnimation() != EntityEmu.ANIMATION_DODGE_LEFT) {
                boolean left = true;
                Vec3 arrowPos = projectile.position();
                Vec3 rightVector = emu.getLookAngle().yRot(0.5F * (float) Math.PI).add(emu.position());
                Vec3 leftVector = emu.getLookAngle().yRot(-0.5F * (float) Math.PI).add(emu.position());
                if (arrowPos.distanceTo(rightVector) < arrowPos.distanceTo(leftVector)) {
                    left = false;
                } else if (arrowPos.distanceTo(rightVector) > arrowPos.distanceTo(leftVector)) {
                    left = true;
                } else {
                    left = emu.getRandom().nextBoolean();
                }
                Vec3 vector3d2 = projectile.getDeltaMovement().yRot((float) ((left ? -0.5F : 0.5F) * Math.PI)).normalize();
                emu.setAnimation(left ? EntityEmu.ANIMATION_DODGE_LEFT : EntityEmu.ANIMATION_DODGE_RIGHT);
                emu.hasImpulse = true;
                if (!emu.horizontalCollision) {
                    emu.move(MoverType.SELF, new Vec3(vector3d2.x() * 0.25F, 0.1F, vector3d2.z() * 0.25F));
                }
                if (!projectile.level.isClientSide) {
                    if (projectile.getOwner() instanceof ServerPlayer serverPlayer) {
                        AMAdvancementTriggerRegistry.EMU_DODGE.trigger(serverPlayer);
                    }
                }
                emu.setDeltaMovement(emu.getDeltaMovement().add(vector3d2.x() * 0.5F, 0.32F, vector3d2.z() * 0.5F));
                cancelled = true;
            }
        }

        return cancelled;
    }

    public static EventResult onEntityDespawnAttempt(Mob entity) {
        if (entity.hasEffect(AMEffectRegistry.DEBILITATING_STING.get()) && entity.getEffect(AMEffectRegistry.DEBILITATING_STING.get()) != null && entity.getEffect(AMEffectRegistry.DEBILITATING_STING.get()).getAmplifier() > 0) {
            return EventResult.interruptFalse();
        }

        return EventResult.pass();
    }

    static {
        onTradeSetup();
        onWanderingTradeSetup();

        LivingEntityEvents.LOOTING_LEVEL.register(((source, target, currentLevel, recentlyHit) -> {
            return onLootLevelEvent(source, currentLevel);
        }));

        InteractionEvent.RIGHT_CLICK_ITEM.register(ServerEvents::onUseItem);

        InteractionEvent.INTERACT_ENTITY.register(ServerEvents::onInteractWithEntity);

        InteractionEvent.CLIENT_RIGHT_CLICK_AIR.register(ServerEvents::onUseItemAir);

        InteractionEvent.RIGHT_CLICK_BLOCK.register(ServerEvents::onUseItemOnBlock);
    }

    public static void onTradeSetup() {
        VillagerTrades.ItemListing ambergrisTrade = new EmeraldsForItemsTrade(AMItemRegistry.AMBERGRIS.get(), 20, 3, 4);
        TradeRegistry.registerVillagerTrade(VillagerProfession.FISHERMAN, 2, ambergrisTrade);
    }

    public static void onWanderingTradeSetup() {
        if (AMConfig.wanderingTraderOffers) {
            List<VillagerTrades.ItemListing> genericTrades = new ArrayList<>();
            List<VillagerTrades.ItemListing> rareTrades = new ArrayList<>();
            genericTrades.add(new ItemsForEmeraldsTrade(AMItemRegistry.ANIMAL_DICTIONARY.get(), 4, 1, 2, 1));
            genericTrades.add(new ItemsForEmeraldsTrade(AMItemRegistry.ACACIA_BLOSSOM.get(), 3, 2, 2, 1));
            if (AMConfig.cockroachSpawnWeight > 0) {
                genericTrades.add(new ItemsForEmeraldsTrade(AMItemRegistry.COCKROACH_OOTHECA.get(), 2, 1, 2, 1));
            }
            if (AMConfig.blobfishSpawnWeight > 0) {
                genericTrades.add(new ItemsForEmeraldsTrade(AMItemRegistry.BLOBFISH_BUCKET.get(), 4, 1, 3, 1));
            }
            if (AMConfig.crocodileSpawnWeight > 0) {
                genericTrades.add(new ItemsForEmeraldsTrade(AMBlockRegistry.CROCODILE_EGG.get().asItem(), 6, 1, 2, 1));
            }
            genericTrades.add(new ItemsForEmeraldsTrade(AMItemRegistry.BEAR_FUR.get(), 1, 1, 2, 1));
            genericTrades.add(new ItemsForEmeraldsTrade(AMItemRegistry.CROCODILE_SCUTE.get(), 5, 1, 2, 1));
            genericTrades.add(new ItemsForEmeraldsTrade(AMItemRegistry.ROADRUNNER_FEATHER.get(), 1, 2, 2, 2));
            genericTrades.add(new ItemsForEmeraldsTrade(AMItemRegistry.MOSQUITO_LARVA.get(), 1, 3, 5, 1));
            rareTrades.add(new ItemsForEmeraldsTrade(AMItemRegistry.SOMBRERO.get(), 20, 1, 1, 1));
            rareTrades.add(new ItemsForEmeraldsTrade(AMBlockRegistry.BANANA_PEEL.get(), 1, 2, 1, 1));
            rareTrades.add(new ItemsForEmeraldsTrade(AMItemRegistry.BLOOD_SAC.get(), 5, 2, 3, 1));

            TradeRegistry.registerTradeForWanderingTrader(false, genericTrades.toArray(new VillagerTrades.ItemListing[0]));
            TradeRegistry.registerTradeForWanderingTrader(true, rareTrades.toArray(new VillagerTrades.ItemListing[0]));
        }
    }

    public static int onLootLevelEvent(DamageSource src, int currentLevel) {
        if (src != null) {
            if (src.getEntity() instanceof EntitySnowLeopard) {
                return currentLevel + 2;
            }
        }
        return currentLevel;
    }

    public static CompoundEventResult<ItemStack> onUseItem(Player player, InteractionHand hand) {
        var itemStack = player.getItemInHand(hand);
        var level = player.level;
        if (itemStack.getItem() == Items.WHEAT && player.getVehicle() instanceof EntityElephant elephant) {
            if (elephant.triggerCharge(itemStack)) {
                player.swing(hand);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
            }
        }
        if (itemStack.getItem() == Items.GLASS_BOTTLE && AMConfig.lavaBottleEnabled) {
            HitResult raytraceresult = rayTrace(level, player, ClipContext.Fluid.SOURCE_ONLY);
            if (raytraceresult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos = ((BlockHitResult) raytraceresult).getBlockPos();
                if (level.mayInteract(player, blockpos)) {
                    if (level.getFluidState(blockpos).is(FluidTags.LAVA)) {
                        player.gameEvent(GameEvent.ITEM_INTERACT_START);
                        level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
                        player.awardStat(Stats.ITEM_USED.get(Items.GLASS_BOTTLE));
                        player.setSecondsOnFire(6);
                        if (!player.addItem(new ItemStack(AMItemRegistry.LAVA_BOTTLE.get()))) {
                            player.spawnAtLocation(new ItemStack(AMItemRegistry.LAVA_BOTTLE.get()));
                        }
                        player.swing(hand);
                        if (!player.isCreative()) {
                            itemStack.shrink(1);
                        }
                    }
                }
            }
        }

        return CompoundEventResult.interruptDefault(itemStack);
    }

    public static EventResult onInteractWithEntity(Player player, Entity entity, InteractionHand hand) {
        var level = player.level;
        var itemStack = player.getItemInHand(hand);

        if (entity instanceof LivingEntity living) {
            if (!player.isShiftKeyDown() && VineLassoUtil.hasLassoData(living)) {
                if (!player.level.isClientSide) {
                    entity.spawnAtLocation(new ItemStack(AMItemRegistry.VINE_LASSO.get()));
                }
                VineLassoUtil.lassoTo(null, living);
                return EventResult.interruptTrue();
            }
            if (!(entity instanceof Player) && !(entity instanceof EntityEndergrade)
                    && living.hasEffect(AMEffectRegistry.ENDER_FLU.get())) {
                if (itemStack.getItem() == Items.CHORUS_FRUIT) {
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                    entity.gameEvent(GameEvent.EAT);
                    entity.playSound(SoundEvents.GENERIC_EAT, 1.0F, 0.5F + player.getRandom().nextFloat());
                    if (player.getRandom().nextFloat() < 0.4F) {
                        living.removeEffect(AMEffectRegistry.ENDER_FLU.get());
                        Items.CHORUS_FRUIT.finishUsingItem(itemStack.copy(), level, ((LivingEntity) entity));
                    }
                    return EventResult.interruptTrue();
                }
            }
            if (RainbowUtil.getRainbowType(living) > 0 && (itemStack.getItem() == Items.SPONGE)) {
                RainbowUtil.setRainbowType(living, 0);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                ItemStack wetSponge = new ItemStack(Items.WET_SPONGE);
                if (!player.addItem(wetSponge)) {
                    player.drop(wetSponge, true);
                }

                return EventResult.interruptTrue();
            }
            if (living instanceof Rabbit rabbit && itemStack.getItem() == AMItemRegistry.MUNGAL_SPORES.get()
                    && AMConfig.bunfungusTransformation) {
                final var random = ThreadLocalRandom.current();
                if (!player.level.isClientSide && random.nextFloat() < 0.15F) {
                    final EntityBunfungus bunfungus = rabbit.convertTo(AMEntityRegistry.BUNFUNGUS.get(), true);
                    if (bunfungus != null) {
                        player.level.addFreshEntity(bunfungus);
                        bunfungus.setTransformsIn(EntityBunfungus.MAX_TRANSFORM_TIME);
                    }
                } else {
                    for (int i = 0; i < 2 + random.nextInt(2); i++) {
                        final double d0 = random.nextGaussian() * 0.02D;
                        final double d1 = 0.05F + random.nextGaussian() * 0.02D;
                        final double d2 = random.nextGaussian() * 0.02D;
                        entity.level.addParticle(AMParticleRegistry.BUNFUNGUS_TRANSFORMATION.get(), entity.getRandomX(0.7F), entity.getY(0.6F), entity.getRandomZ(0.7F), d0, d1, d2);
                    }
                }
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                return EventResult.interruptTrue();
            }
        }

        return EventResult.pass();
    }

    public static void onUseItemAir(Player entity, InteractionHand hand) {
        ItemStack stack = entity.getItemInHand(hand);
        if (stack.isEmpty()) {
            stack = entity.getItemBySlot(EquipmentSlot.MAINHAND);
        }
        if (RainbowUtil.getRainbowType(entity) > 0 && (stack.is(Items.SPONGE))) {
            entity.swing(InteractionHand.MAIN_HAND);
            RainbowUtil.setRainbowType(entity, 0);
            if (!entity.isCreative()) {
                stack.shrink(1);
            }
            ItemStack wetSponge = new ItemStack(Items.WET_SPONGE);
            if (!entity.addItem(wetSponge)) {
                entity.drop(wetSponge, true);
            }
        }
    }

    public static EventResult onUseItemOnBlock(Player entity, InteractionHand hand, BlockPos pos, Direction face) {
        var itemStack = entity.getItemInHand(hand);
        if (AlexsMobs.isAprilFools() && itemStack.is(Items.STICK)
            && !entity.getCooldowns().isOnCooldown(Items.STICK)) {
            BlockState state = entity.level.getBlockState(pos);
            boolean flag = false;
            if (state.is(Blocks.SAND)) {
                flag = true;
                entity.getLevel().setBlockAndUpdate(pos, AMBlockRegistry.SAND_CIRCLE.get().defaultBlockState());
            } else if (state.is(Blocks.RED_SAND)) {
                flag = true;
                entity.getLevel().setBlockAndUpdate(pos, AMBlockRegistry.RED_SAND_CIRCLE.get().defaultBlockState());
            }
            if (flag) {
                entity.gameEvent(GameEvent.BLOCK_PLACE);
                entity.playSound(SoundEvents.SAND_BREAK, 1, 1);
                entity.getCooldowns().addCooldown(Items.STICK, 30);

                return EventResult.interruptTrue();
            }
        }

        return EventResult.pass();
    }

    static {
        LivingEntityEvents.DROPS_WITH_LEVEL.register(((target, source, drops, lootingLevel, recentlyHit) -> {
            onEntityDrops(target, drops);
            return true;
        }));

        SpecialSpawnCallback.EVENT.register(((entity, level, x, y, z, spawner, spawnReason) -> {
            onEntityJoinWorld(level, entity);

            return false;
        }));

        PlayerEvent.ATTACK_ENTITY.register(((player, level, target, hand, result) -> {
            onPlayerAttackEntityEvent(player, level, target, hand, result);
            return EventResult.pass();
        }));

        EntityEvent.LIVING_HURT.register(((entity, source, amount) -> {
            return onLivingDamageEvent(entity, source, amount);
        }));
    }

    public static void onEntityDrops(LivingEntity entity, Collection<ItemEntity> drops) {
        if (VineLassoUtil.hasLassoData(entity)) {
            VineLassoUtil.lassoTo(null, entity);
            drops.add(new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(AMItemRegistry.VINE_LASSO.get())));
        }
    }

    public static void onEntityJoinWorld(LevelAccessor level, Entity entity) {
        if (entity instanceof WanderingTrader trader && AMConfig.elephantTraderSpawnChance > 0) {
            Biome biome = level.getBiome(entity.blockPosition()).value();
            if (RAND.nextFloat() <= AMConfig.elephantTraderSpawnChance
                && (!AMConfig.limitElephantTraderBiomes || biome.getBaseTemperature() >= 1.0F)) {
                EntityElephant elephant = AMEntityRegistry.ELEPHANT.get().create(trader.level);
                elephant.copyPosition(trader);
                if (elephant.canSpawnWithTraderHere()) {
                    elephant.setTrader(true);
                    elephant.setChested(true);
                    if (!level.isClientSide()) {
                        trader.level.addFreshEntity(elephant);
                        trader.startRiding(elephant, true);
                    }
                    elephant.addElephantLoot(null, RAND.nextInt());
                }
            }
        }
        try {
            if (entity instanceof final Spider spider && AMConfig.spidersAttackFlies) {
                spider.targetSelector.addGoal(4,
                    new NearestAttackableTargetGoal<>(spider, EntityFly.class, 1, true, false, null));
            }
            else if (entity instanceof final Wolf wolf && AMConfig.wolvesAttackMoose) {
                wolf.targetSelector.addGoal(6, new NonTameRandomTargetGoal<>(wolf, EntityMoose.class, false, null));
            }
            else if (entity instanceof final PolarBear bear && AMConfig.polarBearsAttackSeals) {
                bear.targetSelector.addGoal(6,
                    new NearestAttackableTargetGoal<>(bear, EntitySeal.class, 15, true, true, null));
            }
            else if (entity instanceof final Creeper creeper) {
                creeper.targetSelector.addGoal(3, new AvoidEntityGoal<>(creeper, EntitySnowLeopard.class, 6.0F, 1.0D, 1.2D));
                creeper.targetSelector.addGoal(3, new AvoidEntityGoal<>(creeper, EntityTiger.class, 6.0F, 1.0D, 1.2D));
            }
            else if ((entity instanceof Fox || entity instanceof Cat
                || entity instanceof Ocelot) && AMConfig.catsAndFoxesAttackJerboas) {
                Mob mb = (Mob) entity;
                mb.targetSelector.addGoal(6,
                    new NearestAttackableTargetGoal<>(mb, EntityJerboa.class, 45, true, true, null));
            }
            else if (entity instanceof final Rabbit rabbit && AMConfig.bunfungusTransformation) {
                rabbit.goalSelector.addGoal(3, new TemptGoal(rabbit, 1.0D, Ingredient.of(AMItemRegistry.MUNGAL_SPORES.get()), false));
            }
            else if (entity instanceof final Dolphin dolphin && AMConfig.dolphinsAttackFlyingFish) {
                dolphin.targetSelector.addGoal(2,
                    new NearestAttackableTargetGoal<>(dolphin, EntityFlyingFish.class, 70, true, true, null));
            }
        } catch (Exception e) {
            AlexsMobs.LOGGER.warn("Tried to add unique behaviors to vanilla mobs and encountered an error");
        }
    }

    public static void onPlayerAttackEntityEvent(Player entity, Level level, Entity target, InteractionHand hand, @Nullable EntityHitResult result) {
        if (target instanceof LivingEntity living) {
            if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == AMItemRegistry.MOOSE_HEADGEAR.get()) {
                living.knockback(1F, Mth.sin(entity.getYRot() * ((float) Math.PI / 180F)),
                        -Mth.cos(entity.getYRot() * ((float) Math.PI / 180F)));
            }
            if (entity.hasEffect(AMEffectRegistry.TIGERS_BLESSING.get())
                    && !target.isAlliedTo(entity) && !(target instanceof EntityTiger)) {
                AABB bb = new AABB(entity.getX() - 32, entity.getY() - 32, entity.getZ() - 32, entity.getZ() + 32, entity.getY() + 32, entity.getZ() + 32);
                final var tigers = entity.level.getEntitiesOfClass(EntityTiger.class, bb,
                        EntitySelector.ENTITY_STILL_ALIVE);
                for (EntityTiger tiger : tigers) {
                    if (!tiger.isBaby()) {
                        tiger.setTarget(living);
                    }
                }
            }
        }
    }

    public static EventResult onLivingDamageEvent(LivingEntity entity, DamageSource source, float amount) {
        if (source.getEntity() instanceof final LivingEntity attacker) {
            if (amount > 0 && attacker.hasEffect(AMEffectRegistry.SOULSTEAL.get()) && attacker.getEffect(AMEffectRegistry.SOULSTEAL.get()) != null) {
                final int level = attacker.getEffect(AMEffectRegistry.SOULSTEAL.get()).getAmplifier() + 1;
                if (attacker.getHealth() < attacker.getMaxHealth()
                    && ThreadLocalRandom.current().nextFloat() < (0.25F + (level * 0.25F))) {
                    attacker.heal(Math.min(amount / 2F * level, 2 + 2 * level));
                }
            }

            if (entity instanceof final Player player) {
                if (attacker instanceof final EntityMimicOctopus octupus && octupus.isOwnedBy(player)) {
                    return EventResult.interruptFalse();
                }
                if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == AMItemRegistry.SPIKED_TURTLE_SHELL.get()) {
                    if (attacker.distanceTo(player) < attacker.getBbWidth() + player.getBbWidth() + 0.5F) {
                        attacker.hurt(DamageSource.thorns(player), 1F);
                        attacker.knockback(0.5F, Mth.sin((attacker.getYRot() + 180) * ((float) Math.PI / 180F)),
                            -Mth.cos((attacker.getYRot() + 180) * ((float) Math.PI / 180F)));
                    }
                }
            }
        }
        if (!entity.getItemBySlot(EquipmentSlot.LEGS).isEmpty() && entity.getItemBySlot(EquipmentSlot.LEGS).getItem() == AMItemRegistry.EMU_LEGGINGS.get()) {
            if (source.isProjectile() && entity.getRandom().nextFloat() < AMConfig.emuPantsDodgeChance) {
                return EventResult.interruptFalse();
            }
        }

        return EventResult.pass();
    }

    static {
        MobEntitySetTargetCallback.EVENT.register((ServerEvents::onLivingSetTargetEvent));

        LivingEntityEvents.TICK.register(ServerEvents::onLivingUpdateEvent);

        FieldOfViewEvents.COMPUTE.register(((renderer, camera, partialTicks, usedFovSetting, fov) -> {
            return onFOVUpdate(Minecraft.getInstance().player, fov);
        }));
    }

    public static void onLivingSetTargetEvent(LivingEntity entity, LivingEntity target) {
        if (target != null && entity instanceof Mob mob) {
            if (mob.getMobType() == MobType.ARTHROPOD) {
                if (target.hasEffect(AMEffectRegistry.BUG_PHEROMONES.get()) && entity.getLastHurtByMob() != target) {
                    mob.setTarget(null);
                }
            }
            if (mob.getMobType() == MobType.UNDEAD && !mob.getType().is(AMTagRegistry.IGNORES_KIMONO)) {
                if (target.getItemBySlot(EquipmentSlot.CHEST).is(AMItemRegistry.UNSETTLING_KIMONO.get()) && entity.getLastHurtByMob() != target) {
                    mob.setTarget(null);
                }
            }
        }
    }

    public static void onLivingUpdateEvent(LivingEntity entity) {
        if (entity instanceof Player player) {
            if (player.getEyeHeight() < player.getBbHeight() * 0.5D) {
                player.refreshDimensions();
            }
            final var attributes = entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == AMItemRegistry.ROADDRUNNER_BOOTS.get()
                || attributes.hasModifier(SAND_SPEED_BONUS)) {
                final boolean sand = player.level.getBlockState(getDownPos(player.blockPosition(), player.level))
                    .is(BlockTags.SAND);
                if (sand && !attributes.hasModifier(SAND_SPEED_BONUS)) {
                    attributes.addPermanentModifier(SAND_SPEED_BONUS);
                }
                if (player.tickCount % 25 == 0
                    && (player.getItemBySlot(EquipmentSlot.FEET).getItem() != AMItemRegistry.ROADDRUNNER_BOOTS.get()
                        || !sand)
                    && attributes.hasModifier(SAND_SPEED_BONUS)) {
                    attributes.removeModifier(SAND_SPEED_BONUS);
                }
            }
            if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == AMItemRegistry.FRONTIER_CAP.get()
                || attributes.hasModifier(SNEAK_SPEED_BONUS)) {
                final var shift = player.isShiftKeyDown();
                if (shift && !attributes.hasModifier(SNEAK_SPEED_BONUS)) {
                    attributes.addPermanentModifier(SNEAK_SPEED_BONUS);
                }
                if ((!shift || player.getItemBySlot(EquipmentSlot.HEAD).getItem() != AMItemRegistry.FRONTIER_CAP.get())
                    && attributes.hasModifier(SNEAK_SPEED_BONUS)) {
                    attributes.removeModifier(SNEAK_SPEED_BONUS);
                }
            }
            if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == AMItemRegistry.SPIKED_TURTLE_SHELL.get()) {
                if (!player.isEyeInFluid(FluidTags.WATER)) {
                    player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 310, 0, false, false, true));
                }
            }
        }
        final ItemStack boots = entity.getItemBySlot(EquipmentSlot.FEET);
        if (!boots.isEmpty() && boots.hasTag() && boots.getOrCreateTag().contains("BisonFur") && boots.getOrCreateTag().getBoolean("BisonFur")) {
            BlockPos pos = new BlockPos(entity.getX(), entity.getY() - 0.5F, entity.getZ());
            if (entity.level.getBlockState(pos).is(Blocks.POWDER_SNOW)) {
                entity.setOnGround(true);
                entity.setTicksFrozen(0);

            }
            if (entity.isInPowderSnow) {
                entity.setPos(entity.getX(), pos.getY() + 1, entity.getZ());
            }
        }

        if (entity.getItemBySlot(EquipmentSlot.LEGS).getItem() == AMItemRegistry.CENTIPEDE_LEGGINGS.get()) {
            if (entity.horizontalCollision && !entity.isInWater()) {
                entity.fallDistance = 0.0F;
                Vec3 motion = entity.getDeltaMovement();
                double d2 = 0.1D;
                if (entity.isShiftKeyDown() || !(entity.getFeetBlockState()).is(Blocks.SCAFFOLDING) && entity.isSuppressingSlidingDownLadder()) {
                    d2 = 0.0D;
                }
                motion = new Vec3(Mth.clamp(motion.x, -0.15F, 0.15F), d2, Mth.clamp(motion.z, -0.15F, 0.15F));
                entity.setDeltaMovement(motion);
            }
        }
        if (entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == AMItemRegistry.SOMBRERO.get() && !entity.level.isClientSide && AlexsMobs.isAprilFools() && entity.isInWaterOrBubble()) {
            RandomSource random = entity.getRandom();
            if (random.nextInt(245) == 0 && !EntitySeaBear.isMobSafe(entity)) {
                final int dist = 32;
                final var nearbySeabears = entity.level.getEntitiesOfClass(EntitySeaBear.class,
                    entity.getBoundingBox().inflate(dist, dist, dist));
                if (nearbySeabears.isEmpty()) {
                    final EntitySeaBear bear = AMEntityRegistry.SEA_BEAR.get().create(entity.level);
                    final BlockPos at = entity.blockPosition();
                    BlockPos farOff = null;
                    for (int i = 0; i < 15; i++) {
                        final int f1 = (int) Math.signum(random.nextInt() - 0.5F);
                        final int f2 = (int) Math.signum(random.nextInt() - 0.5F);
                        final BlockPos pos1 = at.offset(f1 * (10 + random.nextInt(dist - 10)), random.nextInt(1),
                            f2 * (10 + random.nextInt(dist - 10)));
                        if (entity.level.isWaterAt(pos1)) {
                            farOff = pos1;
                        }
                    }
                    if (farOff != null) {
                        bear.setPos(farOff.getX() + 0.5F, farOff.getY() + 0.5F, farOff.getZ() + 0.5F);
                        bear.setYRot(random.nextFloat() * 360F);
                        bear.setTarget(entity);
                        entity.level.addFreshEntity(bear);
                    }
                } else {
                    for (EntitySeaBear bear : nearbySeabears) {
                        bear.setTarget(entity);
                    }
                }
            }
        }
        if (VineLassoUtil.hasLassoData(entity)) {
            VineLassoUtil.tickLasso(entity);
        }
        if (RockyChestplateUtil.isWearing(entity)) {
            RockyChestplateUtil.tickRockyRolling(entity);
        }
        if (FlyingFishBootsUtil.isWearing(entity)) {
            FlyingFishBootsUtil.tickFlyingFishBoots(entity);
        }
    }

    private static BlockPos getDownPos(BlockPos entered, LevelAccessor world) {
        int i = 0;
        while (world.isEmptyBlock(entered) && i < 3) {
            entered = entered.below();
            i++;
        }
        return entered;
    }

    public static double onFOVUpdate(Player player, double fov) {
        if (player.hasEffect(AMEffectRegistry.FEAR.get()) || player.hasEffect(AMEffectRegistry.POWER_DOWN.get())) {
            return fov;
        }

        return fov;
    }

    static {
        LivingEntityEvents.ATTACK.register((ServerEvents::onLivingAttack));

        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            onChestGenerated(id, tableBuilder);
        }));

        ClientTooltipEvent.ITEM.register((stack, lines, flag) -> {
            onTooltip(stack, lines);
        });

        onAddReloadListener();

        BlockEvent.BREAK.register(((level, pos, state, player, xp) -> {
            return onHarvestCheck(player);
        }));
    }

    public static boolean onLivingAttack(LivingEntity entity, DamageSource source, float amount) {
        if (!entity.getUseItem().isEmpty() && source != null && source.getEntity() != null) {
            if (entity.getUseItem().getItem() == AMItemRegistry.SHIELD_OF_THE_DEEP.get()) {
                if (source.getEntity() instanceof LivingEntity living) {
                    boolean flag = false;
                    if (living.distanceTo(entity) <= 4
                        && !living.hasEffect(AMEffectRegistry.EXSANGUINATION.get())) {
                        living.addEffect(new MobEffectInstance(AMEffectRegistry.EXSANGUINATION.get(), 60, 2));
                        flag = true;
                    }
                    if (entity.isInWaterOrBubble()) {
                        entity.setAirSupply(Math.min(entity.getMaxAirSupply(), entity.getAirSupply() + 150));
                        flag = true;
                    }
                    if (flag) {
                        entity.getUseItem().hurtAndBreak(1, entity,
                            player -> player.broadcastBreakEvent(entity.getUsedItemHand()));
                    }
                }
            }
        }

        return false;
    }

    public static void onChestGenerated(ResourceLocation name, LootTable.Builder tableBuilder) {
        if (AMConfig.addLootToChests) {
            if (name.equals(BuiltInLootTables.JUNGLE_TEMPLE)) {
                final var item = LootItem.lootTableItem(AMItemRegistry.ANCIENT_DART.get()).setQuality(40).setWeight(1);
                LootPool.Builder builder = new LootPool.Builder().name("am_dart").add(item).when(LootItemRandomChanceCondition.randomChance(1f)).setRolls(UniformGenerator.between(0, 1)).setBonusRolls(UniformGenerator.between(0, 1));
                tableBuilder.pool(builder.build());
            }
            if (name.equals(BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER)) {
                final var item = LootItem.lootTableItem(AMItemRegistry.ANCIENT_DART.get()).setQuality(20).setWeight(3);
                LootPool.Builder builder = new LootPool.Builder().name("am_dart_dispenser").add(item).when(LootItemRandomChanceCondition.randomChance(1f)).setRolls(UniformGenerator.between(0, 2)).setBonusRolls(UniformGenerator.between(0, 1));
                tableBuilder.pool(builder.build());
            }
        }
        if (name.equals(BuiltInLootTables.PIGLIN_BARTERING) && AMConfig.tusklinShoesBarteringChance > 0) {
            final var item = LootItem.lootTableItem(AMItemRegistry.PIGSHOES.get()).setQuality(5).setWeight(8);
            LootPool.Builder builder = new LootPool.Builder().name("am_pigshoes").add(item).when(LootItemRandomChanceCondition.randomChance((float) AMConfig.tusklinShoesBarteringChance)).setRolls(ConstantValue.exactly(1));
            tableBuilder.pool(builder.build());
        }
    }

    public static void onTooltip(ItemStack itemStack, List<Component> tooltip) {
        CompoundTag tag = itemStack.getTag();
        if (tag != null && tag.contains("BisonFur") && tag.getBoolean("BisonFur")) {
            tooltip.add(Component.translatable("item.alexsmobs.insulated_with_fur").withStyle(ChatFormatting.AQUA));
        }
    }

    public static void onAddReloadListener(){
        AlexsMobs.LOGGER.info("Adding datapack listener capsid_recipes");
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return new ResourceLocation("alexsmobs", "capsid_recipes");
            }

            @Override
            public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
                return AlexsMobs.PROXY.getCapsidRecipeManager().reload(preparationBarrier, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor);
            }
        });
    }

    public static EventResult onHarvestCheck(Player entity){
        if(entity.isHolding(AMItemRegistry.GHOSTLY_PICKAXE.get()) && ItemGhostlyPickaxe.shouldStoreInGhost(entity, entity.getMainHandItem())){
            //stops drops from being spawned
            return EventResult.interruptFalse();
        }

        return EventResult.pass();
    }

}
