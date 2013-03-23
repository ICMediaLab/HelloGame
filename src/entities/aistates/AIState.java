package entities.aistates;

import java.util.HashMap;
import java.util.Map;


public enum AIState {
	IDLE("idle",IdleEntity.class),ROAMING("roam",RoamingEntity.class),RETREATING("retreat",RetreatingEntity.class);
	
	private static final Map<String,AIState> index = new HashMap<String,AIState>();
	
	static {
		for(AIState ais : values()){
			index.put(ais.name, ais);
		}
	}
	
	private final String name;
	private final Class<? extends AINextMove> nextMove;

	private AIState(String name, Class<? extends AINextMove> nextMove){
		this.nextMove = nextMove;
		this.name = name;
	}
	
	public static AIState getAIState(String state){
		return index.get(state.trim().toLowerCase());
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
