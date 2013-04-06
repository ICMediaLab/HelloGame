package utils.interval.two;

import utils.Position;
import utils.interval.one.AngleRange;
import utils.interval.one.Interval;

public class Sector2D extends Range2D {
	
	private final Interval magRange;
	private final AngleRange angRange;
	
	public Sector2D(float minMagnitude, float maxMagnitude, double minAngle, double maxAngle) {
		this.magRange = new Interval(minMagnitude, maxMagnitude);
		this.angRange = new AngleRange(minAngle, maxAngle);
	}

	@Override
	public Position random() {
		float m = magRange.random();
		double a = angRange.random();
		return new Position(m*(float) Math.cos(a), m*(float) Math.sin(a));
	}

	@Override
	public float area() {
		float min = magRange.getMin();
		float max = magRange.getMax();
		return (angRange.length().floatValue()/2f)*(max*max - min*min);
	}

}
