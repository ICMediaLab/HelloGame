package lights;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AmbientLight extends AbstractLight {

	private static final Image i;
	
	static {
		Image img = null;
		try {
			img = new Image("data/dot.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		i = img;
	}
	
	public AmbientLight(Color tint) {
		super(i, tint);
	}

	@Override
	public void update(GameContainer gc) {
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		renderAbsolute(0, 0, gc.getWidth(), gc.getHeight());
	}

}
