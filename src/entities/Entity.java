package entities;

import org.newdawn.slick.geom.Rectangle;

public abstract class Entity implements IEntity {
	
	private final Rectangle hitbox;
	private float dx = 0,dy = 0;
	
	//TODO:	Implement entity image system.
	//		No idea how at the moment.
	
	private int health,maxhealth;

	public Entity(Rectangle hitbox, int maxhealth) {
		this.hitbox = hitbox;
		this.health = maxhealth;
		this.maxhealth = maxhealth;
	}
	
	/**
	 * Reduces this entity's health by an amount influenced by the argument provided according to some formula.
	 * @param normalDamage The damage dealt normally ignoring special hits and armour effects etc...
	 * @return The actual amount of damage taken by this entity.
	 */
	public int takeDamage(int normalDamage){
		//TODO: implement
		return -1;
	}
	
	/**
	 * Returns the amount of damage done by this entity when taking into account critical hits etc...
	 */
	public int getDamage(){
		//TODO: implement
		return -1;
	}
	
	/**
	 * Returns the normal damage (excluding critical hits etc...) done by this entity. 
	 */
	public int getNormalDamage(){
		//TODO: implement
		return -1;
	}
	
	/**
	 * Returns the absolute value of this entity's current health.
	 */
	public int getHealth(){
		return health;
	}
	
	/**
	 * Returns a float value in the range [0.0 - 1.0] inclusive representing the entity's current health.
	 */
	public float getHealthPercent(){
		return (float)health/maxhealth;
	}
	
	/**
	 * Returns the absolute value of this entity's maximum possible health.
	 */
	public int getMaxHealth(){
		return maxhealth;
	}
	
	/**
	 * Moves this entity by it's current velocity values and applies constants such as friction and gravity.
	 */
	public void frameMove() {
		dy += GRAVITY;
		dx *= FRICTION;
		dy *= FRICTION;
		hitbox.setLocation(hitbox.getX()+dx, hitbox.getY()+dy);
	}
	
	/**
	 * Returns true if and only if this entity has an absolute health equal to zero.
	 */
	public boolean isDead() {
		return health <= 0;
	}
	
}
