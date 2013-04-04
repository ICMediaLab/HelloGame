package utils.interval;

import java.util.Random;

import utils.Position;

public abstract class Range2D {
	
	protected static final Random rand = new Random();

	public abstract Position random();
}
