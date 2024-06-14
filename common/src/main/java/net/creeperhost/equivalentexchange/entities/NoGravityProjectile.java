package net.creeperhost.equivalentexchange.entities;

import net.minecraft.SharedConstants;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class NoGravityProjectile extends ThrowableProjectile
{
    protected NoGravityProjectile(EntityType<? extends ThrowableProjectile> entityType, Level level)
    {
        super(entityType, level);
    }

    protected NoGravityProjectile(EntityType<? extends ThrowableProjectile> type, LivingEntity shooter, Level level)
    {
        super(type, shooter, level);
    }

    @Override
    protected float getGravity()
    {
        return 0;
    }

    @Override
    public void tick()
    {
        super.tick();
        if (!this.level().isClientSide)
        {
            if (tickCount > (20 * SharedConstants.TICKS_PER_SECOND) || getDeltaMovement().equals(Vec3.ZERO) || !level().isLoaded(blockPosition()))
            {
                discard();
            }
        }
    }
}
