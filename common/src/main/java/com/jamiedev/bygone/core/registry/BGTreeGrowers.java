package com.jamiedev.bygone.core.registry;

import com.jamiedev.bygone.Bygone;
import com.jamiedev.bygone.common.util.BGTreeGrower;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Optional;

public class BGTreeGrowers {

    public static final AbstractTreeGrower ANCIENT_TREE = register("ancient_tree", 0.0F, Optional.of(BGConfiguredFeatures.ANCIENT_TREE_MEDIUM), Optional.empty(), Optional.of(BGConfiguredFeatures.ANCIENT_TREE), Optional.empty(), Optional.empty(), Optional.empty());

    private static AbstractTreeGrower register(String name, float secondaryChance, Optional<ResourceKey<ConfiguredFeature<?, ?>>> megaTree, Optional<ResourceKey<ConfiguredFeature<?, ?>>> secondaryMegaTree, Optional<ResourceKey<ConfiguredFeature<?, ?>>> tree, Optional<ResourceKey<ConfiguredFeature<?, ?>>> secondaryTree, Optional<ResourceKey<ConfiguredFeature<?, ?>>> flowers, Optional<ResourceKey<ConfiguredFeature<?, ?>>> secondaryFlowers)
    {
        return new BGTreeGrower(String.format("%s:%s", Bygone.MOD_ID, name), secondaryChance, megaTree, secondaryMegaTree, tree, secondaryTree, flowers, secondaryFlowers);
    }

    private static AbstractTreeGrower register(String name, Optional<ResourceKey<ConfiguredFeature<?, ?>>> tree, Optional<ResourceKey<ConfiguredFeature<?, ?>>> secondaryTree) {
        return register(name, 0, Optional.empty(), Optional.empty(), tree, secondaryTree, Optional.empty(), Optional.empty());
    }
}
