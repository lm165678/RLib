package com.ss.rlib.network.packet.impl;

import com.ss.rlib.network.BufferAllocator;
import com.ss.rlib.network.Connection;
import com.ss.rlib.network.packet.ReadablePacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/**
 * @param <R> the readable packet's type.
 * @param <C> the connections' type.
 * @author JavaSaBR
 */
public class DefaultPacketReader<R extends ReadablePacket, C extends Connection<R, ?>> extends
    AbstractPacketReader<R, C> {

    private final IntFunction<R> readPacketFactory;

    public DefaultPacketReader(
        @NotNull C connection,
        @NotNull AsynchronousSocketChannel channel,
        @NotNull BufferAllocator bufferAllocator,
        @NotNull Runnable updateActivityFunction,
        @NotNull Consumer<R> readPacketHandler,
        @NotNull IntFunction<R> readPacketFactory,
        int packetLengthHeaderSize,
        int maxPacketsByRead
    ) {
        super(
            connection,
            channel,
            bufferAllocator,
            updateActivityFunction,
            readPacketHandler,
            packetLengthHeaderSize,
            maxPacketsByRead
        );
        this.readPacketFactory = readPacketFactory;
    }

    @Override
    protected @Nullable R createPacketFor(@NotNull ByteBuffer buffer, int length) {
        return readPacketFactory.apply(length);
    }
}