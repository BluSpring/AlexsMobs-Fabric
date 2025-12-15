package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AMDamageTypes {

    public static final ResourceKey<DamageType> FARSEER = ResourceKey.create(Registries.DAMAGE_TYPE, AlexsMobs.id("farseer"));
    public static final ResourceKey<DamageType> FREDDY = ResourceKey.create(Registries.DAMAGE_TYPE, AlexsMobs.id("freddy"));

    public static DamageSource causeFarseerDamage(LivingEntity attacker){
        return new DamageSourceRandomMessages(attacker.registryAccess().lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(FARSEER), attacker);
    }

    public static DamageSource causeFreddyBearDamage(LivingEntity attacker){
        return attacker.damageSources().source(FREDDY, attacker);
    }

    private static class DamageSourceRandomMessages extends DamageSource {
        public DamageSourceRandomMessages(Holder<DamageType> damageTypeHolder, @Nullable Entity entity) {
            super(damageTypeHolder, entity);
        }

        @Override
        public Component getLocalizedDeathMessage(LivingEntity attacked) {
            int type = attacked.getRandom().nextInt(3);
            LivingEntity livingentity = attacked.getKillCredit();
            String s = "death.attack." + this.getMsgId() + "_" + type;
            String s1 = s + ".player";
            return livingentity != null ? Component.translatable(s1, attacked.getDisplayName(), livingentity.getDisplayName()) : Component.translatable(s, attacked.getDisplayName());
        }
    }
}
