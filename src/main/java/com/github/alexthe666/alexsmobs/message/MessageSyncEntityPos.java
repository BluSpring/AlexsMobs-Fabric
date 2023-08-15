package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.EntityStraddleboard;
import com.github.alexthe666.alexsmobs.entity.IFalconry;
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

public class MessageSyncEntityPos implements C2SPacket, S2CPacket {

    public int eagleId;
    public double posX;
    public double posY;
    public double posZ;

    public MessageSyncEntityPos(int eagleId, double posX, double posY, double posZ) {
        this.eagleId = eagleId;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public MessageSyncEntityPos() {
    }

    public static MessageSyncEntityPos read(FriendlyByteBuf buf) {
        return new MessageSyncEntityPos(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void write(MessageSyncEntityPos message, FriendlyByteBuf buf) {
        buf.writeInt(message.eagleId);
        buf.writeDouble(message.posX);
        buf.writeDouble(message.posY);
        buf.writeDouble(message.posZ);
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

        public static void handle(MessageSyncEntityPos message, BlockableEventLoop<?> loop, Player player) {
            loop.execute(() -> {
                if (player != null) {
                    if (player.level != null) {
                        Entity entity = player.level.getEntity(message.eagleId);
                        if (entity instanceof IFalconry || entity instanceof EntityStraddleboard) {
                            entity.setPos(message.posX, message.posY, message.posZ);
                            entity.teleportToWithTicket(message.posX, message.posY, message.posZ);
                        }
                    }
                }
            });
        }
    }
}