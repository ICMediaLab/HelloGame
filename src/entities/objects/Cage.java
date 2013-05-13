package entities.objects;

import map.Cell;
import map.MapLoader;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import entities.DestructibleEntity;
import entities.MovingEntity;
import entities.players.Player;
import game.config.Config;

public class Cage extends StaticBlockingEntity implements DestructibleEntity {
	
	private final int maxhealth = 100;
	private int health = maxhealth;
	
	public Cage(Cell parent, int x, int y, int width, int height) {
		super(parent, x, y, width, height);
	}
	
	@Override
	public void collide(MovingEntity e) {
		// Why is weapon not colliding?
		// we never had the ability to collide the weapon afaik
		if (e instanceof Player) health -= 10;
	}
	
	@Override
	public void update(GameContainer gc) {
		if(health <= 0){
			MapLoader.getCurrentCell().removeDestructibleEntity(this);
		}
	}
	
	@Override
	public int getLayer() {
		return 0;
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		g.setColor(Color.green);
		
		int incr  = Config.getTileSize()/4;
		int limit = (int) ((getX() + getWidth() - 1)*Config.getTileSize());
		int y = (int) (Config.getTileSize()*(getY() - 1));
		int y2 = (int) (Config.getTileSize()*(getY() + getHeight() - 1));
		int initX = (int) (Config.getTileSize()*(getX() - 1));
		for(int x = initX + incr; x < limit; x += incr){
			g.drawLine(x, y, x, y2);
		}
		g.drawRect(initX, y, limit - initX, y2 - y);
	}
	
	@Override
	public int takeDamage(int damage) {
		int oldhealth = health;
		health = Math.max(0, health - damage);
		return oldhealth - health;
	}
	
	@Override
	public int getHealth() {
		return health;
	}
	
	@Override
	public float getHealthPercent() {
		return (float) getHealth()/getMaxHealth();
	}
	
	@Override
	public int getMaxHealth() {
		return maxhealth;
	}
	
	@Override
	public Cage clone() {
		return new Cage(parent, (int) getX(),(int) getY(),(int) getWidth(),(int) getHeight());
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
