package com.jamiedev.bygone.core.network;

import net.minecraft.network.FriendlyByteBuf;

public interface ModPacket {
    void write(FriendlyByteBuf buf);
}
