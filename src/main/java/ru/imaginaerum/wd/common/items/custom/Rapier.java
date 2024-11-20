package ru.imaginaerum.wd.common.items.custom;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import java.util.Random;
import java.util.UUID;

public class Rapier extends SwordItem {
    private static final UUID SLUGGISH_UUID = UUID.fromString("748D3470-A63E-46E0-8B99-61B1C9323A8B");
    private static final UUID WEAKNESS_UUID = UUID.fromString("B27FC59A-4278-474F-8362-479C195E162E");
    private static final UUID SLOWNESS_UUID = UUID.fromString("3F3F3F3F-3F3F-3F3F-3F3F-3F3F3F3F3F3F");

    public Rapier(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean result = super.hurtEnemy(stack, target, attacker);
        if (result) {
            Random random = new Random();
            if (random.nextFloat() <= 0.4) { // 40% шанс применить слепоту
                target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1000, 2)); // Усиление до 3 уровня и длительность 50 секунд
            }
            if (random.nextFloat() <= 0.2) { // 20% шанс применить слабость
                target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 1000, 2)); // Усиление до 3 уровня и длительность 50 секунд
            }
            if (random.nextFloat() <= 0.4) { // 40% шанс применить замедление
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1000, 2)); // Усиление до 3 уровня и длительность 50 секунд
            }
            if (!(target instanceof EnderDragon) && !(target instanceof WitherBoss) && random.nextFloat() <= 0.05) {
                target.kill(); // Убиваем моба
            }
        }
        return result;
    }

}
