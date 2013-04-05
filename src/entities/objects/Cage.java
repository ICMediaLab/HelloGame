package entities.objects;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import utils.MapLoader;

import entities.DestructibleEntity;
import entities.MovingEntity;
import entities.StaticRectEntity;
import entities.players.Player;
import game.config.Config;

public class Cage extends StaticRectEntity implements DestructibleEntity {
	
	private final int maxhealth = 100;
	private int health = maxhealth;

	public Cage(float x, float y) {
		super(x, y, 1, 2);
	}

	@Override
	public void collide(MovingEntity e) {
		System.out.println("Collided with " + e);
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
		g.drawRect((getX() - 1)*Config.getTileSize(), (getY() - 1)*Config.getTileSize(), getWidth()*Config.getTileSize(), getHeight()*Config.getTileSize());
		g.fillRect((getX() - 1 + getWidth()/4f)*Config.getTileSize(), (getY() - 1)*Config.getTileSize(), 1, getHeight()*Config.getTileSize());
		g.fillRect((getX() - 1 + getWidth()/2f)*Config.getTileSize(), (getY() - 1)*Config.getTileSize(), 1, getHeight()*Config.getTileSize());
		g.fillRect((getX() - 1 + getWidth()*3/4f)*Config.getTileSize(), (getY() - 1)*Config.getTileSize(), 1, getHeight()*Config.getTileSize());
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
		return new Cage(getX(), getY());
	}
	
}
