package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BannerPattern;

public class AMBannerRegistry {

    public static final LazyRegistrar<BannerPattern> DEF_REG = LazyRegistrar.create(Registry.BANNER_PATTERN_REGISTRY, AlexsMobs.MODID);

    static{
        DEF_REG.register("bear", () ->  new BannerPattern("bear"));
        DEF_REG.register("australia_0", () ->  new BannerPattern("australia_0"));
        DEF_REG.register("australia_1", () ->  new BannerPattern("australia_1"));
        DEF_REG.register("new_mexico", () ->  new BannerPattern("new_mexico"));
        DEF_REG.register("brazil", () ->  new BannerPattern("brazil"));
    }
}
