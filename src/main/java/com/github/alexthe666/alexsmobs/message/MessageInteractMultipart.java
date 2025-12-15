package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public record MessageInteractMultipart(
    boolean offhand,
    int parent
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageInteractMultipart> TYPE = new CustomPacketPayload.Type<>(AlexsMobs.id("interact_multipart"));
    public static final StreamCodec<FriendlyByteBuf, MessageInteractMultipart> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL, MessageInteractMultipart::offhand,
        ByteBufCodecs.VAR_INT, MessageInteractMultipart::parent,
        MessageInteractMultipart::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageInteractMultipart message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
                

                if (player != null) {
                    if (player.level() != null) {
                        Entity parent = player.level().getEntity(message.parent);
                        if (parent instanceof Mob && player.distanceTo(parent) < 20) {
                            player.interactOn(parent, message.offhand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
                        }
                    }
                }
            });
        }
    }
}