package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class JumpPlatform extends Entity {

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entity clone() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
