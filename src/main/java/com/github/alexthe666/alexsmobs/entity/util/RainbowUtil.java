package com.github.alexthe666.alexsmobs.entity.util;

import java.awt.Color;
import java.util.Locale;

import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.item.ItemRainbowJelly;
import com.github.alexthe666.alexsmobs.misc.AMSimplexNoise;
import com.github.alexthe666.citadel.server.entity.CitadelEntityData;
import com.github.alexthe666.citadel.server.message.PropertiesMessage;
import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class RainbowUtil {

    private static final String RAINBOW_TYPE = "RainbowTypeAlexsMobs";

    public static void setRainbowType(LivingEntity fabulous, int type) {
        CompoundTag tag = CitadelEntityData.getOrCreateCitadelTag(fabulous);
        tag.putInt(RAINBOW_TYPE, type);
        CitadelEntityData.setCitadelTag(fabulous, tag);
        if (!fabulous.level().isClientSide) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(fabulous, new PropertiesMessage("CitadelPatreonConfig", tag, fabulous.getId()));
        } else {
            PacketDistributor.sendToServer(new PropertiesMessage("CitadelPatreonConfig", tag, fabulous.getId()));
        }
    }

    public static int getRainbowType(LivingEntity entity) {
        CompoundTag lassoedTag = CitadelEntityData.getOrCreateCitadelTag(entity);
        if (lassoedTag.contains(RAINBOW_TYPE)) {
            return lassoedTag.getInt(RAINBOW_TYPE);
        }
        return 0;
    }

    public static int getRainbowTypeFromStack(ItemStack stack){
        String name = stack.getDisplayName().getString().toLowerCase(Locale.ROOT);
        return ItemRainbowJelly.RainbowType.getFromString(name).ordinal() + 1;
    }

    public static int calculateGlassColor(BlockPos pos) {
        float f = (float)AMConfig.rainbowGlassFidelity;
        float f1 = (float)((AMSimplexNoise.noise((pos.getX() + f) / f, (pos.getY() + f) / f, (pos.getZ() + f) / f) + 1.0F) * 0.5F);
        return Color.HSBtoRGB(f1, 1.0F, 1.0F);
    }
}
