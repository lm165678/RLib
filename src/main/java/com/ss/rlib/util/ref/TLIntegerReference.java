package com.ss.rlib.util.ref;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The reference to integer value.
 *
 * @author JavaSaBr
 */
final class TLIntegerReference extends AbstractThreadLocalReference {

    /**
     * The value of this reference.
     */
    private int value;

    @Override
    public int getInt() {
        return value;
    }

    @Override
    public void setInt(final int value) {
        this.value = value;
    }

    @NotNull
    @Override
    public ReferenceType getType() {
        return ReferenceType.INTEGER;
    }

    @Override
    public boolean equals(@Nullable final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final TLIntegerReference that = (TLIntegerReference) object;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "TLIntegerReference{" +
                "value=" + value +
                "} " + super.toString();
    }
}