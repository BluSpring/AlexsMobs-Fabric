package com.github.alexthe666.alexsmobs.inventory;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;

public class AMMenuRegistry {

    public static final LazyRegistrar<MenuType<?>> DEF_REG = LazyRegistrar.create(Registry.MENU_REGISTRY, AlexsMobs.MODID);

    public static final RegistryObject<MenuType<MenuTransmutationTable>> TRANSMUTATION_TABLE = DEF_REG.register("transmutation_table", () -> new MenuType<MenuTransmutationTable>(MenuTransmutationTable::new));

}
