package entities.players;

import org.newdawn.slick.geom.Rectangle;

import entities.Entity;

public class Player extends Entity {

	public Player(Rectangle hitbox, int maxhealth) {
		super(hitbox, maxhealth);
	}
	
	protected Object clone() {
		return new Player(new Rectangle(getX(), getY(), getWidth(), getHeight()),getMaxHealth());
	}

}
