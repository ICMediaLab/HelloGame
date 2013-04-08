package entities;

import org.newdawn.slick.geom.Shape;


public abstract class StaticEntity<S extends Shape> extends VeryAbstractStaticEntity {
	
	private final S hitbox;

	public StaticEntity(S s) {
		hitbox = s;
	}
	
	@Override
	public final S getHitbox() {
		return hitbox;
	}

	public abstract boolean isSolid();
}
