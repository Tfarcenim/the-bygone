package com.jamiedev.bygone.common.entity.projectile;

import com.jamiedev.bygone.core.registry.BGEntityTypes;
import com.jamiedev.bygone.core.registry.BGItems;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class ExoticArrowEntity extends BaseArrowEntity {

    boolean returns = false;

    public ExoticArrowEntity(EntityType<? extends ExoticArrowEntity> entityType, Level world) {
        super(entityType, world);
    }

    public ExoticArrowEntity(Level world, double x, double y, double z) {
        super(BGEntityTypes.EXOTIC_ARROW.get(), x, y, z, world);
    }

    public ExoticArrowEntity(Level world, LivingEntity owner) {
        super(BGEntityTypes.EXOTIC_ARROW.get(), owner, world);
    }



    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        this.returns = true;
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.level().broadcastEntityEvent(this, (byte)0);
    }


    public static void dropArrow(Level world, BlockPos pos) {
        dropStack(world, pos, new ItemStack(BGItems.EXOTIC_ARROW.get(), 1));
    }

    private static void dropStack(Level world, Supplier<ItemEntity> itemEntitySupplier, ItemStack stack) {
        if (!world.isClientSide && !stack.isEmpty() && world.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            ItemEntity itemEntity = itemEntitySupplier.get();
            itemEntity.setDefaultPickUpDelay();
            world.addFreshEntity(itemEntity);
        }
    }
    public static void dropStack(Level world, BlockPos pos, ItemStack stack) {
        double d = (double)EntityType.ITEM.getHeight() / 2.0;
        double e = (double)pos.getX() + 0.5 + Mth.nextDouble(world.random, -0.25, 0.25);
        double f = (double)pos.getY() + 0.5 + Mth.nextDouble(world.random, -0.25, 0.25) - d;
        double g = (double)pos.getZ() + 0.5 + Mth.nextDouble(world.random, -0.25, 0.25);
        dropStack(world, () -> {
            return new ItemEntity(world, e, f, g, stack);
        }, stack);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(BGItems.EXOTIC_ARROW.get());
    }

    @Override
    public double getBaseDamage() {
        return 1.0D;
    }

}
