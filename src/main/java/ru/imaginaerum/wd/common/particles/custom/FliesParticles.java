package ru.imaginaerum.wd.common.particles.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class FliesParticles extends TextureSheetParticle {
    private static final RandomSource RANDOM = RandomSource.create();
    private final SpriteSet sprites;

    FliesParticles(ClientLevel level, double x, double y, double z, double xd, double yd, double zd, SpriteSet sprites) {
        super(level, x, y, z, 0.5 - RANDOM.nextDouble(), yd, 0.5 - RANDOM.nextDouble());
        this.friction = 0.96F;
        this.gravity = 0;
        this.speedUpWhenYMotionIsBlocked = true;
        this.sprites = sprites;
        this.yd *= 0.20000000298023224;

        // Если скорости по осям X и Z равны 0, то мы можем задать им маленькие случайные значения.
        if (xd == 0.0 && zd == 0.0) {
            this.xd *= 0.10000000149011612;
            this.zd *= 0.10000000149011612;
        }

        // Множитель для размера частиц
        this.quadSize *= 0.75F;
        this.lifetime = (int) (8.0 / (Math.random() * 0.8 + 0.2)); // Случайное время жизни
        this.hasPhysics = false;
        this.setSpriteFromAge(sprites);
        if (this.isCloseToScopingPlayer()) {
            this.setAlpha(0.0F);
        }
    }

    @Override
    public void tick() {
        // Добавляем случайные отклонения по осям X и Z
        this.xd += (RANDOM.nextFloat() - 0.5) * 0.02; // Случайное отклонение по оси X
        this.yd += (RANDOM.nextFloat() - 0.5) * 0.02; // Случайное отклонение по оси Y
        this.zd += (RANDOM.nextFloat() - 0.5) * 0.02; // Случайное отклонение по оси Z

        super.tick(); // Вызов метода для продолжения поведения стандартных частиц

        // Обновляем спрайт с учетом возраста
        this.setSpriteFromAge(this.sprites);

        if (this.isCloseToScopingPlayer()) {
            this.setAlpha(0.0F); // Если игрок нацелен на частицу, она исчезает
        } else {
            this.setAlpha(Mth.lerp(0.05F, this.alpha, 1.0F)); // Постепенно увеличиваем альфа-канал
        }
    }

    private boolean isCloseToScopingPlayer() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        return player != null && player.getEyePosition().distanceToSqr(this.x, this.y, this.z) <= 9.0 && mc.options.getCameraType().isFirstPerson() && player.isScoping();
    }

    public void setCustomAlpha(float alpha) {
        super.setAlpha(alpha);
    }
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xd, double yd, double zd) {
            return new FliesParticles(level, x, y, z, xd, yd, zd, this.sprite);
        }
    }
}