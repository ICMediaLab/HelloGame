package lights;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface Light {
	
	void update(GameContainer gc, StateBasedGame sbg, float delta);
	void render(GameContainer gc, StateBasedGame sbg, Graphics g);
    float getX();
    float getY();

}
