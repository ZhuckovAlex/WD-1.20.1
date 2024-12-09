package ru.imaginaerum.wd.common.items.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import ru.imaginaerum.wd.common.items.ItemsWD;

import java.awt.event.ItemEvent;
import java.util.List;

public class NotPickledKebab extends Item {
    public NotPickledKebab(Properties p_41383_) {
        super(p_41383_);
    }
    @Override
    public void onCraftedBy(ItemStack itemstack, Level world, Player entity) {
        super.onCraftedBy(itemstack, world, entity);
        setDamageKebab(itemstack);
    }
    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        marinovanie(itemstack, entity);
    }
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);

        list.add(Component.translatable("wd.progress_kebab"));
    }

    public static void marinovanie(ItemStack itemstack, Entity entity) {
        {

                    {
                        ItemStack _ist = itemstack;
                        if (_ist.hurt(-1, RandomSource.create(), null)) {
                            _ist.shrink(1);
                            _ist.setDamageValue(0);
                        }
                    }


            if ((itemstack).getDamageValue() == 0) {
                if (entity instanceof Player _player) {
                    ItemStack _stktoremove = new ItemStack(ItemsWD.NOT_PICKLED_SHISH_KEBAB.get());
                    _player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
                }
                if (entity instanceof Player _player) {
                    ItemStack _setstack = new ItemStack(ItemsWD.PICKLED_SHISH_KEBAB.get());
                    _setstack.setCount(1);
                    ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
                }
            }

        }
    }

    public static void setDamageKebab(ItemStack itemstack) {
        (itemstack).setDamageValue(1499);
    }

}
