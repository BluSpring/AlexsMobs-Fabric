package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.EntityKangaroo;
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
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class MessageKangarooInventorySync implements C2SPacket, S2CPacket {

    public int kangaroo;
    public int slotId;
    public ItemStack stack;

    public MessageKangarooInventorySync(int kangaroo, int slotId, ItemStack stack) {
        this.kangaroo = kangaroo;
        this.slotId = slotId;
        this.stack = stack;
    }

    public MessageKangarooInventorySync() {
    }

    public static MessageKangarooInventorySync read(FriendlyByteBuf buf) {
        return new MessageKangarooInventorySync(buf.readInt(), buf.readInt(), buf.readItem());
    }

    public static void write(MessageKangarooInventorySync message, FriendlyByteBuf buf) {
        buf.writeInt(message.kangaroo);
        buf.writeInt(message.slotId);
        buf.writeItem(message.stack);
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

    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageKangarooInventorySync message, BlockableEventLoop<?> loop, Player player) {
            if (player != null) {
                if (player.level != null) {
                    Entity entity = player.level.getEntity(message.kangaroo);
                    if(entity instanceof EntityKangaroo && ((EntityKangaroo) entity).kangarooInventory != null){
                        if(message.slotId < 0){

                        }else{
                            ((EntityKangaroo) entity).kangarooInventory.setItem(message.slotId, message.stack);
                        }
                    }
                }
            }
        }
    }
}