package com.github.alexthe666.alexsmobs.entity;

import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.misc.AMSoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class EntityCentipedeHead extends Monster {

    private static final EntityDataAccessor<Optional<UUID>> CHILD_UUID = SynchedEntityData.defineId(EntityCentipedeHead.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> CHILD_ID = SynchedEntityData.defineId(EntityCentipedeHead.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SEGMENT_COUNT = SynchedEntityData.defineId(EntityCentipedeHead.class, EntityDataSerializers.INT);
    public final float[] ringBuffer = new float[64];
    public int ringBufferIndex = -1;
    private EntityCentipedeBody[] parts;

    protected EntityCentipedeHead(EntityType type, Level worldIn) {
        super(type, worldIn);
        this.xpReward = 13;
        this.maxUpStep = 3;
    }

    public static AttributeSupplier.Builder bakeAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 35.0D).add(Attributes.FOLLOW_RANGE, 32.0D).add(Attributes.ARMOR, 6.0D).add(Attributes.ATTACK_DAMAGE, 8.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.5F).add(Attributes.MOVEMENT_SPEED, 0.22F);
    }

    public static <T extends Mob> boolean canCentipedeSpawn(EntityType<EntityCentipedeHead> entityType, ServerLevelAccessor iServerWorld, MobSpawnType reason, BlockPos pos, Random random) {
        return reason == MobSpawnType.SPAWNER || !iServerWorld.canSeeSky(pos) && pos.getY() <= AMConfig.caveCentipedeSpawnHeight && checkMonsterSpawnRules(entityType, iServerWorld, reason, pos, random);
    }

    public boolean checkSpawnRules(LevelAccessor worldIn, MobSpawnType spawnReasonIn) {
        return AMEntityRegistry.rollSpawn(AMConfig.caveCentipedeSpawnRolls, this.getRandom(), spawnReasonIn) && super.checkSpawnRules(worldIn, spawnReasonIn);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.4D, false));
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1.0D, 13, false));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 20, true, true, null));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, 20, true, true, null));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, EntityCockroach.class, 45, true, true, null));
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return AMSoundRegistry.CENTIPEDE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return AMSoundRegistry.CENTIPEDE_HURT;
    }

    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(AMSoundRegistry.CENTIPEDE_WALK, 1F, 1.0F);
    }

    public int getMaxHeadXRot() {
        return 1;
    }

    public int getMaxHeadYRot() {
        return 1;
    }

    public int getHeadRotSpeed() {
        return 1;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CHILD_UUID, Optional.empty());
        this.entityData.define(CHILD_ID, -1);
        this.entityData.define(SEGMENT_COUNT, 5);
    }

    public boolean doHurtTarget(Entity entityIn) {
        if (super.doHurtTarget(entityIn)) {
            if (entityIn instanceof LivingEntity) {
                int i = 3;
                if (this.level.getDifficulty() == Difficulty.NORMAL) {
                    i = 10;
                } else if (this.level.getDifficulty() == Difficulty.HARD) {
                    i = 20;
                }
                if (i > 0) {
                    ((LivingEntity) entityIn).addEffect(new MobEffectInstance(MobEffects.POISON, i * 20, 1));
                }
            }
            this.playSound(AMSoundRegistry.CENTIPEDE_ATTACK, this.getSoundVolume(), this.getVoicePitch());
            return true;
        } else {
            return false;
        }
    }

    public int getSegmentCount() {
        return Math.max(this.entityData.get(SEGMENT_COUNT), 1);
    }

    public void setSegmentCount(int segments) {
        this.entityData.set(SEGMENT_COUNT, segments);
    }

    @Nullable
    public UUID getChildId() {
        return this.entityData.get(CHILD_UUID).orElse(null);
    }

    public void setChildId(@Nullable UUID uniqueId) {
        this.entityData.set(CHILD_UUID, Optional.ofNullable(uniqueId));
    }

    public Entity getChild() {
        UUID id = getChildId();
        if (id != null && !level.isClientSide) {
            return ((ServerLevel) level).getEntity(id);
        }
        return null;
    }

    public void pushEntities() {
        List<Entity> entities = this.level.getEntities(this, this.getBoundingBox().expandTowards(0.20000000298023224D, 0.0D, 0.20000000298023224D));
        entities.stream().filter(entity -> !(entity instanceof EntityCentipedeBody) && entity.isPushable()).forEach(entity -> entity.push(this));
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        this.setSegmentCount(random.nextInt(4) + 5);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.getChildId() != null) {
            compound.putUUID("ChildUUID", this.getChildId());
        }
        compound.putInt("SegCount", getSegmentCount());

    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID("ChildUUID")) {
            this.setChildId(compound.getUUID("ChildUUID"));
        }
        this.setSegmentCount(compound.getInt("SegCount"));
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.IN_WALL || source == DamageSource.FALLING_BLOCK || super.isInvulnerableTo(source);
    }


    public void tick() {
        super.tick();
        isInsidePortal = false;
        this.yBodyRot = Mth.clamp(this.getYRot(), this.yBodyRot - 2, this.yBodyRot + 2);
        this.yHeadRot = this.yBodyRot;
        if (this.ringBufferIndex < 0) {
            for (int i = 0; i < this.ringBuffer.length; ++i) {
                this.ringBuffer[i] = this.yBodyRot;
            }
        }
        if (updateRingBuffer() || ringBufferIndex < 0) {
            this.ringBufferIndex++;
        }
        if (this.ringBufferIndex == this.ringBuffer.length) {
            this.ringBufferIndex = 0;
        }
        this.ringBuffer[this.ringBufferIndex] = this.getYRot();

        if (!level.isClientSide) {
            Entity child = getChild();
            if (child == null) {
                LivingEntity partParent = this;
                parts = new EntityCentipedeBody[this.getSegmentCount()];
                Vec3 prevPos = this.position();
                float backOffset = 0.45F;
                for (int i = 0; i < this.getSegmentCount(); i++) {
                    EntityCentipedeBody part = createBody(partParent, i == this.getSegmentCount() - 1);
                    part.setParent(partParent);
                    part.setBodyIndex(i);
                    if (partParent == this) {
                        this.setChildId(part.getUUID());
                        this.entityData.set(CHILD_ID, part.getId());
                    }
                    if (partParent instanceof EntityCentipedeBody) {
                        ((EntityCentipedeBody) partParent).setChildId(part.getUUID());
                    }
                    part.setPos(part.tickMultipartPosition(this.getId(), backOffset, prevPos, this.getXRot(), getYawForPart(i), false));
                    level.addFreshEntity(part);
                    parts[i] = part;
                    partParent = part;
                    backOffset = part.getBackOffset();
                    prevPos = part.position();
                }
            }
            if(tickCount > 1) {
                if ((parts == null || parts[0] == null) && this.getChild() instanceof EntityCentipedeBody) {
                    parts = new EntityCentipedeBody[this.getSegmentCount()];
                    parts[0] = (EntityCentipedeBody) this.getChild();
                    this.entityData.set(CHILD_ID, parts[0].getId());
                    int i = 1;
                    while (i < parts.length && parts[i - 1].getChild() instanceof EntityCentipedeBody) {
                        parts[i] = (EntityCentipedeBody) parts[i - 1].getChild();
                        i++;
                    }
                }
                Vec3 prev = this.position();
                float xRot = this.getXRot();
                float backOffset = 0.45F;
                for (int i = 0; i < this.getSegmentCount(); i++) {
                    if (this.parts[i] != null) {
                        float reqRot = getYawForPart(i);
                        prev = parts[i].tickMultipartPosition(this.getId(), backOffset, prev, xRot, reqRot, true);
                        xRot = parts[i].getXRot();
                        backOffset = parts[i].getBackOffset();
                    }
                }
            }
        }
    }

    private boolean updateRingBuffer() {
        return this.getDeltaMovement().lengthSqr() >= 0.005D;
    }


    public EntityCentipedeBody createBody(LivingEntity parent, boolean tail) {
        return tail ? new EntityCentipedeBody(AMEntityRegistry.CENTIPEDE_TAIL, parent, 0.84F, 180, 0) : new EntityCentipedeBody(AMEntityRegistry.CENTIPEDE_BODY, parent, 0.84F, 180, 0);
    }

    public boolean canBeLeashed(Player player) {
        return true;
    }

    private float getYawForPart(int i) {
        return this.getRingBuffer(4 + i * 4, 1.0F);
    }

    public float getRingBuffer(int bufferOffset, float partialTicks) {
        if (this.isDeadOrDying()) {
            partialTicks = 0.0F;
        }

        partialTicks = 1.0F - partialTicks;
        int i = this.ringBufferIndex - bufferOffset & 63;
        int j = this.ringBufferIndex - bufferOffset - 1 & 63;
        float d0 = this.ringBuffer[i];
        float d1 = this.ringBuffer[j] - d0;
        return Mth.wrapDegrees(d0 + d1 * partialTicks);
    }
}
