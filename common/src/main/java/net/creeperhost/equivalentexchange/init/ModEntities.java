package net.creeperhost.equivalentexchange.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.equivalentexchange.Constants;
import net.creeperhost.equivalentexchange.entities.EntityEvertideProjectile;
import net.creeperhost.equivalentexchange.entities.EntityVolcaniteProjectile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Constants.MOD_ID, Registries.ENTITY_TYPE);
//    public static final RegistrySupplier<EntityType<EntityNovaCatalyst>> NOVA_CATALYST = ENTITY_TYPES.register("nova_catalyst", () ->
//            EntityType.Builder.<EntityNovaCatalyst>of(EntityNovaCatalyst::new, MobCategory.MISC).clientTrackingRange(10).updateInterval(10).build("nova_catalyst"));
//
//    public static final RegistrySupplier<EntityType<EntityNovaCataclysm>> NOVA_CATACLYSM = ENTITY_TYPES.register("nova_cataclysm", () ->
//            EntityType.Builder.<EntityNovaCataclysm>of(EntityNovaCataclysm::new, MobCategory.MISC).clientTrackingRange(10).updateInterval(10).build("nova_cataclysm"));


    public static final RegistrySupplier<EntityType<EntityVolcaniteProjectile>> VOLCANITE_PROJECTILE = ENTITY_TYPES.register("volcanite_projectile", () ->
            EntityType.Builder.<EntityVolcaniteProjectile>of(EntityVolcaniteProjectile::new, MobCategory.MISC).clientTrackingRange(256).updateInterval(10).build("volcanite_projectile"));

    public static final RegistrySupplier<EntityType<EntityEvertideProjectile>> EVERTIDE_PROJECTILE = ENTITY_TYPES.register("evertide_projectile", () ->
            EntityType.Builder.<EntityEvertideProjectile>of(EntityEvertideProjectile::new, MobCategory.MISC).clientTrackingRange(256).updateInterval(10).build("evertide_projectile"));
}
