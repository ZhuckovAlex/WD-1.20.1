package ru.imaginaerum.wd.common.blocks.custom;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import ru.imaginaerum.wd.common.blocks.BlocksWD;

public class AppleLeaves extends LeavesBlock {
    public AppleLeaves(Properties properties) {
        super(properties);
    }
    @OnlyIn(Dist.CLIENT)
    public static void blockColorLoad(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors().register((bs, world, pos, index) -> {
            return world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.get(0.5D, 1.0D);
        }, BlocksWD.APPLE_LEAVES.get());
    }
}
