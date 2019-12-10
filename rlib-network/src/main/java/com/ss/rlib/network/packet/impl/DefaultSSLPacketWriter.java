package com.ss.rlib.network.packet.impl;

import com.ss.rlib.common.function.NotNullBiConsumer;
import com.ss.rlib.common.function.NotNullConsumer;
import com.ss.rlib.common.function.NullableSupplier;
import com.ss.rlib.network.BufferAllocator;
import com.ss.rlib.network.Connection;
import com.ss.rlib.network.packet.WritablePacket;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.SSLEngine;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * @author JavaSaBr
 */
public class DefaultSSLPacketWriter<W extends WritablePacket, C extends Connection<?, W>> extends
    AbstractSSLPacketWriter<W, C> {

    protected final int packetLengthHeaderSize;

    public DefaultSSLPacketWriter(
        @NotNull C connection,
        @NotNull AsynchronousSocketChannel channel,
        @NotNull BufferAllocator bufferAllocator,
        @NotNull Runnable updateActivityFunction,
        @NotNull NullableSupplier<WritablePacket> nextWritePacketSupplier,
        @NotNull NotNullConsumer<WritablePacket> writtenPacketHandler,
        @NotNull NotNullBiConsumer<WritablePacket, Boolean> sentPacketHandler,
        @NotNull SSLEngine sslEngine,
        @NotNull NotNullConsumer<WritablePacket> packetWriter,
        int packetLengthHeaderSize
    ) {
        super(
            connection,
            channel,
            bufferAllocator,
            updateActivityFunction,
            nextWritePacketSupplier,
            writtenPacketHandler,
            sentPacketHandler,
            sslEngine,
            packetWriter
        );
        this.packetLengthHeaderSize = packetLengthHeaderSize;
    }

    @Override
    protected int getTotalSize(@NotNull WritablePacket packet, int expectedLength) {
        return expectedLength + packetLengthHeaderSize;
    }

    @Override
    protected boolean onBeforeWrite(
        @NotNull W packet,
        int expectedLength,
        int totalSize,
        @NotNull ByteBuffer firstBuffer,
        @NotNull ByteBuffer secondBuffer
    ) {
        firstBuffer.clear().position(packetLengthHeaderSize);
        return true;
    }

    @Override
    protected @NotNull ByteBuffer onResult(
        @NotNull W packet,
        int expectedLength,
        int totalSize,
        @NotNull ByteBuffer firstBuffer,
        @NotNull ByteBuffer secondBuffer
    ) {
        return writePacketLength(firstBuffer, firstBuffer.limit()).position(0);
    }

    protected @NotNull ByteBuffer writePacketLength(@NotNull ByteBuffer buffer, int packetLength) {
        return writeHeader(buffer, 0, packetLength, packetLengthHeaderSize);
    }
}
