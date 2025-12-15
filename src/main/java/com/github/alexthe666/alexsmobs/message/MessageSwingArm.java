package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.item.ILeftClick;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public class MessageSwingArm implements CustomPacketPayload {

    public static final MessageSwingArm INSTANCE = new MessageSwingArm();
    public static final Type<MessageSwingArm> TYPE = new Type<>(AlexsMobs.id("swing_arm"));
    public static final StreamCodec<FriendlyByteBuf, MessageSwingArm> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    private MessageSwingArm() {
    }

    public static class Handler {

        public Handler() {
        }

        public static void handle(MessageSwingArm message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
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
