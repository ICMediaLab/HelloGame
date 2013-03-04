package entities.aistates;

import entities.Entity;


public enum AIState {
	IDLE(new IdleEntity()),ROAMING(new RoamingEntity());
	
	private final AINextMove nextMove;

	private AIState(AINextMove nextMove){
		this.nextMove = nextMove;
	}

	public void updateEntity(Entity e) {
		nextMove.updateEntity(e);
	}
}
