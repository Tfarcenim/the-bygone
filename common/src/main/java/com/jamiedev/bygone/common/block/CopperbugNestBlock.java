package com.jamiedev.bygone.common.block;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.jetbrains.annotations.NotNull;
import com.jamiedev.bygone.common.block.entity.CopperbugNestBlockEntity;
import com.jamiedev.bygone.common.entity.CopperbugEntity;
import com.jamiedev.bygone.core.registry.BGBlockEntities;
import com.jamiedev.bygone.core.registry.BGItems;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CopperbugNestBlock extends BaseEntityBlock
{

    public static final DirectionProperty FACING;
    public static final IntegerProperty OXIDIZATION_LEVEL;
    public static final int FULL_OXIDIZATION_LEVEL = 5;
    private static final int DROPPED_HONEYCOMB_COUNT = 3;


    public CopperbugNestBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(OXIDIZATION_LEVEL, 0).setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return state.getValue(OXIDIZATION_LEVEL);
    }

    @Override
    /**
     * Called after a player has successfully harvested this block. This method will only be called if the player has used the correct tool and drops should be spawned.
     */
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @javax.annotation.Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(level, player, pos, state, te, stack);
        if (!level.isClientSide && te instanceof BeehiveBlockEntity beehiveblockentity) {
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) == 0) {
                beehiveblockentity.emptyAllLivingFromHive(player, state, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
                level.updateNeighbourForOutputSignal(pos, this);
                this.angerNearbyCopperbugs(level, pos);
            }
            CriteriaTriggers.BEE_NEST_DESTROYED.trigger((ServerPlayer)player, state, stack, beehiveblockentity.getOccupantCount());
        }

    }

    private void angerNearbyCopperbugs(Level world, BlockPos pos) {
        AABB box = new AABB(pos).inflate(8.0, 6.0, 8.0);
        List<CopperbugEntity> list = world.getEntitiesOfClass(CopperbugEntity.class, box);
        if (!list.isEmpty()) {
            List<Player> list2 = world.getEntitiesOfClass(Player.class, box);
            if (list2.isEmpty()) {
                return;
            }

            for (CopperbugEntity beeEntity : list) {
                if (beeEntity.getTarget() == null) {
                    Player playerEntity = Util.getRandom(list2, world.random);
                    beeEntity.setTarget(playerEntity);
                }
            }
        }

    }

    public static void dropItems(Level world, BlockPos pos) {
        if (world.random.nextInt(10) == 1)
        {
            popResource(world, pos, new ItemStack(BGItems.VERDIGRIS_SCRAP.get(), 1));
        }
        else
        {
            popResource(world, pos, new ItemStack(Items.COPPER_INGOT, 3));   
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemstack = player.getItemInHand(hand);
        int i = state.getValue(OXIDIZATION_LEVEL);
        boolean flag = false;
        if (i >= 5) {
            Item item = itemstack.getItem();
            if (itemstack.is(Items.SHEARS)) {
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                dropItems(level, pos);
                itemstack.hurtAndBreak(1, player, (p_49571_) -> {
                    p_49571_.broadcastBreakEvent(hand);
                });
                flag = true;
                level.gameEvent(player, GameEvent.SHEAR, pos);
            } else if (itemstack.is(Items.GLASS_BOTTLE)) {
                itemstack.shrink(1);
                level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (itemstack.isEmpty()) {
                    player.setItemInHand(hand, new ItemStack(Items.HONEY_BOTTLE));
                } else if (!player.getInventory().add(new ItemStack(Items.HONEY_BOTTLE))) {
                    player.drop(new ItemStack(Items.HONEY_BOTTLE), false);
                }

                flag = true;
                level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
            }

            if (!level.isClientSide() && flag) {
                player.awardStat(Stats.ITEM_USED.get(item));
            }
        }

        if (flag) {
            if (!CampfireBlock.isSmokeyPos(level, pos)) {
                if (this.hiveContainsCopperbugs(level, pos)) {
                    this.angerNearbyCopperbugs(level, pos);
                }

                this.releaseCopperbugsAndResetHoneyLevel(level, state, pos, player, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
            } else {
                this.resetHoneyLevel(level, state, pos);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return super.use(state, level, pos, player, hand, hit);
        }
    }

    public void releaseCopperbugsAndResetHoneyLevel(Level level, BlockState state, BlockPos pos, @javax.annotation.Nullable Player player, BeehiveBlockEntity.BeeReleaseStatus beeReleaseStatus) {
        this.resetHoneyLevel(level, state, pos);
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof BeehiveBlockEntity beehiveblockentity) {
            beehiveblockentity.emptyAllLivingFromHive(player, state, beeReleaseStatus);
        }
    }

    private boolean hiveContainsCopperbugs(Level level, BlockPos pos) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof CopperbugNestBlockEntity beehiveblockentity) {
            return !beehiveblockentity.isEmpty();
        } else {
            return false;
        }
    }

    public void resetHoneyLevel(Level level, BlockState state, BlockPos pos) {
        level.setBlock(pos, state.setValue(OXIDIZATION_LEVEL, 0), 3);
    }

    private boolean hasCopperbugs(Level world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CopperbugNestBlockEntity beehiveBlockEntity) {
            return !beehiveBlockEntity.hasNoCopperbugs();
        } else {
            return false;
        }
    }

    public void takeOxidization(Level world, BlockState state, BlockPos pos, @Nullable Player player, BeehiveBlockEntity.BeeReleaseStatus beeState) {
        this.takeOxidization(world, state, pos);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CopperbugNestBlockEntity beehiveBlockEntity) {
            beehiveBlockEntity.angerCopperbugs(player, state, beeState);
        }

    }

    public void takeOxidization(Level world, BlockState state, BlockPos pos) {
        world.setBlock(pos, state.setValue(OXIDIZATION_LEVEL, 0), 3);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(OXIDIZATION_LEVEL) >= 5) {
            for(int i = 0; i < random.nextInt(1) + 1; ++i) {
                this.spawnHoneyParticles(world, pos, state);
            }
        }

    }

    private void spawnHoneyParticles(Level world, BlockPos pos, BlockState state) {
        if (state.getFluidState().isEmpty() && !(world.random.nextFloat() < 0.3F)) {
            VoxelShape voxelShape = state.getCollisionShape(world, pos);
            double d = voxelShape.max(Direction.Axis.Y);
            if (d >= 1.0 && !state.is(BlockTags.IMPERMEABLE)) {
                double e = voxelShape.min(Direction.Axis.Y);
                if (e > 0.0) {
                    this.addHoneyParticle(world, pos, voxelShape, (double)pos.getY() + e - 0.05);
                } else {
                    BlockPos blockPos = pos.below();
                    BlockState blockState = world.getBlockState(blockPos);
                    VoxelShape voxelShape2 = blockState.getCollisionShape(world, blockPos);
                    double f = voxelShape2.max(Direction.Axis.Y);
                    if ((f < 1.0 || !blockState.isCollisionShapeFullBlock(world, blockPos)) && blockState.getFluidState().isEmpty()) {
                        this.addHoneyParticle(world, pos, voxelShape, (double)pos.getY() - 0.05);
                    }
                }
            }

        }
    }

    private void addHoneyParticle(Level world, BlockPos pos, VoxelShape shape, double height) {
        this.addHoneyParticle(world, (double)pos.getX() + shape.min(Direction.Axis.X), (double)pos.getX() + shape.max(Direction.Axis.X), (double)pos.getZ() + shape.min(Direction.Axis.Z), (double)pos.getZ() + shape.max(Direction.Axis.Z), height);
    }

    private void addHoneyParticle(Level world, double minX, double maxX, double minZ, double maxZ, double height) {
        world.addParticle(ParticleTypes.DRIPPING_HONEY, Mth.lerp(world.random.nextDouble(), minX, maxX), height, Mth.lerp(world.random.nextDouble(), minZ, maxZ), 0.0, 0.0, 0.0);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OXIDIZATION_LEVEL, FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CopperbugNestBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return world.isClientSide ? null : createTickerHelper(type, BGBlockEntities.COPPERBUGNEST.get(), CopperbugNestBlockEntity::serverTick);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative() && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof CopperbugNestBlockEntity beehiveblockentity) {
                ItemStack itemstack = new ItemStack(this);
                int i = state.getValue(OXIDIZATION_LEVEL);
                boolean flag = !beehiveblockentity.isEmpty();
                if (flag || i > 0) {
                    if (flag) {
                        CompoundTag compoundtag = new CompoundTag();
                        compoundtag.put("Bees", beehiveblockentity.writeCopperbugs());
                        BlockItem.setBlockEntityData(itemstack, BlockEntityType.BEEHIVE, compoundtag);
                    }

                    CompoundTag compoundtag1 = new CompoundTag();
                    compoundtag1.putInt("honey_level", i);
                    itemstack.addTagElement("BlockStateTag", compoundtag1);
                    ItemEntity itementity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemstack);
                    itementity.setDefaultPickUpDelay();
                    level.addFreshEntity(itementity);
                }
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        Entity entity = builder.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (entity instanceof PrimedTnt || entity instanceof Creeper || entity instanceof WitherSkull || entity instanceof WitherBoss || entity instanceof MinecartTNT) {
            BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
            if (blockEntity instanceof CopperbugNestBlockEntity beehiveBlockEntity) {
                beehiveBlockEntity.angerCopperbugs(null, state, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
            }
        }

        return super.getDrops(state, builder);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (world.getBlockState(neighborPos).getBlock() instanceof FireBlock) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CopperbugNestBlockEntity beehiveBlockEntity) {
                beehiveBlockEntity.angerCopperbugs(null, state, BeehiveBlockEntity.BeeReleaseStatus.EMERGENCY);
            }
        }

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        OXIDIZATION_LEVEL = BlockStateProperties.LEVEL_HONEY;
    }
}
