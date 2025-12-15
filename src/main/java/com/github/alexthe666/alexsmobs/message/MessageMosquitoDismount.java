package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.EntityBaldEagle;
import com.github.alexthe666.alexsmobs.entity.EntityCrimsonMosquito;
import com.github.alexthe666.alexsmobs.entity.EntityEnderiophage;
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

public record MessageMosquitoDismount(
    int rider, int mount
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageMosquitoDismount> TYPE = new CustomPacketPayload.Type<>(AlexsMobs.id("mosquito_dismount"));
    public static final StreamCodec<FriendlyByteBuf, MessageMosquitoDismount> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, MessageMosquitoDismount::rider,
        ByteBufCodecs.VAR_INT, MessageMosquitoDismount::mount,
        MessageMosquitoDismount::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageMosquitoDismount message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
                

                if (player != null) {
                    if (player.level() != null) {
                        Entity entity = player.level().getEntity(message.rider);
                        Entity mountEntity = player.level().getEntity(message.mount);
                        if ((entity instanceof EntityCrimsonMosquito || entity instanceof EntityBaldEagle || entity instanceof EntityEnderiophage) && mountEntity != null) {
                            entity.stopRiding();
                        }
                    }
                }
            });
        }
    }
}