package ru.imaginaerum.wd.server.events;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;
import ru.imaginaerum.wd.common.items.ItemsWD;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Random;


@Mod.EventBusSubscriber
public class OpenPillagersChest {

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        // Проверяем, что entity - это игрок
        if (event.getEntity() instanceof ServerPlayer player) {
            // Получаем инвентарь игрока
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();

            // Проверяем, есть ли золотой слиток в одной руке и палка в другой
            if ((mainHand.getItem() == ItemsWD.THE_PILLAGERS_CHEST.get() && offHand.getItem() == ItemsWD.THE_PILLAGERS_KEY.get()) ||
                    (mainHand.getItem() == ItemsWD.THE_PILLAGERS_KEY.get() && offHand.getItem() == ItemsWD.THE_PILLAGERS_CHEST.get())) {

                // Уменьшаем количество предметов на 1 в обеих руках
                mainHand.shrink(1);
                offHand.shrink(1);

                // Загружаем конфиг с шансами выпадения предметов
                JsonObject config = loadConfig();

                // Получаем список предметов с шансами
                JsonElement itemsConfig = config.get("items");
                Random rand = new Random();

                // Обрабатываем массив с предметами
                if (itemsConfig != null && itemsConfig.isJsonArray()) {
                    for (JsonElement itemElement : itemsConfig.getAsJsonArray()) {
                        JsonObject itemData = itemElement.getAsJsonObject();
                        if (itemData.has("min") && itemData.has("max") && itemData.has("chance")) {
                            int minAmount = itemData.get("min").getAsInt();
                            int maxAmount = itemData.get("max").getAsInt();
                            int chance = itemData.get("chance").getAsInt();

                            // Проверяем шанс выпадения предмета
                            if (rand.nextInt(100) < chance) {
                                // Создаем новый предмет с случайным количеством
                                int amount = rand.nextInt(maxAmount - minAmount + 1) + minAmount;
                                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemData.get("item").getAsString()));
                                if (item != null) {
                                    ItemStack itemStack = new ItemStack(item, amount);

                                    // Дропаем предмет на землю
                                    dropItem(player.level(), player.blockPosition(), itemStack);
                                }
                            }
                        } else {
                            // Логирование ошибки, если в конфиге отсутствуют необходимые поля
                            System.err.println("Ошибка конфигурации для предмета: " + itemData);
                        }
                    }
                }
            }
        }
    }

    // Метод для дропа предмета
    private static void dropItem(Level level, BlockPos position, ItemStack itemStack) {
        if (!level.isClientSide && level != null && position != null) {
            ItemEntity itemEntity = new ItemEntity(level, position.getX(), position.getY(), position.getZ(), itemStack);
            level.addFreshEntity(itemEntity);
        }
    }

    // Метод для загрузки конфигурации из JSON файлов
    private static JsonObject loadConfig() {
        JsonObject mergedConfig = new JsonObject();

        try {
            // Указываем путь к папке с JSON-файлами
            ResourceLocation folderPath = new ResourceLocation("wd", "loot_tables/pillager_chest");
            var resourceManager = ServerLifecycleHooks.getCurrentServer().getResourceManager();

            // Получаем ресурсы в папке pillagers
            Map<ResourceLocation, Resource> resources = resourceManager.listResources(folderPath.getPath(), path -> path.getPath().endsWith(".json"));

            // Проходим по всем ресурсам
            for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
                try (InputStream inputStream = entry.getValue().open()) {
                    InputStreamReader reader = new InputStreamReader(inputStream);
                    JsonObject fileConfig = JsonParser.parseReader(reader).getAsJsonObject();

                    // Объединяем содержимое файлов
                    fileConfig.entrySet().forEach(entryFile -> mergedConfig.add(entryFile.getKey(), entryFile.getValue()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Логирование ошибки
        }

        return mergedConfig;
    }
}