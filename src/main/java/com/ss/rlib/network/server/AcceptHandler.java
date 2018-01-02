package com.ss.rlib.network.server;

import com.ss.rlib.network.server.client.Client;
import com.ss.rlib.network.server.client.ClientConnection;
import com.ss.rlib.network.server.client.impl.DefaultClient;
import com.ss.rlib.network.server.client.impl.DefaultClientConnection;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * The interface to implement a handler of accepted connections.
 *
 * @author JavaSaBr
 */
public interface AcceptHandler extends CompletionHandler<AsynchronousSocketChannel, ServerNetwork> {

    /**
     * Create a simple accept handler.
     *
     * @param connectionFactory the connection factory.
     * @param clientFactory the client factory.
     * @return the accept handler.
     */
    static @NotNull AcceptHandler newSimple(@NotNull final BiFunction<@NotNull ServerNetwork, @NotNull AsynchronousSocketChannel, @NotNull ClientConnection> connectionFactory,
                                            @NotNull final Function<@NotNull ClientConnection, @NotNull Client> clientFactory) {
        return (channel, network) -> {

            try {
                channel.setOption(StandardSocketOptions.SO_SNDBUF, 12000);
                channel.setOption(StandardSocketOptions.SO_RCVBUF, 24000);
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }

            final ClientConnection connection = connectionFactory.apply(network, channel);
            final Client client = clientFactory.apply(connection);
            connection.setOwner(client);
            client.notifyConnected();
            connection.startRead();
        };
    }

    /**
     * Create a default accept handler.
     *
     * @return the accept handler.
     */
    static @NotNull AcceptHandler newDefault() {
        return newSimple(DefaultClientConnection::new, DefaultClient::new);
    }

    @Override
    default void completed(@NotNull final AsynchronousSocketChannel result,
                           @NotNull final ServerNetwork network) {
        network.accept(network, this);
        onAccept(result, network);
    }

    @Override
    default void failed(@NotNull final Throwable exc, @NotNull final ServerNetwork network) {
        network.accept(network, this);
        onFailed(exc);
    }

    /**
     * Handle the new client connection.
     *
     * @param channel the client channel.
     * @param network the server network.
     */
    void onAccept(@NotNull AsynchronousSocketChannel channel, @NotNull final ServerNetwork network);

    /**
     * Handle the exception.
     *
     * @param exception the exception.
     */
    default void onFailed(@NotNull Throwable exception) {
    }
}
