package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.fabric.DeferredRegister;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.item.CustomTabBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

public class AMCreativeTabRegistry {


    public static final DeferredRegister<CreativeModeTab> DEF_REG = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AlexsMobs.MODID);

    public static final CreativeModeTab TAB = DEF_REG.register(AlexsMobs.MODID, () -> FabricItemGroup.builder()
            .title(Component.translatable("itemGroup." + AlexsMobs.MODID))
//            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .icon(() -> new ItemStack(AMItemRegistry.TAB_ICON))
            .displayItems((enabledFeatures, output) -> {
                for(Item item : AMItemRegistry.DEF_REG.getEntries()){
                    if(item instanceof CustomTabBehavior customTabBehavior){
                        customTabBehavior.fillItemCategory(output);
                    }else{
                        output.accept(item);
                    }
                }
            })
            .build());
}
