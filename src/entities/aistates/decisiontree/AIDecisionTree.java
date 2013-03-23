package entities.aistates.decisiontree;

import entities.Entity;
import entities.aistates.AINextMove;


public class AIDecisionTree {

	private final DecisionNode root;
	
	public AIDecisionTree(String ai){
		String conditions = ai.trim().replaceAll("\\s+", "");
		DecisionNode newRoot = null;
		try {
			newRoot = DecisionNode.getNode(conditions);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		root = newRoot;
	}

	public AINextMove evaluate(Entity e) {
		return root.evaluate(e);
	}
	
}
