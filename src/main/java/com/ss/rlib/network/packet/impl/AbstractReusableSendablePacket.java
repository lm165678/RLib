package com.ss.rlib.network.packet.impl;

import static com.ss.rlib.util.ObjectUtils.notNull;
import com.ss.rlib.concurrent.atomic.AtomicInteger;
import com.ss.rlib.network.packet.ReusableSendablePacket;
import com.ss.rlib.util.ClassUtils;
import com.ss.rlib.util.pools.Pool;
import com.ss.rlib.util.pools.PoolFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * The reusable implementation of {@link AbstractSendablePacket} using the counter to control the life cycle of
 * this packet.
 *
 * @author JavaSaBr
 */
public abstract class AbstractReusableSendablePacket extends AbstractSendablePacket implements ReusableSendablePacket {

    @NotNull
    protected static final ThreadLocal<Map<Class<? extends ReusableSendablePacket>, Pool<ReusableSendablePacket>>> LOCAL_POOLS = ThreadLocal.withInitial(HashMap::new);

    /**
     * The counter of pending sendings.
     */
    @NotNull
    protected final AtomicInteger counter;

    /**
     * The pool to store this packet after using.
     */
    @Nullable
    protected Pool<ReusableSendablePacket> pool;

    /**
     * The memory barrier.
     */
    protected volatile int barrier;

    /**
     * The sink for the memory barrier.
     */
    protected int barrierSink;

    public AbstractReusableSendablePacket() {
        this.counter = new AtomicInteger();
    }

    @Override
    public void write(@NotNull final ByteBuffer buffer) {
        if (counter.get() < 1) {
            LOGGER.warning(this, "write finished packet " + this + " on thread " + Thread.currentThread().getName());
            return;
        }
        notifyStartedWriting();
        try {
            super.write(buffer);
        } finally {
            notifyFinishedWriting();
        }
    }

    /**
     * Notify about started writing data.
     */
    protected void notifyStartedWriting() {
        barrierSink = barrier;
    }

    /**
     * Notify about finished writing data.
     */
    protected void notifyFinishedWriting() {
        barrier = barrierSink + 1;
    }

    @Override
    public void complete() {
        if (counter.decrementAndGet() == 0) {
            completeImpl();
        }
    }

    @Override
    public void forceComplete() {
        counter.set(1);
        complete();
    }

    /**
     * Get thread local pool.
     *
     * @return thread local pool.
     */
    protected @NotNull Pool<ReusableSendablePacket> getThreadLocalPool() {
        final Map<Class<? extends ReusableSendablePacket>, Pool<ReusableSendablePacket>> poolMap = LOCAL_POOLS.get();
        return poolMap.computeIfAbsent(getClass(), PoolFactory::newConcurrentStampedLockReusablePool);
    }

    @Override
    public void reuse() {
        this.pool = getThreadLocalPool();
    }

    /**
     * @return the pool to store used packet.
     */
    protected @NotNull Pool<ReusableSendablePacket> getPool() {
        if (pool != null) return pool;
        return getThreadLocalPool();
    }

    /**
     * Implementation of handling completion of packet sending.
     */
    protected void completeImpl() {
        getPool().put(this);
    }

    /**
     * Get a new instance of this packet.
     *
     * @return the new instance.
     */
    public <T extends ReusableSendablePacket> @NotNull T newInstance() {

        final Pool<ReusableSendablePacket> pool = getPool();
        final ReusableSendablePacket result = pool.take(getClass(), ClassUtils::newInstance);
        result.setPool(pool);

        return notNull(ClassUtils.unsafeCast(result));
    }

    @Override
    public final void setPool(@NotNull final Pool<ReusableSendablePacket> pool) {
        this.pool = pool;
    }

    @Override
    public final void decreaseSends() {
        counter.decrementAndGet();
    }

    @Override
    public void decreaseSends(final int count) {
        counter.subAndGet(count);
    }

    @Override
    public void increaseSends() {
        counter.incrementAndGet();
    }

    @Override
    public void increaseSends(final int count) {
        counter.addAndGet(count);
    }

    @Override
    public String toString() {
        return "AbstractReusableSendablePacket{" + "counter=" + counter + "} " + super.toString();
    }
}
