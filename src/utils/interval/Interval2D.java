package utils.interval;

import org.newdawn.slick.geom.Rectangle;

import utils.Position;

public class Interval2D extends Range2D {
	
	private final Rectangle r;
	
	public Interval2D(Position src, float xlow, float xhigh, float ylow, float yhigh) {
		float x = src.getX() + xlow;
		float y = src.getY() + ylow;
		r = new Rectangle(x, y, src.getX() + xhigh - x, src.getY() + yhigh - y);
	}
	
	@Override
	public Position random() {
		return new Position(
				rand.nextFloat()*r.getWidth() + r.getX(),
				rand.nextFloat()*r.getHeight() + r.getY());
	}

	
	
}
