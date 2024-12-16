package ru.imaginaerum.wd.common.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import ru.imaginaerum.wd.WD;
import ru.imaginaerum.wd.common.blocks.BlocksWD;
import ru.imaginaerum.wd.common.blocks.entity.RoseMurdererBlockEntity;
import ru.imaginaerum.wd.common.particles.ModParticles;

import java.util.Map;

public class RoseMurderer extends BaseEntityBlock implements IPlantable {

    public static final BooleanProperty IS_SOUL = BooleanProperty.create("is_soul");

    public RoseMurderer(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(IS_SOUL, false));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @SubscribeEvent
    public void onEntityDrops(LivingDropsEvent event) {

        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        BlockPos pos = entity.blockPosition();
        BlockState blockState = level.getBlockState(pos);
        if (!(entity instanceof Player)) {
            if (blockState.getBlock() instanceof RoseMurderer) {
                event.getDrops().clear();
                event.setCanceled(true);
            }
        }
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(IS_SOUL);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(IS_SOUL, false);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock()) && !world.getBlockState(pos.below()).is(Blocks.SOUL_SOIL)) {
            world.destroyBlock(pos, true);
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }


    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
            return;
        }
        WD.queueServerWork(140, () -> {
            level.setBlock(pos, state.setValue(IS_SOUL, false), 3);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity != null) {
                blockEntity.getPersistentData().remove("entity_type");
                blockEntity.getPersistentData().remove("entity_name");
                level.sendBlockUpdated(pos, blockEntity.getBlockState(), blockEntity.getBlockState(), 3);
            }
        });
    }

    public BlockState updateShape(BlockState currentBlockState, Direction direction, BlockState neighborBlockState, LevelAccessor world, BlockPos currentPos, BlockPos neighborPos) {
        if (!currentBlockState.canSurvive(world, currentPos)) {
            world.scheduleTick(currentPos, this, 1);
        }
        return super.updateShape(currentBlockState, direction, neighborBlockState, world, currentPos, neighborPos);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return levelReader.getBlockState(blockPos.below()).is(Blocks.SOUL_SOIL);
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.NETHER;
    }

    public void animateTick(BlockState blockState, Level level, BlockPos pos, RandomSource random) {
        VoxelShape voxelShape = this.getShape(blockState, level, pos, CollisionContext.empty());
        Vec3 center = voxelShape.bounds().getCenter();
        double x = (double) pos.getX() + center.x;
        double z = (double) pos.getZ() + center.z;

        for (int i = 0; i < 3; ++i) {
            if (random.nextBoolean() && !blockState.getValue(IS_SOUL)) {
                level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x + random.nextDouble() / 5.0D, pos.getY() + (0.5D - random.nextDouble()), z + random.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
            } else if (random.nextBoolean() && blockState.getValue(IS_SOUL)) {
                level.addParticle(ModParticles.STOMBLE_ROSE.get(), x + random.nextDouble() / 5.0D, pos.getY() + (0.5D - random.nextDouble()), z + random.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);

            }
        }
    }

    private void saveEntityInfoToBlockEntity(LevelAccessor world, BlockPos pos, Entity entity) {
        if (!world.isClientSide()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity != null) {
                blockEntity.getPersistentData().putString("entity_type", ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()).toString());
                blockEntity.getPersistentData().putString("entity_name", entity.getDisplayName().getString());

                if (world instanceof Level level) {
                    level.sendBlockUpdated(pos, blockEntity.getBlockState(), blockEntity.getBlockState(), 3);
                }
            }
        }
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity instanceof LivingEntity livingEntity && !state.getValue(IS_SOUL)) {
            handleEntityDeath(level, pos, state, livingEntity);
        }
    }

    private void handleEntityDeath(Level level, BlockPos pos, BlockState state, LivingEntity entity) {
        // Применить магический урон к сущности
        entity.hurt(entity.damageSources().magic(), 2);

        if ((entity.isDeadOrDying())
                &&(entity.getMobType() != MobType.UNDEAD)
                &&!(entity instanceof IronGolem)
        ) {
            level.setBlock(pos, state.setValue(IS_SOUL, true), 3);
            saveEntityInfoToBlockEntity(level, pos, entity);

        }
    }

    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return defaultBlockState();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RoseMurdererBlockEntity(pPos, pState);

    }
}