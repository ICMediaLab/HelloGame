package entities.aistates;

import entities.FixedRotationEntity;

/**
 * The entity should remain idle and not move.
 */
public class IdleEntity implements AINextMove {

	@Override
	public void updateEntity(FixedRotationEntity e) {
		//do nothing
	}

}
