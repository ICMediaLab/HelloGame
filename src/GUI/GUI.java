package GUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class GUI {
	
	private Window activeWindow;
	private JournalWindow journalWindow;
	private EmptyWindow emptyWindow;
	private MapWindow mapWindow;

	public GUI() {
		this.activeWindow = new EmptyWindow();
		this.journalWindow = new JournalWindow();
		this.emptyWindow = new EmptyWindow();
		this.mapWindow = new MapWindow();
	}
	
	public Window setActiveWindow(Window window) {
		if (window == activeWindow) {
			activeWindow = emptyWindow;
		} else {
			activeWindow = window;
		}
		return window;
	}

	public Window getActiveWindow() {
		return activeWindow;
	}
	
	public void render(Graphics gr) {
		activeWindow.render(gr);
	}
	public void update(GameContainer gc, StateBasedGame sbg, float delta) {
		activeWindow.update(gc, sbg, delta);
		Input input = gc.getInput();
		
		     if (input.isKeyPressed(Input.KEY_J)) setActiveWindow(journalWindow);
		else if (input.isKeyPressed(Input.KEY_M)) setActiveWindow(mapWindow);
	}

	public JournalWindow getJournal() {
		return journalWindow;
	}

	public MapWindow getMapWindow() {
		return mapWindow;
	}

}
