package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.item.ILeftClick;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class MessageSwingArm implements S2CPacket, C2SPacket {

    public static final MessageSwingArm INSTANCE = new MessageSwingArm();

    private MessageSwingArm() {
    }

    public static MessageSwingArm read(FriendlyByteBuf buf) {
        return INSTANCE;
    }

    public static void write(MessageSwingArm message, FriendlyByteBuf buf) {
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

        public static void handle(MessageSwingArm message, BlockableEventLoop<?> loop, Player player) {
            loop.execute(() -> {
                if (player != null) {
                    ItemStack leftItem = player.getItemInHand(InteractionHand.OFF_HAND);
                    ItemStack rightItem = player.getItemInHand(InteractionHand.MAIN_HAND);
                    if(leftItem.getItem() instanceof ILeftClick){
                        ((ILeftClick)leftItem.getItem()).onLeftClick(leftItem, player);
                    }
                    if(rightItem.getItem() instanceof ILeftClick){
                        ((ILeftClick)rightItem.getItem()).onLeftClick(rightItem, player);
                    }
                }
            });
        }
    }

}
