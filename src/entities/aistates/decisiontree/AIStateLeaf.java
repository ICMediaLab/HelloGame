package entities.aistates.decisiontree;

import conditiontree.Leaf;
import entities.MovingEntity;
import entities.aistates.AINextMove;
import entities.aistates.AIState;

public class AIStateLeaf extends Leaf<AINextMove> {
	
	private static final long serialVersionUID = -8101584764687163407L;
	
	private AIState state;
	
	/**
	 * Creates a new Leaf node with a given state
	 * @param stateStr A string representation of the state to be used.
	 * @throws IllegalStateException If the specified state does not exist.
	 */
	protected void setState(String stateStr) throws IllegalStateException {
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