package ru.imaginaerum.wd.common.mixin;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.imaginaerum.wd.common.effects.EffectsWD;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    // Внедряем наш код в существующий метод hurt
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void onHurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;

        // Проверка на наличие эффекта заморозки и огненного источника урона
        if (livingEntity.hasEffect(EffectsWD.FREEZE.get()) && source.is(DamageTypeTags.IS_FIRE)) {
            cir.setReturnValue(false);  // Блокируем урон от огня, если есть эффект сопротивления огню
        }
    }
}