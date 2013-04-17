package utils.classfinder;

import java.util.Map.Entry;

/**
 * A functional interface for creating a map of class instances using a {@link ClassFinder}.
 * @param <K> The mapping key. Determines the return type of the {@code getKey} method.
 * @param <V> The mapping instance value. Determines the return type of the {@code getValue} method).
 */
public interface ClassMapFunctor<K,V> {
	
	/**
	 * Returns true if and only if the class specified should be included in the map.
	 */
	boolean include(Class<?> c);
	
	/**
	 * Returns the key-value pair for this mapping.
	 */
	Entry<K, V> getEntry(Class<?> clazz);
}
