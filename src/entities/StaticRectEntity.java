package entities;

import game.config.Config;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

public abstract class StaticRectEntity extends StaticEntity<Rectangle> implements FixedRotationEntity {

	public StaticRectEntity(float x, float y, float width, float height) {
		super(new Rectangle(x, y, width, height));
	}
	
	public StaticRectEntity(Rectangle r){
		super(r);
	}
	
	/**
	 * Copy constructor
	 */
	protected StaticRectEntity(StaticRectEntity base) {
		this(new Rectangle(base.getX(),base.getY(),base.getWidth(),base.getHeight()));
	}
	
	@Override
	public float getX(){
		return getHitbox().getX();
	}
	
	@Override
	public float getY(){
		return getHitbox().getY();
	}
	
	@Override
	protected void renderSprite(Animation sprite, float offsetX, float offsetY) {
		//TODO: use somehow filter.darker or filter.lighter with layer
		sprite.draw((int) ((getX() - 1)*Config.getTileSize()) + offsetX, (int) ((getY() - 1)*Config.getTileSize()) + offsetY);		
	}

}
