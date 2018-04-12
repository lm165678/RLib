package com.ss.rlib.geom.bounding.impl;

import com.ss.rlib.geom.Ray3f;
import com.ss.rlib.geom.Vector3f;
import org.jetbrains.annotations.NotNull;

import com.ss.rlib.geom.Quaternion4f;
import com.ss.rlib.geom.Vector3fBuffer;
import com.ss.rlib.geom.bounding.Bounding;
import com.ss.rlib.geom.bounding.BoundingType;
import com.ss.rlib.logging.Logger;
import com.ss.rlib.logging.LoggerManager;

/**
 * The base implementation of a bounding.
 *
 * @author JavaSaBr
 */
public abstract class AbstractBounding implements Bounding {

    /**
     * The constant LOGGER.
     */
    protected static final Logger LOGGER = LoggerManager.getLogger(Bounding.class);

    /**
     * The center.
     */
    protected Vector3f center;

    /**
     * The offset.
     */
    protected Vector3f offset;

    protected AbstractBounding(@NotNull Vector3f center, @NotNull Vector3f offset) {
        this.center = center;
        this.offset = offset;
    }

    @Override
    public boolean contains(float x, float y, float z, @NotNull Vector3fBuffer buffer) {
        return false;
    }

    @Override
    public boolean contains(@NotNull Vector3f point, @NotNull Vector3fBuffer buffer) {
        return contains(point.getX(), point.getY(), point.getZ(), buffer);
    }

    @Override
    public final float distanceTo(@NotNull Vector3f point) {
        return center.distance(point);
    }

    @Override
    public @NotNull BoundingType getBoundingType() {
        return BoundingType.EMPTY;
    }

    @Override
    public final@NotNull Vector3f getCenter() {
        return center;
    }

    @Override
    public void setCenter(@NotNull Vector3f center) {
        this.center = center;
    }

    @Override
    public @NotNull Vector3f getOffset() {
        return offset;
    }

    @Override
    public @NotNull Vector3f getResultCenter(@NotNull Vector3fBuffer buffer) {
        return getCenter();
    }

    @Override
    public boolean intersects(@NotNull Bounding bounding, @NotNull Vector3fBuffer buffer) {
        return false;
    }

    @Override
    public final boolean intersects(@NotNull Ray3f ray, @NotNull Vector3fBuffer buffer) {
        return intersects(ray.getStart(), ray.getDirection(), buffer);
    }

    @Override
    public boolean intersects(@NotNull Vector3f start, @NotNull Vector3f direction, @NotNull Vector3fBuffer buffer) {
        return false;
    }

    @Override
    public void update(@NotNull Quaternion4f rotation, @NotNull Vector3fBuffer buffer) {
    }
}
