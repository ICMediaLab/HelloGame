package entities;

import map.Cell;

import org.newdawn.slick.geom.Rectangle;

public abstract class NonPlayableEntity extends Entity implements INonPlayableEntity {
	
	public NonPlayableEntity(Cell currentCell, Rectangle hitbox, int maxhealth) {
		super(currentCell, hitbox, maxhealth);
	}
}
