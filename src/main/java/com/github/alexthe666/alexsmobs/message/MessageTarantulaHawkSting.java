package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import com.github.alexthe666.alexsmobs.entity.EntityTarantulaHawk;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public record MessageTarantulaHawkSting(
    int hawk, int spider
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageTarantulaHawkSting> TYPE = new CustomPacketPayload.Type<>(AlexsMobs.id("tarantula_hawk_sting"));
    public static final StreamCodec<FriendlyByteBuf, MessageTarantulaHawkSting> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, MessageTarantulaHawkSting::hawk,
        ByteBufCodecs.VAR_INT, MessageTarantulaHawkSting::spider,
        MessageTarantulaHawkSting::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageTarantulaHawkSting message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();

                if (player != null) {
                    if (player.level() != null) {
                        Entity entity = player.level().getEntity(message.hawk);
                        Entity spider = player.level().getEntity(message.spider);
                        if (entity instanceof EntityTarantulaHawk && spider instanceof LivingEntity livingEntity && livingEntity.getType().is(EntityTypeTags.ARTHROPOD)) {
                            livingEntity.addEffect(new MobEffectInstance(AMEffectRegistry.DEBILITATING_STING, EntityTarantulaHawk.STING_DURATION));
                        }
                    }
                }
            });
        }
    }
}