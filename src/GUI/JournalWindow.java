package GUI;

import game.config.Config;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class Journal extends Window {

	public Journal() {
		this.x = Config.getScreenWidth() * 0.25f;
		this.y = Config.getScreenHeight() * 0.25f;
		this.width = Config.getScreenWidth() * 0.5f;
		this.height = Config.getScreenHeight() * 0.5f;
	}

	@Override
	public void render(Graphics gr) {
		if (active) {
			gr.setColor(Color.lightGray);
			gr.fillRoundRect(x, y, width, height, 5);
			
			gr.setColor(Color.black);
			
			gr.drawString("Journal", x + (width - gr.getFont().getWidth("Journal")) * 0.25f , y + 20);
			gr.drawString("Woke up in forest.", x + 20 , y + 60);
			
			gr.drawLine(x + width * 0.5f, y + 10, x + width * 0.5f, y + height - 10);
			
			gr.drawString("Objectives", x + (width - gr.getFont().getWidth("Objectives")) * 0.75f , y + 20);
			gr.drawString("-Kill the princess", x + width * 0.5f + 20 , y + 40);
			gr.drawString("-Rape the dragon", x + width * 0.5f + 20 , y + 60);
			gr.drawString("-???", x + width * 0.5f + 20 , y + 80);
			gr.drawString("-Profit!", x + width * 0.5f + 20 , y + 100);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, float delta) {
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_J)) active = active ? false : true;
	}

}
