package ru.imaginaerum.wd.common.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class WizardPie extends CakeBlock {
    public WizardPie(Properties p_51184_) {
        super(p_51184_);
    }


    @Override
    public void tick(BlockState p_222945_, ServerLevel p_222946_, BlockPos p_222947_, RandomSource p_222948_) {
        int i = p_222945_.getValue(BITES);
             if (i >= 1) {
                p_222946_.setBlock(p_222947_, p_222945_.setValue(BITES, Integer.valueOf(i - 1)), 3);
        }
    }
    public InteractionResult use(BlockState p_51202_, Level p_51203_, BlockPos p_51204_, Player p_51205_, InteractionHand p_51206_, BlockHitResult p_51207_) {
        ItemStack itemstack = p_51205_.getItemInHand(p_51206_);
        p_51203_.playSound((Player)null, p_51204_, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS, 1.0F, 1.0F);

        if (p_51203_.isClientSide) {
            if (eat(p_51203_, p_51204_, p_51202_, p_51205_).consumesAction()) {
                return InteractionResult.SUCCESS;
            }

            if (itemstack.isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }

        return eat(p_51203_, p_51204_, p_51202_, p_51205_);
    }
    protected static InteractionResult eat(LevelAccessor world, BlockPos blockPos, BlockState blockState, Player player) {
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            player.awardStat(Stats.EAT_CAKE_SLICE);
            player.getFoodData().eat(4, 0.8F);

            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2400, 1));

            int i = blockState.getValue(BITES);
            world.gameEvent(player, GameEvent.EAT, blockPos);
            if (i < 6) {
                world.setBlock(blockPos, blockState.setValue(BITES, Integer.valueOf(i + 1)), 3);
            }
            else {
                world.removeBlock(blockPos, false);
                world.gameEvent(player, GameEvent.BLOCK_DESTROY, blockPos);
            }
            return InteractionResult.SUCCESS;
        }
    }
}