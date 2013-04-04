package utils.interval.one;

import java.util.Random;

public abstract class Range<T> {
	
	protected static final Random rand = new Random();

	public abstract T random();
	
}
