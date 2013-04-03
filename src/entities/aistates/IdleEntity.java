package entities.aistates;

import entities.NonPlayableEntity;

/**
 * The entity should remain idle and not move.
 */
public class IdleEntity implements AINextMove {

	@Override
	public void updateEntity(NonPlayableEntity e) {
		//do nothing
	}

}
