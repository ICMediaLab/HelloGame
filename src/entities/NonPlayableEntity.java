package entities;

import entities.aistates.AINextMove;
import entities.aistates.AIState;


public abstract class NonPlayableEntity extends Entity implements INonPlayableEntity {
	
	private AINextMove nextMove = AIState.RETREATING.getStateClass();
	
	public NonPlayableEntity(float x, float y, float width, float height, int maxhealth) {
		super(x,y,width,height,maxhealth);
	}
	
	public NonPlayableEntity(float x, float y, float width, float height){
		super(x,y,width,height);
	}
	
	public NonPlayableEntity(){
		super();
	}
	
	public void setAIState(AIState state){
		nextMove = state.getStateClass();
	}
	
	protected void updateEntity() {
		nextMove.updateEntity(this);
	}
}
