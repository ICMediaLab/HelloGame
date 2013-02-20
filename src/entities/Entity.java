package entities;

import game.config.Config;

import map.Tile;
import map.TileProperty;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;


public abstract class Entity implements IEntity {
	
	private final Rectangle hitbox;
	private float dx = 0;
	private float dy = 0;
	protected Tile[][] properties;
	
	//TODO:	Implement entity image system.
	//		No idea how at the moment.
	
	private int health,maxhealth;

	public Entity(Rectangle hitbox, int maxhealth) {
		this.hitbox = hitbox;
		this.health = maxhealth;
		this.maxhealth = maxhealth;
	}
	
	/**
	 * A utility method for returning the real value of friction given a time difference delta since the last frame.
	 * @param delta The time in microseconds since the last frame update.
	 * @return The effect of the formula: FRICTION ^ (delta * NORMAL_FPS / 1000).
	 */
//	protected static final float getFrictionDelta(int delta){
//		return (float) Math.pow(FRICTION, delta*Config.getNormalFPS()*0.001f);
//	}
	
	/**
	 * A utility method for returning the real value of gravity given a time difference delta since the last frame.
	 * @param delta The time in microseconds since the last frame update.
	 * @return The effect of the formula: GRAVITY * (delta * NORMAL_FPS / 1000).
	 */
	protected static final float getGravityDelta(int delta){
		return GRAVITY*delta*Config.getNormalFPS()*0.001f;
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
	 * @param delta The time in microseconds since the last frame update.
	 */
	@Override
	public void frameMove(int delta) {
//		float modFriction = getFrictionDelta(delta);
//		float modGravity  = getGravityDelta(delta);
		
		//both x and y axis are affected by scalar friction
		if (!isOnGround()) {
			dy += GRAVITY; //fall if not on the ground
		} else if (dy > 0) {
			dy = 0;
		}
		dx *= XFRICTION; dy *= YFRICTION;
		
		hitbox.setLocation(hitbox.getX() + dx * delta, hitbox.getY() + dy * delta); //move to new location
		if (isOnGround()) {
			//if the new location is on the ground, set it so entity isn't clipping into the ground
			hitbox.setLocation(hitbox.getX(), ((int)hitbox.getY() / 32) * 32);
		}
		//vertical collision
		if (topCollision()) {
		    dy = 0;
		    hitbox.setLocation(hitbox.getX(), (((int)hitbox.getY() + 32) / 32) * 32);
		}
		//horizontal collision
		int side = sideCollision();
		if (side != 0) {
		    dx = 0;
		    if (side == -1) {
		        hitbox.setLocation(((hitbox.getX() + 32) / 32) * 32, hitbox.getY());
	        }
	        else {
	            hitbox.setLocation((hitbox.getX() / 32) * 32, hitbox.getY());
	        }
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
		//check bottom left corner of sprite
		String left = bottomLeft();
		//check bottom right
		String right = bottomRight();
		return (left.equals("true") || right.equals("true"));
	}
	
	public boolean topCollision() {
	    String left = topLeft();
	    String right = topRight();
	    return (left.equals("true") || right.equals("true"));
	}
	
	public int sideCollision() {
	    String topLeft = topLeft();
	    String topRight = topRight();
	    String bottomLeft = bottomLeft();
	    String bottomRight = bottomRight();
	    
	    if (topLeft.equals("true") || bottomLeft.equals("true")) {
	        return -1;
	    }
	    else if (topRight.equals("true") || bottomRight.equals("true")) {
	        return 1;
	    }
	    else {
	        return 0;
	    }
	}
	
	//collision checkers
	private String topLeft() {
		int tileSize = Config.getTileSize();
		return properties[((int)getX() / tileSize)][((int)getY() / tileSize)].lookupProperty(TileProperty.BLOCKED);
	}
	
	private String topRight() {
		int tileSize = Config.getTileSize();
		return properties[(((int)getX() + tileSize) / tileSize)][((int)getY() / tileSize)].lookupProperty(TileProperty.BLOCKED);
	}
	
	private String bottomLeft() {
		int tileSize = Config.getTileSize();
		return properties[((int)getX() / tileSize)][(((int)getY() + tileSize) / tileSize)].lookupProperty(TileProperty.BLOCKED);
	}
	
	private String bottomRight() {
		int tileSize = Config.getTileSize();
		return properties[(((int)getX() + tileSize) / tileSize)][(((int)getY() + tileSize) / tileSize)].lookupProperty(TileProperty.BLOCKED);
	}
	
	/**
	 * makes the entity jump. if it is falling, sets its vertical change to zero first.
	 */
	@Override
	public void jump() {
		dy = -1f;
	}
	
	@Override
	public void moveX(float x) {
		dx = x;
	}
	
	@Override
	public abstract void render();

	@Override
	public abstract void update(Input input, Tile[][] properties, int delta);

	@Override
	public void stop_sounds(){
		//left blank in case sounds are moved to this class.
		//should be overridden to add class-specific sounds with a call to the super method.
	}
	
}
