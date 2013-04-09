package utils.interval.two;

import utils.Position;

public class FixedPosition extends Range2D {
	
	private final Position p;
	
	public FixedPosition(Position pos) {
		this.p = pos;
	}
	
	public FixedPosition(float x, float y) {
		p = new Position(x,y);
	}
	
	@Override
	public Position random() {
		return p.clone();
	}

	@Override
	public float area() {
		return 0;
	}

}
