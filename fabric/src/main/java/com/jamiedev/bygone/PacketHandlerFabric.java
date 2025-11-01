package com.jamiedev.bygone;

import com.jamiedev.bygone.core.network.C2SModPacket;
import com.jamiedev.bygone.core.network.PacketHandler;
import com.jamiedev.bygone.core.network.S2CModPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

import java.util.function.Function;

public class PacketHandlerFabric {

    public static <MSG extends C2SModPacket> ServerPlayNetworking.PlayChannelHandler wrapC2S(Function<FriendlyByteBuf, MSG> decodeFunction) {
        return new ServerHandler<>(decodeFunction);
    }

    public record ServerHandler<MSG extends C2SModPacket>(Function<FriendlyByteBuf,MSG> packetDecoder) implements ServerPlayNetworking.PlayChannelHandler {
        @Override
        public void receive(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) {
            MSG decode = packetDecoder.apply(buf);
            server.execute(() -> decode.handleServer(player));
        }
    }
    public static class Client {
        public static <MSG extends S2CModPacket> void register(Class<MSG> packetLocation, Function<FriendlyByteBuf, MSG> reader) {
            ClientPlayNetworking.registerGlobalReceiver(PacketHandler.packet(packetLocation), wrapS2C(reader));
        }

        public static <MSG extends S2CModPacket> ClientPlayNetworking.PlayChannelHandler wrapS2C(Function<FriendlyByteBuf,MSG> decodeFunction) {
            return new ClientHandler<>(decodeFunction);
        }


        public record ClientHandler<MSG extends S2CModPacket>(Function<FriendlyByteBuf, MSG> decodeFunction) implements ClientPlayNetworking.PlayChannelHandler {
            @Override
            public void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
                MSG decode = decodeFunction.apply(buf);
                client.execute(decode::handleClient);
            }
        }
    }
}
