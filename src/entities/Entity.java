package entities;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public abstract class Entity implements IEntity {
	
	private final Rectangle hitbox;
	protected float dx = 0;
	protected float dy = 0;
	
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
		dy += GRAVITY;
		dx *= FRICTION;
		dy *= FRICTION;
		hitbox.setLocation(hitbox.getX()+dx, hitbox.getY()+dy);
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
	 */
	@Override
	public boolean isOnGround() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void jump()
	{
		if (isOnGround())
		{
			dy -= 1f;
		}
	}
	
	public abstract void update(Input input);
	
	public abstract void render();
	
}
