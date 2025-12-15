package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.EntityStraddleboard;
import com.github.alexthe666.alexsmobs.entity.IFalconry;
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

public record MessageSyncEntityPos(
    int eagleId,
    double posX, double posY, double posZ
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageSyncEntityPos> TYPE = new CustomPacketPayload.Type<>(AlexsMobs.id("sync_entity_pos"));
    public static final StreamCodec<FriendlyByteBuf, MessageSyncEntityPos> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, MessageSyncEntityPos::eagleId,
        ByteBufCodecs.DOUBLE, MessageSyncEntityPos::posX,
        ByteBufCodecs.DOUBLE, MessageSyncEntityPos::posY,
        ByteBufCodecs.DOUBLE, MessageSyncEntityPos::posZ,
        MessageSyncEntityPos::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageSyncEntityPos message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
                
                if (player != null) {
                    if (player.level() != null) {
                        Entity entity = player.level().getEntity(message.eagleId);
                        if (entity instanceof IFalconry || entity instanceof EntityStraddleboard) {
                            entity.setPos(message.posX, message.posY, message.posZ);
                            entity.teleportTo(message.posX, message.posY, message.posZ);
                        }
                    }
                }
            });
        }
    }
}