package entities.objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import utils.Position;

import entities.Entity;
import game.config.Config;
import utils.Dimension;

public class JumpPlatform extends Entity {
	
	public JumpPlatform(Position xy,Dimension size, int maxhealth){
		super(xy, size, maxhealth);
	}
	
	public JumpPlatform(int x, int y){
		this(new Position(x, y), new Dimension(1f, 0.2f), 100);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.setColor(Color.pink);
		g.fillRect((this.getX()-1)*Config.getTileSize(), (this.getY()-0.2f)*Config.getTileSize(), Config.getTileSize(), 0.2f*Config.getTileSize());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void collide(Entity e){
		e.translateSmooth(20, e.getX(), e.getY() - 1.5f);
	}

	@Override
	public Entity clone() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
