package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AMBannerRegistry {

    public static final DeferredRegister<BannerPattern> DEF_REG = DeferredRegister.create(Registries.BANNER_PATTERN, AlexsMobs.MODID);

    public static final DeferredHolder<BannerPattern, BannerPattern> BEAR = DEF_REG.register("bear", id ->  new BannerPattern(id, "item.alexsmobs.banner_pattern_" + id.getPath() + ".desc"));
    public static final DeferredHolder<BannerPattern, BannerPattern> AUSTRALIA_0 = DEF_REG.register("australia_0", id ->  new BannerPattern(id, "item.alexsmobs.banner_pattern_" + id.getPath() + ".desc"));
    public static final DeferredHolder<BannerPattern, BannerPattern> AUSTRALIA_1 = DEF_REG.register("australia_1", id ->  new BannerPattern(id, "item.alexsmobs.banner_pattern_" + id.getPath() + ".desc"));
    public static final DeferredHolder<BannerPattern, BannerPattern> NEW_MEXICO = DEF_REG.register("new_mexico", id ->  new BannerPattern(id, "item.alexsmobs.banner_pattern_" + id.getPath() + ".desc"));
    public static final DeferredHolder<BannerPattern, BannerPattern> BRAZIL = DEF_REG.register("brazil", id ->  new BannerPattern(id, "item.alexsmobs.banner_pattern_" + id.getPath() + ".desc"));
}
