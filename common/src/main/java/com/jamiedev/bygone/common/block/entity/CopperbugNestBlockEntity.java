package com.jamiedev.bygone.common.block.entity;

import com.google.common.collect.Lists;
import com.jamiedev.bygone.common.block.CopperbugNestBlock;
import com.jamiedev.bygone.common.entity.CopperbugEntity;
import com.jamiedev.bygone.core.registry.BGBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class CopperbugNestBlockEntity  extends BlockEntity {

    private final List<BeehiveBlockEntity.BeeData> copperbugs = Lists.newArrayList();
    @Nullable
    private BlockPos flowerPos;

    public CopperbugNestBlockEntity(BlockPos pos, BlockState state) {
        super(BGBlockEntities.COPPERBUGNEST.get(), pos, state);
    }

    @Override
    public void setChanged() {
        if (this.isNearFire()) {
            this.angerCopperbugs(null, this.level.getBlockState(this.getBlockPos()), BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
        }

        super.setChanged();
    }

    public boolean isNearFire() {
        if (this.level == null) {
            return false;
        } else {
            Iterator<BlockPos> var1 = BlockPos.betweenClosed(this.worldPosition.offset(-1, -1, -1), this.worldPosition.offset(1, 1, 1)).iterator();

            BlockPos blockPos;
            do {
                if (!var1.hasNext()) {
                    return false;
                }

                blockPos = (BlockPos)var1.next();
            } while(!(this.level.getBlockState(blockPos).getBlock() instanceof FireBlock));

            return true;
        }
    }

    public boolean hasNoCopperbugs() {
        return this.copperbugs.isEmpty();
    }

    public boolean isFullOfCopperbugs() {
        return this.copperbugs.size() == 3;
    }

    public void angerCopperbugs(@Nullable Player player, BlockState state, BeehiveBlockEntity.BeeReleaseStatus beeState) {
        List<Entity> list = this.releaseAllCopperbugs(state, beeState);
        if (player != null) {
            for (Entity entity : list) {
                if (entity instanceof CopperbugEntity beeEntity) {
                    if (player.position().distanceToSqr(entity.position()) <= 16.0) {
                        if (!this.isSmoked()) {
                            beeEntity.setTarget(player);
                        } else {
                            beeEntity.setCannotEnterNestTicks(400);
                        }
                    }
                }
            }
        }
    }

    public boolean isEmpty() {
        return this.copperbugs.isEmpty();
    }

    private List<Entity> releaseAllCopperbugs(BlockState state, BeehiveBlockEntity.BeeReleaseStatus beeState) {
        List<Entity> list = Lists.newArrayList();
        this.copperbugs.removeIf((bee) -> releaseCopperbug(this.level, this.worldPosition, state, bee, list, beeState, this.flowerPos));
        if (!list.isEmpty()) {
            super.setChanged();
        }
        return list;
    }

    @VisibleForDebug
    public int getCopperbugCount() {
        return this.copperbugs.size();
    }

    public static int getOxidizationLevel(BlockState state) {
        return state.getValue(CopperbugNestBlock.OXIDIZATION_LEVEL);
    }

    @VisibleForDebug
    public boolean isSmoked() {
        return CampfireBlock.isSmokeyPos(this.level, this.getBlockPos());
    }

    public void addOccupant(Entity occupant, boolean hasNectar) {
        this.addOccupantWithPresetTicks(occupant, hasNectar, 0);
    }

    private static boolean releaseCopperbug(Level level, BlockPos pos, BlockState state, BeehiveBlockEntity.BeeData data, @Nullable List<Entity> entities, BeehiveBlockEntity.BeeReleaseStatus releaseStatus, @Nullable BlockPos flowerPos) {
        if ((level.isNight() || level.isRaining()) && releaseStatus != BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY) {
            return false;
        } else {
            CompoundTag compoundtag = data.entityData.copy();
            BeehiveBlockEntity.removeIgnoredBeeTags(compoundtag);
            compoundtag.put("HivePos", NbtUtils.writeBlockPos(pos));
            compoundtag.putBoolean("NoGravity", true);
            Direction direction = state.getValue(BeehiveBlock.FACING);
            BlockPos blockpos = pos.relative(direction);
            boolean flag = !level.getBlockState(blockpos).getCollisionShape(level, blockpos).isEmpty();
            if (flag && releaseStatus != BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY) {
                return false;
            } else {
                Entity entity = EntityType.loadEntityRecursive(compoundtag, level, (p_58740_) -> {
                    return p_58740_;
                });
                if (entity != null) {
                    if (!entity.getType().is(EntityTypeTags.BEEHIVE_INHABITORS)) {
                        return false;
                    } else {
                        if (entity instanceof CopperbugEntity bee) {
                            if (flowerPos != null && !bee.hasSavedFlowerPos() && level.random.nextFloat() < 0.9F) {
                                bee.setSavedFlowerPos(flowerPos);
                            }

                            if (releaseStatus == BeehiveBlockEntity.BeeReleaseStatus.HONEY_DELIVERED) {
                                bee.onOxidizationDelivered();
                                if (state.is(BlockTags.BEEHIVES, (p_202037_) -> {
                                    return p_202037_.hasProperty(BeehiveBlock.HONEY_LEVEL);
                                })) {
                                    int i = CopperbugNestBlockEntity.getOxidizationLevel(state);
                                    if (i < 5) {
                                        int j = level.random.nextInt(100) == 0 ? 2 : 1;
                                        if (i + j > 5) {
                                            --j;
                                        }

                                        level.setBlockAndUpdate(pos, state.setValue(BeehiveBlock.HONEY_LEVEL, i + j));
                                    }
                                }
                            }

                            setBeeReleaseData(data.ticksInHive, bee);
                            if (entities != null) {
                                entities.add(bee);
                            }

                            float f = entity.getBbWidth();
                            double d3 = flag ? 0.0D : 0.55D + (double)(f / 2.0F);
                            double d0 = (double)pos.getX() + 0.5D + d3 * (double)direction.getStepX();
                            double d1 = (double)pos.getY() + 0.5D - (double)(entity.getBbHeight() / 2.0F);
                            double d2 = (double)pos.getZ() + 0.5D + d3 * (double)direction.getStepZ();
                            entity.moveTo(d0, d1, d2, entity.getYRot(), entity.getXRot());
                        }

                        level.playSound((Player)null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, level.getBlockState(pos)));
                        return level.addFreshEntity(entity);
                    }
                } else {
                    return false;
                }
            }
        }
    }

    public void addOccupantWithPresetTicks(Entity occupant, boolean hasNectar, int ticksInHive) {
        if (this.copperbugs.size() < 3) {
            occupant.stopRiding();
            occupant.ejectPassengers();
            CompoundTag compoundtag = new CompoundTag();
            occupant.save(compoundtag);
            this.storeBee(compoundtag, ticksInHive, hasNectar);
            if (this.level != null) {
                if (occupant instanceof CopperbugEntity bee) {
                    if (bee.hasSavedFlowerPos() && (!this.hasSavedFlowerPos() || this.level.random.nextBoolean())) {
                        this.flowerPos = bee.getCopperBlockPos();
                    }
                }

                BlockPos blockpos = this.getBlockPos();
                this.level.playSound((Player)null, (double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), SoundEvents.BEEHIVE_ENTER, SoundSource.BLOCKS, 1.0F, 1.0F);
                this.level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(occupant, this.getBlockState()));
            }

            occupant.discard();
            super.setChanged();
        }
    }

    private static void setBeeReleaseData(int timeInHive, CopperbugEntity bee) {
        int i = bee.getAge();
        if (i < 0) {
            bee.setAge(Math.min(0, i + timeInHive));
        } else if (i > 0) {
            bee.setAge(Math.max(0, i - timeInHive));
        }

        bee.setInLoveTime(Math.max(0, bee.getInLoveTime() - timeInHive));
    }

    public void storeBee(CompoundTag entityData, int ticksInHive, boolean hasNectar) {
        this.copperbugs.add(new BeehiveBlockEntity.BeeData(entityData, ticksInHive, hasNectar ? 2400 : 600));
    }


    private boolean hasCopperBlockPos() {
        return this.flowerPos != null;
    }

    private static void tickCopperbugs(Level level, BlockPos pos, BlockState state, List<BeehiveBlockEntity.BeeData> data, @Nullable BlockPos flowerPos) {
        boolean flag = false;

        BeehiveBlockEntity.BeeData beehiveblockentity$beedata;
        for(Iterator<BeehiveBlockEntity.BeeData> iterator = data.iterator(); iterator.hasNext(); ++beehiveblockentity$beedata.ticksInHive) {
            beehiveblockentity$beedata = iterator.next();
            if (beehiveblockentity$beedata.ticksInHive > beehiveblockentity$beedata.minOccupationTicks) {
                BeehiveBlockEntity.BeeReleaseStatus beehiveblockentity$beereleasestatus = beehiveblockentity$beedata.entityData.getBoolean("HasNectar") ? BeehiveBlockEntity.BeeReleaseStatus.HONEY_DELIVERED : BeehiveBlockEntity.BeeReleaseStatus.BEE_RELEASED;
                if (releaseCopperbug(level, pos, state, beehiveblockentity$beedata, (List<Entity>)null, beehiveblockentity$beereleasestatus, flowerPos)) {
                    flag = true;
                    iterator.remove();
                }
            }
        }

        if (flag) {
            setChanged(level, pos, state);
        }

    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, CopperbugNestBlockEntity blockEntity) {
        tickCopperbugs(world, pos, state, blockEntity.copperbugs, blockEntity.flowerPos);
        if (!blockEntity.copperbugs.isEmpty() && world.getRandom().nextDouble() < 0.005) {
            double d = (double)pos.getX() + 0.5;
            double e = pos.getY();
            double f = (double)pos.getZ() + 0.5;
            world.playSound(null, d, e, f, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

      //  DebugInfoSender.sendBeehiveDebugData(world, pos, state, blockEntity);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.copperbugs.clear();
        ListTag listtag = tag.getList("Bees", 10);

        for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            BeehiveBlockEntity.BeeData beehiveblockentity$beedata = new BeehiveBlockEntity.BeeData(compoundtag.getCompound("EntityData"), compoundtag.getInt("TicksInHive"), compoundtag.getInt("MinOccupationTicks"));
            this.copperbugs.add(beehiveblockentity$beedata);
        }

        this.flowerPos = null;
        if (tag.contains("FlowerPos")) {
            this.flowerPos = NbtUtils.readBlockPos(tag.getCompound("FlowerPos"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Bees", this.writeCopperbugs());
        if (this.hasSavedFlowerPos()) {
            tag.put("FlowerPos", NbtUtils.writeBlockPos(this.flowerPos));
        }
    }

    private boolean hasSavedFlowerPos() {
        return this.flowerPos != null;
    }

    public ListTag writeCopperbugs() {
        ListTag listtag = new ListTag();

        for(BeehiveBlockEntity.BeeData beehiveblockentity$beedata : this.copperbugs) {
            CompoundTag compoundtag = beehiveblockentity$beedata.entityData.copy();
            compoundtag.remove("UUID");
            CompoundTag compoundtag1 = new CompoundTag();
            compoundtag1.put("EntityData", compoundtag);
            compoundtag1.putInt("TicksInHive", beehiveblockentity$beedata.ticksInHive);
            compoundtag1.putInt("MinOccupationTicks", beehiveblockentity$beedata.minOccupationTicks);
            listtag.add(compoundtag1);
        }

        return listtag;
    }
}
