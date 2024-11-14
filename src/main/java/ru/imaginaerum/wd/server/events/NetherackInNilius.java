package ru.imaginaerum.wd.server.events;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.imaginaerum.wd.common.items.ItemsWD;

@Mod.EventBusSubscriber
public class NetherackInNilius {
//    @SubscribeEvent
//    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
//        if (event.getHand() != event.getEntity().getUsedItemHand()) {
//            return;
//        }
//
//        // Проверяем, что игрок держит в руке Warped Bone Meal
//        if (event.getEntity().getMainHandItem().getItem() == ItemsWD.WARPED_BONE_MEAL.get()) {
//            transformNetherrack(event, event.getEntity().getCommandSenderWorld(), event.getPos(), Blocks.WARPED_NYLIUM);
//        }
//        // Проверяем, что игрок держит в руке Crimson Bone Meal
//        else if (event.getEntity().getMainHandItem().getItem() == ItemsWD.CRIMSON_BONE_MEAL.get()) {
//            transformNetherrack(event, event.getEntity().getCommandSenderWorld(), event.getPos(), Blocks.CRIMSON_NYLIUM);
//        }
//    }
//
//    private static void transformNetherrack(PlayerInteractEvent.RightClickBlock event, Level world, BlockPos pos, Block targetBlock) {
//        // Проверяем, что блок, на который щелкнули, это Незерак
//        if (world.getBlockState(pos).getBlock() == Blocks.NETHERRACK) {
//            // Проверяем, что блок над Незераком — это воздух
//            BlockPos abovePos = pos.above();
//            if (world.getBlockState(abovePos).getBlock() == Blocks.AIR || world.getBlockState(abovePos).getBlock() == Blocks.CAVE_AIR){
//                // Заменяем Незерак на целевой блок (Warped или Crimson Nylium)
//                world.setBlock(pos, targetBlock.defaultBlockState(), 3);
//
//                // Игрок использует предмет
//                event.getEntity().getMainHandItem().hurtAndBreak(1, event.getEntity(), (player) -> {
//                    player.broadcastBreakEvent(event.getHand());
//                });
//                // Если игрок не в креативе, уменьшаем количество предметов в руке на 1
//                if (!event.getEntity().isCreative()) {
//                    event.getEntity().getMainHandItem().shrink(1);
//                }
//                // Воспроизводим звук
//                world.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
//
//                // Отправляем анимацию частицы (эффект костной муки)
//                if (world instanceof ServerLevel) {
//                    ((ServerLevel) world).sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 5, 0.3, 0.3, 0.3, 0.0);
//                }
//
//                // Прерываем событие, чтобы избежать других действий
//                event.setCanceled(true);
//            }
//        }
//    }
}