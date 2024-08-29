package ru.imaginaerum.wd.common.items.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class AnimateItem extends Item {
    public AnimateItem(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        entity.startUsingItem(hand);
        // Кулдаун

        return new InteractionResultHolder(InteractionResult.SUCCESS, entity.getItemInHand(hand));
    }
    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.DRINK;
    }
}
