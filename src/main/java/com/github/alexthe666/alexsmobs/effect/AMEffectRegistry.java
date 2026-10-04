package com.github.alexthe666.alexsmobs.effect;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.fabric.DeferredRegister;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;

public class AMEffectRegistry {
    public static final DeferredRegister<MobEffect> EFFECT_DEF_REG = DeferredRegister.create(Registries.MOB_EFFECT, AlexsMobs.MODID);
    public static final DeferredRegister<Potion> POTION_DEF_REG = DeferredRegister.create(Registries.POTION, AlexsMobs.MODID);

    public static final Holder<MobEffect> KNOCKBACK_RESISTANCE = EFFECT_DEF_REG.registerHolder("knockback_resistance", ()-> new EffectKnockbackResistance());
    public static final Holder<MobEffect> LAVA_VISION = EFFECT_DEF_REG.registerHolder("lava_vision", ()-> new EffectLavaVision());
    public static final Holder<MobEffect> SUNBIRD_BLESSING = EFFECT_DEF_REG.registerHolder("sunbird_blessing", ()-> new EffectSunbird(false));
    public static final Holder<MobEffect> SUNBIRD_CURSE = EFFECT_DEF_REG.registerHolder("sunbird_curse", ()-> new EffectSunbird(true));
    public static final Holder<MobEffect> POISON_RESISTANCE = EFFECT_DEF_REG.registerHolder("poison_resistance", ()-> new EffectPoisonResistance());
    public static final Holder<MobEffect> OILED = EFFECT_DEF_REG.registerHolder("oiled", ()-> new EffectOiled());
    public static final Holder<MobEffect> ORCAS_MIGHT = EFFECT_DEF_REG.registerHolder("orcas_might", ()-> new EffectOrcaMight());
    public static final Holder<MobEffect> BUG_PHEROMONES = EFFECT_DEF_REG.registerHolder("bug_pheromones", ()-> new EffectBugPheromones());
    public static final Holder<MobEffect> SOULSTEAL = EFFECT_DEF_REG.registerHolder("soulsteal", ()-> new EffectSoulsteal());
    public static final Holder<MobEffect> CLINGING = EFFECT_DEF_REG.registerHolder("clinging", ()-> new EffectClinging());
    public static final Holder<MobEffect> ENDER_FLU = EFFECT_DEF_REG.registerHolder("ender_flu", ()-> new EffectEnderFlu());
    public static final Holder<MobEffect> FEAR = EFFECT_DEF_REG.registerHolder("fear", ()-> new EffectFear());
    public static final Holder<MobEffect> TIGERS_BLESSING = EFFECT_DEF_REG.registerHolder("tigers_blessing", ()-> new EffectTigersBlessing());
    public static final Holder<MobEffect> DEBILITATING_STING = EFFECT_DEF_REG.registerHolder("debilitating_sting", ()-> new EffectDebilitatingSting());
    public static final Holder<MobEffect> EXSANGUINATION = EFFECT_DEF_REG.registerHolder("exsanguination", ()-> new EffectExsanguination());
    public static final Holder<MobEffect> EARTHQUAKE = EFFECT_DEF_REG.registerHolder("earthquake", ()-> new EffectEarthquake());
    public static final Holder<MobEffect> FLEET_FOOTED = EFFECT_DEF_REG.registerHolder("fleet_footed", ()-> new EffectFleetFooted());
    public static final Holder<MobEffect> POWER_DOWN = EFFECT_DEF_REG.registerHolder("power_down", ()-> new EffectPowerDown());

