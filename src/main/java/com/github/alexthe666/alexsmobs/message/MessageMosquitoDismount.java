package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.EntityBaldEagle;
import com.github.alexthe666.alexsmobs.entity.EntityCrimsonMosquito;
import com.github.alexthe666.alexsmobs.entity.EntityEnderiophage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class MessageMosquitoDismount implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageMosquitoDismount> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AlexsMobs.MODID, "mosquito_dismount"));
    public static final StreamCodec<FriendlyByteBuf, MessageMosquitoDismount> CODEC = StreamCodec.ofMember(MessageMosquitoDismount::write, MessageMosquitoDismount::read);

    public int rider;
    public int mount;

    public MessageMosquitoDismount(int rider, int mount) {
        this.rider = rider;
        this.mount = mount;
    }

    public MessageMosquitoDismount() {}

    public static MessageMosquitoDismount read(FriendlyByteBuf buf) {
        return new MessageMosquitoDismount(buf.readInt(), buf.readInt());
    }

    public static void write(MessageMosquitoDismount message, FriendlyByteBuf buf) {
        buf.writeInt(message.rider);
        buf.writeInt(message.mount);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(MessageMosquitoDismount message, ServerPlayNetworking.Context context) {
        handle(message, context.server(), context.player());
    }

    @Environment(EnvType.CLIENT)
    public static void handle(MessageMosquitoDismount message, ClientPlayNetworking.Context context) {
        handle(message, context.client(), context.player());
    }

    public static void handle(MessageMosquitoDismount message, BlockableEventLoop<?> context, Player player) {
        context.execute(() -> {
            if (player != null && player.level() != null) {
                Entity entity = player.level().getEntity(message.rider);
                Entity mountEntity = player.level().getEntity(message.mount);
                if ((entity instanceof EntityCrimsonMosquito || entity instanceof EntityBaldEagle || entity instanceof EntityEnderiophage) && mountEntity != null) {
                    entity.stopRiding();
                }
            }
        });
    }
}
