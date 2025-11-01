package com.jamiedev.bygone;

import com.jamiedev.bygone.client.BygoneClient;
import com.jamiedev.bygone.client.renderer.entity.BygoneDimensionEffects;
import com.jamiedev.bygone.common.block.JamiesModWoodType;
import com.jamiedev.bygone.common.item.SupplierSpawnEggItem;
import com.jamiedev.bygone.core.registry.BGDimensions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.Sheets;

public class BygoneFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SupplierSpawnEggItem.EGGS.forEach(egg ->
                ColorProviderRegistry.ITEM.register((stack, layer) -> egg.getColor(layer), egg)
        );

        BygoneClient.registerRenderLayers(BlockRenderLayerMap.INSTANCE::putBlock);
        BygoneClient.createEntityRenderers();
        BygoneClient.createModelLayers((modelLayerLocation, layerDefinitionSupplier) -> EntityModelLayerRegistry.registerModelLayer(modelLayerLocation, layerDefinitionSupplier::get));
        BygoneClient.registerParticleFactories((particleType, spriteParticleRegistration) -> ParticleFactoryRegistry.getInstance().register(particleType, spriteParticleRegistration::create));


        DimensionRenderingRegistry.registerDimensionEffects(BGDimensions.BYGONE.location(), BygoneDimensionEffects.INSTANCE);
        //DimensionRenderingRegistry.registerSkyRenderer(BGDimensions.BYGONE_LEVEL_KEY, BygoneSkyRenderer.INSTANCE);

        BygoneClient.registerModelPredicateProviders();

        Sheets.SIGN_MATERIALS.put(JamiesModWoodType.ANCIENT, Sheets.getSignMaterial(JamiesModWoodType.ANCIENT));
        Sheets.HANGING_SIGN_MATERIALS.put(JamiesModWoodType.ANCIENT, Sheets.getHangingSignMaterial(JamiesModWoodType.ANCIENT));
    }
}
