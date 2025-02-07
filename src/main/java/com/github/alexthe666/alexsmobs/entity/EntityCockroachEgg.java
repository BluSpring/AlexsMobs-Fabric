package com.github.alexthe666.alexsmobs.entity;

import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class EntityCockroachEgg extends ThrowableItemProjectile {

    public EntityCockroachEgg(EntityType p_i50154_1_, Level p_i50154_2_) {
        super(p_i50154_1_, p_i50154_2_);
    }

    public EntityCockroachEgg(Level worldIn, LivingEntity throwerIn) {
        super(AMEntityRegistry.COCKROACH_EGG.get(), throwerIn, worldIn);
    }

    public EntityCockroachEgg(Level worldIn, double x, double y, double z) {
        super(AMEntityRegistry.COCKROACH_EGG.get(), x, y, z, worldIn);
    }

    /*public EntityCockroachEgg(PlayMessages.SpawnEntity spawnEntity, Level world) {
        this(AMEntityRegistry.COCKROACH_EGG.get(), world);
    }*/

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Environment(EnvType.CLIENT)
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            for (int i = 0; i < 8; ++i) {
                this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level.isClientSide) {
            this.level.broadcastEntityEvent(this, (byte)3);
            int i = random.nextInt(3);
            for (int j = 0; j < i; ++j) {
                final EntityCockroach croc = AMEntityRegistry.COCKROACH.get().create(this.level);
                croc.setAge(-24000);
                croc.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                croc.finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.TRIGGERED, (SpawnGroupData)null, (CompoundTag)null);
                croc.restrictTo(this.blockPosition(), 20);
                this.level.addFreshEntity(croc);
            }
            this.level.broadcastEntityEvent(this, (byte)3);
            this.remove(RemovalReason.DISCARDED);
        }

    }

    protected Item getDefaultItem() {
        return AMItemRegistry.COCKROACH_OOTHECA.get();
    }
}
