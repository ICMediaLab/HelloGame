package entities.aistates.decisiontree;

import entities.Entity;
import entities.aistates.AINextMove;

/**
 * A class holding a series of conditionals and AI states to allow
 * for entities to hold multiple conditioned procedures for AI development. 
 */
public class AIDecisionTree {

	/**
	 * The root of the Decision Tree.
	 */
	private final DecisionNode root;
	
	/**
	 * Creates a new {@link AIDecisionTree} object. 
	 * @param ai The String to be parsed to create the tree.
	 */
	public AIDecisionTree(String ai){
		//remove all whitespace.
		String conditions = ai.trim().replaceAll("\\s+", "");
		DecisionNode newRoot = null;
		try {
			//attempt to generate a root node
			newRoot = DecisionNode.getNode(conditions);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		root = newRoot;
	}

	/**
	 * Evaluates this Decision Tree with respect to an {@link Entity} specified.
	 * @return An instantiation of a AINextMove object which can be used to update an entity.
	 */
	public AINextMove evaluate(Entity e) {
		return root.evaluate(e);
	}
	
}
