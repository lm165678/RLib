package rlib.util.array.impl;

/**
 * Реализация {@link FastArray} с проверкой на уникальность элемента при
 * вставке.
 * 
 * @see FastArray
 * @author Ronn
 */
public class FastArraySet<E> extends FastArray<E> {

	private static final long serialVersionUID = 1L;

	public FastArraySet(final Class<E> type) {
		super(type);
	}

	public FastArraySet(final Class<E> type, final int size) {
		super(type, size);
	}

	@Override
	public FastArray<E> add(final E element) {

		if(contains(element)) {
			return this;
		}

		return super.add(element);
	}
}