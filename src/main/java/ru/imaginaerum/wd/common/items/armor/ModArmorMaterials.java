package ru.imaginaerum.wd.common.items.armor;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import ru.imaginaerum.wd.WD;
import ru.imaginaerum.wd.common.items.ItemsWD;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    ELYTRA("elytra", 28, new int[]{3, 8, 6, 3}, 30, SoundEvents.ARMOR_EQUIP_LEATHER,
            3.0F, 0.0F, () -> Ingredient.of(ItemsWD.MAG_ELYTRA.get())),
    MAGIC("magic", 26, new int[]{ 3, 7, 5, 4 }, 25,
    SoundEvents.ARMOR_EQUIP_LEATHER, 1f, 0f, () -> Ingredient.of(ItemsWD.MAGIC_HAT.get())),
    MAGIC_JAM("magic_jam", 26, new int[]{ 3, 7, 5, 4 }, 25,
    SoundEvents.ARMOR_EQUIP_LEATHER, 1f, 0f, () -> Ingredient.of(ItemsWD.MAGIC_HAT_JAM.get()));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = { 11, 16, 16, 13 };

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound,
                      float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionAmounts[pType.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return WD.MODID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
