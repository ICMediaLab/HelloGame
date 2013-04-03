package entities.aistates;

import entities.NonPlayableEntity;

/**
 * An abstract interface for selecting a non-playable entity's next move.
 */
public interface AINextMove {

	public abstract void updateEntity(NonPlayableEntity e);
	
}
