package ru.imaginaerum.wd.server.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.imaginaerum.wd.common.items.ItemsWD;
@Mod.EventBusSubscriber
public class RobinStickRepair {

    // Регистрируем событие
    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();

        // Проверяем, что в одной руке есть SOUL_STONE с CustomModelData == 1, а в другой - ROBIN_STICK
        if (isSoulStoneCustomModelData(mainHandItem) && isRobinStick(offHandItem)) {
            // Устанавливаем CustomModelData у SOUL_STONE в 0
            setSoulStoneCustomModelData(mainHandItem, 0);
            // Чиним ROBIN_STICK
            removeSoulStoneNBT(mainHandItem);
            repairRobinStick(offHandItem);
            event.setCanceled(true); // Отменяем дальнейшую обработку события
        } else if (isSoulStoneCustomModelData(offHandItem) && isRobinStick(mainHandItem)) {
            // Устанавливаем CustomModelData у SOUL_STONE в 0
            setSoulStoneCustomModelData(offHandItem, 0);
            // Чиним ROBIN_STICK
            removeSoulStoneNBT(offHandItem);
            repairRobinStick(mainHandItem);
            event.setCanceled(true); // Отменяем дальнейшую обработку события
        }
    }

    private static boolean isSoulStoneCustomModelData(ItemStack itemStack) {
        // Проверяем, что это SOUL_STONE и CustomModelData == 1
        return itemStack.getItem() == ItemsWD.SOUL_STONE.get() && getCustomModelData(itemStack) == 1;
    }

    private static boolean isRobinStick(ItemStack itemStack) {
        // Проверяем, что это ROBIN_STICK
        return itemStack.getItem() == ItemsWD.ROBIN_STICK.get();
    }

    private static int getCustomModelData(ItemStack itemStack) {
        // Получаем значение CustomModelData из тега предмета
        CompoundTag tag = itemStack.getTag();
        if (tag != null && tag.contains("CustomModelData", 99)) {
            return tag.getInt("CustomModelData");
        }
        return 0; // Если нет CustomModelData, возвращаем 0
    }

    private static void setSoulStoneCustomModelData(ItemStack itemStack, int customModelData) {
        // Устанавливаем CustomModelData у SOUL_STONE в переданное значение
        CompoundTag tag = itemStack.getOrCreateTag();
        tag.putInt("CustomModelData", customModelData);
    }

    private static void repairRobinStick(ItemStack itemStack) {
        // Чиним ROBIN_STICK (устанавливаем его прочность в максимальное значение)
        itemStack.setDamageValue(0);
    }
    private static void removeSoulStoneNBT(ItemStack itemStack) {
        // Удаляем теги из SOUL_STONE
        CompoundTag tag = itemStack.getTag();
        if (tag != null) {
            tag.remove("entity_type");
            tag.remove("entity_name");
        }
    }
}