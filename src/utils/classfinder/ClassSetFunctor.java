package utils.classfinder;

/**
 * A functional interface for creating a set of class instances using a {@link ClassFinder}.
 * @param <V> The mapping instance value. Determines the return type of the {@code getValue} method).
 */
public interface ClassSetFunctor<V> {
	
	/**
	 * Returns true if and only if the class specified should be included in the map.
	 */
	boolean include(Class<?> clazz);
	
	/**
	 * Returns the value of this mapping.
	 */
	V getValue(Class<?> clazz);
}
