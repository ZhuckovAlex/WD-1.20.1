package ru.imaginaerum.wd.common.particles;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.imaginaerum.wd.WD;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, WD.MODID);

    public static final RegistryObject<SimpleParticleType> ROBIN_STAR_PARTICLES =
            PARTICLE_TYPES.register("robin_star_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> GOLDEN_FLOWER_PARTICLES =
            PARTICLE_TYPES.register("golden_flower_particles", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ROBIN_STAR_PARTICLES_PROJECTILE =
            PARTICLE_TYPES.register("robin_star_particles_projectile", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> STOMBLE_ROSE =
            PARTICLE_TYPES.register("stomble_rose", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FLIES =
            PARTICLE_TYPES.register("flies", () -> new SimpleParticleType(true));
}
