package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.entity.EntityMungus;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public record MessageMungusBiomeChange(
    int mungusID, int posX, int posZ,
    ResourceKey<Biome> biomeOption
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<MessageMungusBiomeChange> TYPE = new CustomPacketPayload.Type<>(AlexsMobs.id("mungus_biome_change"));
    public static final StreamCodec<FriendlyByteBuf, MessageMungusBiomeChange> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, MessageMungusBiomeChange::mungusID,
        ByteBufCodecs.VAR_INT, MessageMungusBiomeChange::posX,
        ByteBufCodecs.VAR_INT, MessageMungusBiomeChange::posZ,
        ResourceKey.streamCodec(Registries.BIOME), MessageMungusBiomeChange::biomeOption,
        MessageMungusBiomeChange::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(MessageMungusBiomeChange message, IPayloadContext context) {
            context.enqueueWork(() -> {
                Player player = context.player();
                

                if (player != null) {
                    if (player.level() != null) {
                        Entity entity = player.level().getEntity(message.mungusID);
                        HolderLookup.RegistryLookup<Biome> registry = player.level().registryAccess().lookupOrThrow(Registries.BIOME);
                        Holder<Biome> holder = registry.get(message.biomeOption).orElse(null);
                        if (AMConfig.mungusBiomeTransformationType == 2 && holder != null) {
                            if (entity instanceof EntityMungus && entity.distanceToSqr(message.posX, entity.getY(), message.posZ) < 1000) {
                                LevelChunk chunk = player.level().getChunkAt(new BlockPos(message.posX, 0, message.posZ));
                                int i = QuartPos.fromBlock(chunk.getMinBuildHeight());
                                int k = i + QuartPos.fromBlock(chunk.getHeight()) - 1;
                                int l = Mth.clamp(QuartPos.fromBlock((int)entity.getY()), i, k);
                                int j = chunk.getSectionIndex(QuartPos.toBlock(l));
                                LevelChunkSection section = chunk.getSection(j);
                                if(section != null){
                                    PalettedContainer<Holder<Biome>> container = section.getBiomes().recreate();
                                    for (int biomeX = 0; biomeX < 4; ++biomeX) {
                                        for (int biomeY = 0; biomeY < 4; ++biomeY) {
                                            for (int biomeZ = 0; biomeZ < 4; ++biomeZ) {
                                                container.getAndSetUnchecked(biomeX, biomeY, biomeZ, holder);
                                            }
                                        }
                                    }
                                    section.biomes = container;
                                }
                                AlexsMobs.PROXY.updateBiomeVisuals(message.posX, message.posZ);
                            }
                        }
                    }
                }
            });

        }
    }

}
