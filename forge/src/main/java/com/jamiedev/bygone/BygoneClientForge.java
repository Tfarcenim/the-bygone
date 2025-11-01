package com.jamiedev.bygone;

import com.jamiedev.bygone.client.BygoneClient;
import com.jamiedev.bygone.client.particles.BlemishParticle;
import com.jamiedev.bygone.client.particles.UpsidedownDropParticle;
import com.jamiedev.bygone.common.block.JamiesModWoodType;
import com.jamiedev.bygone.common.item.SupplierSpawnEggItem;
import com.jamiedev.bygone.core.registry.BGParticleTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.Sheets;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class BygoneClientForge {
    static void init(IEventBus bus) {
        bus.addListener(BygoneClientForge::colorHandler);
        bus.addListener(BygoneClientForge::setup);
        bus.addListener(BygoneClientForge::createRenderers);
        bus.addListener(BygoneClientForge::createModelLayers);
        bus.addListener(BygoneClientForge::registerParticleFactories);
    }

    static void colorHandler(RegisterColorHandlersEvent.Item event) {
        SupplierSpawnEggItem.EGGS.forEach(egg ->
                event.getItemColors().register((stack, layer) -> egg.getColor(layer), egg)
        );
    }


    static void setup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            BygoneClient.registerRenderLayers(ItemBlockRenderTypes::setRenderLayer);
            BygoneClient.registerModelPredicateProviders();
            Sheets.addWoodType(JamiesModWoodType.ANCIENT);
        });
    }

    static void createRenderers(EntityRenderersEvent.RegisterRenderers event) {
        BygoneClient.createEntityRenderers();
    }

    static void createModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        BygoneClient.createModelLayers(event::registerLayerDefinition);
    }

    static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        BygoneClient.registerParticleFactories(event::registerSpriteSet);
        event.registerSpriteSet(BGParticleTypes.BLEMISH.get(), BlemishParticle.BlemishBlockProvider::new);
        event.registerSpriteSet(BGParticleTypes.UPSIDEDOWN.get(), UpsidedownDropParticle.Provider::new);

    }
}
