//package net.creeperhost.equivalentexchange.entities;
//
//import net.creeperhost.equivalentexchange.init.ModEntities;
//import net.minecraft.world.entity.EntityType;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.item.PrimedTnt;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.level.Level;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//public class EntityNovaCatalyst extends PrimedTnt
//{
//    public EntityNovaCatalyst(EntityType<? extends PrimedTnt> entityType, Level level)
//    {
//        super(entityType, level);
//        setFuse(getFuse() / 4);
//    }
//
//    public EntityNovaCatalyst(Level level, double x, double y, double z, LivingEntity placer)
//    {
//        super(level, x, y, z, placer);
//        setFuse(getFuse() / 4);
//        blocksBuilding = true;
//    }
//
//    @Override
//    public @NotNull EntityType<?> getType()
//    {
//        return ModEntities.NOVA_CATALYST.get();
//    }
//
//    @Nullable
//    @Override
//    public ItemStack getPickResult()
//    {
//        //TODO
////        return new ItemStack(ModBlocks.NOVA_CATALYST.get());
//        return ItemStack.EMPTY;
//    }
//}
