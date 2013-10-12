package rlib.compiler;

import rlib.util.array.Array;
import rlib.util.array.Arrays;

/**
 * Загрузчик откомпиленных классов в рантайме.
 * 
 * @author Ronn
 */
public class CompileClassLoader extends ClassLoader {

	/** байткод загруженных классов */
	private final Array<ByteSource> byteCode;

	public CompileClassLoader() {
		super();
		this.byteCode = Arrays.toArray(ByteSource.class);
	}

	/**
	 * Добавить байткод класса.
	 */
	public void addByteCode(ByteSource byteSource) {
		byteCode.add(byteSource);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		Array<ByteSource> byteCode = getByteCode();

		if(byteCode.isEmpty()) {
			return null;
		}

		synchronized(byteCode) {

			for(ByteSource byteSource : byteCode) {

				byte[] bytes = byteSource.getByteSource();

				Class<?> result = null;

				try {
					result = defineClass(name, bytes, 0, bytes.length);
				} catch(ClassFormatError | NoClassDefFoundError e) {
					continue;
				}

				if(result != null) {
					return result;
				}
			}
		}

		return null;
	}

	/**
	 * @return байт код загруженных классов.
	 */
	private Array<ByteSource> getByteCode() {
		return byteCode;
	}
}