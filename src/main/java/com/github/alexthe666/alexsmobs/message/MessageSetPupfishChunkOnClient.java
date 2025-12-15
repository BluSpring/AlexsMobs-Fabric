package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public record MessageSetPupfishChunkOnClient(
    int chunkX, int chunkZ
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageSetPupfishChunkOnClient> TYPE = new CustomPacketPayload.Type<>(AlexsMobs.id("set_pupfish_chunk"));
    public static final StreamCodec<FriendlyByteBuf, MessageSetPupfishChunkOnClient> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, MessageSetPupfishChunkOnClient::chunkX,
        ByteBufCodecs.VAR_INT, MessageSetPupfishChunkOnClient::chunkZ,
        MessageSetPupfishChunkOnClient::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageSetPupfishChunkOnClient message, IPayloadContext context) {
            context.enqueueWork(() -> {
                AlexsMobs.PROXY.setPupfishChunkForItem(message.chunkX, message.chunkZ);
            });
        }
    }
}