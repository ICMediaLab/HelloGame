package entities;

import org.newdawn.slick.geom.Shape;

import utils.LayerRenderable;
import utils.Position;
import utils.PositionReturn;
import utils.Updatable;


public interface Entity extends Updatable, LayerRenderable, PositionReturn {
	Position FRICTION = new Position(0.6f,0.04f);
	float GRAVITY = 0.04f;
	float JUMP_AMOUNT = 0.5f;
	
	/**
	 * Returns the current x-position of this entity.
	 */
	float getCentreX();
	
	/**
	 * Returns the current y-position of this entity.
	 */
	float getCentreY();
	
	/**
	 * Returns the width of the hitbox of this entity.
	 */
	float getWidth();
	
	/**
	 * Returns the height of the hitbox of this entity;
	 */
	float getHeight();
	
	
	/**
	 * Forces this entity to stop all currently playing sounds.<br />
	 * Includes sounds produced by contained objects such as Ability classes.
	 */
	void stop_sounds();

	/**
	 * Returns true if and only if the hitbox given intersects with the bounding box of this entity.
	 */
	boolean intersects(Entity e2);
	
	/**
	 * Returns true if and only if the hitbox given intersects with the bounding box of this entity.
	 */
	boolean intersects(Shape e2);
	
	boolean contains(Position p2);
	boolean contains(Shape s2);
	boolean contains(Entity e2);

	/**
	 * Triggered when an entity is found to be intersecting another entity.
	 */
	void collide(MovingEntity e);
	
	/**
	 * Returns the hitbox of this entity.
	 */
	Shape getHitbox();
}
