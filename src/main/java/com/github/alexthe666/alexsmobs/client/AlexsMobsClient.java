package com.github.alexthe666.alexsmobs.client;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.client.model.layered.AMModelLayers;

import net.fabricmc.api.ClientModInitializer;

public class AlexsMobsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AlexsMobs.PROXY.clientInit();
        AMModelLayers.register();
    }
}
