package entities.objects;

import java.awt.Dimension;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import utils.Position;

import entities.Entity;
import game.config.Config;

public class JumpPlatform extends Entity {
	
	public JumpPlatform(Position xy,Dimension size, int maxhealth){
		super(xy, size, maxhealth);
	}
	
	public JumpPlatform(int x, int y){
		this(new Position(x, y), new Dimension(1, 1), 100);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.setColor(Color.white);
		g.fillRect(this.getX()*Config.getTileSize(), this.getY()*Config.getTileSize() + 25.6f, Config.getTileSize(), 0.2f*Config.getTileSize());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entity clone() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
