package ru.imaginaerum.wd.common.particles.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)

public class GoldenFlowerParticle extends SimpleAnimatedParticle {
    GoldenFlowerParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z, spriteSet, 0.0F);

        // Make the particle stationary
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;

        // Increase the size of the particle by 4 times
        this.quadSize *= 64.0F;

        // Center the particle on the block
        this.x = x + 0; // Assuming x, y, z are the block coordinates
        this.y = y + 0;
        this.z = z + 0;

        this.lifetime = 5;
        this.setSpriteFromAge(spriteSet);
        this.hasPhysics = true;

        this.setAlpha(0.1F);
        this.gravity = 0;
    }

    // Дополнительные методы или изменения, если необходимо

    @Override
    public void move(double motionX, double motionY, double motionZ) {
        super.move(motionX, motionY, motionZ);
        // Дополнительные действия, если необходимо
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double motionX, double motionY, double motionZ) {
            return new GoldenFlowerParticle(level, x, y, z, this.sprites);
        }
    }
}