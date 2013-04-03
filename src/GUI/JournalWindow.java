package GUI;

import game.config.Config;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

class JournalWindow extends AbstractWindow {
	
	private ArrayList<String> objectives;
	private ArrayList<String> journal;
	private Graphics graphics;
	private float journalWidth;
	private int entryIndex = 1;
	private int currentRow = 0;

	public JournalWindow(GUI gui) {
		super(gui);
		this.objectives = new ArrayList<String>();
		
		addObjective("-Free the stranger");
		addObjective("-Find the stick");
		
		this.graphics = gui.getGraphics();
		this.journalWidth = Config.getScreenWidth()*0.25f - 20;
		this.journal = new ArrayList<String>();
		
		addJournalEntry("Woke up in forest. There was some light in the distance.");
		addJournalEntry("Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit.");
		addJournalEntry("I like trains.");
		}

	@Override
	public void render(GameContainer gc, Graphics g) {
		float x = getX(), y = getY(), width = getWidth(), height = getHeight();
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.8f));
		g.fillRoundRect(x, y, width, height, 5);
		
		g.setColor(Color.black);
			
		g.drawString("Journal", x + (width - g.getFont().getWidth("Journal")) * 0.25f , y + 20);
		for (int i = currentRow; i < Math.min(journal.size(), 11 + currentRow); i++){
			g.drawString(journal.get(i), x + 20, y + (i - currentRow + 3)*20);
		}
		
		g.setLineWidth(2);
		g.drawLine(x + width * 0.5f - 2, y + 10, x + width * 0.5f - 2, y + height - 10);
		g.drawLine(x + width * 0.5f + 2, y + 10, x + width * 0.5f + 2, y + height - 10);
		
		g.drawString("Objectives", x + (width - g.getFont().getWidth("Objectives")) * 0.75f , y + 20);
		for (int i = 0; i < objectives.size(); i++){
			g.drawString(objectives.get(i), x + width * 0.5f + 20 , y + (i + 3)*20);
		}
	}
	
	public void addObjective(String s) {
		objectives.add(s);
	}

	@Override
	public void update(GameContainer gc) {
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_DOWN)){
			scroll(1);
		}
		if (input.isKeyPressed(Input.KEY_UP)){
			scroll(-1);
		}
	}
	
	public void addJournalEntry(String str) {
		journal.add("Entry " + new Integer(entryIndex).toString());
		
		if (graphics.getFont().getWidth(str) < journalWidth){
			journal.add(str);
		}else {
			String[] tmpArray = str.split("\\s+");
			String tmpString;
			int j = 0;
			for (int i = 0; i <= tmpArray.length; i++) {
				tmpString = StringUtils.join(tmpArray, " ", j, i);
				if (graphics.getFont().getWidth(tmpString) > journalWidth) {
					journal.add(StringUtils.join(tmpArray, " ", j, i - 1));
					j = i - 1;
				}
			}
			journal.add(StringUtils.join(tmpArray, " ", j, tmpArray.length));
		}
		journal.add(" ");
		entryIndex++;
	} 
	
	public void mouseWheelMoved(int amount) {
		scroll((int) -Math.signum(amount));
	}
	
	private void scroll(int amount){
		currentRow = Math.max(0, Math.min(journal.size()-10, currentRow + amount));
	}

}
