package GUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class GUI {

	private final EmptyWindow emptyWindow = new EmptyWindow();
	private Window activeWindow = emptyWindow;
	private final JournalWindow journalWindow = new JournalWindow();
	private final MapWindow mapWindow = new MapWindow();
	private final AbilitiesWindow abilitiesWindow = new AbilitiesWindow();
	private final MenuWindow menuWindow = new MenuWindow();

	public GUI() {

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
		else if (input.isKeyPressed(Input.KEY_I)) setActiveWindow(abilitiesWindow);
		else if (input.isKeyPressed(Input.KEY_O)) setActiveWindow(menuWindow);
	}

	public JournalWindow getJournal() {
		return journalWindow;
	}

	public MapWindow getMapWindow() {
		return mapWindow;
	}

}
