package ru.imaginaerum.wd.common.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class CleansingDecoction extends Item {
    public CleansingDecoction(Properties properties) {
        super(properties);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if(Screen.hasShiftDown()) {
            components.add(Component.translatable("wd.press_shift2").withStyle(ChatFormatting.DARK_GRAY));
            components.add(Component.translatable("wd.cleansing_decoction").withStyle(ChatFormatting.DARK_PURPLE));
        } else {
            components.add(Component.translatable("wd.press_shift").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, level, components, flag);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        ClearEnchant(entity, ar.getObject(), hand);
        ClearEnchant2(entity, ar.getObject(), hand);
        return ar;
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
        // Возвращаем false, чтобы сделать предмет незачаровуемым
        return false;
    }
    @Override
    public boolean hasCraftingRemainingItem() {
        return true;
    }

    @Override
    public boolean isRepairable(ItemStack itemstack) {
        return false;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemstack) {
        ItemStack retval = new ItemStack(this);
        retval.setDamageValue(itemstack.getDamageValue() + 1);
        if (retval.getDamageValue() >= retval.getMaxDamage()) {
            return ItemStack.EMPTY;
        }
        return retval;
    }

    public static void ClearEnchant(Entity entity, ItemStack stack, InteractionHand hand) {
        if (entity == null)
            return;

        ItemStack itemInHand = (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY);
        Map<Enchantment, Integer> _enchantments = EnchantmentHelper.getEnchantments(itemInHand);

        if (!_enchantments.isEmpty()) {
            Enchantment enchantmentToRemove = _enchantments.keySet().iterator().next();
            _enchantments.remove(enchantmentToRemove);
            EnchantmentHelper.setEnchantments(_enchantments, itemInHand);
            if (entity instanceof LivingEntity) {
                stack.hurtAndBreak(1, (LivingEntity) entity, e -> e.broadcastBreakEvent(hand));
            }
        }
    }
    public static void ClearEnchant2(Entity entity, ItemStack stack, InteractionHand hand) {
        if (entity == null)
            return;

        ItemStack itemInHand = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
        Map<Enchantment, Integer> _enchantments = EnchantmentHelper.getEnchantments(itemInHand);

        if (!_enchantments.isEmpty()) {
            Enchantment enchantmentToRemove = _enchantments.keySet().iterator().next();
            _enchantments.remove(enchantmentToRemove);
            EnchantmentHelper.setEnchantments(_enchantments, itemInHand);
            if (entity instanceof LivingEntity) {
                stack.hurtAndBreak(1, (LivingEntity) entity, e -> e.broadcastBreakEvent(hand));
            }
        }
    }
}
