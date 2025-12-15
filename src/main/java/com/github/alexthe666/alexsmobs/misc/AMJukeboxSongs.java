package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.item.JukeboxSong;

public class AMJukeboxSongs {
    public static final ResourceKey<JukeboxSong> THIME = create("thime");
    public static final ResourceKey<JukeboxSong> DAZE = create("daze");

    private static ResourceKey<JukeboxSong> create(String path) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, AlexsMobs.id(path));
    }
}
