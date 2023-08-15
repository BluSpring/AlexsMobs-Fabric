package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
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
import net.minecraft.world.entity.player.Player;


public class MessageSetPupfishChunkOnClient implements S2CPacket, C2SPacket {

    public int chunkX;
    public int chunkZ;

    public MessageSetPupfishChunkOnClient(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    public MessageSetPupfishChunkOnClient() {
    }

    public static MessageSetPupfishChunkOnClient read(FriendlyByteBuf buf) {
        return new MessageSetPupfishChunkOnClient(buf.readInt(), buf.readInt());
    }

    public static void write(MessageSetPupfishChunkOnClient message, FriendlyByteBuf buf) {
        buf.writeInt(message.chunkX);
        buf.writeInt(message.chunkZ);
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

        public static void handle(MessageSetPupfishChunkOnClient message, BlockableEventLoop<?> loop, Player player) {
            loop.execute(() -> {
                AlexsMobs.PROXY.setPupfishChunkForItem(message.chunkX, message.chunkZ);
            });
        }
    }
}