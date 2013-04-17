package entities.aistates.decisiontree;

import entities.MovingEntity;
import entities.aistates.AINextMove;
import entities.aistates.AIState;

public class AIStateLeaf extends Leaf<AINextMove> {
	
	private AIState state;
	
	/**
	 * Creates a new Leaf node with a given state
	 * @param stateStr A string representation of the state to be used.
	 * @throws IllegalStateException If the specified state does not exist.
	 */
	public void setState(String stateStr) throws IllegalStateException {
		if(stateStr.startsWith(">")){
			stateStr = stateStr.substring(1);
		}
		state = AIState.getAIState(stateStr);
		if(state == null){
			throw new IllegalStateException("No such state (" + stateStr + ")");
		}
	}

	@Override
	public AINextMove evaluate(MovingEntity e) {
		return state.getStateClass();
	}
}