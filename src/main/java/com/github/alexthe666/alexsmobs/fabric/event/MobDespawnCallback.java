package com.github.alexthe666.alexsmobs.fabric.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.world.entity.Mob;

public interface MobDespawnCallback {
    Event<MobDespawnCallback> EVENT = EventFactory.createEventResult();

    EventResult shouldAllowDespawn(Mob mob);
}
