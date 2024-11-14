package ru.imaginaerum.wd.common.items.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import ru.imaginaerum.wd.common.blocks.BlocksWD;
import ru.imaginaerum.wd.common.blocks.custom.RoseMurderer;
import ru.imaginaerum.wd.common.items.ItemsWD;

import javax.annotation.Nullable;
import java.util.List;

public class SoulStone extends Item {
    private static final String ENTITY_TYPE_TAG = "entity_type";
    private static final String ENTITY_NAME_TAG = "entity_name";

    public SoulStone(Properties properties) {
        super(properties);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (Screen.hasShiftDown()) {
            components.add(Component.translatable("wd.press_shift2").withStyle(ChatFormatting.DARK_GRAY));
            components.add(Component.translatable("wd.soul_stone_deactive").withStyle(ChatFormatting.DARK_PURPLE));

            // Извлечение информации из тега сущности, если она существует
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains(ENTITY_TYPE_TAG)) {
                String entityType = tag.getString(ENTITY_TYPE_TAG);
                components.add(Component.translatable("wd.entity_type", entityType).withStyle(ChatFormatting.GOLD));
            } else {
                components.add(Component.translatable("wd.entity_type_not_set").withStyle(ChatFormatting.RED));
            }

            if (tag.contains(ENTITY_NAME_TAG)) {
                String entityName = tag.getString(ENTITY_NAME_TAG);
                components.add(Component.translatable("wd.entity_name", entityName).withStyle(ChatFormatting.GREEN));
            } else {
                components.add(Component.translatable("wd.entity_name_not_set").withStyle(ChatFormatting.RED));
            }

        } else {
            components.add(Component.translatable("wd.press_shift").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, level, components, flag);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        Entity entity = context.getPlayer();
        Player player = context.getPlayer();


        // Сначала проверяем, можно ли зарядить камень душ
        if (chargeSoulStone(level, pos.getX(), pos.getY(), pos.getZ(), entity, stack)) {
            return InteractionResult.SUCCESS; // Успешное выполнение chargeSoulStone
        }

        // Если не удалось зарядить, пытаемся освободить душу
        if (releaseSoulStone(level, pos, stack, player)) {
            return InteractionResult.SUCCESS; // Успешное выполнение releaseSoulStone
        }

        return InteractionResult.PASS;
    }
    public boolean isCharged(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("Charged");

    }
    public static void charged(ItemStack stack) {
        stack.getOrCreateTag().putBoolean("Charged", true);
        stack.getOrCreateTag().putInt("CustomModelData", 1); // Обновляем модель
    }

    public static void discharged(ItemStack stack, Player player) {
        stack.getOrCreateTag().putBoolean("Charged", false);
        stack.getOrCreateTag().putInt("CustomModelData", 0); // Возвращаем обычную модель
    }

    public static boolean chargeSoulStone(LevelAccessor world, int x, int y, int z, Entity entity, ItemStack stack) {
        if (entity == null || !(entity instanceof LivingEntity _livEnt)) {
            return false; // Если сущность не существует или не является живым существом, не выполняем
        }

        ItemStack mainHandItem = _livEnt.getMainHandItem();

        // Проверка на блок и состояние предмета
        BlockState blockState = world.getBlockState(new BlockPos(x, y, z));
        if (blockState.getBlock() == BlocksWD.ROSE_OF_THE_MURDERER.get() // проверяем блок
                && blockState.getValue(RoseMurderer.IS_SOUL) // проверка на состояние
                && mainHandItem.getItem() == ItemsWD.SOUL_STONE.get()) { // проверка на состояние зарядки

            // Устанавливаем состояние CHARGET_TAG = true
            charged(stack); // Передаем stack в метод активации

            // Получаем данные для тегов
            String entityType = getEntityTag(world, new BlockPos(x, y, z), "entity_type");
            String entityName = getEntityTag(world, new BlockPos(x, y, z), "entity_name");

            // Записываем теги в предмет
            CompoundTag tag = mainHandItem.getOrCreateTag();
            tag.putString(ENTITY_TYPE_TAG, entityType);
            tag.putString(ENTITY_NAME_TAG, entityName);

            // Изменяем состояние блока IS_SOUL на false
            BlockPos _bp = new BlockPos(x, y, z);
            BlockState _bs = world.getBlockState(_bp);
            if (_bs.getValue(RoseMurderer.IS_SOUL)) {
                _bs = _bs.setValue(RoseMurderer.IS_SOUL, false);
                world.setBlock(_bp, _bs, 3); // Обновляем блок с флагом обновления соседей
            }

            // Обновляем предмет в руке игрока
            if (_livEnt instanceof Player _player) {
                _player.setItemInHand(InteractionHand.MAIN_HAND, mainHandItem);
                _player.getInventory().setChanged();
            }

            return true; // Условия выполнены, возвращаем true
        }

        return false; // Условия не выполнены, возвращаем false
    }
    public static boolean releaseSoulStone(Level world, BlockPos pos, ItemStack stack, Player player) {
        // Проверка на наличие данных в теге
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.contains(ENTITY_TYPE_TAG) || !tag.contains(ENTITY_NAME_TAG)) {
            return false; // Нет данных о сущности, не продолжаем
        }

