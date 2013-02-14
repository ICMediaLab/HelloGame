package entities;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import utils.Tile;
import utils.TileProperty;

public abstract class Entity implements IEntity {
	
	private final Rectangle hitbox;
	protected float dx = 0;
	protected float dy = 0;
	protected Tile[][] properties;
	protected int delta;
	
	//TODO:	Implement entity image system.
	//		No idea how at the moment.
	
	private int health,maxhealth;

	public Entity(Rectangle hitbox, int maxhealth) {
		this.hitbox = hitbox;
		this.health = maxhealth;
		this.maxhealth = maxhealth;
	}
	
	/**
	 * Returns the current x-position of this entity.
	 */
	@Override
	public float getX(){
		return hitbox.getX();
	}
	
	/**
	 * Returns the current y-position of this entity.
	 */
	@Override
	public float getY(){
		return hitbox.getY();
	}
	
	/**
	 * Returns the width of the hitbox of this entity.
	 */
	@Override
	public float getWidth(){
		return hitbox.getWidth();
	}
	
	/**
	 * Returns the height of the hitbox of this entity;
	 */
	@Override
	public float getHeight(){
		return hitbox.getHeight();
	}
	
	/**
	 * Reduces this entity's health by an amount influenced by the argument provided according to some formula.
	 * @param normalDamage The damage dealt normally ignoring special hits and armour effects etc...
	 * @return The actual amount of damage taken by this entity.
	 */
	@Override
	public int takeDamage(int normalDamage){
		//TODO: update for armour etc...
		int originalHealth = health;
		health = Math.max(0, health - normalDamage);
		return originalHealth - health;
	}
	
	/**
	 * Returns the amount of damage done by this entity when taking into account critical hits etc...
	 */
	@Override
	public int getDamage(){
		//TODO: implement
		return -1;
	}
	
	/**
	 * Returns the normal damage (excluding critical hits etc...) done by this entity. 
	 */
	@Override
	public int getNormalDamage(){
		//TODO: implement
		return -1;
	}
	
	/**
	 * Returns the absolute value of this entity's current health.
	 */
	@Override
	public int getHealth(){
		return health;
	}
	
	/**
	 * Returns a float value in the range [0.0 - 1.0] inclusive representing the entity's current health.
	 */
	@Override
	public float getHealthPercent(){
		return (float)health/maxhealth;
	}
	
	/**
	 * Returns the absolute value of this entity's maximum possible health.
	 */
	@Override
	public int getMaxHealth(){
		return maxhealth;
	}
	
	/**
	 * Moves this entity by it's current velocity values and applies constants such as friction and gravity.
	 */
	@Override
	public void frameMove() {
		if (!isOnGround()) {
			dy += GRAVITY; //fall if not on the ground
		} else if (dy > 0) {
			dy = 0;
		}
		dx *= FRICTION;
		hitbox.setLocation(hitbox.getX() + dx * delta, hitbox.getY() + dy * delta); //move to new location
		if (isOnGround()) {
			//if the new location is on the ground, set it so entity isn't clipping into the ground
			hitbox.setLocation(hitbox.getX(), ((int)hitbox.getY() / 32) * 32);
		}
	}
	
	/**
	 * Returns true if and only if this entity has an absolute health equal to zero.
	 */
	@Override
	public boolean isDead() {
		return health <= 0;
	}
	
	/**
	 * returns whether the entity is touching the ground
	 * @return true if touching ground
	 */
	@Override
	public boolean isOnGround() {
		int tileSize = properties[0][0].getTileSize();
		//check bottom left corner of sprite
		String left = properties[((int)getX() / tileSize)][(((int)getY() + tileSize) / tileSize)].lookupProperty(TileProperty.BLOCKED);
		//check bottom right
		String right = properties[(((int)getX() + tileSize) / tileSize)][(((int)getY() + tileSize) / tileSize)].lookupProperty(TileProperty.BLOCKED);
		return (left.equals("true") || right.equals("true"));
	}
	
	/**
	 * makes the entity jump. if it is falling, sets its vertical change to zero first.
	 */
	public void jump() {
		dy = -1f;
	}
	
	public abstract void render();

	public abstract void update(Input input, Tile[][] properties, int delta);
	
}
