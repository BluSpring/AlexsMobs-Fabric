package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.citadel.server.message.PacketBufferUtils;
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
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class MessageUpdateTransmutablesToDisplay implements S2CPacket, C2SPacket {

    private int playerId;
    public ItemStack stack1;
    public ItemStack stack2;
    public ItemStack stack3;

    public MessageUpdateTransmutablesToDisplay(int playerId, ItemStack stack1, ItemStack stack2, ItemStack stack3) {
        this.stack1 = stack1;
        this.stack2 = stack2;
        this.stack3 = stack3;
        this.playerId = playerId;
    }

    public MessageUpdateTransmutablesToDisplay() {
    }

    public static MessageUpdateTransmutablesToDisplay read(FriendlyByteBuf buf) {
        return new MessageUpdateTransmutablesToDisplay(buf.readInt(), PacketBufferUtils.readItemStack(buf), PacketBufferUtils.readItemStack(buf), PacketBufferUtils.readItemStack(buf));
    }

    public static void write(MessageUpdateTransmutablesToDisplay message, FriendlyByteBuf buf) {
        buf.writeInt(message.playerId);
        PacketBufferUtils.writeItemStack(buf, message.stack1);
        PacketBufferUtils.writeItemStack(buf, message.stack2);
        PacketBufferUtils.writeItemStack(buf, message.stack3);
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

        public static void handle(MessageUpdateTransmutablesToDisplay message, BlockableEventLoop<?> loop, Player player) {
            if(player.getId() == message.playerId){
                AlexsMobs.PROXY.setDisplayTransmuteResult(0, message.stack1);
                AlexsMobs.PROXY.setDisplayTransmuteResult(1, message.stack2);
                AlexsMobs.PROXY.setDisplayTransmuteResult(2, message.stack3);
            }
        }
    }

}