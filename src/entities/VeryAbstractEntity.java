package entities;

import map.AbstractLayerRenderable;

import org.newdawn.slick.geom.Shape;

import utils.Position;

public abstract class VeryAbstractEntity extends AbstractLayerRenderable implements Entity {
	
	@Override
	public final boolean intersects(Entity e2) {
		return intersects(e2.getHitbox());
	}
	
	@Override
	public final boolean intersects(Shape e2) {
		return getHitbox().intersects(e2);
	}
	
	@Override
	public final boolean contains(Position p2) {
		return getHitbox().contains(p2.getX(), p2.getY());
	}
	
	@Override
	public final boolean contains(Shape s2) {
		return getHitbox().contains(s2);
	}
	
	@Override
	public final boolean contains(Entity e2) {
		return contains(e2.getHitbox());
	}
	
	@Override
	public float getWidth() {
		return getHitbox().getWidth();
	}
	
	@Override
	public float getHeight() {
		return getHitbox().getHeight();
	}
	
	@Override
	public final float getCentreX() {
		return getHitbox().getCenterX();
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
	public final boolean isDead() {
		return getHealth() <= 0;
	}
	
	@Override
	public final float getHealthPercent(){
		return (float)getHealth()/getMaxHealth();
	}
	
	@Override
	public final float getCentreY() {
		return getHitbox().getCenterY();
	}
	
	@Override
	public abstract VeryAbstractEntity clone();
	
}
