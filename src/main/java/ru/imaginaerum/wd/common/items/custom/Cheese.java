package ru.imaginaerum.wd.common.items.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import ru.imaginaerum.wd.common.sounds.CustomSoundEvents;

public class Cheese extends Item {
    public static final int MAX_BITES = 2;
    public static final String CHEESE_STATE = "cheese_state";

    public Cheese(Properties properties) {
        super(properties);
    }
    @Override
    public SoundEvent getDrinkingSound() {
        return CustomSoundEvents.NYAMNYAM.get();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.DRINK;
    }



    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        CompoundTag tag = stack.getOrCreateTag();
        int bites = tag.getInt(CHEESE_STATE);
        world.playSound(null, entity, CustomSoundEvents.NYAMNYAM_END.get(),
                SoundSource.AMBIENT, 1.0F, 1.0F);
        if (bites < MAX_BITES) {
// Создаем новый стак с новым состоянием
            ItemStack newStack = new ItemStack(this, 1);
            CompoundTag newTag = newStack.getOrCreateTag();
            newTag.putInt(CHEESE_STATE, bites + 1);
            newStack.getOrCreateTag().putInt("CustomModelData", bites + 1);

// Уменьшаем исходный стак
            stack.shrink(1);

// Если это игрок, добавляем новый стак в инвентарь
            if (entity instanceof Player player) {
                if (!player.getInventory().add(newStack)) {
                    player.drop(newStack, false); // Если не удалось добавить в инвентарь, выбрасываем предмет
                }
            }
        } else {
            stack.shrink(1); // Удаляем предмет после максимального количества надкусов
        }

        return super.finishUsingItem(stack, world, entity);
    }
}