package com.jamiedev.bygone.core.registry;

import com.jamiedev.bygone.Bygone;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import java.util.function.Supplier;

public class BGMobEffects {
    
    public static Supplier<MobEffect> UPDRAFT = () -> {
        MobEffect effect = BuiltInRegistries.MOB_EFFECT.get(Bygone.id("updraft"));
        return effect;
    };
    
    public static void init() {
    }
}