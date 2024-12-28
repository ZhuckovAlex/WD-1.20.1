package ru.imaginaerum.wd.common.mixin;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.imaginaerum.wd.common.effects.EffectsWD;

import java.lang.reflect.Method;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void onHurtModifyDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity.hasEffect(EffectsWD.FREEZE.get()) && source.is(DamageTypeTags.IS_FIRE)) {
            cir.setReturnValue(false);  // Отменяем дальнейшую обработку урона
        }
    }
}