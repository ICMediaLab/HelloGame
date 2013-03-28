package entities.aistates;

import entities.FixedRotationEntity;

/**
 * An abstract interface for selecting a non-playable entity's next move.
 */
public interface AINextMove {

	public abstract void updateEntity(FixedRotationEntity e);
	
}
