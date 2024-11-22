package ru.imaginaerum.wd.common.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;

public class CoastalSteepBlock extends Block {
    public static final BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");

    public CoastalSteepBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
            return;
        }

        // Проверяем условия для изменения состояния WATERLOGGED
        if (!state.getValue(WATERLOGGED)) {
            boolean isRainy = level.isRainingAt(pos.above());
            boolean hasWaterBelow = level.getBlockState(pos.below()).getFluidState().is(Fluids.WATER);
            boolean hasWaterNearby = isWaterWithinDistance(level, pos, 3);

            if ((hasWaterNearby && hasWaterBelow && random.nextFloat() < 0.3f) ||
                    (isRainy && level.getGameTime() % (20 * 60 * 3) == 0)) {
                level.setBlock(pos, state.setValue(WATERLOGGED, true), 3);
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState currentBlockState, Direction direction, BlockState neighborBlockState, LevelAccessor world, BlockPos currentPos, BlockPos neighborPos) {
        if (!currentBlockState.canSurvive(world, currentPos)) {
            world.scheduleTick(currentPos, this, 1);
        }

        return super.updateShape(currentBlockState, direction, neighborBlockState, world, currentPos, neighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos blockPos) {
        BlockState belowBlockState = levelReader.getBlockState(blockPos.below());
        return belowBlockState.is(Blocks.SAND) || belowBlockState.is(Blocks.RED_SAND);
    }

    private boolean isWaterWithinDistance(ServerLevel level, BlockPos pos, int distance) {
        for (int dx = -distance; dx <= distance; dx++) {
            for (int dz = -distance; dz <= distance; dz++) {
                BlockPos checkPos = pos.offset(dx, -1, dz);
                if (level.getBlockState(checkPos).getFluidState().is(Fluids.WATER)) {
                    return true;
                }
            }
        }
        return false;
    }
}