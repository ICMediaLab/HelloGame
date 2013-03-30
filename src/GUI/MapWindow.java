package GUI;

import game.config.Config;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class MapWindow extends Window {

	public MapWindow() {
		this.x = Config.getScreenWidth() * 0.25f;
		this.y = Config.getScreenHeight() * 0.25f;
		this.width = Config.getScreenWidth() * 0.5f;
		this.height = Config.getScreenHeight() * 0.5f;
	}

	@Override
	public void render(Graphics gr) {
		gr.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		gr.fillRoundRect(x, y, width, height, 5);
		
		gr.setColor(Color.black);
		
		gr.drawString("Map needs to be drawn below", x + (width - gr.getFont().getWidth("Map needs to be drawn below")) * 0.5f , y + 20);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, float delta) {
		
	}

}
