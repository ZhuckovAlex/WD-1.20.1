package ru.imaginaerum.wd.common.tab;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import ru.imaginaerum.wd.WD;
import ru.imaginaerum.wd.common.blocks.BlocksWD;
import ru.imaginaerum.wd.common.items.ItemsWD;

public class TabWD extends CreativeModeTab {
    protected TabWD(Builder builder) {
        super(builder);
    }

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WD.MODID);

    public static final RegistryObject<CreativeModeTab> WD_TAB = CREATIVE_MODE_TABS.register("wd_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemsWD.JAM_TONIC.get()))
                    .title(Component.translatable("creativetab.wd"))
                    .displayItems((pParameters, pOutput) -> {
                        //Items
                        pOutput.accept(ItemsWD.POISON_BERRY_JAM.get());
                        pOutput.accept(ItemsWD.SWEET_JAM.get());
                        pOutput.accept(ItemsWD.SUGAR_REFINED.get());
                        pOutput.accept(ItemsWD.LEVITAN_JAM.get());
                        pOutput.accept(ItemsWD.APPLE_JAM.get());
                        pOutput.accept(ItemsWD.JAM_INVISIBILITY.get());
                        pOutput.accept(ItemsWD.JAM_TONIC.get());
                        pOutput.accept(ItemsWD.CHARMING_JAM.get());
                        pOutput.accept(ItemsWD.GLOWING_JAM.get());
                        pOutput.accept(ItemsWD.FREEZE_JAM.get());
                        pOutput.accept(ItemsWD.JAR.get());
                        pOutput.accept(ItemsWD.RAW_BEAR_MEAT.get());
                        pOutput.accept(ItemsWD.RAW_HORSE.get());
                        pOutput.accept(ItemsWD.RAW_GOATS_MEAT.get());
                        pOutput.accept(ItemsWD.COCKED_BEAR_MEAT.get());
                        pOutput.accept(ItemsWD.COCKED_HORSE.get());
                        pOutput.accept(ItemsWD.COCKED_GOATS_MEAT.get());
                        pOutput.accept(ItemsWD.FROG_BODY.get());
                        pOutput.accept(ItemsWD.COCKED_FROG.get());
                        pOutput.accept(ItemsWD.FROG_LEGS.get());
                        pOutput.accept(ItemsWD.COCKED_FROG_LEGS.get());
                        pOutput.accept(ItemsWD.SPAGETTI_IN_THE_NORTH.get());
                        pOutput.accept(ItemsWD.SWEET_ROLL.get());
                        pOutput.accept(ItemsWD.GOULASH_WITH_GOAT_MEAT.get());
                        pOutput.accept(ItemsWD.ROAST_GOAT_MEAT_WITH_FREEZE_BERRIES_SYRUP.get());
                        pOutput.accept(ItemsWD.BEAR_MEAT_SOUP.get());
                        pOutput.accept(ItemsWD.MEDICAL_POTATO.get());
                        pOutput.accept(ItemsWD.PEPPER.get());
                        pOutput.accept(ItemsWD.KRUTNEVY_BREAD.get());
                        pOutput.accept(ItemsWD.THROWING_KNIFE.get());
                        pOutput.accept(ItemsWD.TURTLE_SOUP.get());
                        pOutput.accept(ItemsWD.CLEANED_TURTLE_NECK.get());
                        pOutput.accept(ItemsWD.PICKLED_TURTLE_NECK.get());
                        pOutput.accept(ItemsWD.TURTLE_NECK.get());
                        pOutput.accept(ItemsWD.HOT_COCOA_WITH_SPARKING_POLLEN.get());
                        pOutput.accept(ItemsWD.HUNTING_TWISTER.get());
                        pOutput.accept(ItemsWD.IRIS.get());
                        pOutput.accept(ItemsWD.MUSHROOM_ON_STICK.get());
                        pOutput.accept(ItemsWD.COCKED_MUSHROOM_ON_STICK.get());
                        pOutput.accept(ItemsWD.SHPIKACHKI.get());
                        pOutput.accept(ItemsWD.COCKED_SHPIKACHKI.get());
                        pOutput.accept(ItemsWD.CHEESE.get());
                        pOutput.accept(ItemsWD.SILVERAN.get());
                        pOutput.accept(ItemsWD.HANDFUL_YADOGA.get());
                        pOutput.accept(ItemsWD.A_DROP_OF_LOVE.get());
                        pOutput.accept(ItemsWD.CLEANSING_DECOCTION.get());
                        pOutput.accept(ItemsWD.HANDFUL_NETHER.get());
                        pOutput.accept(ItemsWD.SPARKLING_POLLEN.get());
                        pOutput.accept(ItemsWD.CRIMSON_BONE_MEAL.get());
                        pOutput.accept(ItemsWD.WARPED_BONE_MEAL.get());
                        pOutput.accept(ItemsWD.COCKED_SLISING_GOATS_MEAT.get());
                        pOutput.accept(ItemsWD.RAW_SLISING_GOATS_MEAT.get());
                        pOutput.accept(ItemsWD.COASTAL_STEEP_FIBERS.get());
                        pOutput.accept(ItemsWD.COASTAL_STEEP_FLOWER.get());
                        pOutput.accept(ItemsWD.FLAME_ARROW.get());
                        pOutput.accept(ItemsWD.CHARMING_BERRIES.get());



                        //Blocks
                        pOutput.accept(BlocksWD.FIRE_STEM.get());
                        pOutput.accept(BlocksWD.POISON_BERRY.get());
                        pOutput.accept(BlocksWD.FREEZE_BERRIES.get());
                        pOutput.accept(BlocksWD.WARPED_WART.get());
                        pOutput.accept(BlocksWD.SUGAR_SACK.get());
                        pOutput.accept(BlocksWD.A_BLOCK_OF_SPARKING_POLLEN.get());
                        pOutput.accept(BlocksWD.MEADOW_GOLDEN_FLOWER.get());
                        pOutput.accept(BlocksWD.ROSE_OF_GHOSTY_TEARS.get());
                        pOutput.accept(BlocksWD.WIZARD_PIE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