        // Получаем информацию о сущности
        String entityType = tag.getString(ENTITY_TYPE_TAG);
        String entityName = tag.getString(ENTITY_NAME_TAG);

        // Получаем информацию о сущности
        EntityType<?> entityTypeObj = EntityType.byString(entityType).orElse(null);
        if (entityTypeObj != null) {
            // Спавним сущность
            Entity entityToSpawn = entityTypeObj.create(world);
            if (entityToSpawn != null) {
                // Определяем сторону, по которой кликнули, используя блоковую позицию
                BlockPos spawnPos = getSpawnPositionForBlock(world, pos, player);

                // Устанавливаем позицию сущности в свободном месте
                entityToSpawn.setPos(spawnPos.getX(), spawnPos.getY() + 1, spawnPos.getZ()); // Сущность будет спауниться немного выше позиции блока
                world.addFreshEntity(entityToSpawn); // Добавляем сущность в мир
            }
        }

        // Очищаем данные о сущности в SoulStone
        tag.remove(ENTITY_TYPE_TAG);
        tag.remove(ENTITY_NAME_TAG);
        discharged(stack, null); // Возвращаем предмет в исходное состояние

        return true; // Успешное выполнение, возвращаем true
    }

    // Получаем позицию для спауна сущности в зависимости от того, на какой грани блока был клик
    private static BlockPos getSpawnPositionForBlock(Level world, BlockPos pos, Player player) {
        // Определяем позицию клика (грань блока)
        BlockPos clickedPos = player.blockPosition();

        // Ищем соседние позиции для каждой грани блока
        BlockPos spawnPos = pos;

        // Проверяем, куда кликнули: на грань блока, и ищем ближайшую свободную позицию
        if (clickedPos.getX() > pos.getX()) { // Клик по правой грани
            spawnPos = pos.offset(1, 0, 0); // Спауним справа
        } else if (clickedPos.getX() < pos.getX()) { // Клик по левой грани
            spawnPos = pos.offset(-1, 0, 0); // Спауним слева
        } else if (clickedPos.getZ() > pos.getZ()) { // Клик по передней грани
            spawnPos = pos.offset(0, 0, 1); // Спауним спереди
        } else if (clickedPos.getZ() < pos.getZ()) { // Клик по задней грани
            spawnPos = pos.offset(0, 0, -1); // Спауним сзади
        } else if (clickedPos.getY() > pos.getY()) { // Клик по верхней грани
            spawnPos = pos.offset(0, 1, 0); // Спауним сверху
        } else if (clickedPos.getY() < pos.getY()) { // Клик по нижней грани
            spawnPos = pos.offset(0, -1, 0); // Спауним снизу
        }

        // Проверяем, что позиция свободна (если здесь нет блока)
        while (!world.isEmptyBlock(spawnPos)) {
            spawnPos = spawnPos.above(); // Пробуем сместиться вверх, если блок занят
        }

        return spawnPos;
    }
    private static String getEntityTag(LevelAccessor world, BlockPos pos, String tag) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            CompoundTag persistentData = blockEntity.getPersistentData();
            if (persistentData.contains(tag)) {
                return persistentData.getString(tag);
            } else {
                System.out.println("Tag not found: " + tag);  // Логируем отсутствие тега
                return "";
            }
        } else {
            System.out.println("BlockEntity not found at " + pos);  // Логируем отсутствие BlockEntity
            return "";
        }
    }
}