package ru.imaginaerum.wd.common.blocks.custom;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.Nullable;
import ru.imaginaerum.wd.common.blocks.BlocksWD;

public class StrippedWoodLogs extends RotatedPillarBlock {
    public StrippedWoodLogs(Properties properties) {
        super(properties);
    }
    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        if(context.getItemInHand().getItem() instanceof AxeItem) {
            if(state.is(BlocksWD.APPLE_LOG.get())) {
                return BlocksWD.STRIPPED_APPLE_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            if(state.is(BlocksWD.APPLE_WOOD.get())) {
                return BlocksWD.STRIPPED_APPLE_WOOD.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }
        }

        return super.getToolModifiedState(state, context, toolAction, simulate);
    }
}
