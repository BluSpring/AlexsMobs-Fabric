package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.IDancingMob;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class MessageStartDancing implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageStartDancing> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AlexsMobs.MODID, "start_dancing"));
    public static final StreamCodec<FriendlyByteBuf, MessageStartDancing> CODEC = StreamCodec.ofMember(MessageStartDancing::write, MessageStartDancing::read);

    public int entityID;
    public boolean dance;
    public BlockPos jukeBox;

    public MessageStartDancing(int entityID, boolean dance, BlockPos jukeBox) {
        this.entityID = entityID;
        this.dance = dance;
        this.jukeBox = jukeBox;
    }

    public MessageStartDancing() {}

    public static MessageStartDancing read(FriendlyByteBuf buf) {
        return new MessageStartDancing(buf.readInt(), buf.readBoolean(), buf.readBlockPos());
    }

    public static void write(MessageStartDancing message, FriendlyByteBuf buf) {
        buf.writeInt(message.entityID);
        buf.writeBoolean(message.dance);
        buf.writeBlockPos(message.jukeBox);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(MessageStartDancing message, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            Player player = context.player();
            if (player != null && player.level() != null) {
                Entity entity = player.level().getEntity(message.entityID);
                if (entity instanceof IDancingMob dancingMob) {
                    dancingMob.setDancing(message.dance);
                    if (message.dance) {
                        dancingMob.setJukeboxPos(message.jukeBox);
                    } else {
                        dancingMob.setJukeboxPos(null);
                    }
                }
            }
        });
    }
}
