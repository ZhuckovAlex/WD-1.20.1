package ru.imaginaerum.wd.common.blocks;

import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.imaginaerum.wd.WD;
import ru.imaginaerum.wd.common.blocks.custom.*;
import ru.imaginaerum.wd.common.items.ItemsWD;

import java.util.function.Supplier;

public class BlocksWD {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, WD.MODID);
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
            () -> new SoulRose(BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> CHARMING_BERRIES_BLOCK = registerBlock("charming_berries_block",
            () -> new CharmingBerries(BlockBehaviour.Properties.of().randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH)));
    public static final RegistryObject<Block> FREEZE_BERRIES = BLOCKS.register("freeze_berries",
            () -> new FreezeBerries(BlockBehaviour.Properties.of().randomTicks().noCollission().sound(SoundType.SWEET_BERRY_BUSH)));

    // Блоки
    public static final RegistryObject<Block> SUGAR_SACK = registerBlock("sugar_sack",
            () -> new FacingBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<Block> A_BLOCK_OF_SPARKING_POLLEN = registerBlock("a_block_of_sparkling_pollen",
            () -> new FallingBlock(BlockBehaviour.Properties.of().strength(0.2F, 30)
                    .sound(SoundType.SAND)));
    public static final RegistryObject<Block> WIZARD_PIE = BLOCKS.register("wizard_pie",
            () -> new WizardPie(BlockBehaviour.Properties.of().strength(0.5F).sound(SoundType.WOOL).randomTicks()));
    //Метод регистрации блоков
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ItemsWD.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
