package com.jamiedev.bygone.core.network;

import java.util.UUID;

import com.jamiedev.bygone.client.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;

public record SyncPlayerHookS2C(int hookId, UUID playerUUID) implements S2CModPacket {

    public SyncPlayerHookS2C(FriendlyByteBuf buf) {
        this(buf.readInt(),buf.readUUID());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(hookId);
        buf.writeUUID(playerUUID);
    }

    @Override
    public void handleClient() {
        ClientPacketHandler.handle(this);
    }
}
