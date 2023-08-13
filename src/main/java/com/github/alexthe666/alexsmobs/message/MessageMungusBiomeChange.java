package com.github.alexthe666.alexsmobs.message;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.entity.EntityMungus;
import com.github.alexthe666.alexsmobs.mixin.LevelChunkSectionAccessor;
import me.pepperbell.simplenetworking.C2SPacket;
import me.pepperbell.simplenetworking.S2CPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.Mth;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.PalettedContainer;

import java.util.function.Supplier;

public class MessageMungusBiomeChange implements S2CPacket, C2SPacket {

    public int mungusID;
    public int posX;
    public int posZ;
    public String biomeOption;

    public MessageMungusBiomeChange(int mungusID, int posX, int posY, String biomeOption) {
        this.mungusID = mungusID;
        this.posX = posX;
        this.posZ = posY;
        this.biomeOption = biomeOption;
    }

    public MessageMungusBiomeChange() {
    }

    public static MessageMungusBiomeChange read(FriendlyByteBuf buf) {
        return new MessageMungusBiomeChange(buf.readInt(), buf.readInt(), buf.readInt(), buf.readUtf());
    }

    public static void write(MessageMungusBiomeChange message, FriendlyByteBuf buf) {
        buf.writeInt(message.mungusID);
        buf.writeInt(message.posX);
        buf.writeInt(message.posZ);
        buf.writeUtf(message.biomeOption);
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

        public static void handle(MessageMungusBiomeChange message, BlockableEventLoop<?> loop, Player player) {
            loop.execute(() -> {
                if (player != null) {
                    if (player.level != null) {
                        Entity entity = player.level.getEntity(message.mungusID);
                        Registry<Biome> registry = player.level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);
                        Biome biome = registry.get(new ResourceLocation(message.biomeOption));
                        ResourceKey<Biome> resourceKey = registry.getResourceKey(biome).orElse(null);
                        Holder<Biome> holder = registry.getHolder(resourceKey).orElse(null);
                        if (AMConfig.mungusBiomeTransformationType == 2) {
                            if (entity instanceof EntityMungus && entity.distanceToSqr(message.posX, entity.getY(), message.posZ) < 1000 && biome != null) {
                                LevelChunk chunk = player.level.getChunkAt(new BlockPos(message.posX, 0, message.posZ));
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
                                    ((LevelChunkSectionAccessor) section).setBiomes(container);
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
