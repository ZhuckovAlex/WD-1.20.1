package ru.imaginaerum.wd.common.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.imaginaerum.wd.WD;
import ru.imaginaerum.wd.common.ModWoodType;
import ru.imaginaerum.wd.common.blocks.custom.*;
import ru.imaginaerum.wd.common.items.ItemsWD;
import ru.imaginaerum.wd.common.trees.AppleTreeGrower;
import java.util.function.Supplier;

public class BlocksWD {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, WD.MODID);

    public static final RegistryObject<Block> BERRIES_WAFFLES = BLOCKS.register("berries_waffles",
            () -> new BerriesWaffles(BlockBehaviour.Properties.of().randomTicks().sound(SoundType.WOOL)));
    // Растения
    public static final RegistryObject<Block> FIRE_STEM = BLOCKS.register("fire_stem",
            () -> new FireRod(BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> POTTED_FIRE_STEM = BLOCKS.register("potted_fire_stem",
            () -> new FlowerPotBlock(FIRE_STEM.get(), BlockBehaviour.Properties.of().instabreak().sound(SoundType.STONE)));
    public static final RegistryObject<Block> WARPED_WART = BLOCKS.register("warped_wart",
            () -> new WarpedWartBlock(BlockBehaviour.Properties.of().instabreak()
                    .noCollission().randomTicks().sound(SoundType.NETHER_WART)));
    public static final RegistryObject<Block> POISON_BERRY = BLOCKS.register("poison_berry",
            () -> new PoisonBerries(BlockBehaviour.Properties.of().randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH)));
    public static final RegistryObject<Block> MEADOW_GOLDEN_FLOWER = BLOCKS.register("meadow_golden_flower",
            () -> new GoldenRose(BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> ROSE_OF_GHOSTY_TEARS = BLOCKS.register("rose_of_ghosty_tears",
            () -> new SoulRose(BlockBehaviour.Properties.of().noCollission()
                    .randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SPATIAL_ORCHID = BLOCKS.register("spatial_orchid",
            () -> new SpatialOrchid(BlockBehaviour.Properties.of().noCollission()
                    .randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> COASTAL_STEEP = BLOCKS.register("coastal_steep",
            () -> new CoastalSteepBlock(BlockBehaviour.Properties.of().noCollission()
                    .randomTicks().instabreak().sound(SoundType.GRASS)));

    public static final RegistryObject<Block> ROSE_OF_THE_MURDERER = BLOCKS.register("rose_of_the_murderer",
            () -> new RoseMurderer(BlockBehaviour.Properties.of().noCollission()
                    .randomTicks().instabreak().sound(SoundType.GRASS)));



    public static final RegistryObject<Block> CHARMING_BERRIES_BLOCK = registerBlock("charming_berries_block",
            () -> new CharmingBerries(BlockBehaviour.Properties.of().randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH)));
    public static final RegistryObject<Block> FREEZE_BERRIES = BLOCKS.register("freeze_berries",
            () -> new FreezeBerries(BlockBehaviour.Properties.of().randomTicks()
                    .noCollission().sound(SoundType.SWEET_BERRY_BUSH)));

    // Блоки
    public static final RegistryObject<Block> SUGAR_SACK = registerBlock("sugar_sack",
            () -> new FacingBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> DRAGOLIT_GRID = registerBlock("dragolit_grid",
            () -> new DragolitGrid(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(2.5F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> DRAGOLIT_BLOCK = registerBlock("dragolit_block",
            () -> new DragolitBlock(BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(5F).noOcclusion().requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> STRANGE_CHIP = registerBlock("strange_chip",
            () -> new DragolitBlock(BlockBehaviour.Properties.of().sound(SoundType.ANCIENT_DEBRIS).strength(30F, 1200F).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Block> A_BLOCK_OF_SPARKING_POLLEN = registerBlock("a_block_of_sparkling_pollen",
            () -> new FallingBlock(BlockBehaviour.Properties.of().strength(0.2F, 30)
                    .sound(SoundType.SAND)));
    public static final RegistryObject<Block> WIZARD_PIE = BLOCKS.register("wizard_pie",
            () -> new WizardPie(BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.WOOL).randomTicks()));
    public static final RegistryObject<Block> ROTTEN_PIE = BLOCKS.register("rotten_pie",
            () -> new RottenPie(BlockBehaviour.Properties.of().randomTicks().strength(0.5F).sound(SoundType.WOOL)));

    //Уникальные блоки
    public static final RegistryObject<Block> DRAGOLITE_CAGE = registerBlock("dragolite_cage",
            () -> new DragoliteCage(BlockBehaviour.Properties.of().sound(SoundType.METAL)
                    .noOcclusion().strength(4F).explosionResistance(20F).randomTicks().requiresCorrectToolForDrops()));

    //Торты со свечами wizard
    public static final RegistryObject<Block> CANDLE_WIZARD_PIE = registerBlock("candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> WHITE_CANDLE_WIZARD_PIE = registerBlock("white_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.WHITE_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> BLACK_CANDLE_WIZARD_PIE = registerBlock("black_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.BLACK_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> YELLOW_CANDLE_WIZARD_PIE = registerBlock("yellow_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.YELLOW_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> RED_CANDLE_WIZARD_PIE = registerBlock("red_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.RED_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> ORANGE_CANDLE_WIZARD_PIE = registerBlock("orange_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.ORANGE_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> PINK_CANDLE_WIZARD_PIE = registerBlock("pink_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.PINK_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> PURPLE_CANDLE_WIZARD_PIE = registerBlock("purple_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.PURPLE_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> MAGENTA_CANDLE_WIZARD_PIE = registerBlock("magenta_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.MAGENTA_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> GRAY_CANDLE_WIZARD_PIE = registerBlock("gray_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.GRAY_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> CYAN_CANDLE_WIZARD_PIE = registerBlock("cyan_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.CYAN_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> BLUE_CANDLE_WIZARD_PIE = registerBlock("blue_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.BLUE_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> BROWN_CANDLE_WIZARD_PIE = registerBlock("brown_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.BROWN_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> GREEN_CANDLE_WIZARD_PIE = registerBlock("green_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.GREEN_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> LIME_CANDLE_WIZARD_PIE = registerBlock("lime_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.LIME_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> LIGHT_BLUE_CANDLE_WIZARD_PIE = registerBlock("light_blue_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.LIGHT_BLUE_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));
    public static final RegistryObject<Block> LIGHT_GRAY_CANDLE_WIZARD_PIE = registerBlock("light_gray_candle_wizard_pie",
            () -> new CandleWizardPie(Blocks.LIGHT_GRAY_CANDLE,BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)));


    //Яблоня
    public static final RegistryObject<Block> APPLE_LOG = registerBlock("apple_log",
            () -> new StrippedWoodLogs(BlockBehaviour.Properties.of()
                    .instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> APPLE_WOOD = registerBlock("apple_wood",
            () -> new StrippedWoodLogs(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F)
                    .sound(SoundType.WOOD).ignitedByLava()));

    public static final RegistryObject<Block> STRIPPED_APPLE_LOG = registerBlock("stripped_apple_log",
            () -> new StrippedWoodLogs(BlockBehaviour.Properties.of()
                    .instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> STRIPPED_APPLE_WOOD = registerBlock("stripped_apple_wood",
            () -> new StrippedWoodLogs(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F)
                    .sound(SoundType.WOOD).ignitedByLava()));

    public static final RegistryObject<Block> APPLE_PLANKS = registerBlock("apple_planks",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD).ignitedByLava()));

    public static final RegistryObject<Block> APPLE_SIGN = BLOCKS.register("apple_sign",
            () -> new ModStandingSignBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD), ModWoodType.APPLE_WOOD));
    public static final RegistryObject<Block> APPLE_WALL_SIGN = BLOCKS.register("apple_wall_sign",
            () -> new ModWallSignBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD).noCollission().strength(1.0F).sound(SoundType.CHERRY_WOOD), ModWoodType.APPLE_WOOD));
    public static final RegistryObject<Block> APPLE_HANGING_SIGN = BLOCKS.register("apple_hanging_sign",
            () -> new ModHangingSignBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F), ModWoodType.APPLE_WOOD));
    public static final RegistryObject<Block> APPLE_WALL_HANGING_SIGN = BLOCKS.register("apple_wall_hanging_sign",
            () -> new ModWallHangingSignBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.WOOD).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).dropsLike(APPLE_HANGING_SIGN.get()), ModWoodType.APPLE_WOOD));

    public static final RegistryObject<Block> APPLE_LEAVES = BLOCKS.register("apple_leaves",
            () -> new AppleLeaves(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block> APPLE_LEAVES_STAGES = BLOCKS.register("apple_leaves_stages",
            () -> new AppleLeavesStages(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));

    public static final RegistryObject<Block> APPLE_SAPLING = BLOCKS.register("apple_sapling",
            () -> new SaplingBlock(new AppleTreeGrower() ,BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT).noCollission()
                    .randomTicks().instabreak().sound(SoundType.GRASS)));

    public static final RegistryObject<Block> APPLE_STAIRS = registerBlock("apple_stairs",
            () -> new StairBlock(APPLE_PLANKS.get().defaultBlockState(),BlockBehaviour.Properties.copy(APPLE_PLANKS.get())));
    public static final RegistryObject<Block> APPLE_SLAB = registerBlock("apple_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> APPLE_FENCE = registerBlock("apple_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(APPLE_PLANKS.get().defaultMapColor())
                    .forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F)
                    .sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> APPLE_FENCE_GATE = registerBlock("apple_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(APPLE_PLANKS.get()
                    .defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F)
                    .ignitedByLava(), ModWoodType.APPLE_WOOD));
    public static final RegistryObject<Block> APPLE_BUTTON = registerBlock("apple_button",
            () -> new ButtonBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY),
                    BlockSetType.OAK, 10, true));
    public static final RegistryObject<Block> APPLE_PRESSURE_PLATE = registerBlock("apple_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,BlockBehaviour.Properties.of()
                    .mapColor(APPLE_PLANKS.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission()
                    .strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY), BlockSetType.OAK));

    public static final RegistryObject<Block> APPLE_DOOR = registerBlock("apple_door",
            () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(APPLE_PLANKS.get()
                    .defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion()
                    .ignitedByLava().pushReaction(PushReaction.DESTROY), BlockSetType.OAK));
    public static final RegistryObject<Block> APPLE_TRAPDOOR = registerBlock("apple_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(APPLE_PLANKS.get()
                            .defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion()
                    .ignitedByLava().pushReaction(PushReaction.DESTROY), BlockSetType.OAK));
    //Метод регистрации блоков
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ItemsWD.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
}
