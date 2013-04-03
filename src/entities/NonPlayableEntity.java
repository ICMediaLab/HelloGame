package entities;

import org.newdawn.slick.GameContainer;

import utils.MapLoader;
import entities.aistates.decisiontree.AIDecisionTree;


public abstract class NonPlayableEntity extends AbstractEntity {
	
	protected static final String DEFAULT_AI_STRING = ">idle";
	
	private final AIDecisionTree aitree;
	
	public NonPlayableEntity(float x, float y, float width, float height, int maxhealth, String AI) {
		this(x,y,width,height,maxhealth,new AIDecisionTree(AI));
	}
	
	public NonPlayableEntity(float x, float y, float width, float height, int maxhealth, AIDecisionTree aitree) {
		super(x,y,width,height,maxhealth);
		this.aitree = aitree;
	}
	
	@Override
	public void update(GameContainer gc) {
		aitree.evaluate(this).updateEntity(this);
		if(isDead()) {
	        MapLoader.getCurrentCell().removeEntity(this);
		}
	}
	
	protected AIDecisionTree getAIDecisionTree(){
		return aitree;
	}
}
