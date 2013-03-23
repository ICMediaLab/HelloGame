package entities.npcs;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import entities.Entity;
import entities.NonPlayableEntity;
import entities.aistates.decisiontree.AIDecisionTree;

public class NPC extends NonPlayableEntity{
	
	private static final int NPC_DEFAULT_LAYER = -10;
	
	public NPC(float x, float y, float width, float height, int maxhealth, String AIStr) {
		super(x,y,width,height,maxhealth,AIStr);
	}
	
	public NPC(float x, float y, float width, float height, int maxHealth,AIDecisionTree aiDecisionTree) {
		super(x,y,width,height,maxHealth,aiDecisionTree);
	}

	@Override
	public NPC clone() {
		return new NPC(getX(), getY(), getWidth(), getHeight(),getMaxHealth(),getAIDecisionTree());
	}
	
	public void render() {
		//TODO
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void collide(Entity e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getLayer() {
		return NPC_DEFAULT_LAYER;
	}
	
}
