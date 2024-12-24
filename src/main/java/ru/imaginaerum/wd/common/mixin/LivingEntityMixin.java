package ru.imaginaerum.wd.common.mixin;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeLivingEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.imaginaerum.wd.common.effects.EffectsWD;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    public void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        // Проверка на наличие эффекта заморозки и огненного источника урона
        if (livingEntity.hasEffect(EffectsWD.FREEZE.get()) && source.is(DamageTypeTags.IS_PROJECTILE)) {
            cir.setReturnValue(false);  // Блокируем урон от огня, если есть эффект сопротивления огню
        }
    }
}