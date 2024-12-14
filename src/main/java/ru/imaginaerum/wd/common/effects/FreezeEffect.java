package ru.imaginaerum.wd.common.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FreezeEffect extends MobEffect {
    protected FreezeEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // Получаем текущий уровень (мир)
        var level = entity.level();
        // Получаем текущую позицию сущности
        var entityPos = entity.blockPosition();

        // Проверяем, находится ли сущность в блоке огня или лавы
        if (level.getBlockState(entityPos).is(net.minecraft.world.level.block.Blocks.FIRE) ||
                level.getBlockState(entityPos).is(net.minecraft.world.level.block.Blocks.LAVA)) {
            entity.setIsInPowderSnow(false); // Отключаем эффект "порошкового снега"
            entity.clearFire();
            return; // Завершаем выполнение метода
        }

        // Если не в огне и не в лаве, продолжаем применять эффект
        entity.setIsInPowderSnow(true);

        // Защита от горения: если сущность горит, то очищаем статус горения
        if (entity.isOnFire()) {
            entity.clearFire();
        }
    }


    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // Эффект будет применяться каждый тик
    }
}
