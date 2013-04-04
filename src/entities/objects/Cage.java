package entities.objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import entities.MovingEntity;
import entities.StaticRectEntity;
import entities.players.Player;
import game.config.Config;

public class Cage extends StaticRectEntity {
	
	private float health = 100;
	private boolean dead = false;

	public Cage(float x, float y) {
		super(x, y, 1, 2);
	}

	@Override
	public void collide(MovingEntity e) {
		// Why is weapon not colliding?
		if (e instanceof Player) health -= 10;
		
		// Why only moving entities can be removed?
		if (health < 0) dead = true;
	}

	@Override
	public void update(GameContainer gc) {
		
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		if (!dead) {
			g.setColor(Color.green);
			g.drawRect((getX() - 1)*Config.getTileSize(), (getY() - 1)*Config.getTileSize(), getWidth()*Config.getTileSize(), getHeight()*Config.getTileSize());
			g.fillRect((getX() - 1 + getWidth()/4f)*Config.getTileSize(), (getY() - 1)*Config.getTileSize(), 1, getHeight()*Config.getTileSize());
			g.fillRect((getX() - 1 + getWidth()/2f)*Config.getTileSize(), (getY() - 1)*Config.getTileSize(), 1, getHeight()*Config.getTileSize());
			g.fillRect((getX() - 1 + getWidth()*3/4f)*Config.getTileSize(), (getY() - 1)*Config.getTileSize(), 1, getHeight()*Config.getTileSize());
		}
	}



}
