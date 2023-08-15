package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.EntityBaldEagle;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class MessageUpdateEagleControls implements S2CPacket, C2SPacket {

    public int eagleId;
    public float rotationYaw;
    public float rotationPitch;
    public boolean chunkLoad;
    public int overEntityId;

    public MessageUpdateEagleControls(int eagleId, float rotationYaw, float rotationPitch, boolean chunkLoad, int overEntityId) {
        this.eagleId = eagleId;
        this.rotationYaw = rotationYaw;
        this.rotationPitch = rotationPitch;
        this.chunkLoad = chunkLoad;
        this.overEntityId = overEntityId;
    }

    public MessageUpdateEagleControls() {
    }

    public static MessageUpdateEagleControls read(FriendlyByteBuf buf) {
        return new MessageUpdateEagleControls(buf.readInt(), buf.readFloat(), buf.readFloat(), buf.readBoolean(), buf.readInt());
    }

    public static void write(MessageUpdateEagleControls message, FriendlyByteBuf buf) {
        buf.writeInt(message.eagleId);
        buf.writeFloat(message.rotationYaw);
        buf.writeFloat(message.rotationPitch);
        buf.writeBoolean(message.chunkLoad);
        buf.writeInt(message.overEntityId);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {
        Handler.handle(this, server, player);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handle(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel) {
        Handler.handle(this, client, AlexsMobs.PROXY.getClientSidePlayer());
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        write(this, buf);
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageUpdateEagleControls message, BlockableEventLoop<?> loop, Player player) {
            loop.execute(() -> {
                if (player != null) {
                    if (player.level != null) {
                        Entity entity = player.level.getEntity(message.eagleId);
                        if (entity instanceof EntityBaldEagle) {
                            Entity over = null;
                            if(message.overEntityId >= 0){
                                over = player.level.getEntity(message.overEntityId);
                            }
                            ((EntityBaldEagle) entity).directFromPlayer(message.rotationYaw, message.rotationPitch, message.chunkLoad, over);
                        }
                    }
                }
            });
        }
    }
}