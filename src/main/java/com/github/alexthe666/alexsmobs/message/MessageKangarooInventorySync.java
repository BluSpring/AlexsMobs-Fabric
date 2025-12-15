package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.EntityKangaroo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public record MessageKangarooInventorySync(
    int kangaroo, int slotId,
    ItemStack stack
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageKangarooInventorySync> TYPE = new CustomPacketPayload.Type<>(AlexsMobs.id("kangaroo_inventory_sync"));
    public static final StreamCodec<RegistryFriendlyByteBuf, MessageKangarooInventorySync> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, MessageKangarooInventorySync::kangaroo,
        ByteBufCodecs.VAR_INT, MessageKangarooInventorySync::slotId,
        ItemStack.OPTIONAL_STREAM_CODEC, MessageKangarooInventorySync::stack,
        MessageKangarooInventorySync::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageKangarooInventorySync message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
                

                if (player != null) {
                    if (player.level() != null) {
                        Entity entity = player.level().getEntity(message.kangaroo);
                        if (entity instanceof EntityKangaroo kangaroo && kangaroo.kangarooInventory != null) {
                            if (message.slotId < 0) {

                            } else {
                                kangaroo.kangarooInventory.setItem(message.slotId, message.stack);
                            }
                        }
                    }
                }
            });
        }
    }
}