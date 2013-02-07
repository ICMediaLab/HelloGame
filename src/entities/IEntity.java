package entities;

public interface IEntity {
	float FRICTION = 0.98f;
	float GRAVITY = 1.0f;
	
	/**
	 * Reduces this entity's health by an amount influenced by the argument provided according to some formula.
	 * @param normalDamage The damage dealt normally ignoring special hits and armour effects etc...
	 * @return The actual amount of damage taken by this entity.
	 */
	int takeDamage(int normalDamage);
	
	/**
	 * Returns the amount of damage done by this entity when taking into account critical hits etc...
	 */
	int getDamage();
	
	/**
	 * Returns the normal damage (excluding critical hits etc...) done by this entity. 
	 */
	int getNormalDamage();
	
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
	 * Moves this entity by it's current velocity values and applies constants such as friction and gravity.
	 */
	void frameMove();
	
	/**
	 * Returns true if and only if this entity has an absolute health equal to zero.
	 */
	boolean isDead();
}
