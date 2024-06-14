package net.creeperhost.equivalentexchange.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.creeperhost.equivalentexchange.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds
{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Constants.MOD_ID, Registries.SOUND_EVENT);
    public static final RegistrySupplier<SoundEvent> TRANSMUTE = SOUNDS.register("transmute", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Constants.MOD_ID, "transmute")));
    public static final RegistrySupplier<SoundEvent> CHARGE = SOUNDS.register("charge", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Constants.MOD_ID, "charge")));
    public static final RegistrySupplier<SoundEvent> UNCHARGE = SOUNDS.register("uncharge", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Constants.MOD_ID, "uncharge")));
    public static final RegistrySupplier<SoundEvent> HEAL = SOUNDS.register("heal", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Constants.MOD_ID, "heal")));

}
