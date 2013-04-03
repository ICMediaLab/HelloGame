package entities;

import game.config.Config;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;

public abstract class StaticRectEntity extends StaticEntity<Rectangle> {

	public StaticRectEntity(float x, float y, float width, float height) {
		super(new Rectangle(x, y, width, height));
	}
	
	public StaticRectEntity(Rectangle r){
		super(r);
	}
	
	public float getX(){
		return getHitbox().getX();
	}
	
	public float getY(){
		return getHitbox().getY();
	}
	
	@Override
	protected void renderSprite(Animation sprite, float offsetX, float offsetY) {
		//TODO: use somehow filter.darker or filter.lighter with layer
		sprite.draw((int) ((getX() - 1)*Config.getTileSize()) + offsetX, (int) ((getY() - 1)*Config.getTileSize()) + offsetY);		
	}

}
