package entities;

public interface MovingEntity extends DestructibleEntity {
	
	/**
	 * Returns the current x-velocity of this entity.
	 */
	float getdX();
	
	/**
	 * Returns the current y-velocity of this entity.
	 */
	float getdY();
	
	/**
	 * Returns the amount of damage done by this entity when taking into account critical hits etc...
	 */
	int getDamage();
	
	/**
	 * Returns the normal damage (excluding critical hits etc...) done by this entity. 
	 */
	int getNormalDamage();
	
	/**
	 * Moves this entity by it's current velocity values and applies constants such as friction and gravity.<br />
	 * This method should only be used if update is not called in the same frame.
	 * @param delta The time in microseconds since the last frame update.
	 */
	void frameMove();
	
	/**
	 * Returns true if and only if this entity is touching the ground.
	 */
	boolean isOnGround();
	
	/**
	 * Returns true if and only if this entity is touching a wall.
	 */
	boolean isOnWall();
	
	/**
	 * Modified this entity's dx,dy values corresponding to a player jumping.<br />
	 * Note the precondition of the entity touching a surface should NOT be checked.
	 */
	void jump();
	
	/**
	 * Accelerates this entity in the vector specified.
	 * @param ddx
	 * @param ddy
	 */
	void accelerate(float ddx, float ddy);
	
	/**
	 * Sets this entity's velocity vector equal to that specified.
	 * @param dx
	 * @param dy
	 */
	void setVelocity(float dx, float dy);
	
	/**
	 * Should only be used if you desperately need to teleport the player.
	 * @param x position
	 * @param y position
	 */
	void setCentre(float x, float y);
	
	/**
	 * Triggered when an entity is found to be intersecting a static entity.
	 */
	void collide(StaticEntity<?> e);
	void collide(DestructibleEntity d);

	MovingEntity clone();

	
}
