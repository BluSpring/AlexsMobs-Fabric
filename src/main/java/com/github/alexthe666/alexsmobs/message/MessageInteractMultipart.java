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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class MessageInteractMultipart implements S2CPacket, C2SPacket {

    public boolean offhand;
    public int parent;

    public MessageInteractMultipart(int parent, boolean offhand) {
        this.parent = parent;
        this.offhand = offhand;
    }


    public MessageInteractMultipart() {
    }

    public static MessageInteractMultipart read(FriendlyByteBuf buf) {
        return new MessageInteractMultipart(buf.readInt(), buf.readBoolean());
    }

    public static void write(MessageInteractMultipart message, FriendlyByteBuf buf) {
        buf.writeInt(message.parent);
        buf.writeBoolean(message.offhand);
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

        public static void handle(MessageInteractMultipart message, BlockableEventLoop<?> loop, Player player) {
            if (player != null) {
                if (player.level != null) {
                    Entity parent = player.level.getEntity(message.parent);
                    if(player.distanceTo(parent) < 20 && parent instanceof Mob){
                        player.interactOn(parent, message.offhand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
                    }
                }
            }
        }
    }
}