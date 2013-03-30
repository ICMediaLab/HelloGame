package GUI;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

class JournalWindow extends AbstractWindow {
	
	private ArrayList<String> objectives;

	public JournalWindow() {
		super();
		this.objectives = new ArrayList<String>();
		
		addObjective("-Kill the princess");
		addObjective("-Rape the dragon"); //NSFL
		addObjective("-???");
		addObjective("-Profit!");
	}

	@Override
	public void render(Graphics gr) {
		float x = getX(), y = getY(), width = getWidth(), height = getHeight();
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
