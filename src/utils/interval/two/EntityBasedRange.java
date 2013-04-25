package utils.interval.two;

import utils.Position;
import entities.Entity;

public class EntityBasedRange extends Range2D {
	
	private Entity e;
	private Range2D r;

	public EntityBasedRange(Entity e, Range2D r) {
		this.e = e;
		this.r = r;
	}
	
	
	@Override
	public Position random() {
		Position res = e.getPosition();
		res.translate(r.random());
		return res;
	}

	@Override
	public float area() {
		return r.area();
	}

}
