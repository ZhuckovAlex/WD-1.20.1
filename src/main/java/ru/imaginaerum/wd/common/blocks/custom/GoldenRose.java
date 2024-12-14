package ru.imaginaerum.wd.common.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;
import ru.imaginaerum.wd.common.blocks.BlocksWD;
import ru.imaginaerum.wd.common.items.ItemsWD;

public class GoldenRose extends Block implements net.minecraftforge.common.IPlantable {
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    private static final int TICK_DELAY = 6000; // 20 секунд (20 тиков = 1 секунда, 400 тиков = 20 секунд)

    public GoldenRose(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack itemStack = context.getItemInHand();
        if (itemStack.getItem() == ItemsWD.MEADOW_GOLDEN_FLOWER.get()) {
            return this.defaultBlockState().setValue(ACTIVE, true);
        } else {
            return this.defaultBlockState().setValue(ACTIVE, false);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return state.getValue(GoldenRose.ACTIVE) ?
                new ItemStack(ItemsWD.MEADOW_GOLDEN_FLOWER.get()) :
                new ItemStack(ItemsWD.MEADOW_GOLDEN_FLOWER_INACTIVE.get());
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide && !state.getValue(ACTIVE)) {
            level.scheduleTick(pos, this, TICK_DELAY);
        }
        super.onPlace(state, level, pos, oldState, isMoving);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
            return;
        }

        if (!state.getValue(ACTIVE)) {
            level.setBlock(pos, state.setValue(ACTIVE, true), 3);
        }
    }

    @Override
    public BlockState updateShape(BlockState currentBlockState, Direction direction, BlockState neighborBlockState, LevelAccessor world, BlockPos currentPos, BlockPos neighborPos) {
        if (!currentBlockState.canSurvive(world, currentPos)) {
            world.scheduleTick(currentPos, this, 1);
        } else if (!currentBlockState.getValue(ACTIVE) && !world.getBlockTicks().hasScheduledTick(currentPos, this)) {
            if (world instanceof Level level) {
                level.scheduleTick(currentPos, this, TICK_DELAY);
            }
        }
        return super.updateShape(currentBlockState, direction, neighborBlockState, world, currentPos, neighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos blockPos) {
        BlockState belowBlockState = levelReader.getBlockState(blockPos.below());
        return belowBlockState.is(Blocks.SOUL_SOIL) || belowBlockState.is(Blocks.SOUL_SAND) || belowBlockState.is(BlockTags.NYLIUM) || belowBlockState.is(BlockTags.SAND) || belowBlockState.is(Blocks.DIRT) || belowBlockState.is(Blocks.GRASS_BLOCK);
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.PLAINS;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos blockPos, RandomSource randomSource) {
        VoxelShape voxelShape = this.getShape(state, level, blockPos, CollisionContext.empty());
        Vec3 vec3 = voxelShape.bounds().getCenter();
        double d0 = blockPos.getX() + vec3.x;
        double d1 = blockPos.getZ() + vec3.z;

        if (state.getValue(ACTIVE)) {
            for (int i = 0; i < 3; ++i) {
                if (randomSource.nextBoolean()) {
                    level.addParticle(ParticleTypes.HAPPY_VILLAGER, d0 + randomSource.nextDouble() / 5.0D, blockPos.getY() + (0.5D - randomSource.nextDouble()), d1 + randomSource.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && state.getValue(ACTIVE) && entity instanceof LivingEntity livingEntity) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.HEAL, 10));
        }
    }

    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return defaultBlockState();
    }
}