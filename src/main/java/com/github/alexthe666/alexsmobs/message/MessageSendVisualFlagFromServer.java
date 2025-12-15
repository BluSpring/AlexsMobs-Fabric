package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public record MessageSendVisualFlagFromServer(
    int entityID,
    int flag
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageSendVisualFlagFromServer> TYPE = new CustomPacketPayload.Type<>(AlexsMobs.id("send_visual_flag"));
    public static final StreamCodec<FriendlyByteBuf, MessageSendVisualFlagFromServer> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, MessageSendVisualFlagFromServer::entityID,
        ByteBufCodecs.VAR_INT, MessageSendVisualFlagFromServer::flag,
        MessageSendVisualFlagFromServer::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageSendVisualFlagFromServer message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
                if(context.flow().getReceptionSide() == LogicalSide.CLIENT){
                    player = AlexsMobs.PROXY.getClientSidePlayer();
                }

                if (player != null) {
                    if (player.level() != null) {
                        Entity entity = player.level().getEntity(message.entityID);
                        AlexsMobs.PROXY.processVisualFlag(entity, message.flag);
                    }
                }
            });
        }
    }
}