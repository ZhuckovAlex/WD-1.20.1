package ru.imaginaerum.wd.common.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;


public class HealingDew extends Item {
    public HealingDew(Properties properties) {
        super(properties);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()) {
            components.add(Component.translatable("wd.press_shift2").withStyle(ChatFormatting.DARK_GRAY));
            components.add(Component.translatable("wd.healing_dew").withStyle(ChatFormatting.DARK_PURPLE));
        } else {
            components.add(Component.translatable("wd.press_shift").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, level, components, flag);
    }


    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        Level level = livingEntity.level();

        if (livingEntity instanceof ZombieVillager zombieVillager) {
            if (!level.isClientSide()) {
                // Генерация случайного числа для вероятности
                boolean success = Math.random() < 0.7; // 70% вероятность превращения в жителя

                if (success) {
                    // Успешное превращение в жителя
                    boolean isBaby = zombieVillager.isBaby();

                    // Удаляем зомби-жителя
                    zombieVillager.discard();

                    // Создаём нового жителя
                    Villager villager = new Villager(EntityType.VILLAGER, (ServerLevel) level);
                    villager.moveTo(zombieVillager.getX(), zombieVillager.getY(), zombieVillager.getZ(), zombieVillager.getYRot(), zombieVillager.getXRot());

                    // Устанавливаем статус isBaby
                    if (isBaby) {
                        villager.setBaby(true);
                    }

                    // Добавляем жителя в мир
                    level.addFreshEntity(villager);

                    // Эффект частиц happy villager
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                                zombieVillager.getX(), zombieVillager.getY() + 1, zombieVillager.getZ(),
                                10, 0.5, 0.5, 0.5, 0.1);
                    }

                    // Звук колокола
                    level.playSound(null, zombieVillager.blockPosition(),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bell.use")),
                            SoundSource.PLAYERS, 1.0F, 1.0F);

                } else {
                    // Неудача: превращение в зомби
                    boolean isBaby = zombieVillager.isBaby();

                    // Удаляем зомби-жителя
                    zombieVillager.discard();

                    // Создаём обычного зомби
                    Zombie zombie = new Zombie(EntityType.ZOMBIE, (ServerLevel) level);
                    zombie.moveTo(zombieVillager.getX(), zombieVillager.getY(), zombieVillager.getZ(), zombieVillager.getYRot(), zombieVillager.getXRot());

                    // Устанавливаем статус isBaby
                    if (isBaby) {
                        zombie.setBaby(true);
                    }

                    // Добавляем зомби в мир
                    level.addFreshEntity(zombie);

                    // Эффект дыма
                    if (level instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.SMOKE,
                                zombieVillager.getX(), zombieVillager.getY() + 1, zombieVillager.getZ(),
                                15, 0.5, 0.5, 0.5, 0.1);
                    }

                    // Звук шипения огня
                    level.playSound(null, zombieVillager.blockPosition(),
                            ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")),
                            SoundSource.PLAYERS, 1.0F, 1.0F);
                }

                // Убираем Healing Dew из инвентаря игрока
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
            }

            // Анимация руки
            player.swing(interactionHand);

            return InteractionResult.SUCCESS; // Указываем, что действие выполнено успешно
        }

        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
    }
}
