package com.github.alexthe666.alexsmobs.misc;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class AMAdvancementTrigger extends SimpleCriterionTrigger<AMAdvancementTrigger.Instance> {
    public final ResourceLocation resourceLocation;

    public AMAdvancementTrigger(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public void trigger(ServerPlayer p_192180_1_) {
        this.trigger(p_192180_1_, (p_226308_1_) -> {
            return true;
        });
    }

    @Override
    public Codec<Instance> codec() {
        return Instance.CODEC;
    }

    public static record Instance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<Instance> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player)
            )
                .apply(instance, Instance::new)
        );
    }
}
