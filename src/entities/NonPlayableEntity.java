package entities;

import entities.aistates.decisiontree.AIDecisionTree;


public abstract class NonPlayableEntity extends AbstractEntity {
	
	private final AIDecisionTree aitree;
	
	public NonPlayableEntity(float x, float y, float width, float height, int maxhealth, String AI) {
		this(x,y,width,height,maxhealth,new AIDecisionTree(AI));
	}
	
	public NonPlayableEntity(float x, float y, float width, float height, int maxhealth, AIDecisionTree aitree) {
		super(x,y,width,height,maxhealth);
		this.aitree = aitree;
	}
	
	protected void updateEntity() {
		aitree.evaluate(this).updateEntity(this);
	}
	
	protected AIDecisionTree getAIDecisionTree(){
		return aitree;
	}
}
