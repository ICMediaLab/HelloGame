package entities.aistates;

import entities.Entity;

/**
 * The entity should remain idle and not move.
 */
public class IdleEntity implements AINextMove {

	@Override
	public void updateEntity(Entity e) {
		//do nothing
	}

}
