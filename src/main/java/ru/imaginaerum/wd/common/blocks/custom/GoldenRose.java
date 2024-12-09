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
        ItemStack itemStack = context.getItemInHand(); // Получаем предмет, с которым игрок пытается разместить блок

        // Проверяем, какой предмет был использован
        if (itemStack.getItem() == ItemsWD.MEADOW_GOLDEN_FLOWER.get()) {
            // Если использован активный предмет, устанавливаем блок как активный
            return this.defaultBlockState().setValue(ACTIVE, true);
        } else {
            // Если использован неактивный предмет, устанавливаем блок как неактивный
            return this.defaultBlockState().setValue(ACTIVE, false);
        }
    }
    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        // Если блок активен, возвращаем активный предмет
        if (state.getValue(GoldenRose.ACTIVE)) {
            return new ItemStack(ItemsWD.MEADOW_GOLDEN_FLOWER.get());
        } else {
            // Если неактивен, возвращаем неактивный предмет
            return new ItemStack(ItemsWD.MEADOW_GOLDEN_FLOWER_INACTIVE.get());
        }
    }
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
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
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide && !state.getValue(ACTIVE)) {
            // Планируем активацию через 5 минут (6000 тиков)
            level.scheduleTick(pos, this, 6000);
        }
        super.onPlace(state, level, pos, oldState, isMoving);
    }
    public boolean canSurvive(BlockState p_57175_, LevelReader levelReader, BlockPos blockPos) {
        BlockState belowBlockState = levelReader.getBlockState(blockPos.below());
        return belowBlockState.is(Blocks.SOUL_SOIL)||belowBlockState.is(Blocks.SOUL_SAND)|| belowBlockState.is(BlockTags.NYLIUM)|| belowBlockState.is(BlockTags.SAND)||belowBlockState.is(Blocks.DIRT)||belowBlockState.is(Blocks.GRASS_BLOCK);
    }
    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.PLAINS;
    }

    public void animateTick(BlockState state, Level level, BlockPos blockPos, RandomSource randomSource) {
        VoxelShape voxelshape = this.getShape(state, level, blockPos, CollisionContext.empty());
        Vec3 vec3 = voxelshape.bounds().getCenter();
        double d0 = (double)blockPos.getX() + vec3.x;
        double d1 = (double)blockPos.getZ() + vec3.z;
        if (state.getValue(ACTIVE)) {
            for (int i = 0; i < 3; ++i) {
                if (randomSource.nextBoolean()) {
                    level.addParticle(ParticleTypes.HAPPY_VILLAGER, d0 + randomSource.nextDouble() / 5.0D, (double) blockPos.getY() + (0.5D - randomSource.nextDouble()), d1 + randomSource.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if ((!level.isClientSide)&&(state.getValue(ACTIVE))) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                    livingentity.addEffect(new MobEffectInstance(MobEffects.HEAL, 10));
            }
        }
    }


    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return defaultBlockState();
    }
}
