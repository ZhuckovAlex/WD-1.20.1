package ru.imaginaerum.wd.common.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

import java.lang.reflect.Method;

public class ModFlammableBlocks {

    public static void registerFlammableBlocks() {
        try {
            // Получаем метод setFlammable из класса FireBlock через рефлексию
            Method setFlammableMethod = FireBlock.class.getDeclaredMethod("setFlammable", Block.class, int.class, int.class);
            // Делаем метод доступным для вызова
            setFlammableMethod.setAccessible(true);
            // Получаем экземпляр блока огня (FireBlock)
            FireBlock fireBlock = (FireBlock) Blocks.FIRE;
            // Устанавливаем горючие свойства для пользовательского блока APPLE_LOG:
            // - 5: вероятность воспламенения (Ignite Odds). Низкая вероятность для древесины.
            //       0: блок никогда не загорается (например, камень).
            //       60: блок очень легко воспламеняется (например, лиственница или шерсть).
            // - 20: скорость распространения огня (Burn Odds). Средняя скорость для древесных блоков.
            //       0: огонь не распространяется через блок (например, камень).
            //       60: огонь распространяется очень быстро (например, листья).
            setFlammableMethod.invoke(fireBlock, BlocksWD.APPLE_LOG.get(), 5, 5);
            setFlammableMethod.invoke(fireBlock, BlocksWD.APPLE_WOOD.get(), 5, 5);
            setFlammableMethod.invoke(fireBlock, BlocksWD.STRIPPED_APPLE_LOG.get(), 5, 5);
            setFlammableMethod.invoke(fireBlock, BlocksWD.STRIPPED_APPLE_WOOD.get(), 5, 5);
            setFlammableMethod.invoke(fireBlock, BlocksWD.APPLE_PLANKS.get(), 5, 20);
            setFlammableMethod.invoke(fireBlock, BlocksWD.APPLE_SAPLING.get(), 10, 40);
            setFlammableMethod.invoke(fireBlock, BlocksWD.APPLE_LEAVES.get(), 30, 60);
            setFlammableMethod.invoke(fireBlock, BlocksWD.APPLE_LEAVES_STAGES.get(), 30, 60);
            setFlammableMethod.invoke(fireBlock, BlocksWD.APPLE_STAIRS.get(), 5, 20);
            setFlammableMethod.invoke(fireBlock, BlocksWD.APPLE_SLAB.get(), 5, 20);
            setFlammableMethod.invoke(fireBlock, BlocksWD.APPLE_FENCE.get(), 5, 20);
            setFlammableMethod.invoke(fireBlock, BlocksWD.APPLE_FENCE_GATE.get(), 5, 20);
        } catch (Exception e) {
            // Если происходит ошибка (например, метод не найден), она будет выведена в консоль
            e.printStackTrace();
        }
    }
}