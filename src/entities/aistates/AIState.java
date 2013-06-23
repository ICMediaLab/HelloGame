package entities.aistates;

public enum AIState {
	IDLE(new IdleEntity()),
	ROAM(new RoamingEntity()),
	RETREAT(new RetreatingEntity()),
	FLYING_ROAM(new FlyingRoamingEntity());
	
	private final AINextMove nextMove;

	private AIState(AINextMove nextMove){
		this.nextMove = nextMove;
	}
	
	public static AIState getAIState(String state){
		return valueOf(state.trim().toUpperCase());
	}
	
	public AINextMove getStateClass(){
		return nextMove;
	}
}
