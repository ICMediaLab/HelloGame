package entities;

import map.Cell;
import map.MapLoader;

public abstract class VeryAbstractEntity extends VeryAbstractStaticEntity implements MovingEntity {
	
	private static final long serialVersionUID = 6416013909933529409L;
	
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
	public final boolean isDead() {
		return getHealth() <= 0;
	}
	
	@Override
	public final float getHealthPercent(){
		return (float)getHealth()/getMaxHealth();
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
	
}
