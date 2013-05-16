package entities;

import map.Cell;
import map.MapLoader;

public abstract class VeryAbstractEntity extends VeryAbstractDestructibleEntity implements MovingEntity {
	
	public VeryAbstractEntity() {
		super();
	}
	
	public VeryAbstractEntity(VeryAbstractEntity base) {
		super(base);
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
	public abstract VeryAbstractEntity clone();
	
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
