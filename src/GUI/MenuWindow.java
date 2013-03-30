package GUI;

import game.config.Config;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class MenuWindow extends Window {

	public MenuWindow() {
		this.x = Config.getScreenWidth() * 0.25f;
		this.y = Config.getScreenHeight() * 0.25f;
		this.width = Config.getScreenWidth() * 0.5f;
		this.height = Config.getScreenHeight() * 0.5f;
	}

	@Override
	public void render(Graphics gr) {
		gr.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		gr.fillRoundRect(x, y, width, height, 5);
		
		gr.setColor(Color.darkGray);
		gr.fillRoundRect(x + width * 0.5f - 80, y + 30, 160, 60, 5);
		gr.setColor(Color.black);
		gr.drawString("Resume", x + width * 0.5f - gr.getFont().getWidth("Resume")/2 , y + 30 + 30 - gr.getFont().getHeight("Resume")/2);
		
		gr.setColor(Color.darkGray);
		gr.fillRoundRect(x + width * 0.5f - 80, y + 100, 160, 60, 5);
		gr.setColor(Color.black);
		gr.drawString("Options", x + width * 0.5f - gr.getFont().getWidth("Options")/2 , y + 100 + 30 - gr.getFont().getHeight("Options")/2);
		
		gr.setColor(Color.darkGray);
		gr.fillRoundRect(x + width * 0.5f - 80, y + 170, 160, 60, 5);
		gr.setColor(Color.black);
		gr.drawString("Exit", x + width * 0.5f - gr.getFont().getWidth("Exit")/2 , y + 170 + 30 - gr.getFont().getHeight("Exit")/2);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, float delta) {
		
	}

}
