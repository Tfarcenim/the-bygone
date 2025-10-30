package com.jamiedev.bygone.core.registry;

import com.jamiedev.bygone.Bygone;
import com.kekecreations.jinxedlib.core.util.JinxedRegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

public class BGParticleTypes
{

    public static final Supplier<SimpleParticleType> AMBER_DUST = simple("amber_dust");
    public static final Supplier<SimpleParticleType> RAFFLESIA_SPORES = simple( "rafflesia_spores");
    public static final Supplier<SimpleParticleType> ALGAE_BLOOM = simple( "algae_bloom");

    public static final Supplier<SimpleParticleType> BLEMISH = simple( "blemish_bubble");
    public static final Supplier<SimpleParticleType> SHELF = simple("shelf");
    public static final Supplier<SimpleParticleType> ANCIENT_LEAVES = simple("ancient_leaves");
    public static final Supplier<SimpleParticleType> UPSIDEDOWN = simple("upside_down_rain");

    public static void init() {
    }

    public static Supplier<SimpleParticleType> simple(String name) {
        return (Supplier<SimpleParticleType>)(Object)JinxedRegistryHelper.register(BuiltInRegistries.PARTICLE_TYPE,Bygone.MOD_ID,name,() -> new SimpleParticleType(false){});
    }

}
