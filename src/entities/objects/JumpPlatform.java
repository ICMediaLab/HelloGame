package entities.objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import utils.Dimension;
import utils.Position;
import entities.AbstractEntity;
import entities.Entity;
import game.config.Config;

public class JumpPlatform extends AbstractEntity {
	
	private static final int JUMP_PLATFORM_DEFAULT_LAYER = -100;
	
	public JumpPlatform(Position xy,Dimension size, int maxhealth){
		super(xy, size, maxhealth);
	}
	
	public JumpPlatform(float x, float y){
		this(new Position(x, y), new Dimension(1f, 0.2f), 100);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.setColor(Color.pink);
		g.fillRect((this.getX()-1)*Config.getTileSize(), (this.getY()-1)*Config.getTileSize(), Config.getTileSize(), 0.2f*Config.getTileSize());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void collide(Entity e){
		e.setVelocity(e.getdX(),-1.2f);
	}

	@Override
	public AbstractEntity clone() {
		return this;
	}

	@Override
	public int getLayer() {
		return JUMP_PLATFORM_DEFAULT_LAYER;
	}

	

}
