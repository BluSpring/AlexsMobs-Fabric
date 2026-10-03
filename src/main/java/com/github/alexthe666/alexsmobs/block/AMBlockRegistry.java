package com.github.alexthe666.alexsmobs.block;

import com.github.alexthe666.alexsmobs.AlexsMobs;
import com.github.alexthe666.alexsmobs.entity.AMEntityRegistry;
import com.github.alexthe666.alexsmobs.fabric.DeferredRegister;
import com.github.alexthe666.alexsmobs.item.AMBlockItem;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.item.BlockItemAMRender;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class AMBlockRegistry {
        public static final BlockBehaviour.Properties PURPUR_PLANKS_PROPERTIES = BlockBehaviour.Properties.of()
                        .mapColor(MapColor.COLOR_PINK).strength(0.5F, 1.0F).sound(SoundType.WOOD);

        public static final DeferredRegister<Block> DEF_REG = DeferredRegister.create(Registries.BLOCK,
                        AlexsMobs.MODID);
        public static final Block BANANA_PEEL = registerBlockAndItem("banana_peel",
                        () -> new BlockBananaPeel());
        public static final Block HUMMINGBIRD_FEEDER = registerBlockAndItem("hummingbird_feeder",
                        () -> new BlockHummingbirdFeeder());
        public static final Block CROCODILE_EGG = registerBlockAndItem("crocodile_egg",
                        () -> new BlockReptileEgg(AMEntityRegistry.CROCODILE));
        public static final Block GUSTMAKER = registerBlockAndItem("gustmaker",
                        () -> new BlockGustmaker());
        public static final Block STRADDLITE_BLOCK = registerBlockAndItem("straddlite_block",
                        () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL)
                                        .requiresCorrectToolForDrops()
                                        .strength(1.0F, 1200.0F).sound(SoundType.ANCIENT_DEBRIS)),
                        new Item.Properties().fireResistant(), false);
        public static final Block PLATYPUS_EGG = registerBlockAndItem("platypus_egg",
                        () -> new BlockReptileEgg(AMEntityRegistry.PLATYPUS));
        public static final Block LEAFCUTTER_ANTHILL = registerBlockAndItem("leafcutter_anthill",
                        () -> new BlockLeafcutterAnthill());
        public static final Block LEAFCUTTER_ANT_CHAMBER = registerBlockAndItem(
                        "leafcutter_ant_chamber", () -> new BlockLeafcutterAntChamber());
        public static final Block CAPSID = registerBlockAndItem("capsid",
                        () -> new BlockCapsid());
        public static final Block VOID_WORM_BEAK = registerBlockAndItem("void_worm_beak",
                        () -> new BlockVoidWormBeak());
        public static final Block VOID_WORM_EFFIGY = registerBlockAndItem("void_worm_effigy",
                        () -> new BlockVoidWormEffigy());
        public static final Block TERRAPIN_EGG = registerBlockAndItem("terrapin_egg",
                        () -> new BlockTerrapinEgg());
        public static final Block RAINBOW_GLASS = registerBlockAndItem("rainbow_glass",
                        () -> new BlockRainbowGlass());
        public static final Block BISON_FUR_BLOCK = registerBlockAndItem("bison_fur_block",
                        () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN)
                                        .strength(0.6F, 1.0F)
                                        .sound(SoundType.WOOL)));
        public static final Block BISON_CARPET = registerBlockAndItem("bison_carpet",
                        () -> new BlockBisonCarpet());
        public static final Block SAND_CIRCLE = registerBlockAndItem("sand_circle",
                        () -> new SandCircleBlock(14406560, BlockBehaviour.Properties.ofFullCopy(Blocks.SAND)),
                        new Item.Properties(), false);
        public static final Block RED_SAND_CIRCLE = registerBlockAndItem("red_sand_circle",
                        () -> new SandCircleBlock(11098145, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_SAND)),
                        new Item.Properties(), false);
        public static final Block ENDER_RESIDUE = registerBlockAndItem("ender_residue",
                        () -> new BlockEnderResidue());
        public static final Block TRANSMUTATION_TABLE = registerBlockAndItem(
                        "transmutation_table",
                        () -> new BlockTransmutationTable(), new Item.Properties().rarity(Rarity.EPIC).fireResistant(),
                        true);
        public static final Block SCULK_BOOMER = registerBlockAndItem("sculk_boomer",
                        () -> new BlockSculkBoomer());
        public static final Block SKUNK_SPRAY = DEF_REG.register("skunk_spray",
                        () -> new BlockSkunkSpray());
        public static final Block BANANA_SLUG_SLIME_BLOCK = registerBlockAndItem(
                        "banana_slug_slime_block", () -> new BlockBananaSlugSlime());
        public static final Block CRYSTALIZED_BANANA_SLUG_MUCUS = registerBlockAndItem(
                        "crystalized_banana_slug_mucus", () -> new BlockCrystalizedMucus());
        public static final Block CAIMAN_EGG = registerBlockAndItem("caiman_egg",
                        () -> new BlockReptileEgg(AMEntityRegistry.CAIMAN));
        public static final Block TRIOPS_EGGS = registerBlockAndItem("triops_eggs",
                        () -> new BlockTriopsEggs());

        public static final Block PURPUR_PLANKS = registerBlockAndItem("purpur_planks",
                        () -> new Block(PURPUR_PLANKS_PROPERTIES));
        public static final Block PURPUR_PLANKS_STAIRS = registerBlockAndItem(
                        "purpur_planks_stairs",
                        () -> new StairBlock(PURPUR_PLANKS.defaultBlockState(), PURPUR_PLANKS_PROPERTIES));
        public static final Block PURPUR_PLANKS_SLAB = registerBlockAndItem("purpur_planks_slab",
                        () -> new SlabBlock(PURPUR_PLANKS_PROPERTIES));
        public static final Block PURPUR_PLANKS_WALL = registerBlockAndItem("purpur_planks_wall",
                        () -> new WallBlock(PURPUR_PLANKS_PROPERTIES));
        
        // End Pirate blocks
        public static final Block END_PIRATE_DOOR = registerBlockAndItem("end_pirate_door", () -> new BlockEndPirateDoor());
        public static final Block END_PIRATE_TRAPDOOR = registerBlockAndItem("end_pirate_trapdoor", () -> new TrapDoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PURPLE).lightLevel((state) -> 3).strength(3.0F).sound(SoundType.GLASS).noOcclusion()));
        public static final Block END_PIRATE_ANCHOR = registerBlockAndItem("end_pirate_anchor", () -> new BlockEndPirateAnchor(), new Item.Properties(), true);
        public static final Block END_PIRATE_ANCHOR_WINCH = registerBlockAndItem("end_pirate_anchor_winch", () -> new BlockEndPirateAnchorWinch(), new Item.Properties(), true);
        public static final Block END_PIRATE_SHIP_WHEEL = registerBlockAndItem("end_pirate_ship_wheel", () -> new BlockEndPirateShipWheel(), new Item.Properties(), true);
        public static final Block END_PIRATE_FLAG = registerBlockAndItem("end_pirate_flag", () -> new BlockEndPirateFlag());
        public static final Block PHANTOM_SAIL = registerBlockAndItem("phantom_sail", () -> new BlockEndPirateSail(false));
        public static final Block SPECTRE_SAIL = registerBlockAndItem("spectre_sail", () -> new BlockEndPirateSail(true));

        public static Block registerBlockAndItem(String name, Supplier<Block> block) {
                return registerBlockAndItem(name, block, new Item.Properties(), false);
        }

        public static Block registerBlockAndItem(String name, Supplier<Block> block,
                        Item.Properties blockItemProps, boolean specialRender) {
                Block blockObj = DEF_REG.register(name, block);
                AMItemRegistry.DEF_REG.register(name,
                                () -> specialRender ? new BlockItemAMRender(blockObj, blockItemProps)
                                                : new AMBlockItem(blockObj, blockItemProps));
                return blockObj;
        }

        public static class SandCircleBlock extends FallingBlock {
                public static final MapCodec<SandCircleBlock> CODEC = RecordCodecBuilder
                                .mapCodec(instance -> instance.group(
                                                Codec.INT.fieldOf("dust_color").forGetter(b -> b.dustColor),
                                                propertiesCodec()).apply(instance, SandCircleBlock::new));

                private final int dustColor;

                public SandCircleBlock(int dustColor, Properties props) {
                        super(props);
                        this.dustColor = dustColor;
                }

                @Override
                public MapCodec<SandCircleBlock> codec() {
                        return CODEC;
                }

                @Override
                public int getDustColor(BlockState state, BlockGetter level, BlockPos pos) {
                        return dustColor;
                }
        }
}
