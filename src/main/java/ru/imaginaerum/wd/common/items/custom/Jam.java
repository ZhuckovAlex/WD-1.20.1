package ru.imaginaerum.wd.common.items.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;

public class Jam extends Item {
    public Jam(Properties properties) {
        super(properties);
    }

    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }
}
