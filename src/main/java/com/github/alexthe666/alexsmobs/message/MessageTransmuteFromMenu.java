package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.inventory.MenuTransmutationTable;
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
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class MessageTransmuteFromMenu implements S2CPacket, C2SPacket {

    private int playerId;
    private int choice;

    public MessageTransmuteFromMenu(int playerId, int choice) {
        this.playerId = playerId;
        this.choice = choice;
    }

    public MessageTransmuteFromMenu() {
    }

    public static MessageTransmuteFromMenu read(FriendlyByteBuf buf) {
        return new MessageTransmuteFromMenu(buf.readInt(), buf.readInt());
    }

    public static void write(MessageTransmuteFromMenu message, FriendlyByteBuf buf) {
        buf.writeInt(message.playerId);
        buf.writeInt(message.choice);
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

        public static void handle(MessageTransmuteFromMenu message, BlockableEventLoop<?> loop, Player player) {
            if(player.getId() == message.playerId && player.containerMenu instanceof MenuTransmutationTable){
                MenuTransmutationTable table = (MenuTransmutationTable) player.containerMenu;
                table.transmute(player, message.choice);
            }
        }
    }

}