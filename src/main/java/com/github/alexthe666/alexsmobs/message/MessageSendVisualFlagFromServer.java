package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
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

public class MessageSendVisualFlagFromServer implements S2CPacket, C2SPacket {

    public int entityID;
    public int flag;

    public MessageSendVisualFlagFromServer(int entityID, int flag) {
        this.entityID = entityID;
        this.flag = flag;
    }

    public MessageSendVisualFlagFromServer() {
    }

    public static MessageSendVisualFlagFromServer read(FriendlyByteBuf buf) {
        return new MessageSendVisualFlagFromServer(buf.readInt(), buf.readInt());
    }

    public static void write(MessageSendVisualFlagFromServer message, FriendlyByteBuf buf) {
        buf.writeInt(message.entityID);
        buf.writeInt(message.flag);
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {
        Handler.handle(this, server, player);
    }

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

        public static void handle(MessageSendVisualFlagFromServer message, BlockableEventLoop<?> loop, Player player) {
            loop.execute(() -> {
                if (player != null) {
                    if (player.level != null) {
                        Entity entity = player.level.getEntity(message.entityID);
                        AlexsMobs.PROXY.processVisualFlag(entity, message.flag);
                    }
                }
            });
        }
    }
}