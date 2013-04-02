package GUI;

import game.config.Config;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

class JournalWindow extends AbstractWindow {
	
	private ArrayList<String> objectives;
	private ArrayList<String> journal;
	private Graphics graphics;
	private float journalWidth;

	public JournalWindow(GUI gui) {
		super(gui);
		this.objectives = new ArrayList<String>();
		
		addObjective("-Free the stranger");
		addObjective("-Find the stick");
		
		this.graphics = gui.getGraphics();
		this.journalWidth = Config.getScreenWidth()*0.25f - 20;
		this.journal = new ArrayList<String>();
		
		addJournalEntry("Woke up in forest.");
		addJournalEntry("There was some light in the distance.");
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		float x = getX(), y = getY(), width = getWidth(), height = getHeight();
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		g.fillRoundRect(x, y, width, height, 5);
		
		g.setColor(Color.black);
			
		g.drawString("Journal", x + (width - g.getFont().getWidth("Journal")) * 0.25f , y + 20);
		for (int i = 0; i < journal.size(); i++) g.drawString(journal.get(i), x + 20, y + (i + 3)*20);
		
		g.setLineWidth(2);
		g.drawLine(x + width * 0.5f - 2, y + 10, x + width * 0.5f - 2, y + height - 10);
		g.drawLine(x + width * 0.5f + 2, y + 10, x + width * 0.5f + 2, y + height - 10);
			
		g.drawString("Objectives", x + (width - g.getFont().getWidth("Objectives")) * 0.75f , y + 20);
		for (int i = 0; i < objectives.size(); i++) g.drawString(objectives.get(i), x + width * 0.5f + 20 , y + (i + 3)*20);
	}
	
	public void addObjective(String s) {
		objectives.add(s);
	}

	@Override
	public void update(GameContainer gc) {
		
	}
	
	public void addJournalEntry(String str) {
		if (graphics.getFont().getWidth(str) < journalWidth) journal.add(str);
	} 

}