    public static final Holder<MobEffect> MOSQUITO_REPELLENT = EFFECT_DEF_REG.registerHolder("mosquito_repellent", ()-> new EffectMosquitoRepellent());
    public static final Holder<Potion> KNOCKBACK_RESISTANCE_POTION = POTION_DEF_REG.registerHolder("knockback_resistance", ()-> new Potion(new MobEffectInstance(KNOCKBACK_RESISTANCE, 3600)));
    public static final Holder<Potion> LONG_KNOCKBACK_RESISTANCE_POTION = POTION_DEF_REG.registerHolder("long_knockback_resistance", ()-> new Potion(new MobEffectInstance(KNOCKBACK_RESISTANCE, 9600)));
    public static final Holder<Potion> STRONG_KNOCKBACK_RESISTANCE_POTION = POTION_DEF_REG.registerHolder("strong_knockback_resistance", ()-> new Potion(new MobEffectInstance(KNOCKBACK_RESISTANCE, 1800, 1)));
    public static final Holder<Potion> LAVA_VISION_POTION = POTION_DEF_REG.registerHolder("lava_vision", ()-> new Potion(new MobEffectInstance(LAVA_VISION, 3600)));
    public static final Holder<Potion> LONG_LAVA_VISION_POTION = POTION_DEF_REG.registerHolder("long_lava_vision", ()-> new Potion(new MobEffectInstance(LAVA_VISION, 9600)));
    public static final Holder<Potion> SPEED_III_POTION = POTION_DEF_REG.registerHolder("speed_iii", ()-> new Potion(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2200, 2)));
    public static final Holder<Potion> POISON_RESISTANCE_POTION = POTION_DEF_REG.registerHolder("poison_resistance", ()-> new Potion(new MobEffectInstance(POISON_RESISTANCE, 3600)));
    public static final Holder<Potion> LONG_POISON_RESISTANCE_POTION = POTION_DEF_REG.registerHolder("long_poison_resistance", ()-> new Potion(new MobEffectInstance(POISON_RESISTANCE, 9600)));
    public static final Holder<Potion> BUG_PHEROMONES_POTION = POTION_DEF_REG.registerHolder("bug_pheromones", ()-> new Potion(new MobEffectInstance(BUG_PHEROMONES, 3600)));
    public static final Holder<Potion> LONG_BUG_PHEROMONES_POTION = POTION_DEF_REG.registerHolder("long_bug_pheromones", ()-> new Potion(new MobEffectInstance(BUG_PHEROMONES, 9600)));
    public static final Holder<Potion> SOULSTEAL_POTION = POTION_DEF_REG.registerHolder("soulsteal", ()-> new Potion(new MobEffectInstance(SOULSTEAL, 3600)));
    public static final Holder<Potion> LONG_SOULSTEAL_POTION = POTION_DEF_REG.registerHolder("long_soulsteal", ()-> new Potion(new MobEffectInstance(SOULSTEAL, 9600)));
    public static final Holder<Potion> STRONG_SOULSTEAL_POTION = POTION_DEF_REG.registerHolder("strong_soulsteal", ()-> new Potion(new MobEffectInstance(SOULSTEAL, 1800, 1)));
    public static final Holder<Potion> CLINGING_POTION = POTION_DEF_REG.registerHolder("clinging", ()-> new Potion(new MobEffectInstance(CLINGING, 3600)));
    public static final Holder<Potion> LONG_CLINGING_POTION = POTION_DEF_REG.registerHolder("long_clinging", ()-> new Potion(new MobEffectInstance(CLINGING, 9600)));

    public static ItemStack createPotion(Holder<Potion> potion){
        ItemStack stack = new ItemStack(Items.POTION);
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(potion));
        return stack;
    }

    public static void registerBrewingRecipes(PotionBrewing.Builder builder){
        builder.addMix(Potions.STRENGTH, AMItemRegistry.BEAR_FUR, KNOCKBACK_RESISTANCE_POTION);
        builder.addMix(KNOCKBACK_RESISTANCE_POTION, Items.REDSTONE, LONG_KNOCKBACK_RESISTANCE_POTION);
        builder.addMix(KNOCKBACK_RESISTANCE_POTION, Items.GLOWSTONE_DUST, STRONG_KNOCKBACK_RESISTANCE_POTION);
        builder.addRecipe(new ProperBrewingRecipe(Ingredient.of(AMItemRegistry.KOMODO_SPIT_BOTTLE), Ingredient.of(AMItemRegistry.RATTLESNAKE_RATTLE), new ItemStack(AMItemRegistry.POISON_BOTTLE)));
        builder.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(Potions.POISON)), Ingredient.of(AMItemRegistry.RATTLESNAKE_RATTLE), new ItemStack(AMItemRegistry.POISON_BOTTLE)));
        builder.addRecipe(new ProperBrewingRecipe(Ingredient.of(AMItemRegistry.KOMODO_SPIT_BOTTLE), Ingredient.of(AMItemRegistry.CENTIPEDE_LEG), new ItemStack(AMItemRegistry.POISON_BOTTLE)));
        builder.addRecipe(new ProperBrewingRecipe(Ingredient.of(AMItemRegistry.POISON_BOTTLE), Ingredient.of(AMItemRegistry.CENTIPEDE_LEG), createPotion(POISON_RESISTANCE_POTION)));
        builder.addMix(POISON_RESISTANCE_POTION, AMItemRegistry.KOMODO_SPIT, LONG_POISON_RESISTANCE_POTION);
        builder.addMix(Potions.STRONG_SWIFTNESS, AMItemRegistry.GAZELLE_HORN, SPEED_III_POTION);
        builder.addMix(Potions.AWKWARD, AMItemRegistry.COCKROACH_WING, BUG_PHEROMONES_POTION);
        builder.addMix(BUG_PHEROMONES_POTION, Items.REDSTONE, LONG_BUG_PHEROMONES_POTION);
        builder.addMix(Potions.AWKWARD, AMItemRegistry.SOUL_HEART, SOULSTEAL_POTION);
        builder.addMix(SOULSTEAL_POTION, Items.REDSTONE, LONG_SOULSTEAL_POTION);
        builder.addMix(SOULSTEAL_POTION, Items.GLOWSTONE_DUST, STRONG_SOULSTEAL_POTION);
        builder.addMix(Potions.AWKWARD, AMItemRegistry.DROPBEAR_CLAW, CLINGING_POTION);
        builder.addMix(CLINGING_POTION, Items.REDSTONE, LONG_CLINGING_POTION);
        builder.addRecipe(new ProperBrewingRecipe(Ingredient.of(AMItemRegistry.LAVA_BOTTLE), Ingredient.of(AMItemRegistry.BONE_SERPENT_TOOTH), createPotion(LAVA_VISION_POTION)));
        builder.addMix(LAVA_VISION_POTION, Items.REDSTONE, LONG_LAVA_VISION_POTION);
    }

    public static void init(){
        // Brewing recipes are now registered via RegisterBrewingRecipesEvent
    }
}
