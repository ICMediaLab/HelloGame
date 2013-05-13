package entities.objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import entities.MovingEntity;
import entities.StaticRectEntity;
import game.config.Config;

public class JumpPlatform extends StaticRectEntity {
	
	private static final int JUMP_PLATFORM_DEFAULT_LAYER = -100;
	
	public JumpPlatform(float x, float y, int width){
		super(x,y, width, 0.2f);
	}
	
	public JumpPlatform(float x, float y){
		super(x,y, 1f, 0.2f);
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		g.setColor(Color.pink);
		g.fillRect((this.getX()-1)*Config.getTileSize(), (this.getY()-1)*Config.getTileSize(), Config.getTileSize(), 0.2f*Config.getTileSize());
	}

	@Override
	public void update(GameContainer gc) {
		// nothing to do here...
	}
	
	@Override
	public void collide(MovingEntity e){
		if(e instanceof MovingEntity){
			MovingEntity me = (MovingEntity) e;
			me.setVelocity(me.getdX(),-1.2f);
		}
	}

	@Override
	public int getLayer() {
		return JUMP_PLATFORM_DEFAULT_LAYER;
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
