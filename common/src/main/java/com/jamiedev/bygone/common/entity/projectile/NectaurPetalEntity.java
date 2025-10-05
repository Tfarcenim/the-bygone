package com.jamiedev.bygone.common.entity.projectile;

import com.jamiedev.bygone.core.registry.BGEntityTypes;
import com.jamiedev.bygone.core.registry.BGItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NectaurPetalEntity extends BaseArrowEntity
{
    public NectaurPetalEntity(EntityType<? extends NectaurPetalEntity> entityType, Level world) {
        super(entityType, world);
    }

    public NectaurPetalEntity(Level world, LivingEntity owner) {
        super(BGEntityTypes.NECTAUR_PETAL.get(), owner, world);
    }

    public NectaurPetalEntity(Level world, double x, double y, double z) {
        super(BGEntityTypes.NECTAUR_PETAL.get(), x, y, z, world);
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.MUD_HIT;
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(BGItems.NECTAUR_PETAL.get());
    }

}
