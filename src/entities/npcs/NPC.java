package entities.npcs;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import entities.NonPlayableEntity;

public class NPC extends NonPlayableEntity{

	public NPC(float x, float y, float width, float height, int maxhealth) {
		super(x,y,width,height,maxhealth);
	}
	
	@Override
	public NPC clone() {
		return new NPC(getX(), getY(), getWidth(), getHeight(),getMaxHealth());
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
	
}
