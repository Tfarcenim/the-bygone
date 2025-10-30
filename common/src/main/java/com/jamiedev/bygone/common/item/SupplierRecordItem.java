package com.jamiedev.bygone.common.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.RecordItem;

import java.util.function.Supplier;

public class SupplierRecordItem extends RecordItem {
    public SupplierRecordItem(int analogOutput, Supplier<SoundEvent> sound, Properties properties, int lengthInSeconds) {
        super(analogOutput, SoundEvents.BEEHIVE_EXIT, properties, lengthInSeconds);//this is a dummy to prevent crashing
        this.soundSupplier = sound;
    }
    private final java.util.function.Supplier<SoundEvent> soundSupplier;

    @Override
    public SoundEvent getSound() {
        return soundSupplier.get();
    }
}
