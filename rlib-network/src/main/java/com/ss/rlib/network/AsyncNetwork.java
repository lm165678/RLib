package com.ss.rlib.network;

import com.ss.rlib.network.packet.registry.ReadablePacketRegistry;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

/**
 * The interface to implement an asynchronous network.
 *
 * @author JavaSaBr
 */
public interface AsyncNetwork {

    /**
     * Get a network's config.
     *
     * @return the network's config.
     */
    @NotNull NetworkConfig getConfig();

    /**
     * Get a readable packet registry.
     *
     * @return the readable packet registry.
     */
    @NotNull ReadablePacketRegistry getPacketRegistry();

    /**
     * Get a new read buffer to use.
     *
     * @return the new buffer.
     */
    @NotNull ByteBuffer takeReadBuffer();

    /**
     * Get a new wait buffer to use.
     *
     * @return the new wait buffer.
     */
    @NotNull ByteBuffer takeWaitBuffer();

    /**
     * Get a new write buffer to use.
     *
     * @return the new buffer.
     */
    @NotNull ByteBuffer takeWriteBuffer();

    /**
     * Store the old read buffer.
     *
     * @param buffer the old buffer.
     */
    @NotNull AsyncNetwork putReadBuffer(@NotNull ByteBuffer buffer);

    /**
     * Store the old wait buffer.
     *
     * @param buffer the old wait buffer.
     */
    @NotNull AsyncNetwork putWaitBuffer(@NotNull ByteBuffer buffer);

    /**
     * Store the old write buffer.
     *
     * @param buffer the old buffer.
     */
    @NotNull AsyncNetwork putWriteBuffer(@NotNull ByteBuffer buffer);

    /**
     * Shutdown this network.
     */
    void shutdown();
}
