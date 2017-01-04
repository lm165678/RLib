package rlib.util.array.impl;

/**
 * The final version of the {@link FastArraySet}.
 *
 * @author JavaSaBr
 */
public final class FinalFastArraySet<E> extends FastArraySet<E> {

    public FinalFastArraySet(final Class<E> type) {
        super(type);
    }

    public FinalFastArraySet(final Class<E> type, final int size) {
        super(type, size);
    }
}