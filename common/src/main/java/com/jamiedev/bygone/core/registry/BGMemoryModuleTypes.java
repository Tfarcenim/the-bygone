package com.jamiedev.bygone.core.registry;

import com.jamiedev.bygone.Bygone;
import com.kekecreations.jinxedlib.core.util.JinxedRegistryHelper;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class BGMemoryModuleTypes {
    public static final Supplier<MemoryModuleType<UUID>> GROUP_LEADER = register("group_leader",UUIDUtil.CODEC);
    public static final Supplier<MemoryModuleType<Boolean>> IS_LEADER = register("is_leader");
    public static final Supplier<MemoryModuleType<Boolean>> IS_STALKING = register("is_stalking");
    public static final Supplier<MemoryModuleType<BGMemoryModuleTypes>> IS_IN_GROUP = register("is_in_group");
    public static final Supplier<MemoryModuleType<LivingEntity>> NEAREST_NECTAUR_ALLY = register("nearest_nectaur_ally");
    public static final Supplier<MemoryModuleType<Unit>> NECTAUR_RANGED_COOLDOWN = register("nectaur_ranged_cooldown",Codec.unit(Unit.INSTANCE));


    private static <U> Supplier<MemoryModuleType<U>> register(String identifier, Codec<U> codec) {
        return (Supplier<MemoryModuleType<U>>)(Object) JinxedRegistryHelper.register(BuiltInRegistries.MEMORY_MODULE_TYPE, Bygone.MOD_ID,identifier,() ->
                new MemoryModuleType<>(Optional.of(codec))
        );
    }

    private static <U> Supplier<MemoryModuleType<U>> register(String identifier) {
        return (Supplier<MemoryModuleType<U>>)(Object) JinxedRegistryHelper.register(
                BuiltInRegistries.MEMORY_MODULE_TYPE, Bygone.MOD_ID,identifier, () -> new MemoryModuleType<>(Optional.empty())
        );
    }

    protected static void init() {
        Bygone.LOGGER.info("Registering {} memory module types", Bygone.MOD_ID);
    }
}
