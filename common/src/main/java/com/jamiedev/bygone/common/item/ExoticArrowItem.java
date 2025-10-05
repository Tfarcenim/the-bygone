package com.jamiedev.bygone.common.item;

import com.jamiedev.bygone.common.entity.projectile.ExoticArrowEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ExoticArrowItem extends ArrowItem {
    public ExoticArrowItem(Item.Properties settings) {
        super(settings);
    }

    TridentItem ref;

    @Override
    public AbstractArrow createArrow(Level world, ItemStack stack, LivingEntity shooter) {
        return new ExoticArrowEntity(world, shooter);
    }
}