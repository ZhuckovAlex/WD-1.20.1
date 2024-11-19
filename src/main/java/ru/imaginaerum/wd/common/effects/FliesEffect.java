package ru.imaginaerum.wd.common.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;

import java.util.concurrent.ThreadLocalRandom;

public class FliesEffect extends MobEffect {
    protected FliesEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }
    // Метод, который будет вызываться каждый тик для сущности
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // Проверяем, является ли сущность игроком
        if (entity instanceof Player player) {
            // Применяем эффект каждую секунду (каждые 20 тиков)
            if (entity.level().getGameTime() % 20 == 0) {  // Каждые 20 тиков (1 секунда)
                // Увеличиваем урон в зависимости от уровня эффекта
                float damage = 1.0F + amplifier;  // Урон увеличивается с каждым уровнем эффекта
                player.hurt(player.damageSources().magic(), damage);  // Отнимаем здоровье

                // Получаем данные о сытости игрока
                FoodData foodData = player.getFoodData();

                // Увеличиваем шанс снижения сытости с каждым уровнем
                int chance = 70;
                if (foodData.getFoodLevel() > 0 && ThreadLocalRandom.current().nextInt(100) < chance) {
                    foodData.setFoodLevel(foodData.getFoodLevel() - 1);  // Отнимаем сытость
                }
            }
        }
    }

    // Метод, который проверяет, истекает ли эффект
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // Этот метод вернет true, что означает, что эффект будет применяться каждый тик
        return true;
    }
}
