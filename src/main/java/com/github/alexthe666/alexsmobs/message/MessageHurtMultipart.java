package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.IHurtableMultipart;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import io.github.fabricators_of_create.porting_lib.entity.MultiPartEntity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class MessageHurtMultipart implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageHurtMultipart> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AlexsMobs.MODID, "hurt_multipart"));
    public static final StreamCodec<FriendlyByteBuf, MessageHurtMultipart> CODEC = StreamCodec.ofMember(MessageHurtMultipart::write, MessageHurtMultipart::read);

    public int part;
    public int parent;
    public float damage;
    public String damageType;

    public MessageHurtMultipart(int part, int parent, float damage) {
        this.part = part;
        this.parent = parent;
        this.damage = damage;
        this.damageType = "";
    }

    public MessageHurtMultipart(int part, int parent, float damage, String damageType) {
        this.part = part;
        this.parent = parent;
        this.damage = damage;
        this.damageType = damageType;
    }

    public MessageHurtMultipart() {}

    public static MessageHurtMultipart read(FriendlyByteBuf buf) {
        return new MessageHurtMultipart(buf.readInt(), buf.readInt(), buf.readFloat(), buf.readUtf());
    }

    public static void write(MessageHurtMultipart message, FriendlyByteBuf buf) {
        buf.writeInt(message.part);
        buf.writeInt(message.parent);
        buf.writeFloat(message.damage);
        buf.writeUtf(message.damageType);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(MessageHurtMultipart message, ServerPlayNetworking.Context context) {
        handle(message, context.server(), context.player());
    }

    @Environment(EnvType.CLIENT)
    public static void handle(MessageHurtMultipart message, ClientPlayNetworking.Context context) {
        handle(message, context.client(), context.player());
    }

    public static void handle(MessageHurtMultipart message, BlockableEventLoop<?> context, Player player) {
        context.execute(() -> {
            if (player != null && player.level() != null) {
                Entity part = player.level().getEntity(message.part);
                Entity parent = player.level().getEntity(message.parent);
                Registry<DamageType> registry = player.level().registryAccess().registry(Registries.DAMAGE_TYPE).get();
                DamageType dmg = registry.get(ResourceLocation.parse(message.damageType));
                if (dmg != null) {
                    Holder<DamageType> holder = registry.getHolder(registry.getId(dmg)).orElse(null);
                    if (holder != null) {
                        DamageSource source = new DamageSource(registry.getHolder(registry.getId(dmg)).get());
                        if (part instanceof IHurtableMultipart && parent instanceof LivingEntity) {
                            ((IHurtableMultipart) part).onAttackedFromServer((LivingEntity) parent, message.damage, source);
                        }
                        if (part == null && parent != null && parent instanceof MultiPartEntity multiPartEntity && multiPartEntity.isMultipartEntity()) {
                            parent.hurt(source, message.damage);
                        }
                    }
                }
            }
        });
    }
}
