package conditiontree;

import entities.MovingEntity;
import entities.aistates.AINextMove;

public abstract class TreeNode<T> {
	
	/**
	 * Returns a new Decision Node for a Decision Tree.
	 * @param str The string to be parsed to create this node.
	 * @return A new complete Decision Node (i.e. no null leaves). 
	 * @throws IllegalArgumentException If a specified leaf state does not exist.
	 * @throws NoSuchFieldException If a specified condition parameter does not exist or the condition is malformed.
	 * @throws IllegalAccessException If the leaf class provided does not have a public default constructor.
	 * @throws InstantiationException If the leaf class provided does not have a public default constructor.
	 */
	public static <T, L extends Leaf<T>> TreeNode<T> getNode(String str, Class<L> leafClass) throws IllegalArgumentException, NoSuchFieldException, InstantiationException, IllegalAccessException{
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
			return new ConditionBranch<T>(cond,accept,reject,leafClass);
		}else{
			//return new leaf node of specified state
			L ret = leafClass.newInstance();
			ret.setState(str);
			return ret;
		}
	}

	public abstract AINextMove evaluate(MovingEntity e);
	
}

class ConditionBranch<T> extends TreeNode<T> {

	private final Condition cond;
	private final TreeNode<T> accept,reject;
	
	/**
	 * Creates a new two-way conditional branch 
	 * @param condition The condition to be evaluated
	 * @param accept The true branch
	 * @param reject The false branch
	 * @throws IllegalArgumentException If a specified leaf state does not exist.
	 * @throws NoSuchFieldException If a specified condition parameter does not exist or the condition is malformed.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	ConditionBranch(String condition, String accept, String reject, Class<? extends Leaf<T>> leafClass) throws IllegalArgumentException, NoSuchFieldException, InstantiationException, IllegalAccessException {
		//parse condition, then parse true and false subtrees just as before
		cond = Condition.getCondition(condition);
		this.accept = TreeNode.getNode(accept,leafClass);
		this.reject = TreeNode.getNode(reject,leafClass);
	}
	
	@Override
	public AINextMove evaluate(MovingEntity e){
		return cond.evaluate(e) ? accept.evaluate(e) : reject.evaluate(e);
	}
}


