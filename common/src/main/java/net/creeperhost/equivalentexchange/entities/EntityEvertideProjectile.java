package net.creeperhost.equivalentexchange.entities;

import net.creeperhost.equivalentexchange.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class EntityEvertideProjectile extends NoGravityProjectile
{
    public EntityEvertideProjectile(EntityType<EntityEvertideProjectile> entityType, Level level)
    {
        super(entityType, level);
    }

    public EntityEvertideProjectile(Player entity, Level level)
    {
        super(ModEntities.EVERTIDE_PROJECTILE.get(), entity, level);
    }

    @Override
    public void tick()
    {
        super.tick();
        if(!level().isClientSide() && isAlive())
        {
            Entity thrower = getOwner();
            if(thrower instanceof ServerPlayer player)
            {
                if(getY() > 128)
                {
                    LevelData levelData = level().getLevelData();
                    levelData.setRaining(true);
                    discard();
                }
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult)
    {
        super.onHitBlock(blockHitResult);
        if(!level().isClientSide() && getOwner() instanceof ServerPlayer player)
        {
            BlockPos hitPos = blockHitResult.getBlockPos();
            Direction sideHit = blockHitResult.getDirection();
            level().setBlock(hitPos.relative(sideHit, 1), Blocks.WATER.defaultBlockState(), 3);
        }
    }

    @Override
    protected void onHit(@NotNull HitResult result)
    {
        super.onHit(result);
        discard();
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    public boolean ignoreExplosion(@NotNull Explosion explosion)
    {
        return true;
    }
}
