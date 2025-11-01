package com.jamiedev.bygone.platform;

import com.jamiedev.bygone.PacketHandlerFabric;
import com.jamiedev.bygone.core.network.C2SModPacket;
import com.jamiedev.bygone.core.network.PacketHandler;
import com.jamiedev.bygone.core.network.S2CModPacket;
import com.jamiedev.bygone.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.Collection;
import java.util.function.Function;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public <MSG extends S2CModPacket> void registerClientPlayPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf, MSG> reader) {
        if (MixinEnvironment.getCurrentEnvironment().getSide() == MixinEnvironment.Side.CLIENT) {
            PacketHandlerFabric.Client.register(packetLocation,reader);
        }
    }

    @Override
    public <MSG extends C2SModPacket> void registerServerPlayPacket(Class<MSG> packetLocation, Function<FriendlyByteBuf, MSG> reader) {
        ServerPlayNetworking.registerGlobalReceiver(PacketHandler.packet(packetLocation), PacketHandlerFabric.wrapC2S(reader));
    }

    @Override
    public void sendToClient(S2CModPacket msg, ServerPlayer player) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        msg.write(buf);
        ServerPlayNetworking.send(player, PacketHandler.packet(msg.getClass()), buf);
    }

    @Override
    public void sendToServer(C2SModPacket msg) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        msg.write(buf);
        ClientPlayNetworking.send(PacketHandler.packet(msg.getClass()), buf);
    }


    @Override
    public void sendToTracking(S2CModPacket msg, Entity entity, boolean includeSelf) {
        Collection<ServerPlayer> tracking = PlayerLookup.tracking(entity);
        if (includeSelf && entity instanceof ServerPlayer player) {
            tracking.add(player);
        }
        for (ServerPlayer player: tracking) {
            sendToClient(msg,player);
        }
    }

    @Override
    public int getTimeInBygone(Entity entity) {
        return 0;
    }

    @Override
    public void setTimeInBygone(Entity entity, int time) {

    }
}
