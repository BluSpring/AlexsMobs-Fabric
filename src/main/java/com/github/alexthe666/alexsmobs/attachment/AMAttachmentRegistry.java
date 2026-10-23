package com.github.alexthe666.alexsmobs.attachment;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.item.ItemRainbowJelly;
import com.mojang.serialization.Codec;

import net.minecraft.network.codec.ByteBufCodecs;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public class AMAttachmentRegistry {
    public static final AttachmentType<Integer> ROCKY_ROLL_TICKS = AttachmentRegistry.create(AlexsMobs.prefix("rocky_roll_ticks"), builder ->
        builder.persistent(Codec.INT)
            .syncWith(ByteBufCodecs.VAR_INT, AttachmentSyncPredicate.all()));

    public static final AttachmentType<Integer> ROCKY_ROLL_TIMESTAMP = AttachmentRegistry.create(AlexsMobs.prefix("rocky_roll_timestamp"), builder ->
        builder.persistent(Codec.INT)
            .syncWith(ByteBufCodecs.VAR_INT, AttachmentSyncPredicate.all()));

    public static final AttachmentType<LassoData> LASSO_DATA = AttachmentRegistry.create(AlexsMobs.prefix("lasso_data"), builder ->
        builder.persistent(LassoData.CODEC)
            .syncWith(LassoData.STREAM_CODEC, AttachmentSyncPredicate.all()));

    public static final AttachmentType<SquidGrappleData> SQUID_GRAPPLE_DATA = AttachmentRegistry.create(AlexsMobs.prefix("squid_grapple_data"), builder ->
        builder.persistent(SquidGrappleData.CODEC)
    );

    public static final AttachmentType<ItemRainbowJelly.RainbowType> RAINBOW_TYPE = AttachmentRegistry.create(AlexsMobs.prefix("rainbow_type"), builder ->
        builder.persistent(ItemRainbowJelly.RainbowType.CODEC)
            .syncWith(ItemRainbowJelly.RainbowType.STREAM_CODEC, AttachmentSyncPredicate.all())
    );

    public static final AttachmentType<EntityReference> TENDON_WHIP = AttachmentRegistry.create(AlexsMobs.prefix("tendon_whip"), builder ->
        builder.persistent(EntityReference.CODEC)
            .syncWith(EntityReference.STREAM_CODEC, AttachmentSyncPredicate.all())
            .initializer(() -> EntityReference.Empty.INSTANCE)
    );

    public static final AttachmentType<Integer> FLYING_FISH_BOOST = AttachmentRegistry.create(AlexsMobs.prefix("flying_fish_boost"), builder ->
        builder.persistent(Codec.INT)
            .syncWith(ByteBufCodecs.VAR_INT, AttachmentSyncPredicate.all())
    );

    public static void init() {}
}
