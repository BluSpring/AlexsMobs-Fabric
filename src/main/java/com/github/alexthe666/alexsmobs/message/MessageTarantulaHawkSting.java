package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import com.github.alexthe666.alexsmobs.entity.EntityTarantulaHawk;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class MessageTarantulaHawkSting implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageTarantulaHawkSting> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(AlexsMobs.MODID, "tarantula_hawk_sting"));
    public static final StreamCodec<FriendlyByteBuf, MessageTarantulaHawkSting> CODEC = StreamCodec.ofMember(MessageTarantulaHawkSting::write, MessageTarantulaHawkSting::read);

    public int hawk;
    public int spider;

    public MessageTarantulaHawkSting(int rider, int mount) {
        this.hawk = rider;
        this.spider = mount;
    }

    public MessageTarantulaHawkSting() {}

    public static MessageTarantulaHawkSting read(FriendlyByteBuf buf) {
        return new MessageTarantulaHawkSting(buf.readInt(), buf.readInt());
    }

    public static void write(MessageTarantulaHawkSting message, FriendlyByteBuf buf) {
        buf.writeInt(message.hawk);
        buf.writeInt(message.spider);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    @Environment(EnvType.CLIENT)
    public static void handle(MessageTarantulaHawkSting message, ClientPlayNetworking.Context context) {
        Player player = context.player();
        context.client().execute(() -> {
            if (player != null && player.level() != null) {
                Entity entity = player.level().getEntity(message.hawk);
                Entity spider = player.level().getEntity(message.spider);
                if (entity instanceof EntityTarantulaHawk && spider instanceof LivingEntity livingSpider && livingSpider.getType().is(EntityTypeTags.ARTHROPOD)) {
                    livingSpider.addEffect(new MobEffectInstance(AMEffectRegistry.DEBILITATING_STING, EntityTarantulaHawk.STING_DURATION));
                }
            }
        });
    }
}
