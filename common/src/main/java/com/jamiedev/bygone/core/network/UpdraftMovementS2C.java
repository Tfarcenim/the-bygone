package com.jamiedev.bygone.core.network;

import com.jamiedev.bygone.client.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;

public record UpdraftMovementS2C(double velocityY, boolean isDescending) implements S2CModPacket {
    public UpdraftMovementS2C(FriendlyByteBuf buf) {
        this(buf.readDouble(),buf.readBoolean());
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeDouble(velocityY);
        buf.writeBoolean(isDescending);
    }

    @Override
    public void handleClient() {
        ClientPacketHandler.handle(this);
    }
}