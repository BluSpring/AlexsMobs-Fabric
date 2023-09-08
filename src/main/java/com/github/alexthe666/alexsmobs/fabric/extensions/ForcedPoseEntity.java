package com.github.alexthe666.alexsmobs.fabric.extensions;

import net.minecraft.world.entity.Pose;

public interface ForcedPoseEntity {
    Pose getForcedPose();
    void setForcedPose(Pose pose);
}
