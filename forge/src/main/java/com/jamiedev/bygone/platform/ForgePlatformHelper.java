package com.jamiedev.bygone.platform;

import com.jamiedev.bygone.PacketHandlerForge;
import com.jamiedev.bygone.core.network.C2SModPacket;
import com.jamiedev.bygone.core.network.PacketHandler;
import com.jamiedev.bygone.core.network.S2CModPacket;
import com.jamiedev.bygone.platform.services.IPlatformHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Function;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }

    int i;
    @Override
    public <MSG extends S2CModPacket> void registerClientPlayPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf, MSG> reader) {
        PacketHandlerForge.INSTANCE.registerMessage(i++, packetLocation, MSG::write, reader, PacketHandlerForge.wrapS2C());
    }

    @Override
    public <MSG extends C2SModPacket> void registerServerPlayPacket(Class<MSG>  packetLocation, Function<FriendlyByteBuf, MSG> reader) {
        PacketHandlerForge.INSTANCE.registerMessage(i++, packetLocation, MSG::write, reader, PacketHandlerForge.wrapC2S());
    }

    @Override
    public void sendToClient(S2CModPacket msg, ServerPlayer player) {
        PacketHandlerForge.sendToClient(msg,player);
    }

    @Override
    public void sendToServer(C2SModPacket msg) {
        PacketHandlerForge.sendToServer(msg);
    }

    @Override
    public void sendToTracking(S2CModPacket msg, Entity entity, boolean includeSelf) {
        PacketDistributor<Entity> trackingEntity = includeSelf ? PacketDistributor.TRACKING_ENTITY_AND_SELF : PacketDistributor.TRACKING_ENTITY;
        PacketHandlerForge.INSTANCE.send(trackingEntity.with(() -> entity),msg);
    }

    @Override
    public int getTimeInBygone(Entity entity) {
        return entity.getPersistentData().getInt("time_in_bygone");
    }

    @Override
    public void setTimeInBygone(Entity entity, int time) {
        entity.getPersistentData().putInt("time_in_bygone",time);
    }
}