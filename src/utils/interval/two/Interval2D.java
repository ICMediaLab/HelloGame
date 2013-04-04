package utils.interval.two;

import utils.Position;
import utils.interval.one.Interval;

public class Interval2D extends Range2D {
	
	private final Interval x,y;
	
	public Interval2D(float xlow, float xhigh, float ylow, float yhigh) {
		x = new Interval(xlow, xhigh);
		y = new Interval(ylow, yhigh);
		
	}
	
	@Override
	public Position random() {
		return new Position(x.random(),y.random());
	}
}
