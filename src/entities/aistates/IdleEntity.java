package entities.aistates;

import java.util.Random;

import utils.ani.AnimationState;
import entities.NonPlayableEntity;

/**
 * The entity should remain idle and not move.
 */
public class IdleEntity implements AINextMove {
	
	private static final Random r = new Random();
	
	private static int countdown = 0;
	
	@Override
	public void updateEntity(NonPlayableEntity e) {
		if(countdown <= 0){
			if(r.nextBoolean()){
				e.setCurrentAnimationState(AnimationState.PAUSE_RIGHT);
			}else {
				e.setCurrentAnimationState(AnimationState.PAUSE_LEFT);
			}
			countdown = 100;
		}else {
			countdown--;
			AnimationState state = e.getCurrentAnimationState();
			if(state == AnimationState.ROAM_LEFT){
				e.setCurrentAnimationState(AnimationState.PAUSE_LEFT);
			}else if(state == AnimationState.ROAM_RIGHT){
				e.setCurrentAnimationState(AnimationState.PAUSE_RIGHT);
			}
		}
	}

}
