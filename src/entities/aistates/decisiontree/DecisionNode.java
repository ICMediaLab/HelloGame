package entities.aistates.decisiontree;

import entities.Entity;
import entities.aistates.AINextMove;
import entities.aistates.AIState;

abstract class DecisionNode {
	
	static DecisionNode getNode(String str) throws IllegalArgumentException, NoSuchFieldException{
		str = str.trim();
		if(str.startsWith("if") || str.startsWith("IF")){
			int firstSplit = str.indexOf('{');
			int lastSplit = str.lastIndexOf('}');
			String p1 = str.substring(2, firstSplit).trim();
			String p2 = str.substring(firstSplit+1,lastSplit).trim();
			String p3 = str.substring(lastSplit+1).trim();
			System.out.println(p1 + " <cond-accept> " + p2 + " <accept-reject> " + p3);
			return new ConditionBranch(p1, p2, p3);
		}else{
			return new Leaf(str);
		}
	}

	public abstract AINextMove evaluate(Entity e);
	
}

class ConditionBranch extends DecisionNode {

	private final Condition cond;
	private final DecisionNode accept,reject;
	
	ConditionBranch(String condition, String accept, String reject) throws IllegalArgumentException, NoSuchFieldException {
		cond = Condition.getCondition(condition);
		this.accept = DecisionNode.getNode(accept);
		this.reject = DecisionNode.getNode(reject);
	}
	
	@Override
	public AINextMove evaluate(Entity e){
		return cond.evaluate(e) ? accept.evaluate(e) : reject.evaluate(e);
	}
}

class Leaf extends DecisionNode {
	
	private final AIState state;
	
	Leaf(String stateStr) throws IllegalStateException {
		if(stateStr.startsWith(">")){
			stateStr = stateStr.substring(1);
		}
		state = AIState.getAIState(stateStr);
		if(state == null){
			throw new IllegalStateException("No such state (" + stateStr + ")");
		}
	}

	@Override
	public AINextMove evaluate(Entity e) {
		return state.getStateClass();
	}
	
}
