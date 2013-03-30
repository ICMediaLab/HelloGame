package GUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;


public abstract class Window {

	float x;
	float y;
	float width;
	float height;
	boolean active;
	
	public abstract void render(Graphics gr);
	public abstract void update(GameContainer gc, StateBasedGame sbg, float delta);
}
