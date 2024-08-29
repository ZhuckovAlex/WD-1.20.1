package ru.imaginaerum.wd.common.items.arrows;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.imaginaerum.wd.WD;

public class EntityTypeInit {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, WD.MODID);

    public static final RegistryObject<EntityType<FlameArrow>> FLAME_ARROW = ENTITY_TYPES.register("flame_arrow",
            () -> EntityType.Builder.<FlameArrow>of(FlameArrow::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).setCustomClientFactory(FlameArrow::new)
                    .build(new ResourceLocation(WD.MODID, "flame_arrow").toString()));
}
