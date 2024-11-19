package ru.imaginaerum.wd.common.items.custom;


import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import ru.imaginaerum.wd.server.events.StrikeRobinStick;

import javax.annotation.Nullable;
import java.util.List;


public class StarBallItem extends Item {

    public StarBallItem(Properties properties) {
        super(properties);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()) {
            components.add(Component.translatable("wd.press_shift2").withStyle(ChatFormatting.DARK_GRAY));
            components.add(Component.translatable("wd.robin_stick").withStyle(ChatFormatting.DARK_PURPLE));
        } else {
            components.add(Component.translatable("wd.press_shift").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player entity, InteractionHand hand) {
        entity.startUsingItem(hand);

        StrikeRobinStick.execute(entity,level,entity.getX(),entity.getY(),entity.getZ());

        // Кулдаун
        entity.getCooldowns().addCooldown(this, 40);
        return new InteractionResultHolder(InteractionResult.SUCCESS, entity.getItemInHand(hand));
    }

}