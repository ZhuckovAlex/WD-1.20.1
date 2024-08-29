package ru.imaginaerum.wd.common.items.arrows;


import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import ru.imaginaerum.wd.common.items.ItemsWD;

public class DispenserRegistry {
    public static void registerBehaviors() {

        DispenserBlock.registerBehavior(ItemsWD.FLAME_ARROW.get(), new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level level, Position pos, ItemStack stack) {
                AbstractArrow abstractarrow = new FlameArrow(level, pos.x(), pos.y(), pos.z());
                abstractarrow.pickup = AbstractArrow.Pickup.ALLOWED;
                return abstractarrow;
            }
        });

    }
}
