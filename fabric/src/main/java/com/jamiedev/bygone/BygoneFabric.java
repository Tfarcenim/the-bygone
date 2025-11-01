package com.jamiedev.bygone;

import com.jamiedev.bygone.common.item.SupplierSpawnEggItem;
import com.jamiedev.bygone.common.util.ServerTickHandler;
import com.jamiedev.bygone.core.network.PacketHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.SpawnPlacements;

public class BygoneFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Bygone.init();
        Bygone.registerSpawnPlacements(SpawnPlacements::register);

        Bygone.initAttributes(FabricDefaultAttributeRegistry::register);


        //Bygone.addValidBlocks((type, block) -> type.addSupportedBlock(block));

        Bygone.registerStrippables();
        Bygone.addFlammable();
        SupplierSpawnEggItem.registerDispenserBehaviors();
        PacketHandler.registerPackets();
        ServerTickEvents.END_SERVER_TICK.register(ServerTickHandler::onServerTick);
    }
}
