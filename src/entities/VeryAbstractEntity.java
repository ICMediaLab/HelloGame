package entities;

import org.newdawn.slick.geom.Shape;

import map.Cell;
import map.MapLoader;

public abstract class VeryAbstractEntity<S extends Shape> extends VeryAbstractDestructibleEntity<S> implements MovingEntity {
	
	public VeryAbstractEntity(S hitbox) {
		super(hitbox);
	}
	
	public VeryAbstractEntity(S hitbox, VeryAbstractEntity<S> base) {
		super(hitbox, base);
	}

	protected final void setCentreX(float centreX) {
		getHitbox().setCenterX(centreX);
	}
	
	protected final void setCentreY(float centreY) {
		getHitbox().setCenterY(centreY);
	}
	
	public final void setCentre(float centreX, float centreY) {
		setCentreX(centreX);
		setCentreY(centreY);
	}
	
	@Override
	public abstract VeryAbstractEntity<S> clone();
	
	/**
	 * Returns true if and only if this entity is touching the edge of the map.
	 */
	protected boolean checkMapChanged() {
		Cell cell = MapLoader.getCurrentCell();
		float y = getCentreY() - getHeight()/2;
		float x = getCentreX() - getWidth()/2;
		return ((y < 1 && getdY() < 0) || (x < 1 && getdX() < 0) ||
				(x >= cell.getWidth() - (1 + getWidth()) && getdX() > 0) || 
				(y >= cell.getHeight() - (1 + getHeight()) && getdY() > 0));
	}
	
	@Override
	public void die() {
		MapLoader.getCurrentCell().removeMovingEntity(this);
		super.die();
	}
	
}
