package ru.imaginaerum.wd;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import ru.imaginaerum.wd.common.blocks.BlocksWD;
import ru.imaginaerum.wd.common.blocks.entity.ModBlockEntities;
import ru.imaginaerum.wd.common.custom_recipes.BetterBrewingRecipe;
import ru.imaginaerum.wd.common.effects.EffectsWD;
import ru.imaginaerum.wd.common.entities.ModEntities;
import ru.imaginaerum.wd.common.items.ItemsWD;
import ru.imaginaerum.wd.common.items.arrows.DispenserRegistry;
import ru.imaginaerum.wd.common.items.arrows.EntityTypeInit;
import ru.imaginaerum.wd.common.items.arrows.FlameArrowRenderer;
import ru.imaginaerum.wd.common.particles.ModParticles;
import ru.imaginaerum.wd.common.sounds.CustomSoundEvents;
import ru.imaginaerum.wd.common.tab.TabWD;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WD.MODID)
public class WD
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "wd";
    // Directly reference a slf4j logger


    public WD()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EntityTypeInit.ENTITY_TYPES.register(modEventBus);
        ItemsWD.ITEMS.register(modEventBus);
        ModParticles.PARTICLE_TYPES.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        BlocksWD.BLOCKS.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        CustomSoundEvents.SOUND_EVENTS.register(modEventBus);
        EffectsWD.MOB_EFFECTS.register(modEventBus);
        modEventBus.addListener(this::addCreative);
        TabWD.CREATIVE_MODE_TABS.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        FMLJavaModLoadingContext.get().getModEventBus().addListener((BuildCreativeModeTabContentsEvent e) -> {
            if (e.getTabKey() == TabWD.WD_TAB.getKey()) {

            }
        });
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        DispenserRegistry.registerBehaviors();


    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
//            event.accept(EXAMPLE_BLOCK_ITEM);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }
    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();
    public static void queueServerWork(int tick, Runnable action) {
        workQueue.add(new AbstractMap.SimpleEntry(action, tick));
    }
    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            List<AbstractMap.SimpleEntry<Runnable, Integer>> actions = new ArrayList<>();
            workQueue.forEach(work -> {
                work.setValue(work.getValue() - 1);
                if (work.getValue() == 0)
                    actions.add(work);
            });
            actions.forEach(e -> e.getKey().run());
            workQueue.removeAll(actions);
        }
    }
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            EntityRenderers.register(EntityTypeInit.FLAME_ARROW.get(), FlameArrowRenderer::new);

            event.enqueueWork(() -> {

                ComposterBlock.COMPOSTABLES.put(ItemsWD.FIRE_STEM.get(), 0.2f);
                ComposterBlock.COMPOSTABLES.put(ItemsWD.WARPED_WART.get(), 0.2f);
                ComposterBlock.COMPOSTABLES.put(ItemsWD.POISON_BERRY.get(), 0.2f);
                ComposterBlock.COMPOSTABLES.put(ItemsWD.MEADOW_GOLDEN_FLOWER.get(), 0.2f);
                ComposterBlock.COMPOSTABLES.put(ItemsWD.ROSE_OF_GHOSTY_TEARS.get(), 0.2f);
                ComposterBlock.COMPOSTABLES.put(ItemsWD.ROSE_OF_THE_MURDERER.get(), 0.2f);
                ComposterBlock.COMPOSTABLES.put(ItemsWD.MEDICAL_POTATO.get(), 0.2f);
                ComposterBlock.COMPOSTABLES.put(ItemsWD.SPATIAL_ORCHID.get(), 0.2f);

                BrewingRecipeRegistry.addRecipe(new BetterBrewingRecipe(Potions.WATER,
                        ItemsWD.WARPED_WART.get(), Potions.AWKWARD));
            });
        }
    }
}
