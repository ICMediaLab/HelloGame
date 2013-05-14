package utils.interval.one;

import java.util.Random;

/**
 * An abstract class for defining ranges of one-dimensional objects (floats, doubles, colours etc...)
 * @param T The class being spanned by the range.
 */
public abstract class Range<T> {
	
	protected static final Random rand = new Random();
	
	public abstract T random();
	
	public abstract T length();
	
	public abstract T getMin();
	public abstract T getMax();
}
