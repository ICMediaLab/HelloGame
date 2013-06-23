package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import utils.ani.AnimationManager;

import entities.aistates.decisiontree.AIDecisionTree;


public abstract class NonPlayableEntity extends AbstractEntity {
	
	private static final String DEFAULT_AI_STRING = ">idle";
	
	private final AIDecisionTree aitree;
	
	public NonPlayableEntity(float x, float y, float width, float height, int maxhealth, Element node) throws SlickException {
		this(x,y,width,height,maxhealth,AnimationManager.parseElement(node),parseAIElement(node));
	}
	
	public NonPlayableEntity(float x, float y, float width, float height, int maxhealth, AnimationManager ani, AIDecisionTree aitree) {
		super(x,y,width,height,ani,maxhealth);
		this.aitree = aitree;
	}
	
	private static AIDecisionTree parseAIElement(Element node){
		String AIText = DEFAULT_AI_STRING;
		{
			Node AINode = node.getElementsByTagName("ai").item(0);
			if(AINode == null){
				AINode = node.getElementsByTagName("AI").item(0);
			}
			if(AINode != null){
				AIText = AINode.getTextContent();
			}
		}
		return new AIDecisionTree(AIText);
	}
	
	/**
	 * Copy constructor
	 */
	protected NonPlayableEntity(NonPlayableEntity base) {
		super(base);
		this.aitree = base.aitree;
	}

	@Override
	public void update(GameContainer gc) {
		super.update(gc);
		aitree.evaluate(this).updateEntity(this);
	}
	
	protected AIDecisionTree getAIDecisionTree(){
		return aitree;
	}
}
