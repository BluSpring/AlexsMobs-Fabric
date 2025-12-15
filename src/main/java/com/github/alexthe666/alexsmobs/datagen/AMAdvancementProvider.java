package com.github.alexthe666.alexsmobs.datagen;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.block.AMBlockRegistry;
import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;
import com.github.alexthe666.alexsmobs.entity.AMEntityRegistry;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.misc.AMAdvancementTrigger;
import com.github.alexthe666.alexsmobs.misc.AMAdvancementTriggerRegistry;
import com.github.alexthe666.alexsmobs.misc.AMDataComponentTypeRegistry;
import com.github.alexthe666.alexsmobs.misc.AMTagRegistry;
import com.github.alexthe666.citadel.Citadel;
import com.github.alexthe666.citadel.item.component.CustomRenderDisplay;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.advancements.packs.VanillaAdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class AMAdvancementProvider implements AdvancementSubProvider {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> output) {
        AdvancementHolder root = add(output, "root", builder -> builder
            .display(AMItemRegistry.ANIMAL_DICTIONARY, Component.translatable("advancements.alexsmobs.root.title"), Component.translatable("advancements.alexsmobs.root.desc"), AlexsMobs.id("textures/advancement_background.png"), AdvancementType.TASK, true, true, false)
            .addCriterion("useitem", ConsumeItemTrigger.TriggerInstance.usedItem(AMItemRegistry.ANIMAL_DICTIONARY))
        );

        add(output, "acacia_blossom", AdvancementType.TASK, AMItemRegistry.ACACIA_BLOSSOM, builder -> builder
            .parent(root)
            .addCriterion("acacia_blossom", InventoryChangeTrigger.TriggerInstance.hasItems(AMItemRegistry.ACACIA_BLOSSOM))
        );

        AdvancementHolder crocodile = add(output, "crocodile", AdvancementType.TASK, tabIcon(AMEntityRegistry.CROCODILE.get()), builder ->
            entityInteraction(builder, AMEntityRegistry.CROCODILE.get())
                .parent(root)
        );

        add(output, "alligator_snapping_turtle", AdvancementType.TASK, tabIcon(AMEntityRegistry.ALLIGATOR_SNAPPING_TURTLE.get()), builder ->
            entityInteraction(builder, AMEntityRegistry.ALLIGATOR_SNAPPING_TURTLE.get())
                .parent(crocodile)
        );

        add(output, "bald_eagle_challenge", AdvancementType.CHALLENGE, tabIcon(AMEntityRegistry.BALD_EAGLE.get(), 98), builder ->
            customTrigger(builder, AMAdvancementTriggerRegistry.BALD_EAGLE_CHALLENGE)
                .parent(falconryHood)
        );

        AdvancementHolder banana = add(output, "banana", AdvancementType.TASK, AMItemRegistry.BANANA, builder -> builder
            .parent(root)
            .addCriterion("banana", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(AMTagRegistry.BANANAS)))
        );

        add(output, "banana_slug", AdvancementType.TASK, tabIcon(AMEntityRegistry.BANANA_SLUG.get()), builder ->
            entityInteraction(builder, AMEntityRegistry.BANANA_SLUG.get())
                .parent(banana)
        );

        add(output, "bison_spyglass", AdvancementType.CHALLENGE, tabIcon(AMEntityRegistry.BISON.get()), builder -> builder
            .parent(root)
            .addCriterion("spyglass_at_bison", lookAtThroughItem(AMEntityRegistry.BISON.get(), Items.SPYGLASS))
        );

        AdvancementHolder crimsonMosquito = add(output, "crimson_mosquito", AdvancementType.TASK, tabIcon(AMEntityRegistry.CRIMSON_MOSQUITO.get()), builder ->
            entityInteraction(builder, AMEntityRegistry.CRIMSON_MOSQUITO.get())
                .parent(root)
        );

        add(output, "blood_sprayer", AdvancementType.TASK, AMItemRegistry.BLOOD_SPRAYER, builder -> builder
            .parent(crimsonMosquito)
            .addCriterion("blood_sprayer", InventoryChangeTrigger.TriggerInstance.hasItems(AMItemRegistry.BLOOD_SPRAYER))
        );

        add(output, "breed_anteater", AdvancementType.TASK, tabIcon(AMEntityRegistry.ANTEATER.get()), builder -> builder
            .parent(leafcutterAntPupa)
            .addCriterion("breed", BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(AMEntityRegistry.ANTEATER.get())))
        );

        AdvancementHolder breedCrocodile = add(output, "breed_crocodile", AdvancementType.TASK, tabIcon(AMEntityRegistry.CROCODILE.get()), builder -> builder
            .parent(crocodile)
            .addCriterion("breed", BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(AMEntityRegistry.CROCODILE.get())))
        );

        add(output, "breed_froststalker", AdvancementType.CHALLENGE, tabIcon(AMEntityRegistry.FROSTSTALKER.get()), builder -> builder
            .parent(froststalkerHelmet)
            .addCriterion("breed", BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(AMEntityRegistry.FROSTSTALKER.get())))
        );

        add(output, "breed_hummingbird", AdvancementType.TASK, tabIcon(AMEntityRegistry.HUMMINGBIRD.get()), builder -> builder
            .parent(root)
            .addCriterion("breed", BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(AMEntityRegistry.HUMMINGBIRD.get())))
        );

        add(output, "crimson_mosquito_larva", AdvancementType.TASK, AMItemRegistry.MOSQUITO_LARVA, builder -> builder
            .parent(crimsonMosquito)
            .addCriterion("capsid", InventoryChangeTrigger.TriggerInstance.hasItems(AMItemRegistry.MOSQUITO_LARVA))
        );

        add(output, "crimson_mosquito_sick", AdvancementType.TASK, fancyItem(new CustomRenderDisplay(new ItemStack(Items.WARPED_FUNGUS), true, false, false, false, 1f)), builder ->
            customTrigger(builder, AMAdvancementTriggerRegistry.MOSQUITO_SICK)
                .parent(crimsonMosquito)
        );

        add(output, "crocodile_chestplate", AdvancementType.CHALLENGE, AMItemRegistry.CROCODILE_CHESTPLATE, builder -> builder
            .parent(breedCrocodile)
            .addCriterion("crocodile_chestplate", InventoryChangeTrigger.TriggerInstance.hasItems(AMItemRegistry.CROCODILE_CHESTPLATE))
        );

        add(output, "devils_hole_pupfish_bucket", AdvancementType.CHALLENGE, tabIcon(AMEntityRegistry.DEVILS_HOLE_PUPFISH.get()), builder -> builder
            .parent(strangeFishFinder)
            .addCriterion("devils_hole_pupfish_bucket", InventoryChangeTrigger.TriggerInstance.hasItems(AMItemRegistry.DEVILS_HOLE_PUPFISH_BUCKET))
        );

        add(output, "dimensional_carver", AdvancementType.CHALLENGE, fancyItem(new CustomRenderDisplay(new ItemStack(AMItemRegistry.DIMENSIONAL_CARVER.get()), false, false, false, true, 1f)), builder -> builder
            .parent(voidWormKill)
            .addCriterion("dimensional_carver", InventoryChangeTrigger.TriggerInstance.hasItems(AMItemRegistry.DIMENSIONAL_CARVER))
        );

        AdvancementHolder echolocator = add(output, "echolocator", AdvancementType.TASK, AMItemRegistry.ECHOLOCATOR, builder -> builder
            .parent(saveCachalotWhale)
            .addCriterion("echolocator", InventoryChangeTrigger.TriggerInstance.hasItems(AMItemRegistry.ECHOLOCATOR))
        );

        add(output, "elephant_swag", AdvancementType.TASK, tabIcon(AMEntityRegistry.ELEPHANT.get(), 98), builder ->
            customTrigger(builder, AMAdvancementTriggerRegistry.ELEPHANT_SWAG)
                .parent(tameElephant)
        );

        AdvancementHolder emu = add(output, "emu", AdvancementType.TASK, tabIcon(AMEntityRegistry.EMU.get()), builder ->
            entityInteraction(builder, AMEntityRegistry.EMU.get())
                .parent(kangaroo)
        );

        add(output, "emu_dodge", AdvancementType.TASK, Items.BLUE_BANNER, builder ->
            customTrigger(builder, AMAdvancementTriggerRegistry.EMU_DODGE)
                .parent(emu)
        );

        AdvancementHolder enderiophage = add(output, "enderiophage", AdvancementType.TASK, tabIcon(AMEntityRegistry.ENDERIOPHAGE.get()), builder ->
            entityInteraction(builder, AMEntityRegistry.ENDERIOPHAGE.get())
                .parent(root)
        );

        AdvancementHolder capsid = add(output, "capsid", AdvancementType.TASK, AMBlockRegistry.CAPSID, builder -> builder
            .parent(enderiophage)
            .addCriterion("capsid", InventoryChangeTrigger.TriggerInstance.hasItems(AMBlockRegistry.CAPSID))
        );

        add(output, "cosmic_cod", AdvancementType.TASK, AMItemRegistry.COSMIC_COD, builder -> builder
            .parent(capsid)
            .addCriterion("cosmic_cod", InventoryChangeTrigger.TriggerInstance.hasItems(AMItemRegistry.COSMIC_COD))
        );

        add(output, "ender_flu", AdvancementType.TASK, effectItem(AMEffectRegistry.ENDER_FLU), builder -> builder
            .parent(enderiophage)
            .addCriterion("all_effects", EffectsChangedTrigger.TriggerInstance.hasEffects(MobEffectsPredicate.Builder.effects().and(AMEffectRegistry.ENDER_FLU)))
        );

        add(output, "enderiophage_rocket", AdvancementType.TASK, AMItemRegistry.ENDERIOPHAGE_ROCKET, builder -> builder
            .parent(capsid)
            .addCriterion("enderiophage_rocket", InventoryChangeTrigger.TriggerInstance.hasItems(AMItemRegistry.ENDERIOPHAGE_ROCKET))
        );

        add(output, "endolocator", AdvancementType.TASK, AMItemRegistry.ENDOLOCATOR, builder -> builder
            .parent(echolocator)
            .addCriterion("endolocator", InventoryChangeTrigger.TriggerInstance.hasItems(AMItemRegistry.ENDOLOCATOR))
        );

        AdvancementHolder falconryGlove = add(output, "falconry_glove", AdvancementType.TASK, fancyItem(new CustomRenderDisplay(new ItemStack(AMItemRegistry.FALCONRY_GLOVE.get()), false, true, false, false, 1f)), builder -> builder
            .parent(tameBaldEagle)
            .addCriterion("falconry_glove", InventoryChangeTrigger.TriggerInstance.hasItems(AMItemRegistry.FALCONRY_GLOVE))
        );

        add(output, "falconry_hood", AdvancementType.TASK, AMItemRegistry.FALCONRY_HOOD, builder -> builder
            .parent(falconryGlove)
            .addCriterion("falconry_hood", InventoryChangeTrigger.TriggerInstance.hasItems(AMItemRegistry.FALCONRY_HOOD))
        );

        add(output, "farseer", AdvancementType.TASK, tabIcon(AMEntityRegistry.FARSEER.get()), builder ->
            entityInteraction(builder, AMEntityRegistry.FARSEER.get())
                .parent(shatteredDimensionalCarver)
        );

        // TODO: needs more work
    }

    private static Advancement.Builder customTrigger(Advancement.Builder builder, DeferredHolder<CriterionTrigger<?>, AMAdvancementTrigger> trigger) {
        return builder
            .addCriterion("ex", new Criterion<>(trigger.get(), new AMAdvancementTrigger.Instance(Optional.empty())))
            .requirements(new AdvancementRequirements(List.of(List.of("ex"))));
    }

    private static Criterion<UsingItemTrigger.TriggerInstance> lookAtThroughItem(EntityType<?> entity, Item item) {
        return UsingItemTrigger.TriggerInstance.lookingAt(
            EntityPredicate.Builder.entity().subPredicate(PlayerPredicate.Builder.player().setLookingAt(EntityPredicate.Builder.entity().of(entity)).build()),
            ItemPredicate.Builder.item().of(item)
        );
    }

    private Advancement.Builder entityInteraction(Advancement.Builder builder, EntityType<?> entityType) {
        return builder
            .addCriterion("killed", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityType))))
            .addCriterion("hurt_by", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity(Optional.of(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityType)).build())))
            .addCriterion("interact", PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(Optional.empty(), ItemPredicate.Builder.item(), Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityType))))))
            .requirements(new AdvancementRequirements(List.of(List.of("killed", "hurt_by", "interact"))));
    }

    private ItemStack tabIcon(EntityType<?> entityType) {
        ItemStack stack = new ItemStack(AMItemRegistry.TAB_ICON.get(), 1);
        stack.set(AMDataComponentTypeRegistry.DISPLAY_ENTITY_TYPE, BuiltInRegistries.ENTITY_TYPE.getResourceKey(entityType).orElseThrow());
        return stack;
    }

    private ItemStack tabIcon(EntityType<?> entityType, int flags) {
        ItemStack stack = new ItemStack(AMItemRegistry.TAB_ICON.get(), 1);
        stack.set(AMDataComponentTypeRegistry.DISPLAY_ENTITY_TYPE, BuiltInRegistries.ENTITY_TYPE.getResourceKey(entityType).orElseThrow());
        stack.set(AMDataComponentTypeRegistry.DISPLAY_MOB_FLAGS, flags);
        return stack;
    }

    private ItemStack fancyItem(CustomRenderDisplay display) {
        ItemStack stack = new ItemStack(Citadel.FANCY_ITEM, 1);
        stack.set(Citadel.CUSTOM_RENDER_DISPLAY, display);
        return stack;
    }

    private ItemStack effectItem(Holder<MobEffect> effect) {
        ItemStack stack = new ItemStack(Citadel.EFFECT_ITEM.get(), 1);
        stack.set(Citadel.DISPLAY_EFFECT, BuiltInRegistries.MOB_EFFECT.getResourceKey(effect.value()).orElseThrow());
        return stack;
    }

    private AdvancementHolder add(Consumer<AdvancementHolder> output, String path, UnaryOperator<Advancement.Builder> builder) {
        var advancement = builder.apply(Advancement.Builder.advancement())
            .build(AlexsMobs.id(path));

        output.accept(advancement);

        return advancement;
    }

    private AdvancementHolder add(Consumer<AdvancementHolder> output, String path, AdvancementType type, ItemLike icon, UnaryOperator<Advancement.Builder> builder) {
        return add(output, path, type, new ItemStack(icon), builder);
    }

    private AdvancementHolder add(Consumer<AdvancementHolder> output, String path, AdvancementType type, ItemStack icon, UnaryOperator<Advancement.Builder> builder) {
        var advancement = builder.apply(Advancement.Builder.advancement())
            .display(simpleDisplay(type, icon, advancementComponent(path + ".title"), advancementComponent(path + ".desc")))
            .build(AlexsMobs.id(path));
        output.accept(advancement);

        return advancement;
    }

    private Component advancementComponent(String path) {
        return Component.translatable("advancements.alexsmobs." + path);
    }

    private DisplayInfo simpleDisplay(AdvancementType type, ItemStack icon, Component title, Component description) {
        return new DisplayInfo(icon, title, description, Optional.empty(), type, true, true, false);
    }
}
