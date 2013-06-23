package entities.aistates;

import utils.ani.AnimationState;
import entities.NonPlayableEntity;

public class SleepingEntity implements AINextMove {

	@Override
	public void updateEntity(NonPlayableEntity e) {
		e.setCurrentAnimationState(AnimationState.SLEEP);
		e.setPosition(e.getX(), (int) (e.getY()));
		e.setVelocity(0f, 0f);
	}

}
