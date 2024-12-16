package ru.imaginaerum.wd.common.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import ru.imaginaerum.wd.common.blocks.entity.DragoliteCageBlockEntity;
import ru.imaginaerum.wd.common.blocks.entity.RoseMurdererBlockEntity;
import ru.imaginaerum.wd.common.items.ItemsWD;
import ru.imaginaerum.wd.common.items.custom.SoulStone;
import ru.imaginaerum.wd.common.particles.ModParticles;

public class DragoliteCage extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty SPARKING = IntegerProperty.create("sparking", 0, 5);
    public static final IntegerProperty SOULS = IntegerProperty.create("souls", 0, 2);
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    public DragoliteCage(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(SPARKING, 0)
                .setValue(SOULS, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SPARKING, SOULS);
    }
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource source) {
        super.animateTick(state, level, pos, source);

        if (state.getValue(SOULS) == 2) {
            if (source.nextInt(100) == 0) {
                level.playSound((Player)null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            double d0 = (double)pos.getX() + 0.5D + (0.5D - source.nextDouble());
            double d1 = (double)pos.getY() + 0.3D;
            double d2 = (double)pos.getZ() + 0.5D + (0.5D - source.nextDouble());
            double d3 = (double)source.nextFloat() * 0.04D;
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, d0, d1, d2, 0.0D, d3, 0.0D);
        }
        if (state.getValue(SPARKING) > 0) {
            if (source.nextInt(100) == 0) {
                level.playSound((Player)null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            double d0 = (double)pos.getX() + 0.5D + (0.5D - source.nextDouble());
            double d1 = (double)pos.getY() + 1D;
            double d2 = (double)pos.getZ() + 0.5D + (0.5D - source.nextDouble());
            double d3 = (double)source.nextFloat() * 0.04D;
            level.addParticle(ModParticles.ROBIN_STAR_PARTICLES_PROJECTILE.get(), d0, d1, d2, 0.0D, d3, 0.0D);
        }
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blocHitResult) {
        if (player.getItemInHand(hand).is(ItemsWD.SPARKLING_POLLEN.get())) {
            if (!level.isClientSide) {
                int currentSparking = state.getValue(SPARKING);
                if (currentSparking < 5) {
                    level.setBlock(pos, state.setValue(SPARKING, currentSparking + 1), 3);
                    if (!player.isCreative()) {
                        player.getItemInHand(hand).shrink(1);
                    }
                    level.playSound(null, pos, SoundEvents.SAND_PLACE, SoundSource.BLOCKS, 1, 1);
                }
            }
        }

        if (player.getItemInHand(hand).getItem() instanceof SoulStone) {
            SoulStone soulStone = (SoulStone) player.getItemInHand(hand).getItem();
            if (soulStone.isCharged(player.getItemInHand(hand))) {
                if (!level.isClientSide) {
                    int currentSoul = state.getValue(SOULS);
                    if (currentSoul < 2) {
                        CompoundTag tag = player.getItemInHand(hand).getOrCreateTag();
                        String entityType = tag.getString("entity_type");

                        level.setBlock(pos, state.setValue(SOULS, currentSoul + 1), 3);

                        BlockEntity blockEntity = level.getBlockEntity(pos);
                        if (blockEntity != null) {
                            CompoundTag blockTag = blockEntity.getPersistentData();
                            if (currentSoul == 0) {
                                blockTag.putString("soul_first", entityType);
                            } else if (currentSoul == 1) {
                                blockTag.putString("soul_second", entityType);
                            }
                        }

                        if (!player.isCreative()) {
                            player.getItemInHand(hand).shrink(1);
                        }

                        level.playSound(null, pos, SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1, 1);
                    }
                }
            }
        }

        // Проверка при клике палкой
        if (player.getItemInHand(hand).is(Items.STICK)) {
            if (!level.isClientSide) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity != null) {
                    CompoundTag blockTag = blockEntity.getPersistentData();
                    String soulFirst = blockTag.getString("soul_first");
                    String soulSecond = blockTag.getString("soul_second");

                    player.displayClientMessage(
                            Component.literal("Soul First: " + (soulFirst.isEmpty() ? "None" : soulFirst) + ", Soul Second: " + (soulSecond.isEmpty() ? "None" : soulSecond)),
                            true // Отображается на экране, а не в чате
                    );
                }
            }
        }

        return super.use(state, level, pos, player, hand, blocHitResult);
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(SPARKING, 0)
                .setValue(SOULS, 0);

    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DragoliteCageBlockEntity(pPos, pState);

    }
}
