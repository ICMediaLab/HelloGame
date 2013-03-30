package GUI;

import game.config.Config;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class JournalWindow extends Window {
	
	private ArrayList<String> objectives;

	public JournalWindow() {
		this.x = Config.getScreenWidth() * 0.25f;
		this.y = Config.getScreenHeight() * 0.25f;
		this.width = Config.getScreenWidth() * 0.5f;
		this.height = Config.getScreenHeight() * 0.5f;
		this.objectives = new ArrayList<String>();
		
		addObjective("-Kill the princess");
		addObjective("-Rape the dragon");
		addObjective("-???");
		addObjective("-Profit!");
	}

	@Override
	public void render(Graphics gr) {
		gr.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		gr.fillRoundRect(x, y, width, height, 5);
		
		gr.setColor(Color.black);
			
		gr.drawString("Journal", x + (width - gr.getFont().getWidth("Journal")) * 0.25f , y + 20);
		gr.drawString("Woke up in forest.", x + 20 , y + 60);
		
		gr.drawLine(x + width * 0.5f - 2, y + 10, x + width * 0.5f - 2, y + height - 10);
		gr.drawLine(x + width * 0.5f + 2, y + 10, x + width * 0.5f + 2, y + height - 10);
			
		gr.drawString("Objectives", x + (width - gr.getFont().getWidth("Objectives")) * 0.75f , y + 20);
		for (int i = 0; i < objectives.size(); i++) gr.drawString(objectives.get(i), x + width * 0.5f + 20 , y + (i + 3)*20);
	}
	
	public void addObjective(String s) {
		objectives.add(s);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, float delta) {
		
	}

}
