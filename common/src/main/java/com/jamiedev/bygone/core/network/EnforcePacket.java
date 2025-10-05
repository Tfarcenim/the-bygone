package com.jamiedev.bygone.core.network;

import net.minecraft.network.FriendlyByteBuf;

public record EnforcePacket(boolean enforce) implements S2CModPacket {
    public static boolean enforcedProgression = true;

    public EnforcePacket(FriendlyByteBuf buf) {
        this(buf.readBoolean());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(this.enforce);
    }

    @Override
    public void handleClient() {

    }
}
