package entities;

import org.newdawn.slick.geom.Rectangle;

public abstract class NonPlayableEntity extends Entity implements INonPlayableEntity {
	
	public NonPlayableEntity(Rectangle hitbox, int maxhealth) {
		super(hitbox, maxhealth);
	}
}
