package GUI;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class GUI {

	private Window activeWindow = null;
	private final JournalWindow journalWindow = new JournalWindow();
	private final MapWindow mapWindow = new MapWindow();
	private final AbilitiesWindow abilitiesWindow = new AbilitiesWindow();
	private final MenuWindow menuWindow = new MenuWindow();

	public GUI() {

	}
	
	public void setActiveWindow(Window window) {
		if (window == activeWindow) {
			activeWindow = null;  
		} else {
			activeWindow = window;
		}
	}

	public Window getActiveWindow() {
		return activeWindow;
	}
	
	public void closeWindow(){
		setActiveWindow(null);
	}
	
	public boolean anyWindowOpen() {
		return getActiveWindow() != null;
	}
	
	public void render(Graphics gr) {
		if(anyWindowOpen()){
			activeWindow.render(gr);
		}
	}
	public void update(GameContainer gc, StateBasedGame sbg, float delta) {
		if(anyWindowOpen()){
			activeWindow.update(gc, sbg, delta);
		}
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_J)){
			setActiveWindow(journalWindow);
		}else if (input.isKeyPressed(Input.KEY_M)){
			setActiveWindow(mapWindow);
		}else if (input.isKeyPressed(Input.KEY_I)){
			setActiveWindow(abilitiesWindow);
		}else if (input.isKeyPressed(Input.KEY_O)){
			setActiveWindow(menuWindow);
		}
	}

	public JournalWindow getJournal() {
		return journalWindow;
	}

	public MapWindow getMapWindow() {
		return mapWindow;
	}

}
