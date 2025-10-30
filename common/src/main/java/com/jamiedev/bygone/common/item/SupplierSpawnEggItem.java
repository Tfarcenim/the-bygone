package com.jamiedev.bygone.common.item;

import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Multiloader friendly variant of ForgeSpawnEggItem
 */
public class SupplierSpawnEggItem extends SpawnEggItem {

    public static final List<SupplierSpawnEggItem> EGGS = new ArrayList<>();
    private static final Map<EntityType<? extends Mob>, SupplierSpawnEggItem> TYPE_MAP = new IdentityHashMap<>();
    private final Supplier<? extends EntityType<? extends Mob>> typeSupplier;


    public SupplierSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props)
    {
        super(null, backgroundColor, highlightColor, props);
        this.typeSupplier = type;

        EGGS.add(this);
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundTag tag)
    {
        EntityType<?> type = super.getType(tag);
        return type != null ? type : typeSupplier.get();
    }

    private static final DispenseItemBehavior DEFAULT_DISPENSE_BEHAVIOR = (source, stack) ->
    {
        Direction face = source.getBlockState().getValue(DispenserBlock.FACING);
        EntityType<?> type = ((SpawnEggItem)stack.getItem()).getType(stack.getTag());

        try
        {
            type.spawn(source.getLevel(), stack, null, source.getPos().relative(face), MobSpawnType.DISPENSER, face != Direction.UP, false);
        }
        catch (Exception exception)
        {
            DispenseItemBehavior.LOGGER.error("Error while dispensing spawn egg from dispenser at {}", source.getPos(), exception);
            return ItemStack.EMPTY;
        }

        stack.shrink(1);
        source.getLevel().gameEvent(GameEvent.ENTITY_PLACE, source.getPos(), GameEvent.Context.of(source.getBlockState()));
        return stack;
    };

    public static void registerDispenserBehaviors() {
        EGGS.forEach(egg ->
        {
            DispenserBlock.registerBehavior(egg, DEFAULT_DISPENSE_BEHAVIOR);

            TYPE_MAP.put(egg.typeSupplier.get(), egg);
        });
    }
}
