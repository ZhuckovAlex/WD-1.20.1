package ru.imaginaerum.wd.common.items.arrows;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.PlayMessages;
import ru.imaginaerum.wd.common.items.ItemsWD;

public class FlameArrow extends AbstractArrow {

    public FlameArrow(EntityType<? extends FlameArrow> p_37411_, Level p_37412_) {
        super(p_37411_, p_37412_);
    }

    public FlameArrow(Level p_37419_, LivingEntity p_37420_) {
        super(EntityTypeInit.FLAME_ARROW.get(), p_37420_, p_37419_);
    }

    public FlameArrow(Level p_37414_, double p_37415_, double p_37416_, double p_37417_) {
        super(EntityTypeInit.FLAME_ARROW.get(), p_37415_, p_37416_, p_37417_, p_37414_);
    }

    public FlameArrow(PlayMessages.SpawnEntity spawnEntity, Level world) {
        this(EntityTypeInit.FLAME_ARROW.get(), world);
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(ParticleTypes.LAVA, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }

    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ItemsWD.FLAME_ARROW.get());
    }




    protected void onHitBlock(BlockHitResult p_37384_) {
        super.onHitBlock(p_37384_);
        if (!this.level().isClientSide) {
            Entity entity = this.getOwner();
            if (!(entity instanceof Mob) || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), entity)) {
                BlockPos blockpos = p_37384_.getBlockPos().relative(p_37384_.getDirection());
                if (this.level().isEmptyBlock(blockpos)) {
                    this.level().setBlockAndUpdate(blockpos, BaseFireBlock.getState(this.level(), blockpos));
                }
            }

        }
    }
    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity targetEntity = result.getEntity();

        if (targetEntity instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) targetEntity;
            livingTarget.setSecondsOnFire(10); // Поджигаем цель на 5 секунд

        }
    }
}