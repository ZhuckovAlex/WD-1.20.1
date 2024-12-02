package ru.imaginaerum.wd.common.sounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.imaginaerum.wd.WD;

public class CustomSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WD.MODID);

    public static final RegistryObject<SoundEvent> NYAMNYAM =
            registerSoundEvents("nyamnyam");
    public static final RegistryObject<SoundEvent> NYAMNYAM_END =
            registerSoundEvents("nyamnyam_end");
    public static final RegistryObject<SoundEvent> FLIES =
            registerSoundEvents("flies");
    public static final RegistryObject<SoundEvent> ROBIN_STICK =
            registerSoundEvents("robin_stick");
    public static final RegistryObject<SoundEvent> WD_JAM_MUSIC_DISK =
            registerSoundEvents("wd_jam_music_disk");



    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        ResourceLocation id = new ResourceLocation(WD.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

}
