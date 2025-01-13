package ru.imaginaerum.wd.common.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FireRod extends Block implements net.minecraftforge.common.IPlantable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
    protected static final float AABB_OFFSET = 6.0F;
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public FireRod(Properties p_49795_) {
        super(p_49795_);
    }


    public VoxelShape getShape(BlockState p_57193_, BlockGetter p_57194_, BlockPos p_57195_, CollisionContext p_57196_) {
        return SHAPE;
    }

    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource p_222546_) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }

    }

    public void randomTick(BlockState p_222548_, ServerLevel p_222549_, BlockPos p_222550_, RandomSource p_222551_) {
        if (p_222549_.isEmptyBlock(p_222550_.above())) {
            int i;
            for(i = 1; p_222549_.getBlockState(p_222550_.below(i)).is(this); ++i) {
            }

            if (i < 3) {
                int j = p_222548_.getValue(AGE);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_222549_, p_222550_, p_222548_, true)) {
                    if (j == 15) {
                        p_222549_.setBlockAndUpdate(p_222550_.above(), this.defaultBlockState());
                        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_222549_, p_222550_.above(), this.defaultBlockState());
                        p_222549_.setBlock(p_222550_, p_222548_.setValue(AGE, Integer.valueOf(0)), 4);
                    } else {
                        p_222549_.setBlock(p_222550_, p_222548_.setValue(AGE, Integer.valueOf(j + 1)), 4);
                    }
                }
            }
        }

    }

    public BlockState updateShape(BlockState p_57179_, Direction p_57180_, BlockState p_57181_, LevelAccessor p_57182_, BlockPos p_57183_, BlockPos p_57184_) {
        if (!p_57179_.canSurvive(p_57182_, p_57183_)) {
            p_57182_.scheduleTick(p_57183_, this, 1);
        }

        return super.updateShape(p_57179_, p_57180_, p_57181_, p_57182_, p_57183_, p_57184_);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos position) {
        BlockState blockBelow = world.getBlockState(position.below());

        // Проверяем, поддерживает ли блок снизу растения
        if (blockBelow.canSustainPlant(world, position.below(), Direction.UP, this)) {
            return true;
        }

        // Если блок ниже такой же, как текущий блок
        if (blockBelow.is(this)) {
            return true;
        }

        // Проверяем, если блок ниже — один из допустимых блоков
        if (blockBelow.is(BlockTags.NYLIUM) || blockBelow.is(Blocks.NETHERRACK) ||
                blockBelow.is(Blocks.GRAVEL) || blockBelow.is(Blocks.BASALT) ||
                blockBelow.is(Blocks.BLACKSTONE)) {

            BlockPos belowPosition = position.below();

            // Проверяем, есть ли лава по горизонтали от текущего блока
            for (Direction horizontalDirection : Direction.Plane.HORIZONTAL) {
                BlockState neighborBlock = world.getBlockState(belowPosition.relative(horizontalDirection));
                FluidState neighborFluid = world.getFluidState(belowPosition.relative(horizontalDirection));

                if (neighborFluid.is(Fluids.LAVA) || neighborFluid.is(Fluids.FLOWING_LAVA)) {
                    return true;
                }
            }
        }

        // Если блок ниже — магма, возвращаем true
        if (blockBelow.is(Blocks.MAGMA_BLOCK)) {
            return true;
        }

        // В остальных случаях блок не может выжить
        return false;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_57186_) {
        p_57186_.add(AGE);
    }

    @Override
    public net.minecraftforge.common.PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return null;
    }

    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return defaultBlockState();
    }


    @Override
    public void entityInside(BlockState p_49260_, Level world, BlockPos p_49262_, Entity entity) {
        super.entityInside(p_49260_, world, p_49262_, entity);
        entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.IN_FIRE)), 1);
        entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), 4);
        entity.setSecondsOnFire(5);
    }
}