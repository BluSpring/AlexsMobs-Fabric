package com.github.alexthe666.alexsmobs.entity.ai;

import java.util.EnumSet;

import com.github.alexthe666.alexsmobs.entity.EntitySeal;

import net.minecraft.world.entity.ai.goal.Goal;

public class SealAIBask extends Goal {
    private final EntitySeal seal;

    public SealAIBask(EntitySeal seal) {
        this.seal = seal;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }

    @Override
    public boolean canContinueToUse() {
        return this.seal.isBasking() && !this.seal.isInWaterOrBubble();
    }

    @Override
    public boolean canUse() {
        if (this.seal.isInWaterOrBubble()) {
            return false;
        } else {
            return seal.getLastHurtByMob() == null && seal.getTarget() == null && seal.isBasking();
        }
    }

    @Override
    public void tick() {
        this.seal.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.seal.setBasking(false);
    }
}
