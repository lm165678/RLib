package com.ss.rlib.network.packet.impl.simple;

import com.ss.rlib.network.annotation.PacketDescription;
import com.ss.rlib.network.packet.impl.AbstractWritablePacket;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

@PacketDescription(id = 1)
@RequiredArgsConstructor
public class StringWritablePacket extends AbstractWritablePacket {

    @NotNull
    private final String data;

    @Override
    protected void writeImpl(@NotNull ByteBuffer buffer) {
        super.writeImpl(buffer);
        writeString(buffer, data);
    }
}
