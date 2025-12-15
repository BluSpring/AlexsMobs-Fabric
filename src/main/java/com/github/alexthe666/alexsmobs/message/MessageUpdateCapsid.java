package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.tileentity.TileEntityCapsid;
import com.github.alexthe666.citadel.server.message.PacketBufferUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public record MessageUpdateCapsid(
    BlockPos blockPos,
    ItemStack heldStack
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageUpdateCapsid> TYPE = new CustomPacketPayload.Type<>(AlexsMobs.id("update_capsid"));
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageUpdateCapsid> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, MessageUpdateCapsid::blockPos,
        ItemStack.OPTIONAL_STREAM_CODEC, MessageUpdateCapsid::heldStack,
        MessageUpdateCapsid::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageUpdateCapsid message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();

                if (player != null) {
                    if (player.level() != null) {
                        BlockPos pos = message.blockPos;
                        if (player.level().getBlockEntity(pos) != null) {
                            if (player.level().getBlockEntity(pos) instanceof TileEntityCapsid podium) {
                                podium.setItem(0, message.heldStack);
                            }
                        }
                    }
                }
            });
        }
    }

}