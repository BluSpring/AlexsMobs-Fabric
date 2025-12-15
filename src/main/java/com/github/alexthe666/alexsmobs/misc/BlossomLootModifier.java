package com.github.alexthe666.alexsmobs.misc;

import com.github.alexthe666.alexsmobs.config.AMConfig;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BlossomLootModifier implements IGlobalLootModifier {

    public static final MapCodec<BlossomLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
        inst.group(
            LOOT_CONDITIONS_CODEC.fieldOf("conditions")
                .forGetter(lm -> lm.conditions)
        )
            .apply(inst, BlossomLootModifier::new)
    );

    private final LootItemCondition[] conditions;

    private final Predicate<LootContext> orConditions;

    public BlossomLootModifier(LootItemCondition[] conditionsIn) {
        this.conditions = conditionsIn;
        this.orConditions = Util.anyOf(List.of(conditionsIn));
    }

    @NotNull
    @Override
    public ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        return this.orConditions.test(context) ? this.doApply(generatedLoot, context) : generatedLoot;
    }

    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (AMConfig.acaciaBlossomsDropFromLeaves) {
            ItemStack ctxTool = context.getParamOrNull(LootContextParams.TOOL);
            RandomSource random = context.getRandom();
            HolderGetter<Enchantment> enchantmentRegistry = context.getResolver().lookupOrThrow(Registries.ENCHANTMENT);
            if (ctxTool != null) {
                int silkTouch = EnchantmentHelper.getItemEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.SILK_TOUCH), ctxTool);
                if (silkTouch > 0 || ctxTool.getItem() instanceof ShearsItem) {
                    return generatedLoot;
                }
            }
            int bonusLevel = ctxTool != null ? EnchantmentHelper.getItemEnchantmentLevel(enchantmentRegistry.getOrThrow(Enchantments.FORTUNE), ctxTool) : 0;
            int blossomStep = (int) Math.floor(AMConfig.acaciaBlossomChance * 0.1F);
            int blossomRarity = AMConfig.acaciaBlossomChance - (bonusLevel * blossomStep);
            if (blossomRarity < 1 || random.nextInt(blossomRarity) == 0) {
                generatedLoot.add(new ItemStack(AMItemRegistry.ACACIA_BLOSSOM.get()));
            }
        }
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}