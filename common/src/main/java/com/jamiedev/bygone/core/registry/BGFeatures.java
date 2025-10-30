package com.jamiedev.bygone.core.registry;

import com.jamiedev.bygone.Bygone;
import com.jamiedev.bygone.common.worldgen.feature.*;
import com.jamiedev.bygone.common.worldgen.feature.config.MegalithConfig;
import com.jamiedev.bygone.common.worldgen.feature.config.SmallCloudConfig;
import com.jamiedev.bygone.common.worldgen.structure.AncientForestVegetationFeature;
import com.jamiedev.bygone.common.worldgen.structure.AncientForestVegetationFeatureConfig;
import com.jamiedev.bygone.common.worldgen.structure.AncientTreeFeature;
import com.jamiedev.bygone.common.worldgen.structure.AncientTreeFeatureConfig;
import com.kekecreations.jinxedlib.core.util.JinxedRegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;

import java.util.function.Supplier;

public class BGFeatures
{
    public static final Supplier<Feature<AncientTreeFeatureConfig>> ANCIENT_TREE = register("ancient_tree",() -> new AncientTreeFeature(AncientTreeFeatureConfig.CODEC));
    public static  final Supplier<Feature<NoneFeatureConfiguration>> ANCIENT_VINES = register("ancient_vines",() ->  new AncientVinesFeature(NoneFeatureConfiguration.CODEC));
    public static final Supplier<Feature<RandomPatchConfiguration>> ANCIENT_FLOWERS = register("ancient_flowers",() ->  new RandomPatchFeature(RandomPatchConfiguration.CODEC));
    public static final Supplier<Feature<AncientForestVegetationFeatureConfig>> ANCIENT_FOREST_VEGATATION = register("underhang_vegetation", () -> new AncientForestVegetationFeature(AncientForestVegetationFeatureConfig.VEGETATION_CODEC));

    public static final Supplier<Feature<SmallCloudConfig>> SMALL_CLOUD = register("small_cloud",() ->  new SmallCloudFeature(SmallCloudConfig.CODEC));

    public static final Supplier<Feature<BlockStateConfiguration>> AMBER = register("amber",() ->  new AmberFeature(BlockStateConfiguration.CODEC));
    public static  final Supplier<Feature<NoneFeatureConfiguration>> AMBER_UNDER = register("amber_under",() ->  new AmberUnderFeature(NoneFeatureConfiguration.CODEC));

    public static final Supplier<Feature<PointedAmberFeatureConfig>> POINTED_AMBER = register("pointed_amber",() ->  new PointedAmberFeature(PointedAmberFeatureConfig.CODEC));
    public static final Supplier<Feature<PointedAmberClusterFeatureConfig>> AMBER_CLUSTER  = register("amber_cluster", () -> new PointedAmberClusterFeature(PointedAmberClusterFeatureConfig.CODEC));
    public static  final Supplier<Feature<NoneFeatureConfiguration>> PRIMORDIAL_CORAL_CLAW = register("primordial_coral_claw", () -> new PrimordialCoralClawFeature(NoneFeatureConfiguration.CODEC));
    public static  final Supplier<Feature<NoneFeatureConfiguration>> PRIMORDIAL_CORAL_MUSHROOM = register("primordial_coral_mushroom", () -> new PrimordialCoralMushroomFeature(NoneFeatureConfiguration.CODEC));
    public static  final Supplier<Feature<NoneFeatureConfiguration>> PRIMORDIAL_CORAL_TREE = register("primordial_coral_tree",() ->  new PrimordialCoralTreeFeature(NoneFeatureConfiguration.CODEC));

    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSS_VEGETATION = FeatureUtils.createKey("alpha_moss_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSS_PATCH = FeatureUtils.createKey("alpha_moss_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSS_PATCH_BONEMEAL = FeatureUtils.createKey("alpha_moss_patch_bonemeal");

    public static  final Supplier<Feature<NoneFeatureConfiguration>> SHELF_FUNGI = register("shelf_fungi",() ->  new ShelfFungiFeature(NoneFeatureConfiguration.CODEC));
    public static  final Supplier<Feature<NoneFeatureConfiguration>> FUNGI_VINES = register("fungi_vines",() ->  new TestFungiVineFeature(NoneFeatureConfiguration.CODEC));

    public static final Supplier<Feature<MegalithConfig>> MEGALITH = register("megalith",() ->  new MegalithFeature(MegalithConfig.CODEC));

    public static <T extends FeatureConfiguration> Supplier<Feature<T>> register(String name, Supplier<Feature<T>> type) {
        return JinxedRegistryHelper.register((Registry<Feature<T>>)(Object)BuiltInRegistries.FEATURE, Bygone.MOD_ID, name, type);
    }

    public static void init() {}
}

