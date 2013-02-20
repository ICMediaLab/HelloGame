package entities;

import org.lwjgl.util.Renderable;
import org.newdawn.slick.Input;


public interface IEntity extends Cloneable, Renderable {
	float XFRICTION = 0.68f;
	float YFRICTION = 0.98f;
	float GRAVITY = 0.09f;
	float JUMP_AMOUNT = 0.1f;
	
	/**
	 * Returns the current x-position of this entity.
	 */
	float getX();
	
	/**
	 * Returns the current y-position of this entity.
	 */
	float getY();
	
	/**
	 * Returns the width of the hitbox of this entity.
	 */
	float getWidth();
	
	/**
	 * Returns the height of the hitbox of this entity;
	 */
	float getHeight();
	
	
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
	 * Moves this entity by it's current velocity values and applies constants such as friction and gravity.<br />
	 * This method should only be used if update is not called in the same frame.
	 * @param delta The time in microseconds since the last frame update.
	 */
	void frameMove(int delta);
	
	/**
	 * Returns true if and only if this entity has an absolute health equal to zero.
	 */
	boolean isDead();
	
	/**
	 * returns whether the entity is touching the ground
	 * @return
	 */
	boolean isOnGround();
	
	/**
	 * Modified this entity's dx,dy values corresponding to a player jumping.<br />
	 * Note the precondition of the entity touching a surface should NOT be checked.
	 */
	void jump();
	
	/**
	 * Modifies the entity's dx value to reflect movement in x axis.
	 */
	void moveX(float x);
	
	/**
	 * Updates this entity given keyboard input, tile properties and the time delta.<br />
	 * This method should make exactly one call to frameMove();
	 * @param input The current state of the keyboard.
	 * @param delta The time in microseconds since the last update.
	 */
	void update(Input input, int delta);
	
	/**
	 * Forces this entity to stop all currently playing sounds.<br />
	 * Includes sounds produced by contained objects such as Ability classes.
	 */
	void stop_sounds();
}
