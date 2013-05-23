package entities;

import game.config.Config;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import utils.AnimationContainer;
import utils.Position;

public abstract class VeryAbstractStaticEntity<S extends Shape> implements Entity {
	
	private S hitbox;

	public VeryAbstractStaticEntity(S hitbox) {
		this.hitbox = hitbox;
	}
	
	@Override
	public S getHitbox() {
		return hitbox;
	}
	
	public void setHitbox(S hitbox) {
		this.hitbox = hitbox;
	}
	
	@SuppressWarnings("unchecked")
	public void transformHitbox(Transform transform){
		hitbox = (S) hitbox.transform(transform);
	}
	
	@Override
	public Position getPosition() {
		return new Position(getCentreX(),getCentreY());
	}
	
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
	public final float getWidth() {
		return getHitbox().getWidth();
	}
	
	@Override
	public final float getHeight() {
		return getHitbox().getHeight();
	}
	
	@Override
	public final float getCentreX() {
		return getHitbox().getCenterX();
	}
	
	@Override
	public final float getCentreY() {
		return getHitbox().getCenterY();
	}
	
	@Override
	public void stop_sounds() { }
	
	protected void renderSprite(Animation sprite){
		renderSprite(sprite, 0, 0);
	}
	
	protected void renderSprite(Animation sprite, float offsetX, float offsetY){
		//TODO: use somehow filter.darker or filter.lighter with layer
		float x = getCentreX() - getWidth()/2;
		float y = getCentreY() - getHeight()/2;
		sprite.draw((int) ((x - 1)*Config.getTileSize()) + offsetX, (int) ((y - 1)*Config.getTileSize()) + offsetY);
	}
	
	protected void renderSprite(Animation sprite, Position offset){
		renderSprite(sprite, offset.getX(),offset.getY());
	}
	
	protected void renderSprite(AnimationContainer sprite){
		renderSprite(sprite.getAnimation(),sprite.getOffset());
	}
	
	@Override
	public final boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public final int hashCode() {
		return super.hashCode();
	}
}
