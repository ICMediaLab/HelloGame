package entities;

import org.lwjgl.util.Renderable;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import utils.Position;


public interface IEntity extends Cloneable, Renderable {
	Position FRICTION = new Position(0.6f,0.84f);
	float GRAVITY = 0.10f;
	float JUMP_AMOUNT = 1.15f;
	long DELTA = 1000/60;
	
	/**
	 * Returns the current x-position of this entity.
	 */
	float getX();
	
	/**
	 * Returns the current y-position of this entity.
	 */
	float getY();
	
	/**
	 * Returns the current x-velocity of this entity.
	 */
	float getdX();
	
	/**
	 * Returns the current y-velocity of this entity.
	 */
	float getdY();
	
	/**
	 * Returns the width of the hitbox of this entity.
	 */
	int getWidth();
	
	/**
	 * Returns the height of the hitbox of this entity;
	 */
	int getHeight();
	
	
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
	void frameMove();
	
	/**
	 * Should only be used if you desperately need to teleport the player.
	 * @param x position
	 * @param y position
	 */
	void setPosition(float x, float y);
	
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
	void update(Input input);
	
	/**
	 * Forces this entity to stop all currently playing sounds.<br />
	 * Includes sounds produced by contained objects such as Ability classes.
	 */
	void stop_sounds();

	/**
	 * Returns true if and only if the hitbox given intersects with the bounding box of this entity.
	 */
	boolean intersects(Rectangle hitbox);
}
