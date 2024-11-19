package ru.imaginaerum.wd.common.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.imaginaerum.wd.WD;

public class EffectsWD {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, WD.MODID);

    public static final RegistryObject<MobEffect> FLIES = MOB_EFFECTS.register("flies",
            () -> new FliesEffect(MobEffectCategory.HARMFUL, 0xFFFFFF));
}
