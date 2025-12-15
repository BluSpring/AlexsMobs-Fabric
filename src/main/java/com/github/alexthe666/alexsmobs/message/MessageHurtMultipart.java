package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.IHurtableMultipart;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Optional;
import java.util.function.Supplier;

public record MessageHurtMultipart(
    int part, int parent,
    float damage, ResourceKey<DamageType> damageType
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageHurtMultipart> TYPE = new CustomPacketPayload.Type<>(AlexsMobs.id("hurt_multipart"));
    public static final StreamCodec<FriendlyByteBuf, MessageHurtMultipart> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, MessageHurtMultipart::part,
        ByteBufCodecs.VAR_INT, MessageHurtMultipart::parent,
        ByteBufCodecs.FLOAT, MessageHurtMultipart::damage,
        ResourceKey.streamCodec(Registries.DAMAGE_TYPE), MessageHurtMultipart::damageType,
        MessageHurtMultipart::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageHurtMultipart message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
                

                if (player != null) {
                    if (player.level() != null) {
                        Entity part = player.level().getEntity(message.part);
                        Entity parent = player.level().getEntity(message.parent);
                        DamageSource source = player.damageSources().source(message.damageType());

                        if (part instanceof IHurtableMultipart && parent instanceof LivingEntity) {
                            ((IHurtableMultipart) part).onAttackedFromServer((LivingEntity) parent, message.damage, source);
                        }
                        if (part == null && parent != null && parent.isMultipartEntity()) {
                            parent.hurt(source, message.damage);
                        }

                    }
                }
            });
        }
    }
}