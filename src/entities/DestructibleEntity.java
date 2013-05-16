package entities;

public interface DestructibleEntity extends Entity, Cloneable {
	
	/**
	 * Reduces this entity's health by an amount influenced by the argument provided according to some formula.
	 * @param normalDamage The damage dealt normally ignoring special hits and armour effects etc...
	 * @return The actual amount of damage taken by this entity.
	 */
	int takeDamage(int normalDamage);
	
	/**
	 * Returns the absolute value of this entity's current health.
	 */
	int getHealth();
	
	/**
	 * Returns a float value in the range [0.0 - 1.0] inclusive representing the entity's current health.
	 */
	float getHealthPercent();
	
	/**
	 * Returns the absolute value of this entity's maximum possible health.
	 */
	int getMaxHealth();
	
	/**
	 * Returns true if and only if this entity has an absolute health equal to zero.
	 */
	boolean isDead();
	
	/**
	 * Performs cleanup for entity death (e.g. removing from current cell, etc).
	 */
	void die();

	DestructibleEntity clone();

}
