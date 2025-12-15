package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.IDancingMob;
import net.minecraft.core.BlockPos;
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

public record MessageSetDancing(
    int entityID,
    boolean dance,
    BlockPos jukeBox
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageSetDancing> TYPE = new CustomPacketPayload.Type<>(AlexsMobs.id("set_dancing"));
    public static final StreamCodec<FriendlyByteBuf, MessageSetDancing> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, MessageSetDancing::entityID,
        ByteBufCodecs.BOOL, MessageSetDancing::dance,
        BlockPos.STREAM_CODEC, MessageSetDancing::jukeBox,
        MessageSetDancing::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageSetDancing message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
                if(context.flow().getReceptionSide() == LogicalSide.CLIENT){
                    player = AlexsMobs.PROXY.getClientSidePlayer();
                }

                if (player != null) {
                    if (player.level() != null) {
                        Entity entity = player.level().getEntity(message.entityID);
                        if (entity instanceof IDancingMob dancingMob) {
                            dancingMob.setDancing(message.dance);
                            if(message.dance){
                                dancingMob.setJukeboxPos(message.jukeBox);
                            }else{
                                dancingMob.setJukeboxPos(null);
                            }
                        }
                    }
                }
            });
        }
    }
}