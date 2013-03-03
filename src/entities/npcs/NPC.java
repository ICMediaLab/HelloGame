package entities.npcs;

import org.newdawn.slick.Input;

import entities.NonPlayableEntity;

public class NPC extends NonPlayableEntity{

	public NPC(float x, float y, int width, int height, int maxhealth) {
		super(x,y,width,height,maxhealth);
	}
	
	@Override
	protected NPC clone() {
		return new NPC(getX(), getY(), getWidth(), getHeight(),getMaxHealth());
	}

	public void update(Input input) {
		//TODO
	}
	
	public void render() {
		//TODO
	}
	
}
