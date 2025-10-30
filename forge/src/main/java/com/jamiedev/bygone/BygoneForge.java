package com.jamiedev.bygone;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.jamiedev.bygone.common.item.SupplierSpawnEggItem;
import com.jamiedev.bygone.core.registry.BGBlocks;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Set;

@Mod(Bygone.MOD_ID)
public class BygoneForge {
    
    public BygoneForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::createAttributes);
        bus.addListener(this::spawnPlacements);
        if (FMLEnvironment.dist.isClient()) {
            BygoneClientForge.init(bus);
        }
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        Bygone.init();
        Bygone.registerBuiltIn();
        
    }

    void createAttributes(EntityAttributeCreationEvent event) {
        Bygone.initAttributes(event::put);
    }

    void spawnPlacements(SpawnPlacementRegisterEvent event) {
        Bygone.registerSpawnPlacements((entityType, spawnPlacementType, types, spawnPredicate) -> event.register(
                entityType,
                spawnPlacementType,
                types,
                spawnPredicate,
                SpawnPlacementRegisterEvent.Operation.REPLACE
        ));
    }

    void setup(FMLCommonSetupEvent event) {
        SupplierSpawnEggItem.registerDispenserBehaviors();
        event.enqueueWork(() -> {

         //   Set<Block> validBlocks = Sets.newHashSet(BlockEntityType.BRUSHABLE_BLOCK.validBlocks);
           // validBlocks.addAll(Sets.newHashSet(BGBlocks.SUSPICIOUS_SHELLSAND.get(), BGBlocks.SUSPICIOUS_CLAYSTONE.get()));
        //    BlockEntityType.BRUSHABLE_BLOCK.validBlocks = ImmutableSet.copyOf(validBlocks);

            Bygone.registerStrippables();
            Bygone.addFlammable();
           // JamiesModPortalsNeoForge.init();
          //  GumboPotBlockEntity.GumboScooping.setFilled(Items.BOWL, BGItems.GUMBO_BOWL.get());
           // GumboPotBlockEntity.GumboScooping.setFilled(Items.GLASS_BOTTLE, BGItems.GUMBO_BOTTLE.get());
        });
    }
}