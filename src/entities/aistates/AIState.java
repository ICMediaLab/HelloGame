package entities.aistates;


public enum AIState {
	IDLE(IdleEntity.class),ROAMING(RoamingEntity.class);
	
	private final Class<? extends AINextMove> nextMove;

	private AIState(Class<? extends AINextMove> nextMove){
		this.nextMove = nextMove;
	}
	
	public AINextMove getStateClass(){
		try {
			return nextMove.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
