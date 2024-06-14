package net.creeperhost.equivalentexchange.entities;

import net.creeperhost.equivalentexchange.init.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityNovaCataclysm extends PrimedTnt
{
    public EntityNovaCataclysm(EntityType<? extends PrimedTnt> entityType, Level level)
    {
        super(entityType, level);
    }

    public EntityNovaCataclysm(Level level, double x, double y, double z, LivingEntity placer)
    {
        super(level, x, y, z, placer);
        blocksBuilding = true;
    }

    @Override
    public int getFuse()
    {
        return 20;
    }

    @Override
    public @NotNull EntityType<?> getType()
    {
        return ModEntities.NOVA_CATACLYSM.get();
    }

    @Nullable
    @Override
    public ItemStack getPickResult()
    {
        //TODO
//        return new ItemStack(ModBlocks.NOVA_CATACLYSM.get());
        return ItemStack.EMPTY;
    }
//    @Override
//    public void explode() {
//        float f = 4.0F;
//        this.level.explode(this, this.getX(), this.getY(0.0625), this.getZ(), 4.0F, Explosion.BlockInteraction.BREAK);
//    }
}
