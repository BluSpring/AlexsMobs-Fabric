package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.EntityBaldEagle;
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

public record MessageUpdateEagleControls(
    int eagleId,
    float rotationYaw, float rotationPitch,
    boolean chunkLoad, int overEntityId
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageUpdateEagleControls> TYPE = new CustomPacketPayload.Type<>(AlexsMobs.id("update_eagle_controls"));
    public static final StreamCodec<FriendlyByteBuf, MessageUpdateEagleControls> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, MessageUpdateEagleControls::eagleId,
        ByteBufCodecs.FLOAT, MessageUpdateEagleControls::rotationYaw,
        ByteBufCodecs.FLOAT, MessageUpdateEagleControls::rotationPitch,
        ByteBufCodecs.BOOL, MessageUpdateEagleControls::chunkLoad,
        ByteBufCodecs.VAR_INT, MessageUpdateEagleControls::overEntityId,
        MessageUpdateEagleControls::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageUpdateEagleControls message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();

                if (player != null) {
                    if (player.level() != null) {
                        Entity entity = player.level().getEntity(message.eagleId);
                        if (entity instanceof EntityBaldEagle baldEagle) {
                            Entity over = null;
                            if(message.overEntityId >= 0){
                                over = player.level().getEntity(message.overEntityId);
                            }
                            baldEagle.directFromPlayer(message.rotationYaw, message.rotationPitch, message.chunkLoad, over);
                        }
                    }
                }
            });
        }
    }
}