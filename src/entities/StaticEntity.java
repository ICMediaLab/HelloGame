package entities;

import org.newdawn.slick.geom.Shape;


public abstract class StaticEntity<S extends Shape> extends VeryAbstractStaticEntity<S> {
	
	public StaticEntity(S s) {
		super(s);
	}
	
	public abstract boolean isSolid();
}
