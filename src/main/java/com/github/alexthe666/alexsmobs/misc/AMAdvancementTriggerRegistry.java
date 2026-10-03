package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.fabric.DeferredRegister;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;

public class AMAdvancementTriggerRegistry {

    public static final DeferredRegister<CriterionTrigger<?>> DEF_REG = DeferredRegister.create(Registries.TRIGGER_TYPE, AlexsMobs.MODID);

    public static final AMAdvancementTrigger MOSQUITO_SICK = DEF_REG.register("mosquito_sick", AMAdvancementTrigger::new);
    public static final AMAdvancementTrigger EMU_DODGE = DEF_REG.register("emu_dodge", AMAdvancementTrigger::new);
    public static final AMAdvancementTrigger STOMP_LEAFCUTTER_ANTHILL = DEF_REG.register("stomp_leafcutter_anthill", AMAdvancementTrigger::new);
    public static final AMAdvancementTrigger BALD_EAGLE_CHALLENGE = DEF_REG.register("bald_eagle_challenge", AMAdvancementTrigger::new);
    public static final AMAdvancementTrigger VOID_WORM_SUMMON = DEF_REG.register("void_worm_summon", AMAdvancementTrigger::new);
    public static final AMAdvancementTrigger VOID_WORM_SPLIT = DEF_REG.register("void_worm_split", AMAdvancementTrigger::new);
    public static final AMAdvancementTrigger VOID_WORM_SLAY_HEAD = DEF_REG.register("void_worm_kill", AMAdvancementTrigger::new);
    public static final AMAdvancementTrigger SEAGULL_STEAL = DEF_REG.register("seagull_steal", AMAdvancementTrigger::new);
    public static final AMAdvancementTrigger LAVIATHAN_FOUR_PASSENGERS = DEF_REG.register("laviathan_four_passengers", AMAdvancementTrigger::new);
    public static final AMAdvancementTrigger TRANSMUTE_1000_ITEMS = DEF_REG.register("transmute_1000_items", AMAdvancementTrigger::new);
    public static final AMAdvancementTrigger UNDERMINE_UNDERMINER = DEF_REG.register("undermine_underminer", AMAdvancementTrigger::new);
    public static final AMAdvancementTrigger ELEPHANT_SWAG = DEF_REG.register("elephant_swag", AMAdvancementTrigger::new);
    public static final AMAdvancementTrigger SKUNK_SPRAY = DEF_REG.register("skunk_spray", AMAdvancementTrigger::new);

    // init() is no longer needed as triggers are registered via DeferredRegister
    public static void init(){
        // Registry triggers are now handled by DeferredRegister in AlexsMobs constructor
    }

}
