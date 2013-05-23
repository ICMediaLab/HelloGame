package utils.interval.one;

import java.util.Random;

/**
 * An abstract class for defining ranges of one-dimensional objects (floats, doubles, colours etc...)
 * @param T The class being spanned by the range.
 */
public abstract class Range {
	
	protected static final Random rand = new Random();
	
	public abstract float random();
	
	public abstract float length();
	
	public abstract float getMin();
	public abstract float getMax();
}
