package entities.objects;

import map.Cell;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import entities.FixedRotationEntity;
import entities.MovingEntity;
import entities.VeryAbstractDestructibleEntity;
import entities.players.Player;
import game.config.Config;

public class Cage extends VeryAbstractDestructibleEntity<Rectangle> implements FixedRotationEntity {
	
	private final int maxhealth;
	private int health;
	
	private final Cell parent;
	
	public Cage(Cell parent, int x, int y, int width, int height) {
		super(new Rectangle(x, y, width, height));
		this.parent = parent;
		maxhealth = 100;
		health = maxhealth;
		parent.setBlocked(this,true);
	}
	
	private Cage(Cage base){
		super(new Rectangle(base.getX(), base.getY(), base.getWidth(), base.getHeight()),base);
		this.parent = base.parent;
		this.maxhealth = base.maxhealth;
		this.health = base.health;
		parent.setBlocked(this,true);
	}
	
	@Override
	public void collide(MovingEntity e) {
		// Why is weapon not colliding?
		// we never had the ability to collide the weapon afaik
		if (e instanceof Player) takeDamage(10);
	}
	
	@Override
	public void update(GameContainer gc) {
		if(isDead()){
			die();
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
	public int getMaxHealth() {
		return maxhealth;
	}
	
	@Override
	public Cage clone() {
		return new Cage(this);
	}
	
	@Override
	public void die() {
		parent.setBlocked(this,false);
		super.die();
	}

	@Override
	public float getX() {
		return getHitbox().getX();
	}

	@Override
	public float getY() {
		return getHitbox().getY();
	}
}
