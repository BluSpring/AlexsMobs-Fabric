package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.EntityCrow;
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

public record MessageCrowMountPlayer(int rider, int mount) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageCrowMountPlayer> TYPE = new CustomPacketPayload.Type<>(AlexsMobs.id("crow_mount"));
    public static final StreamCodec<FriendlyByteBuf, MessageCrowMountPlayer> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, MessageCrowMountPlayer::rider,
        ByteBufCodecs.VAR_INT, MessageCrowMountPlayer::mount,
        MessageCrowMountPlayer::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageCrowMountPlayer message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
                

                if (player != null) {
                    if (player.level() != null) {
                        Entity entity = player.level().getEntity(message.rider);
                        Entity mountEntity = player.level().getEntity(message.mount);
                        if (entity instanceof EntityCrow && mountEntity instanceof Player && entity.distanceTo(mountEntity) < 16D) {
                            entity.startRiding(mountEntity, true);
                        }
                    }
                }
            });
        }
    }
}