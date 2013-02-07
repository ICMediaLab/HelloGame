package entities.enemies;

import org.newdawn.slick.geom.Rectangle;

import entities.Entity;

public class Enemy extends Entity {

	public Enemy(Rectangle hitbox, int maxhealth) {
		super(hitbox, maxhealth);
	}

}
