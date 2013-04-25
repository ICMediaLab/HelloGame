package utils.interval.two;

import utils.Position;
import utils.PositionReturn;

public abstract class Range2D implements PositionReturn {
	
	public abstract Position random();
	
	public abstract float area();
	
	@Override
	public Position getPosition() {
		return random();
	}
}
