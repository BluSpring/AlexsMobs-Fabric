package com.github.alexthe666.alexsmobs.entity.util;

import com.mojang.serialization.Codec;

public enum AnacondaPartIndex {
    HEAD(0F), NECK(0.5F), BODY(0.5F), TAIL(0.4F);

    public static final Codec<AnacondaPartIndex> CODEC = Codec.INT.xmap(AnacondaPartIndex::fromOrdinal, AnacondaPartIndex::ordinal);

    private final float backOffset;

    AnacondaPartIndex(float partOffset){
        this.backOffset = partOffset;
    }

    public static AnacondaPartIndex fromOrdinal(int i) {
        return switch (i) {
            case 0 -> HEAD;
            case 1 -> NECK;
            case 3 -> TAIL;
            default -> BODY; // case 2 and others
        };

    }

    public static AnacondaPartIndex sizeAt(int bodyindex) {
        return switch (bodyindex) {
            case 0 -> HEAD;
            case 1, 5, 6 -> NECK;
            case 7 -> TAIL;
            default -> BODY; // cases 2, 3, 4 and others
        };
    }

    public float getBackOffset() {
        return backOffset;
    }
}
