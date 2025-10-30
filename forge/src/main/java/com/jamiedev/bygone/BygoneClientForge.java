package com.jamiedev.bygone;

import com.jamiedev.bygone.common.item.SupplierSpawnEggItem;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class BygoneClientForge {
    static void init(IEventBus bus) {
        bus.addListener(BygoneClientForge::colorHandler);
    }

    static void colorHandler(RegisterColorHandlersEvent.Item event) {
        SupplierSpawnEggItem.EGGS.forEach(egg ->
                event.getItemColors().register((stack, layer) -> egg.getColor(layer), egg)
        );
    }
}
