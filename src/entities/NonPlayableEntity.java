package entities;

import org.newdawn.slick.geom.Rectangle;

public abstract class NonPlayableEntity extends Entity implements IEntity {
	
	public NonPlayableEntity(Rectangle hitbox, int maxhealth) {
		super(hitbox, maxhealth);
	}

}
