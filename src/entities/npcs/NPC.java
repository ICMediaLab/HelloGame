package entities.npcs;

import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import entities.NonPlayableEntity;

public class NPC extends NonPlayableEntity{

	public NPC(Rectangle hitbox, int maxhealth) {
		super(hitbox, maxhealth);
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new NPC(new Rectangle(getX(), getY(), getWidth(), getHeight()),getMaxHealth());
	}

	public void update(Input input) {
		//TODO
	}
	
	public void render() {
		//TODO
	}
	
}
