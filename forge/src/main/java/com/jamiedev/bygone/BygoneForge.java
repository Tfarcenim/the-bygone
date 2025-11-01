package com.jamiedev.bygone;

import com.jamiedev.bygone.common.item.SupplierSpawnEggItem;
import com.jamiedev.bygone.common.util.ServerTickHandler;
import com.jamiedev.bygone.core.network.PacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

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
        MinecraftForge.EVENT_BUS.addListener(this::serverTick);
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        Bygone.init();
    }

    void serverTick(TickEvent.ServerTickEvent event) {
        ServerTickHandler.onServerTick(event.getServer());
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
        PacketHandler.registerPackets();
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