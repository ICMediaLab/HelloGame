package entities.aistates.decisiontree;

import entities.MovingEntity;
import entities.aistates.AINextMove;
import entities.aistates.AIState;

abstract class DecisionNode {
	
	/**
	 * Returns a new Decision Node for a Decision Tree.
	 * @param str The string to be parsed to create this node.
	 * @return A new complete Decision Node (i.e. no null leaves). 
	 * @throws IllegalArgumentException If a specified leaf state does not exist.
	 * @throws NoSuchFieldException If a specified condition parameter does not exist or the condition is malformed.
	 */
	static DecisionNode getNode(String str) throws IllegalArgumentException, NoSuchFieldException{
		//if the string starts with an if statement, it is a branch, else it is a leaf
		if(str.startsWith("if") || str.startsWith("IF")){
			//find end of condition/start of true section
			int firstSplit = str.indexOf('{');
			//find end of true section/start of false section
			int lastSplit = str.lastIndexOf('}');
			
			//create respective strings and return new branch node
			String cond = str.substring(2, firstSplit).trim();
			String accept = str.substring(firstSplit+1,lastSplit).trim();
			String reject = str.substring(lastSplit+1).trim();
			return new ConditionBranch(cond,accept,reject);
		}else{
			//return new leaf node of specified state
			return new Leaf(str);
		}
	}

	public abstract AINextMove evaluate(MovingEntity e);
	
}

class ConditionBranch extends DecisionNode {

	private final Condition cond;
	private final DecisionNode accept,reject;
	
	/**
	 * Creates a new two-way conditional branch 
	 * @param condition The condition to be evaluated
	 * @param accept The true branch
	 * @param reject The false branch
	 * @throws IllegalArgumentException If a specified leaf state does not exist.
	 * @throws NoSuchFieldException If a specified condition parameter does not exist or the condition is malformed.
	 */
	ConditionBranch(String condition, String accept, String reject) throws IllegalArgumentException, NoSuchFieldException {
		//parse condition, then parse true and false subtrees just as before
		cond = Condition.getCondition(condition);
		this.accept = DecisionNode.getNode(accept);
		this.reject = DecisionNode.getNode(reject);
	}
	
	@Override
	public AINextMove evaluate(MovingEntity e){
		return cond.evaluate(e) ? accept.evaluate(e) : reject.evaluate(e);
	}
}

class Leaf extends DecisionNode {
	
	private final AIState state;
	
	/**
	 * Creates a new Leaf node with a given state
	 * @param stateStr A string representation of the state to be used.
	 * @throws IllegalStateException If the specified state does not exist.
	 */
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
	public AINextMove evaluate(MovingEntity e) {
		return state.getStateClass();
	}
	
}
