package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.EntityCrow;
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

public class MessageCrowMountPlayer implements S2CPacket, C2SPacket {

    public int rider;
    public int mount;

    public MessageCrowMountPlayer(int rider, int mount) {
        this.rider = rider;
        this.mount = mount;
    }

    public MessageCrowMountPlayer() {
    }

    public static MessageCrowMountPlayer read(FriendlyByteBuf buf) {
        return new MessageCrowMountPlayer(buf.readInt(), buf.readInt());
    }

    public static void write(MessageCrowMountPlayer message, FriendlyByteBuf buf) {
        buf.writeInt(message.rider);
        buf.writeInt(message.mount);
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

        public static void handle(MessageCrowMountPlayer message, BlockableEventLoop<?> loop, Player player) {
            if (player != null) {
                if (player.level != null) {
                    Entity entity = player.level.getEntity(message.rider);
                    Entity mountEntity = player.level.getEntity(message.mount);
                    if (entity instanceof EntityCrow && mountEntity instanceof Player && entity.distanceTo(mountEntity) < 16D) {
                        entity.startRiding(mountEntity, true);
                    }
                }
            }
        }
    }
}