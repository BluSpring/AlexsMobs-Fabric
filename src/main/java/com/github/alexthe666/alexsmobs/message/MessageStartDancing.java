package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.IDancingMob;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class MessageStartDancing implements S2CPacket, C2SPacket {

    public int entityID;
    public boolean dance;
    public BlockPos jukeBox;

    public MessageStartDancing(int entityID, boolean dance, BlockPos jukeBox) {
        this.entityID = entityID;
        this.dance = dance;
        this.jukeBox = jukeBox;
    }

    public MessageStartDancing() {
    }

    public static MessageStartDancing read(FriendlyByteBuf buf) {
        return new MessageStartDancing(buf.readInt(), buf.readBoolean(), buf.readBlockPos());
    }

    public static void write(MessageStartDancing message, FriendlyByteBuf buf) {
        buf.writeInt(message.entityID);
        buf.writeBoolean(message.dance);
        buf.writeBlockPos(message.jukeBox);
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

        public static void handle(MessageStartDancing message, BlockableEventLoop<?> loop, Player player) {
            loop.execute(() -> {
                if (player != null) {
                    if (player.level != null) {
                        Entity entity = player.level.getEntity(message.entityID);
                        if (entity instanceof IDancingMob) {
                            ((IDancingMob)entity).setDancing(message.dance);
                            if(message.dance){
                                ((IDancingMob)entity).setJukeboxPos(message.jukeBox);
                            }else{
                                ((IDancingMob)entity).setJukeboxPos(null);
                            }
                        }
                    }
                }
            });
        }
    }
}